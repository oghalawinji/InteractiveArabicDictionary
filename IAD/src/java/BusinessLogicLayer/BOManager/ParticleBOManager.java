/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.ParticleBO;
import BusinessLogicLayer.BusinessObjects.SemanticParticleBO;
import BusinessLogicLayer.BusinessObjects.UpdatedParticleBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.CompareWordsBO;
import BusinessLogicLayer.Util.FilterDiacritics;
import BusinessLogicLayer.Util.WordStatus;
import PersistenceLayer.Derivedparticle;
import PersistenceLayer.Particletype;
import PersistenceLayer.Rawword;
import PersistenceLayer.Root;
import PersistenceLayer.Semanticparticle;
import PersistenceLayer.Suggestion;
import PersistenceLayer.User;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riad
 */
public class ParticleBOManager {

    private WordBOManager newWordBOManager;
    private RootManager newRootManager;
    private ParticleTypeManager newParticleTypeManager;

    public ParticleBOManager() {
        newWordBOManager = new WordBOManager();
        newRootManager = new RootManager();
        newParticleTypeManager = new ParticleTypeManager();
    }

    public static boolean hasNewValues(int derivedParticleId) throws RawNotFoundException {
        Derivedparticle oldParticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(derivedParticleId);
        return hasNewValues(oldParticle);
    }

    public static boolean hasNewValues(Derivedparticle oldParticle) {
        if (oldParticle.getInfoStatus().equals("U")) {
            Suggestion updateSuggestion = oldParticle.getSuggestion();
            if (updateSuggestion != null && !(updateSuggestion.getAffirmStatus().equals("A")) && updateSuggestion.getInfoStatus().equals("U")) {
                return true;
            }
        }
        return false;
    }

    public static ParticleBO getParticleBO(Integer derivedParticleId) {
        return getParticleBO(derivedParticleId, SearchProperties.detailedSearchOptions);
    }

    public static ParticleBO getParticleBO(Integer derivedParticleId, SearchProperties options) {
        try {
            return getParticleBO(BLUtil.daoFactory.getDerivedparticleDAO().getById(derivedParticleId), options);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(ParticleBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static ParticleBO getParticleBO(Integer derivedParticleId, Integer semanticWordId) {
        return getParticleBO(derivedParticleId, semanticWordId, SearchProperties.detailedSearchOptions);
    }

    public static ParticleBO getParticleBO(Integer derivedParticleId, Integer semanticWordId, SearchProperties options) {
        try {
            return getParticleBO(BLUtil.daoFactory.getDerivedparticleDAO().getById(derivedParticleId), semanticWordId, options);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(ParticleBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static ParticleBO getParticleBO(Derivedparticle derivedParticle) {
        return getParticleBO(derivedParticle, SearchProperties.detailedSearchOptions);
    }

    public static ParticleBO getParticleBO(Derivedparticle derivedParticle, SearchProperties options) {
        return getParticleBO(derivedParticle, -1, options);
    }

    public static ParticleBO getParticleBO(Derivedparticle derivedParticle, Integer semanticWordId) {
        return getParticleBO(derivedParticle, semanticWordId, SearchProperties.detailedSearchOptions);
    }

    public static ParticleBO getParticleBO(Derivedparticle derivedParticle, Integer semanticWordId, SearchProperties options) {

        if (derivedParticle.getInfoStatus().equals("U") && hasNewValues(derivedParticle)) {
            UpdatedParticleBO particle = new UpdatedParticleBO();
            try {
                particle.setDerivedParticleId(derivedParticle.getIdentity());
                particle.setVocalizedParticle(derivedParticle.getVocalizedParticle());
                particle.setRoot(derivedParticle.getRoot().getRoot());
                //particleBO.setPhonetic(derivedParticle.getPhonetic());
                particle.setParticletype(derivedParticle.getParticletype().getParticleType());
                particle.setStatus(derivedParticle.getInfoStatus());

                Derivedparticle newParticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(derivedParticle.getSuggestion().getUpdateId());

                particle.setNewDerivedParticleId(newParticle.getIdentity());
                particle.setNewParticletype(newParticle.getParticletype().getParticleType());
                particle.setNewRoot(newParticle.getRoot().getRoot());

                //Retrieve semantic particle:
                if (options.FindSemanticInformations) {
                    List<SemanticParticleBO> semanticCases = new ArrayList<SemanticParticleBO>();

                    Set<Semanticparticle> semParticleList = derivedParticle.getSemanticparticles();
                    for (Iterator iter2 = semParticleList.iterator(); iter2.hasNext();) {
                        //each derived particle:
                        Semanticparticle semParticle = (Semanticparticle) iter2.next();
                        SemanticParticleBO semParticleBO = SemanticParticleBOManager.getSemanticParticleBO(semParticle, options);
                        int pId = semParticleBO.getSemanticParticleId();
                        if (semanticWordId == pId || semanticWordId < 0) {
                            semanticCases.add(semParticleBO);
                        }
                    }

                    Collections.sort(semanticCases, new CompareWordsBO());
                    particle.setSemanticCases(semanticCases);
                }
            } catch (RawNotFoundException ex) {
                Logger.getLogger(ParticleBOManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            return particle;
        } else {

            ParticleBO particle = new ParticleBO();

            particle.setDerivedParticleId(derivedParticle.getIdentity());
            particle.setVocalizedParticle(derivedParticle.getVocalizedParticle());
            particle.setRoot(derivedParticle.getRoot().getRoot());
            //particleBO.setPhonetic(derivedParticle.getPhonetic());
            particle.setParticletype(derivedParticle.getParticletype().getParticleType());
            particle.setStatus(derivedParticle.getInfoStatus());

            //Retrieve semantic particle:
            if (options.FindSemanticInformations) {
                List<SemanticParticleBO> semanticCases = new ArrayList<SemanticParticleBO>();

                Set<Semanticparticle> semParticleList = derivedParticle.getSemanticparticles();
                for (Iterator iter2 = semParticleList.iterator(); iter2.hasNext();) {
                    //each derived particle:
                    Semanticparticle semParticle = (Semanticparticle) iter2.next();
                    SemanticParticleBO semParticleBO = SemanticParticleBOManager.getSemanticParticleBO(semParticle, options);
                    int pId = semParticleBO.getSemanticParticleId();
                    if (semanticWordId == pId || semanticWordId < 0) {
                        semanticCases.add(semParticleBO);
                    }
                }

                Collections.sort(semanticCases, new CompareWordsBO());
                particle.setSemanticCases(semanticCases);
            }
            return particle;
        }
    }

    public int suggestAdding(ParticleBO newParticleBO) throws RawNotFoundException, EntryExistedException {
        String rawWord = FilterDiacritics.execute(newParticleBO.getVocalizedParticle());
        Map restrictions = BOManagerUtil.getAddRestrictions();

        int newRawWordId = newWordBOManager.suggestAdding(rawWord);
        restrictions.put("eq_rawword.rawWordId", newRawWordId);
        Rawword newRawword = BLUtil.daoFactory.getRawwordDAO().getById(newRawWordId);

        int newRootId = newRootManager.suggestAdding(newParticleBO.getRoot());
        Root newRoot = BLUtil.daoFactory.getRootDAO().getById(newRootId);
        restrictions.put("eq_root.rootId", newRootId);

        int newParticleTypeId = newParticleTypeManager.suggestAdding(newParticleBO.getParticletype());
        Particletype newParticleType = BLUtil.daoFactory.getParticletypeDAO().getById(newParticleTypeId);
        restrictions.put("eq_particletype.particleTypeId", newParticleTypeId);

        Derivedparticle derivedParticle = new Derivedparticle();
        derivedParticle.setRawword(newRawword);
        derivedParticle.setRoot(newRoot);
        derivedParticle.setParticletype(newParticleType);
        derivedParticle.setVocalizedParticle(newParticleBO.getVocalizedParticle());
        //derivedParticle.setPronunciation (new Pronunciation ());

        derivedParticle.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        derivedParticle.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        derivedParticle.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        int id = BLUtil.daoFactory.getDerivedparticleDAO().insertWithCheck(derivedParticle, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES,
                restrictions, "الأداة موجود مسبقاً!");
        return id;
    }

    public void suggestDeleting(Integer derivedParticleId) throws RawNotFoundException {
        Derivedparticle derivedparticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(derivedParticleId);
        derivedparticle.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        derivedparticle.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        derivedparticle.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());
        BLUtil.daoFactory.getDerivedparticleDAO().update(derivedparticle);
    }

    public void suggestUpdating(Integer derivedParticleId, ParticleBO newParticleBO) throws RawNotFoundException {
        int tempParticleId = this.addTempParticleBO(newParticleBO);
        Derivedparticle derivedparticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(derivedParticleId);

        derivedparticle.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
        derivedparticle.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
        derivedparticle.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(tempParticleId));
        BLUtil.daoFactory.getDerivedparticleDAO().update(derivedparticle);
    }

    private int addTempParticleBO(ParticleBO newParticleBO) throws RawNotFoundException {
        String rawWord = FilterDiacritics.execute(newParticleBO.getVocalizedParticle());
        Map restrictions = BOManagerUtil.getAddRestrictions();

        int newRawWordId = newWordBOManager.suggestAdding(rawWord);
        restrictions.put("eq_rawword.rawWordId", newRawWordId);
        Rawword newRawword = BLUtil.daoFactory.getRawwordDAO().getById(newRawWordId);

        int newRootId = newRootManager.suggestAdding(newParticleBO.getRoot());
        Root newRoot = BLUtil.daoFactory.getRootDAO().getById(newRootId);
        restrictions.put("eq_root.rootId", newRootId);

        int newParticleTypeId = newParticleTypeManager.suggestAdding(newParticleBO.getParticletype());
        Particletype newParticleType = BLUtil.daoFactory.getParticletypeDAO().getById(newParticleTypeId);
        restrictions.put("eq_particletype.particleTypeId", newParticleTypeId);

        Derivedparticle derivedParticle = new Derivedparticle();
        derivedParticle.setRawword(newRawword);
        derivedParticle.setRoot(newRoot);
        derivedParticle.setParticletype(newParticleType);
        derivedParticle.setVocalizedParticle(newParticleBO.getVocalizedParticle());
        //derivedParticle.setPronunciation (new Pronunciation ());

        derivedParticle.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
        derivedParticle.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());
        int id = BLUtil.daoFactory.getDerivedparticleDAO().insertWithCheck(derivedParticle, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES,
                restrictions);
        return id;
    }

    public static void setNeedCheck(int particleId) throws RawNotFoundException {
        Derivedparticle newParticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(particleId);

        newParticle.setChechStatus(BOManagerUtil.NEEDS_CHECK_STATUS.getCheckStatus());

        BLUtil.daoFactory.getDerivedparticleDAO().update(newParticle);
    }

    public static List<ParticleBO> getNeedCheck() throws RawNotFoundException {
        List<Derivedparticle> particles = BLUtil.daoFactory.getDerivedparticleDAO().getNeedCheck();
        List<ParticleBO> needCheckParticles = new ArrayList<ParticleBO>();
        for (Derivedparticle particle : particles) {
            needCheckParticles.add(ParticleBOManager.getParticleBO(particle, new SearchProperties()));
        }
        return needCheckParticles;
    }

    public static void affirmAdding(int derivedParticleId) throws RawNotFoundException {
        Derivedparticle newDerivedparticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(derivedParticleId);
        newDerivedparticle.setInfoStatus("S");
//        newDerivedparticle.getRawword ().setInfoStatus( "S" );
        BLUtil.daoFactory.getDerivedparticleDAO().update(newDerivedparticle);
    }

    public static boolean affirmAddingAU(int derivedParticleId, ParticleBO updatedParticle) throws RawNotFoundException {
        Derivedparticle newDerivedparticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(derivedParticleId);
        String rawWord = FilterDiacritics.execute(updatedParticle.getVocalizedParticle());
        Map restrictions = BOManagerUtil.getAddRestrictions();

        int newRawWordId = WordBOManager.suggestAdding(rawWord);
        restrictions.put("eq_rawword.rawWordId", newRawWordId);
        Rawword newRawword = BLUtil.daoFactory.getRawwordDAO().getById(newRawWordId);

        int newRootId = RootManager.suggestAdding(updatedParticle.getRoot());
        Root newRoot = BLUtil.daoFactory.getRootDAO().getById(newRootId);
        restrictions.put("eq_root.rootId", newRootId);

        int newParticleTypeId = ParticleTypeManager.suggestAdding(updatedParticle.getParticletype());
        Particletype newParticleType = BLUtil.daoFactory.getParticletypeDAO().getById(newParticleTypeId);
        restrictions.put("eq_particletype.particleTypeId", newParticleTypeId);

        if (BLUtil.daoFactory.getDerivedparticleDAO().getByExample(new Derivedparticle(), BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions).size() > 0) {
            return false;
        } else {
            newDerivedparticle.setRawword(newRawword);
            newDerivedparticle.setRoot(newRoot);
            newDerivedparticle.setParticletype(newParticleType);
            newDerivedparticle.setVocalizedParticle(updatedParticle.getVocalizedParticle());
            //derivedParticle.setPronunciation (new Pronunciation ());
            newDerivedparticle.setInfoStatus(BOManagerUtil.CONFIRM_STATUS.getInfoStatus());

            BOManagerUtil.AFFIRM_SUGGESTION(newDerivedparticle.getSuggestion());
            BLUtil.daoFactory.getDerivedparticleDAO().update(newDerivedparticle);
            return true;
        }
    }

    public static void rejectAdding(int particleId) throws RawNotFoundException {
        Derivedparticle newDerivedparticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(particleId);
        for (Semanticparticle semanticparticle : newDerivedparticle.getSemanticparticles()) {
            SemanticParticleBOManager.rejectAdding(semanticparticle.getIdentity());
        }
        BOManagerUtil.REJECT_SUGGESTION(newDerivedparticle.getSuggestion());
        newDerivedparticle.setInfoStatus("R");
        BLUtil.daoFactory.getDerivedparticleDAO().update(newDerivedparticle);

    }

    public static void clearCheck(int derivedParticleId) throws RawNotFoundException {
        Derivedparticle newDerivedparticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(derivedParticleId);
        newDerivedparticle.setChechStatus(0);
        BLUtil.daoFactory.getDerivedparticleDAO().update(newDerivedparticle);
    }

    public static void affirmUpdating(int derivedParticleId) throws RawNotFoundException {
        Derivedparticle oldParticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(derivedParticleId);
        Suggestion updateSuggestion = oldParticle.getSuggestion();
        // get update particle id from old particle suggestion.
        Derivedparticle newParticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(updateSuggestion.getUpdateId());
        Derivedparticle copyOfNewParticle = new Derivedparticle(null, newParticle.getRoot(), newParticle.getRawword(),
                newParticle.getParticletype(), newParticle.getVocalizedParticle());
        // update particle will store old particle values before updates.
        newParticle.setRawword(oldParticle.getRawword());
        newParticle.setRoot(oldParticle.getRoot());
        newParticle.setParticletype(oldParticle.getParticletype());
        newParticle.setVocalizedParticle(oldParticle.getVocalizedParticle());
        newParticle.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
        // assign new values to the original particle.
        oldParticle.setRawword(copyOfNewParticle.getRawword());
        oldParticle.setRoot(copyOfNewParticle.getRoot());
        oldParticle.setParticletype(copyOfNewParticle.getParticletype());
        oldParticle.setVocalizedParticle(copyOfNewParticle.getVocalizedParticle());
        oldParticle.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);

        BOManagerUtil.AFFIRM_SUGGESTION(oldParticle.getSuggestion());

        BLUtil.daoFactory.getDerivedparticleDAO().update(newParticle);
        BLUtil.daoFactory.getDerivedparticleDAO().update(oldParticle);
    }

    private static void affirmUpdatingAfterAltering(Derivedparticle oldParticle) throws RawNotFoundException {
        Suggestion updateSuggestion = oldParticle.getSuggestion();
        // get update particle id from old particle suggestion.
        Derivedparticle newParticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(updateSuggestion.getUpdateId());
        Derivedparticle copyOfNewParticle = new Derivedparticle(null, newParticle.getRoot(), newParticle.getRawword(),
                newParticle.getParticletype(), newParticle.getVocalizedParticle());
        // update particle will store old particle values before updates.
        newParticle.setRawword(oldParticle.getRawword());
        newParticle.setRoot(oldParticle.getRoot());
        newParticle.setParticletype(oldParticle.getParticletype());
        newParticle.setVocalizedParticle(oldParticle.getVocalizedParticle());
        newParticle.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
        // assign new values to the original particle.
        oldParticle.setRawword(copyOfNewParticle.getRawword());
        oldParticle.setRoot(copyOfNewParticle.getRoot());
        oldParticle.setParticletype(copyOfNewParticle.getParticletype());
        oldParticle.setVocalizedParticle(copyOfNewParticle.getVocalizedParticle());
        oldParticle.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);

        BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldParticle.getSuggestion());

        BLUtil.daoFactory.getDerivedparticleDAO().update(newParticle);
        BLUtil.daoFactory.getDerivedparticleDAO().update(oldParticle);
    }

    public static void rejectUpdating(int derivedParticleId) throws RawNotFoundException {
        Derivedparticle originalDerivedparticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(derivedParticleId);
        Derivedparticle updatedDerivedparticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(originalDerivedparticle.getSuggestion().getUpdateId());

        originalDerivedparticle.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        BOManagerUtil.REJECT_SUGGESTION(originalDerivedparticle.getSuggestion());
        BLUtil.daoFactory.getDerivedparticleDAO().update(originalDerivedparticle);

        updatedDerivedparticle.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
        BLUtil.daoFactory.getDerivedparticleDAO().update(updatedDerivedparticle);
    }

    public static boolean affirmUpdatingAU(int derivedParticleId, ParticleBO updateParticle) throws RawNotFoundException {
        Derivedparticle originalDerivedparticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(derivedParticleId);
        Derivedparticle updatedDerivedparticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(originalDerivedparticle.getSuggestion().getUpdateId());

        String rawWord = FilterDiacritics.execute(updateParticle.getVocalizedParticle());
        Map restrictions = new HashMap();
        int newRawWordId = WordBOManager.suggestAdding(rawWord);
        restrictions.put("eq_rawword.rawWordId", newRawWordId);
        Rawword newRawword = BLUtil.daoFactory.getRawwordDAO().getById(newRawWordId);

        int newRootId = RootManager.suggestAdding(updateParticle.getRoot());
        Root newRoot = BLUtil.daoFactory.getRootDAO().getById(newRootId);
        restrictions.put("eq_root.rootId", newRootId);

        int newParticleTypeId = ParticleTypeManager.suggestAdding(updateParticle.getParticletype());
        Particletype newParticleType = BLUtil.daoFactory.getParticletypeDAO().getById(newParticleTypeId);
        restrictions.put("eq_particletype.particleTypeId", newParticleTypeId);

        // if the updated particle  already exists in the database then return false and the expert can decide what to do.
        if (BLUtil.daoFactory.getDerivedparticleDAO().getByExample(new Derivedparticle(), BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions).size() > 0) {
            //System.out.println( "the particle already exists in the database....");
            return false;
        } else {
            updatedDerivedparticle.setRawword(newRawword);
            updatedDerivedparticle.setRoot(newRoot);
            updatedDerivedparticle.setParticletype(newParticleType);
            updatedDerivedparticle.setVocalizedParticle(updateParticle.getVocalizedParticle());
            //derivedParticle.setPronunciation (new Pronunciation ());

            BLUtil.daoFactory.getDerivedparticleDAO().update(updatedDerivedparticle);
            affirmUpdatingAfterAltering(originalDerivedparticle);
            return true;
        }
    }

    public static void affirmDeleting(int derivedParticleId) throws RawNotFoundException {
        Derivedparticle newDerivedParticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(derivedParticleId);
        for (Semanticparticle semanticparticle : newDerivedParticle.getSemanticparticles()) {
            SemanticParticleBOManager.affirmDeleting(semanticparticle.getIdentity());
        }
        newDerivedParticle.setInfoStatus(WordStatus.NEED_DELETING);
        newDerivedParticle.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
        BOManagerUtil.AFFIRM_SUGGESTION(newDerivedParticle.getSuggestion());
        BLUtil.daoFactory.getDerivedparticleDAO().update(newDerivedParticle);
    }

    public static void rejectDeleting(int derivedParticleId) throws RawNotFoundException {
        Derivedparticle newDerivedparticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(derivedParticleId);
        BOManagerUtil.REJECT_SUGGESTION(newDerivedparticle.getSuggestion());
        newDerivedparticle.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        BLUtil.daoFactory.getDerivedparticleDAO().update(newDerivedparticle);
    }

    public int getCheckedParticleWeight(int particleId) throws RawNotFoundException {
        int weight = 0;
        Derivedparticle newDerivedparticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(particleId);
        if (!newDerivedparticle.getInfoStatus().equals("S")) {
            weight++;
        }
        Set<Semanticparticle> semParticles = newDerivedparticle.getSemanticparticles();
        for (Semanticparticle semParticle : semParticles) {
            if (!semParticle.getInfoStatus().equals("S")) {
                weight++;
            }
        }
        return weight;
    }

    public static void main(String[] arg) {
        try {
            User currUser = BLUtil.daoFactory.getUserDAO().getById(5);
            BOManagerUtil.setCurrentUser(currUser);
            /**
             * *
             * test needCheck()
             */
            /*List<ParticleBO> needCheckParticles = getNeedCheck();
             for ( ParticleBO particle : needCheckParticles )
             {
             System.out.print( "[" + particle.getDerivedParticleId() + "] , " );
             }*/
            /**
             * *
             * test affirmAddingAU()
             */
            /*ParticleBO newParticleBO = new ParticleBO();
             newParticleBO.setVocalizedParticle( "testAAU" );
             newParticleBO.setRoot( "testAAU" );
             newParticleBO.setParticletype( "testAAU" );
             affirmAddingAU( 8 , newParticleBO );*/
            /**
             * test affirmUpdating()
             */
            //affirmUpdating( 5 );
            /**
             * test rejectUpdating()
             */
            //rejectUpdating( 3 );
            /**
             * test affirmUpdatingAU()
             */
            /*ParticleBO newParticleBO = new ParticleBO();
             newParticleBO.setVocalizedParticle( "testUAU" );
             newParticleBO.setRoot( "testUAU" );
             newParticleBO.setParticletype( "testUAU" );
             affirmUpdatingAU( 1 , newParticleBO );*/
            rejectDeleting(4);

        } catch (Exception ex) {
            Logger.getLogger(ParticleBOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

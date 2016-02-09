/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.CommonMistakeBO;
import BusinessLogicLayer.BusinessObjects.ExampleBO;
import BusinessLogicLayer.BusinessObjects.LinguisticBenefitBO;
import BusinessLogicLayer.BusinessObjects.MeaningBO;
import BusinessLogicLayer.BusinessObjects.SemanticParticleBO;
import BusinessLogicLayer.BusinessObjects.UpdatedSemanticParticleBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.SemanticparticleJPADAO;
import PersistenceLayer.Derivedparticle;
import PersistenceLayer.Semanticentry;
import PersistenceLayer.Semanticparticle;
import PersistenceLayer.Suggestion;
import PersistenceLayer.User;
import Util.RawNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riad
 */
public class SemanticParticleBOManager extends SemanticEntryBOManager {

    private SemanticEntryBOManager newSemanticEntryBOManager;
    private MeaningBOManager newMeaningBOManager;
    private static SemanticparticleJPADAO SEMANTIC_PARTICLE_DAO = BLUtil.daoFactory.getSemanticparticleDAO();

    public SemanticParticleBOManager() {
        newSemanticEntryBOManager = new SemanticEntryBOManager();
        newMeaningBOManager = new MeaningBOManager();
    }

    public static boolean hasNewValues(Semanticparticle newSemanticParticle) {
        Suggestion newSuggestion = newSemanticParticle.getSuggestion();
        if (newSuggestion != null && newSuggestion.getInfoStatus().equals("U") && !(newSuggestion.getAffirmStatus().equals("A"))) {
            return true;
        }
        return false;
    }

    public static boolean hasNewValues(int semanticParticleId) throws RawNotFoundException {
        Semanticparticle newSemanticParticle = BLUtil.daoFactory.getSemanticparticleDAO().getById(semanticParticleId);
        return hasNewValues(newSemanticParticle);
    }

    /// <editor-fold defaultstate="collapsed" desc="getters....">
    public static SemanticParticleBO getSemanticParticleBO(Semanticparticle semParticle) {
        return getSemanticParticleBO(semParticle, SearchProperties.detailedSearchOptions);
    }

    public static SemanticParticleBO getSemanticParticleBO(Integer semanticWordId, SearchProperties options) {
        try {
            Semanticparticle semParticle = SEMANTIC_PARTICLE_DAO.getById(semanticWordId);
            return getSemanticParticleBO(semParticle, options);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(SemanticParticleBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static SemanticParticleBO getSemanticParticleBO(Semanticparticle semParticle, SearchProperties options) {

        if (semParticle.getInfoStatus().equals("U")) {
            UpdatedSemanticParticleBO semanticParticleBO = new UpdatedSemanticParticleBO();
            //if there is no suggestion then update value is containted in semanticentry update.
            if (!hasNewValues(semParticle)) {
                Semanticentry newSemanticentry = semParticle.getSemanticentry();
                Suggestion newSuggestion = newSemanticentry.getSuggestion();
                if (newSuggestion != null && newSuggestion.getInfoStatus().equals("U") && !(newSuggestion.getAffirmStatus().equals("A"))) {
                    try {
                        Semanticentry tempSemanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(newSuggestion.getUpdateId());
                        semanticParticleBO.setNewDifficultydegree(tempSemanticentry.getDifficultydegree().getDifficultyDegree());
                        semanticParticleBO.setNewEpoch(tempSemanticentry.getEpoch().getEpoch());
                        semanticParticleBO.setNewRegion(tempSemanticentry.getRegion().getRegion());
                        semanticParticleBO.setNewSemanticscop(tempSemanticentry.getSemanticscop().getSemanticScop());
                        semanticParticleBO.setNewSpecialization(tempSemanticentry.getSpecialization().getSpecialization());
                        semanticParticleBO.setNewSpreadingdegree(tempSemanticentry.getSpreadingdegree().getSpreadingDegree());
                    } catch (RawNotFoundException ex) {
                        Logger.getLogger(SemanticParticleBOManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } // there is a suggestion then update values is contained in a temp semantic entry.
            else {
                try {
                    Suggestion newSuggestion = semParticle.getSuggestion();
                    Semanticentry tempSemanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(newSuggestion.getUpdateId());
                    semanticParticleBO.setNewDifficultydegree(tempSemanticentry.getDifficultydegree().getDifficultyDegree());
                    semanticParticleBO.setNewEpoch(tempSemanticentry.getEpoch().getEpoch());
                    semanticParticleBO.setNewRegion(tempSemanticentry.getRegion().getRegion());
                    semanticParticleBO.setNewSemanticscop(tempSemanticentry.getSemanticscop().getSemanticScop());
                    semanticParticleBO.setNewSpecialization(tempSemanticentry.getSpecialization().getSpecialization());
                    semanticParticleBO.setNewSpreadingdegree(tempSemanticentry.getSpreadingdegree().getSpreadingDegree());
                } catch (RawNotFoundException ex) {
                    Logger.getLogger(SemanticParticleBOManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //Save semantic Particle ID
            semanticParticleBO.setSemanticParticleId(semParticle.getIdentity());

            //Retrieve and Save semantic entry info
            Semanticentry semEntry = semParticle.getSemanticentry();
            semanticParticleBO.setSemanticEntryId(semEntry.getIdentity());
            semanticParticleBO.setDifficultydegree(semEntry.getDifficultydegree().getDifficultyDegree());
            semanticParticleBO.setEpoch(semEntry.getEpoch().getEpoch());
            semanticParticleBO.setRegion(semEntry.getRegion().getRegion());
            semanticParticleBO.setSemanticscop(semEntry.getSemanticscop().getSemanticScop());
            semanticParticleBO.setSpecialization(semEntry.getSpecialization().getSpecialization());
            semanticParticleBO.setSpreadingdegree(semEntry.getSpreadingdegree().getSpreadingDegree());
            semanticParticleBO.setStatus(semParticle.getInfoStatus());
            semanticParticleBO.setChecked(semParticle.getChechStatus() == 1 ? true : false);

            //Semantic Entry Related Infos:
            if (options.FindMeanings) {
                semanticParticleBO.setMeanings(MeaningBOManager.getMeaningsOfSemanticEntry(semEntry));
            }
            if (options.FindCommonMistakes) {
                semanticParticleBO.setCommonMistakes(CommonMistakeBOManager.getCommonMistakesOfSemanticEntry(semEntry));
            }
            if (options.FindLinguisticBenifits) {
                semanticParticleBO.setLinguisticBenefits(LinguisticBenefitBOManager.getLinguisticBenefitsOfSemanticEntry(semEntry));
            }
            if (options.FindExamples) {
                semanticParticleBO.setExamples(ExampleBOManager.getExamplesOfSemanticEntry(semEntry));
            }
            if (options.FindRelatedIdiom) {
                semanticParticleBO.setRelatedidioms(IdiomBOManager.getRelatedIdiom(semEntry));
            }

            //multimedia:
            if (options.FindImages) {
                semanticParticleBO.setImages(ImageManager.getImage(semEntry));
            }
            if (options.FindSounds) {
                semanticParticleBO.setSounds(SoundManager.getSound(semEntry));
            }
            if (options.FindVideos) {
                semanticParticleBO.setVideos(VideoManager.getVideo(semEntry));
            }

            return semanticParticleBO;
        }

        SemanticParticleBO semanticParticleBO = new SemanticParticleBO();

        //Save semantic Particle ID
        semanticParticleBO.setSemanticParticleId(semParticle.getIdentity());

        //Retrieve and Save semantic entry info
        Semanticentry semEntry = semParticle.getSemanticentry();
        semanticParticleBO.setSemanticEntryId(semEntry.getIdentity());
        semanticParticleBO.setDifficultydegree(semEntry.getDifficultydegree().getDifficultyDegree());
        semanticParticleBO.setEpoch(semEntry.getEpoch().getEpoch());
        semanticParticleBO.setRegion(semEntry.getRegion().getRegion());
        semanticParticleBO.setSemanticscop(semEntry.getSemanticscop().getSemanticScop());
        semanticParticleBO.setSpecialization(semEntry.getSpecialization().getSpecialization());
        semanticParticleBO.setSpreadingdegree(semEntry.getSpreadingdegree().getSpreadingDegree());
        semanticParticleBO.setStatus(semParticle.getInfoStatus());
        semanticParticleBO.setChecked(semParticle.getChechStatus() == 1 ? true : false);

        //Semantic Entry Related Infos:
        if (options.FindMeanings) {
            semanticParticleBO.setMeanings(MeaningBOManager.getMeaningsOfSemanticEntry(semEntry));
        }
        if (options.FindCommonMistakes) {
            semanticParticleBO.setCommonMistakes(CommonMistakeBOManager.getCommonMistakesOfSemanticEntry(semEntry));
        }
        if (options.FindLinguisticBenifits) {
            semanticParticleBO.setLinguisticBenefits(LinguisticBenefitBOManager.getLinguisticBenefitsOfSemanticEntry(semEntry));
        }
        if (options.FindExamples) {
            semanticParticleBO.setExamples(ExampleBOManager.getExamplesOfSemanticEntry(semEntry));
        }
        if (options.FindRelatedIdiom) {
            semanticParticleBO.setRelatedidioms(IdiomBOManager.getRelatedIdiom(semEntry));
        }

        //multimedia:
        if (options.FindImages) {
            semanticParticleBO.setImages(ImageManager.getImage(semEntry));
        }
        if (options.FindSounds) {
            semanticParticleBO.setSounds(SoundManager.getSound(semEntry));
        }
        if (options.FindVideos) {
            semanticParticleBO.setVideos(VideoManager.getVideo(semEntry));
        }

        return semanticParticleBO;
    }
    //</editor-fold>

    public void suggestDelete(int semantciParticleId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semantciParticleId);

        newSemanticparticle.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        newSemanticparticle.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        newSemanticparticle.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        SEMANTIC_PARTICLE_DAO.update(newSemanticparticle);
        ParticleBOManager.setNeedCheck(newSemanticparticle.getDerivedparticle().getIdentity());
        SEMANTIC_PARTICLE_DAO.clearSession();
    }

    public void suggestUpdateInfo(SemanticParticleBO newSemanticParticleBO, Integer semanticParticleId) throws RawNotFoundException {
        Semanticparticle oldSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        Semanticentry oldSemanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(oldSemanticparticle.getSemanticentry().getIdentity());

        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", oldSemanticentry.getIdentity());

        List<Semanticparticle> semnticParticleList = SEMANTIC_PARTICLE_DAO.getConfirmedInstance(oldSemanticparticle, restrictions);

        if (semnticParticleList.size() == 1) {
            newSemanticEntryBOManager.suggestUpdating(oldSemanticentry.getIdentity(), newSemanticParticleBO);

            oldSemanticparticle.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
            oldSemanticparticle.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());

            SEMANTIC_PARTICLE_DAO.update(oldSemanticparticle);

        } else {
            int newSemanticEntryId = newSemanticEntryBOManager.addTempSemanticEntry(newSemanticParticleBO);

            oldSemanticparticle.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
            oldSemanticparticle.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
            oldSemanticparticle.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(newSemanticEntryId));

            SEMANTIC_PARTICLE_DAO.update(oldSemanticparticle);
        }
        ParticleBOManager.setNeedCheck(oldSemanticparticle.getDerivedparticle().getIdentity());
        SEMANTIC_PARTICLE_DAO.clearSession();
    }

    public static void affirmUpdating(int semanticParticleId) throws RawNotFoundException {
        Semanticparticle oldSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        if (oldSemanticparticle.getSemanticentry().getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            SemanticEntryBOManager.affirmUpdating(oldSemanticparticle.getSemanticentry().getIdentity());
            oldSemanticparticle.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            oldSemanticparticle.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            SEMANTIC_PARTICLE_DAO.update(oldSemanticparticle);
        } else {
            //we don't need to implement this part, because  current database structure doesnt
            //have any semanticentry row relates with two semanticnoun or more.
        }
    }

    public static boolean affirmUpdatingAU(int semanticParticleId, SemanticParticleBO newSemanticParticleBO) throws RawNotFoundException {
        Semanticparticle oldSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        if (oldSemanticparticle.getSemanticentry().getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            SemanticEntryBOManager.affirmUpdatingAU(oldSemanticparticle.getSemanticentry().getIdentity(), newSemanticParticleBO);
            oldSemanticparticle.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            SEMANTIC_PARTICLE_DAO.update(oldSemanticparticle);
        }
        oldSemanticparticle.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        SEMANTIC_PARTICLE_DAO.update(oldSemanticparticle);
        return true;
    }

    public static void rejectUpdating(int semanticVerbId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticVerbId);
        if (newSemanticparticle.getSuggestion() == null) {
            if (newSemanticparticle.getSemanticentry().getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                SemanticEntryBOManager.rejectUpdating(newSemanticparticle.getSemanticentry().getIdentity());
                newSemanticparticle.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                SEMANTIC_PARTICLE_DAO.update(newSemanticparticle);
            }
        }
    }

    public static void rejectAdding(int semanticparticleId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticparticleId);
        if (newSemanticparticle.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            SemanticEntryBOManager.rejectAdding(newSemanticparticle.getSemanticentry().getIdentity());
            if (newSemanticparticle.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newSemanticparticle.getSuggestion());
            }

            newSemanticparticle.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
            newSemanticparticle.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            SEMANTIC_PARTICLE_DAO.update(newSemanticparticle);
        }
    }

    public int suggestAdding(SemanticParticleBO semanticParticleBO, int derivedParticleId) throws EntryExistedException, RawNotFoundException {
        int semanticEntryId = newSemanticEntryBOManager.suggestAdding(semanticParticleBO);
        Semanticentry newSemanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);

        Derivedparticle newDerivedparticle = BLUtil.daoFactory.getDerivedparticleDAO().getById(derivedParticleId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_derivedparticle.derivedParticleId", derivedParticleId);
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);

        Semanticparticle newSemanticparticle = new Semanticparticle(newSemanticentry, newDerivedparticle);

        newSemanticparticle.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newSemanticparticle.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newSemanticparticle.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

        int newSemanticParticleId = SEMANTIC_PARTICLE_DAO.insertWithCheck(newSemanticparticle, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

        ParticleBOManager.setNeedCheck(derivedParticleId);
        SEMANTIC_PARTICLE_DAO.clearSession();

        return newSemanticParticleId;
    }

    public static void affirmAdding(int semanticParticleId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        newSemanticparticle.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        SemanticEntryBOManager.affirmAdding(newSemanticparticle.getSemanticentry().getIdentity());
        BOManagerUtil.AFFIRM_SUGGESTION(newSemanticparticle.getSuggestion());
        SEMANTIC_PARTICLE_DAO.update(newSemanticparticle);
    }

    public static boolean affirmAddingAU(int semanticParticleId, SemanticParticleBO updateSemanticParticleBO, int derivedParticleId) throws RawNotFoundException, EntryExistedException {
        Semanticparticle orginalSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.update(orginalSemanticparticle.getSemanticentry().getIdentity(), updateSemanticParticleBO);
        orginalSemanticparticle.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(orginalSemanticparticle.getSuggestion());
        SEMANTIC_PARTICLE_DAO.update(orginalSemanticparticle);
        return true;
    }

    public static void setNeedCheck(int semanticParticleId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);

        newSemanticparticle.setChechStatus(BOManagerUtil.NEEDS_CHECK_STATUS.getCheckStatus());

        SEMANTIC_PARTICLE_DAO.update(newSemanticparticle);
    }

    public static void affirmDeleting(int semanticNounId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticNounId);
        if (newSemanticparticle.getInfoStatus().equals(semanticNounId)) {
            SemanticEntryBOManager.affirmDeleting(newSemanticparticle.getSemanticentry().getIdentity());
            if (newSemanticparticle.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newSemanticparticle.getSuggestion());
            }
            newSemanticparticle.setInfoStatus(WordStatus.NEED_DELETING);
            newSemanticparticle.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            SEMANTIC_PARTICLE_DAO.update(newSemanticparticle);
        }
    }

    public static void rejectDeleting(int semanticNounId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticNounId);
        if (newSemanticparticle.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            SemanticEntryBOManager.rejectDeleting(newSemanticparticle.getSemanticentry().getIdentity());
            newSemanticparticle.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newSemanticparticle.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

            if (newSemanticparticle.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newSemanticparticle.getSuggestion());
            }
            SEMANTIC_PARTICLE_DAO.update(newSemanticparticle);
        }
    }

    public static void clearCheck(Integer semanticParticleId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        newSemanticparticle.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
        SEMANTIC_PARTICLE_DAO.update(newSemanticparticle);
    }

    public static int getCheckedSemParticleWeight(int semanticParticleId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        int weight = SemanticEntryBOManager.getCheckedSemEntryWeight(newSemanticparticle.getSemanticentry().getIdentity());
        return weight;
    }

    /// <editor-fold defaultstate="collapsed" desc="Meaning...">
    public int suggestAddMeaning(MeaningBO newMeaningBO, SemanticParticleBO newSemanticParticleBO) throws RawNotFoundException, EntryExistedException {
        int newMeaningId = newMeaningBOManager.suggestAdding(newMeaningBO, newSemanticParticleBO.getSemanticEntryId());
        int semanticParticleId = newSemanticParticleBO.getSemanticParticleId();
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        setNeedCheck(semanticParticleId);
        ParticleBOManager.setNeedCheck(newSemanticparticle.getDerivedparticle().getIdentity());
        SEMANTIC_PARTICLE_DAO.clearSession();
        return newMeaningId;
    }

    public static void suggestUpdateMeaning(MeaningBO newMeaningBO, MeaningBO oldMeaningBO, SemanticParticleBO newSemanticParticleBO) throws RawNotFoundException {
        MeaningBOManager.suggestUpdating(newMeaningBO, oldMeaningBO);
        int semanticParticleId = newSemanticParticleBO.getSemanticParticleId();
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        setNeedCheck(semanticParticleId);
        ParticleBOManager.setNeedCheck(newSemanticparticle.getDerivedparticle().getIdentity());
        SEMANTIC_PARTICLE_DAO.clearSession();
    }

    public static void suggestDeleteMeaning(MeaningBO newMeaningBO, SemanticParticleBO newSemanticParticleBO) throws RawNotFoundException {
        MeaningBOManager.suggestDeleting(newMeaningBO);
        int semanticParticleId = newSemanticParticleBO.getSemanticParticleId();
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        setNeedCheck(semanticParticleId);
        ParticleBOManager.setNeedCheck(newSemanticparticle.getDerivedparticle().getIdentity());
        SEMANTIC_PARTICLE_DAO.clearSession();
    }
///</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Example...">

    public int suggestAddExample(ExampleBO newExampleBO, SemanticParticleBO newSemanticParticleBO) throws RawNotFoundException, EntryExistedException {
        int newExampleId = super.suggestAddExample(newExampleBO, newSemanticParticleBO.getSemanticEntryId());
        int semanticParticleId = newSemanticParticleBO.getSemanticParticleId();
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        setNeedCheck(semanticParticleId);
        ParticleBOManager.setNeedCheck(newSemanticparticle.getDerivedparticle().getIdentity());
        SEMANTIC_PARTICLE_DAO.clearSession();
        return newExampleId;
    }

    public void suggestUpdateExample(ExampleBO newExampleBO, ExampleBO oldExampleBO, SemanticParticleBO newSemanticParticleBO) throws RawNotFoundException {
        super.suggestUpdateExample(newExampleBO, oldExampleBO, newSemanticParticleBO.getSemanticEntryId());
        int semanticParticleId = newSemanticParticleBO.getSemanticParticleId();
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        setNeedCheck(semanticParticleId);
        ParticleBOManager.setNeedCheck(newSemanticparticle.getDerivedparticle().getIdentity());
        SEMANTIC_PARTICLE_DAO.clearSession();
    }

    public void suggestDeleteExample(ExampleBO newExampleBO, SemanticParticleBO newSemanticParticleBO) throws RawNotFoundException {
        super.suggestDeleteExample(newExampleBO, newSemanticParticleBO.getSemanticEntryId());
        int semanticParticleId = newSemanticParticleBO.getSemanticParticleId();
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        setNeedCheck(semanticParticleId);
        ParticleBOManager.setNeedCheck(newSemanticparticle.getDerivedparticle().getIdentity());
        SEMANTIC_PARTICLE_DAO.clearSession();
    }

    public static void affirmExampleAdding(int semanticParticleId, int exampleId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.affirmExampleAdding(newSemanticparticle.getSemanticentry().getIdentity(), exampleId);
    }

    public static boolean affirmExampleAddingAU(int semanticParticleId, int exampleId, ExampleBO newExampleBO) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        return SemanticEntryBOManager.affirmExampleAddingAU(newSemanticparticle.getSemanticentry().getIdentity(), exampleId, newExampleBO);
    }

    public static void rejectExampleAdding(int semanticParticleId, int exampleId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.rejectExampleAdding(newSemanticparticle.getSemanticentry().getIdentity(), exampleId);
    }

    public static void affirmExampleUpdating(int semanticParticleId, int exampleId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.affirmExampleUpdating(newSemanticparticle.getSemanticentry().getIdentity(), exampleId);
    }

    public static boolean affirmExampleUpdatingAU(int semanticParticleId, int exampleId, ExampleBO newExampleBO) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        return SemanticEntryBOManager.affirmExampleUpdatingAU(newSemanticparticle.getSemanticentry().getIdentity(), exampleId, newExampleBO);
    }

    public static void rejectExampleUpdating(int semanticParticleId, int exampleId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.rejectExampleUpdating(newSemanticparticle.getSemanticentry().getIdentity(), exampleId);
    }

    public static void affirmExampleDeleting(int semanticParticleId, int exampleId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.affirmExampleDeleting(newSemanticparticle.getSemanticentry().getIdentity(), exampleId);
    }

    public static void rejectExampleDeleting(int semanticParticleId, int exampleId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.rejectExampleDeleting(newSemanticparticle.getSemanticentry().getIdentity(), exampleId);
    }

///</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="CommonMistake...">
    public int suggestAddCommonMistake(CommonMistakeBO newCommonMistakeBO, SemanticParticleBO newSemanticParticleBO) throws RawNotFoundException, EntryExistedException {
        int newCommonMistakeId = super.suggestAddCommonMistake(newCommonMistakeBO, newSemanticParticleBO.getSemanticEntryId());
        int semanticParticleId = newSemanticParticleBO.getSemanticParticleId();
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        setNeedCheck(semanticParticleId);
        ParticleBOManager.setNeedCheck(newSemanticparticle.getDerivedparticle().getIdentity());
        SEMANTIC_PARTICLE_DAO.clearSession();
        return newCommonMistakeId;
    }

    public void suggestUpdateCommonMistake(CommonMistakeBO newCommonMistakeBO, CommonMistakeBO oldCommonMistakeBO, SemanticParticleBO newSemanticParticleBO) throws RawNotFoundException {
        super.suggestUpdateCommonMistake(newCommonMistakeBO, oldCommonMistakeBO, newSemanticParticleBO.getSemanticEntryId());
        int semanticParticleId = newSemanticParticleBO.getSemanticParticleId();
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        setNeedCheck(semanticParticleId);
        ParticleBOManager.setNeedCheck(newSemanticparticle.getDerivedparticle().getIdentity());
        SEMANTIC_PARTICLE_DAO.clearSession();
    }

    public void suggestDeleteCommonMistake(CommonMistakeBO newCommonMistakeBO, SemanticParticleBO newSemanticParticleBO) throws RawNotFoundException {
        super.suggestDeleteCommonMistake(newCommonMistakeBO, newSemanticParticleBO.getSemanticEntryId());
        int semanticParticleId = newSemanticParticleBO.getSemanticParticleId();
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        setNeedCheck(semanticParticleId);
        ParticleBOManager.setNeedCheck(newSemanticparticle.getDerivedparticle().getIdentity());
        SEMANTIC_PARTICLE_DAO.clearSession();
    }

    public static boolean affirmCommonMistakeUpdatingAU(int semanticParticleId, int commonMistakeId, CommonMistakeBO newCommonMistakeBO) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        return SemanticEntryBOManager.affirmCommonMistakeUpdatingAU(newSemanticparticle.getSemanticentry().getIdentity(), commonMistakeId, newCommonMistakeBO);
    }

    public static void rejectCommonmistakeUpdating(int semanticParticleId, int commonmistakeId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.rejectCommonmistakeUpdating(newSemanticparticle.getIdentity(), commonmistakeId);
    }

    public static void affirmCommonmistakeDeleting(int semanticParticleId, int commonmistakeId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.affirmCommonmistakeDeleting(newSemanticparticle.getSemanticentry().getIdentity(), commonmistakeId);
    }

    public static void rejectCommonmistakeDeleting(int semanticParticleId, int commonmistakeId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.rejectCommonmistakeDeleting(newSemanticparticle.getSemanticentry().getIdentity(), commonmistakeId);
    }

    public static void affirmCommonMistakeAdding(int semanticParticleId, int exampleId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.affirmCommonMistakeAdding(newSemanticparticle.getSemanticentry().getIdentity(), exampleId);
    }

    public static boolean affirmCommonMistakeAddingAU(int semanticParticleId, int commonMistakeId, CommonMistakeBO newCommonMistakeBO) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        return SemanticEntryBOManager.affirmCommonMistakeAddingAU(newSemanticparticle.getSemanticentry().getIdentity(), commonMistakeId, newCommonMistakeBO);
    }

    public static void rejectCommonMistakeAdding(int semanticParticleId, int commonmistakeId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.rejectCommonMistakeAdding(newSemanticparticle.getSemanticentry().getIdentity(), commonmistakeId);
    }

///</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="LinguisticBenefit...">
    public int suggestAddLinguisticBenefit(LinguisticBenefitBO newLinguisticBenefitBO, SemanticParticleBO newSemanticParticleBO) throws RawNotFoundException, EntryExistedException {
        int newLinguisticBenefitId = super.suggestAddLinguisticBenefit(newLinguisticBenefitBO, newSemanticParticleBO.getSemanticEntryId());
        int semanticParticleId = newSemanticParticleBO.getSemanticParticleId();
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        setNeedCheck(semanticParticleId);
        ParticleBOManager.setNeedCheck(newSemanticparticle.getDerivedparticle().getIdentity());
        SEMANTIC_PARTICLE_DAO.clearSession();
        return newLinguisticBenefitId;
    }

    public void suggestUpdateLinguisticBenefit(LinguisticBenefitBO newLinguisticBenefitBO, LinguisticBenefitBO oldLinguisticBenefitBO, SemanticParticleBO newSemanticParticleBO) throws RawNotFoundException {
        super.suggestUpdateLinguisticBenefit(newLinguisticBenefitBO, oldLinguisticBenefitBO, newSemanticParticleBO.getSemanticEntryId());
        int semanticParticleId = newSemanticParticleBO.getSemanticParticleId();
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        setNeedCheck(semanticParticleId);
        ParticleBOManager.setNeedCheck(newSemanticparticle.getDerivedparticle().getIdentity());
        SEMANTIC_PARTICLE_DAO.clearSession();
    }

    public void suggestDeleteLinguisticBenefit(LinguisticBenefitBO newLinguisticBenefitBO, SemanticParticleBO newSemanticParticleBO) throws RawNotFoundException {
        super.suggestDeleteLinguisticBenefit(newLinguisticBenefitBO, newSemanticParticleBO.getSemanticEntryId());
        int semanticParticleId = newSemanticParticleBO.getSemanticParticleId();
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        setNeedCheck(semanticParticleId);
        ParticleBOManager.setNeedCheck(newSemanticparticle.getDerivedparticle().getIdentity());
        SEMANTIC_PARTICLE_DAO.clearSession();
    }

    public static void affirmLinguisticBenefitAdding(int semanticParticleId, int exampleId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.affirmLinguisticBenefitAdding(newSemanticparticle.getSemanticentry().getIdentity(), exampleId);
    }

    public static boolean affirmLinguisticBenefitAddingAU(int semanticParticleId, int linguisticBenefitId, LinguisticBenefitBO newLinguisticBenefitBO) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        return SemanticEntryBOManager.affirmLinguisticBenefitAddingAU(newSemanticparticle.getSemanticentry().getIdentity(), linguisticBenefitId, newLinguisticBenefitBO);
    }

    public static void rejectLinguisticBenefitAdding(int semanticParticleId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.rejectLinguisticBenefitAdding(newSemanticparticle.getSemanticentry().getIdentity(), linguisticbenefitId);
    }

    public static void affirmLinguisticBenefitUpdating(int semanticParticleId, int linguisticBenefitId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.affirmLinguisticBenefitUpdating(newSemanticparticle.getSemanticentry().getIdentity(), linguisticBenefitId);
    }

    public static boolean affirmLinguisticBenefitUpdatingAU(int semanticParticleId, int linguisticBenefitId, LinguisticBenefitBO newLinguisticBenefitBO) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        return SemanticEntryBOManager.affirmLinguisticBenefitUpdatingAU(newSemanticparticle.getSemanticentry().getIdentity(), linguisticBenefitId, newLinguisticBenefitBO);
    }

    public static void rejectLinguisticbenefitUpdating(int semanticParticleId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.rejectLinguisticBenefitUpdating(newSemanticparticle.getSemanticentry().getIdentity(), linguisticbenefitId);
    }

    public static void affirmLinguisticbenefitDeleting(int semanticParticleId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.affirmLinguisticBenefitDeleting(newSemanticparticle.getSemanticentry().getIdentity(), linguisticbenefitId);
    }

    public static void rejectLinguisticbenefitDeleting(int semanticParticleId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticparticle newSemanticparticle = SEMANTIC_PARTICLE_DAO.getById(semanticParticleId);
        SemanticEntryBOManager.rejectLinguisticBenefitDeleting(newSemanticparticle.getSemanticentry().getIdentity(), linguisticbenefitId);
    }

///</editor-fold>
    public static void main(String[] str) throws RawNotFoundException {
        User currUser = BLUtil.daoFactory.getUserDAO().getById(5);
        BOManagerUtil.setCurrentUser(currUser);
        /**
         * test affirmAdding()
         */
        //affirmAdding( 13 );
    }
}

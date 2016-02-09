/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.NounBO;
import BusinessLogicLayer.BusinessObjects.SemanticNounBO;
import BusinessLogicLayer.BusinessObjects.UpdatedNounBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.SystemStateManager;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.CompareWordsBO;
import BusinessLogicLayer.Util.FilterDiacritics;
import BusinessLogicLayer.Util.WordStatus;
import PersistenceLayer.Derivednoun;
import PersistenceLayer.Derivedverb;
import PersistenceLayer.Gender;
import PersistenceLayer.Pattern;
import PersistenceLayer.Rawword;
import PersistenceLayer.Root;
import PersistenceLayer.Number;
import PersistenceLayer.Origin;
import PersistenceLayer.Semanticnoun;
import PersistenceLayer.Suggestion;
import PersistenceLayer.Type;
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
public class NounBOManager {

    private WordBOManager newWordBOManager;
    private PatternManager newPatternManager;
    private RootManager newRootManager;
    private NumberManager newNumberManager;
    private GenderManager newGenderManager;
    private OriginManager newOriginManager;
    private TypeManager newTypeManager;

    public NounBOManager() {
        newWordBOManager = new WordBOManager();
        newPatternManager = new PatternManager();
        newRootManager = new RootManager();
        newNumberManager = new NumberManager();
        newGenderManager = new GenderManager();
        newOriginManager = new OriginManager();
        newTypeManager = new TypeManager();
    }

    public List<NounBO> listNounLike(String noun) throws RawNotFoundException {
        noun = noun.trim();
        String filteredNoun = FilterDiacritics.execute(noun);
        List<Rawword> rawwords = BLUtil.daoFactory.getRawwordDAO().getWordsLike(filteredNoun + "%");
        List<NounBO> nouns = new ArrayList<NounBO>();

        for (Rawword rawword : rawwords) {
            List<Derivednoun> derivednouns = new ArrayList<Derivednoun>(rawword.getDerivednouns());

            for (Derivednoun derivednoun : derivednouns) {
                nouns.add(getNounBO(derivednoun, SearchProperties.simpleSearchOptions, ""));
            }
        }
        return nouns;
    }

    public static boolean hasNewValues(int derivedNounId) throws RawNotFoundException {
        Derivednoun oldNoun = BLUtil.daoFactory.getDerivednounDAO().getById(derivedNounId);
        return hasNewValues(oldNoun);
    }

    public static boolean hasNewValues(Derivednoun oldNoun) {
        if (oldNoun.getInfoStatus().equals("U")) {
            Suggestion updateSuggestion = oldNoun.getSuggestion();
            if (updateSuggestion != null && !(updateSuggestion.getAffirmStatus().equals("A")) && updateSuggestion.getInfoStatus().equals("U")) {
                return true;
            }
        }
        return false;
    }

    ///<editor-fold defaultstate="collapsed" desc="getters....">
    public static NounBO getNounBO(Integer derivedNounId, String mode) {
        return getNounBO(derivedNounId, SearchProperties.detailedSearchOptions, mode);
    }

    public static NounBO getNounBO(Integer derivedNounId, SearchProperties options, String mode) {
        try {
            return getNounBO(BLUtil.daoFactory.getDerivednounDAO().getById(derivedNounId), options, mode);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(NounBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static NounBO getNounBO(Integer derivedNounId, Integer semanticWordId, String mode) {
        return getNounBO(derivedNounId, semanticWordId, SearchProperties.detailedSearchOptions, mode);
    }

    public static NounBO getNounBO(Integer derivedNounId, Integer semanticWordId, SearchProperties options, String mode) {
        try {
            return getNounBO(BLUtil.daoFactory.getDerivednounDAO().getById(derivedNounId), semanticWordId, options, mode);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(NounBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static NounBO getNounBO(Derivednoun derivednoun, String mode) throws RawNotFoundException {
        return getNounBO(derivednoun, SearchProperties.detailedSearchOptions, mode);
    }

    public static NounBO getNounBO(Derivednoun derivednoun, SearchProperties options, String mode) throws RawNotFoundException {
        return getNounBO(derivednoun, -1, options, mode);
    }

    public static NounBO getNounBO(Derivednoun derivednoun, Integer semanticNounId, String mode) throws RawNotFoundException {
        return getNounBO(derivednoun, semanticNounId, SearchProperties.detailedSearchOptions, mode);
    }

    public static NounBO getNounBO(Derivednoun derivednoun, Integer semanticNounId, SearchProperties options, String mode) throws RawNotFoundException {
        if (derivednoun.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS) && hasNewValues(derivednoun)) {
            UpdatedNounBO noun = new UpdatedNounBO();
            if (!SystemStateManager.available(mode, derivednoun.getInfoStatus(), derivednoun.getChechStatus())) {
                return null;
            }
            try {
                noun.setDerivedNounId(derivednoun.getIdentity());
                noun.setVocalizedNoun(derivednoun.getVocalizedNoun());
                noun.setRoot(derivednoun.getRoot().getRoot());
                noun.setPattern(derivednoun.getPattern().getPattern());
                noun.setGender(derivednoun.getGender().getGender());
                noun.setNumber(derivednoun.getNumber().getNumber());
                noun.setOrigin(derivednoun.getOrigin().getOrigin());
                noun.setType(derivednoun.getType().getType());
                noun.setStatus(derivednoun.getInfoStatus());

                Derivednoun newNoun = BLUtil.daoFactory.getDerivednounDAO().getById(derivednoun.getSuggestion().getUpdateId());
                noun.setNewDerivedNounId(newNoun.getIdentity());
                noun.setNewVocalizedNoun(newNoun.getVocalizedNoun());
                noun.setNewRoot(newNoun.getRoot().getRoot());
                noun.setNewPattern(newNoun.getPattern().getPattern());
                noun.setNewGender(newNoun.getGender().getGender());
                noun.setNewNumber(newNoun.getNumber().getNumber());
                noun.setNewOrigin(newNoun.getOrigin().getOrigin());
                noun.setNewType(newNoun.getType().getType());

                if (options.FindSemanticInformations) {
                    List<SemanticNounBO> semanticCases = new ArrayList<SemanticNounBO>();

                    Set<Semanticnoun> SemanticnounList = derivednoun.getSemanticnouns();
                    for (Iterator iter2 = SemanticnounList.iterator(); iter2.hasNext();) {
                        //each derived noun:
                        Semanticnoun semNoun = (Semanticnoun) iter2.next();
                        if (!SystemStateManager.available(mode, semNoun.getInfoStatus(), semNoun.getChechStatus())) {
                            continue;
                        }

                        SemanticNounBO semanticNounBO = SemanticNounBOManager.getSemanticNounBO(semNoun, options);
                        semanticNounBO.setNoun(noun);
                        int sId = semanticNounBO.getSemanticNounId();
                        if (sId == semanticNounId || semanticNounId < 0) {
                            semanticCases.add(semanticNounBO);
                        }
                    }

                    Collections.sort(semanticCases, new CompareWordsBO());
                    noun.setSemanticCases(semanticCases);
                }
            } catch (RawNotFoundException ex) {
                Logger.getLogger(VerbBOManager.class.getName()).log(Level.SEVERE, null, ex);
            }

            return noun;
        } else {
            if (!SystemStateManager.available(mode, derivednoun.getInfoStatus(), derivednoun.getChechStatus())) {
                return null;
            }
            NounBO noun = new NounBO();
            //Save derived particle infos:
            noun.setDerivedNounId(derivednoun.getIdentity());
            noun.setVocalizedNoun(derivednoun.getVocalizedNoun());
            noun.setRoot(derivednoun.getRoot().getRoot());
            noun.setPattern(derivednoun.getPattern().getPattern());
            noun.setGender(derivednoun.getGender().getGender());
            noun.setNumber(derivednoun.getNumber().getNumber());
            noun.setOrigin(derivednoun.getOrigin().getOrigin());
            noun.setType(derivednoun.getType().getType());
            noun.setStatus(derivednoun.getInfoStatus());
            //noun.setPhonetic(derivednoun.getPhonetic());

            //Retrieve semantic noun:
            if (options.FindSemanticInformations) {
                List<SemanticNounBO> semanticCases = new ArrayList<SemanticNounBO>();

                Set<Semanticnoun> SemanticnounList = derivednoun.getSemanticnouns();
                for (Iterator iter2 = SemanticnounList.iterator(); iter2.hasNext();) {
                    //each derived noun:
                    Semanticnoun semNoun = (Semanticnoun) iter2.next();
                    if (!SystemStateManager.available(mode, semNoun.getInfoStatus(), semNoun.getChechStatus())) {
                        continue;
                    }

                    SemanticNounBO semanticNounBO = SemanticNounBOManager.getSemanticNounBO(semNoun, options);
                    semanticNounBO.setNoun(noun);
                    int sId = semanticNounBO.getSemanticNounId();
                    if (sId == semanticNounId || semanticNounId < 0) {
                        semanticCases.add(semanticNounBO);
                    }
                }

                Collections.sort(semanticCases, new CompareWordsBO());
                noun.setSemanticCases(semanticCases);
            }
            return noun;
        }
    }
//</editor-fold>

    public int suggestAdding(NounBO newNounBO) throws RawNotFoundException, EntryExistedException {
        String rawWord = FilterDiacritics.execute(newNounBO.getVocalizedNoun());
        Map restrictions = BOManagerUtil.getAddRestrictions();
        int newRawWordId = newWordBOManager.suggestAdding(rawWord);
        restrictions.put("eq_rawword.rawWordId", newRawWordId);
        Rawword newRawword = BLUtil.daoFactory.getRawwordDAO().getById(newRawWordId);

        int newPatternId = newPatternManager.suggestAdding(newNounBO.getPattern());
        Pattern newPattern = BLUtil.daoFactory.getPatternDAO().getById(newPatternId);
        restrictions.put("eq_pattern.patternId", newPatternId);

        int newRootId = newRootManager.suggestAdding(newNounBO.getRoot());
        Root newRoot = BLUtil.daoFactory.getRootDAO().getById(newRootId);
        restrictions.put("eq_root.rootId", newRootId);

        int newNumberId = newNumberManager.suggestAdding(newNounBO.getNumber());
        Number newNumber = BLUtil.daoFactory.getNumberDAO().getById(newNumberId);
        restrictions.put("eq_number.numberId", newNumberId);

        int newGenderId = newGenderManager.suggestAdding(newNounBO.getGender());
        Gender newGender = BLUtil.daoFactory.getGenderDAO().getById(newGenderId);
        restrictions.put("eq_gender.genderId", newGenderId);

        int newOriginId = newOriginManager.suggestAdding(newNounBO.getOrigin());
        Origin newOrigin = BLUtil.daoFactory.getOriginDAO().getById(newOriginId);
        restrictions.put("eq_origin.originId", newOriginId);

        int newTypeId = newTypeManager.suggestAdding(newNounBO.getType());
        Type newType = BLUtil.daoFactory.getTypeDAO().getById(newTypeId);
        restrictions.put("eq_type.typeId", newTypeId);

        Derivednoun derivedNoun = new Derivednoun();
        derivedNoun.setRawword(newRawword);
        derivedNoun.setPattern(newPattern);
        derivedNoun.setRoot(newRoot);
        derivedNoun.setGender(newGender);
        derivedNoun.setNumber(newNumber);
        derivedNoun.setOrigin(newOrigin);
        derivedNoun.setType(newType);
        derivedNoun.setVocalizedNoun(newNounBO.getVocalizedNoun());
        //derivedNoun.setPronunciation (new Pronunciation ());

        derivedNoun.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        derivedNoun.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        derivedNoun.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        int id = BLUtil.daoFactory.getDerivednounDAO().insertWithCheck(derivedNoun, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES,
                restrictions, "الاسم موجود مسبقاً!");
        return id;

    }
    /*public static  NounBO get( Integer derivedNounId, SearchProperties options, String mode )throws RawNotFoundException
     {
     return get( BLUtil.daoFactory.getDerivedverbDAO().getById( derivedNounId ), options, mode );
     }
    
     public static  NounBO get ( Derivednoun derivedNounId , SearchProperties options , String mode )
     {
     return get( derivedNounId, -1, options, mode );
     }
    
     public static  NounBO get ( Derivednoun derivedNounId , int i , SearchProperties options , String mode )
     {
     throw new UnsupportedOperationException ( "Not yet implemented" );
     }
    
     public static NounBO get( Integer derivedNounId, String mode ) throws RawNotFoundException
     {
     return get( derivedNounId, SearchProperties.detailedSearchOptions, mode );
     }
    
     public static NounBO get( Derivednoun derivedNounId, String mode ) throws RawNotFoundException
     {
     return get( derivedNounId, SearchProperties.detailedSearchOptions, mode );
     }
    
     public static NounBO get( Integer derivedNounId, Integer semanticVerbId, String mode )
     {
     return get( derivedNounId, semanticVerbId, SearchProperties.detailedSearchOptions, mode );
     }
    
     public static NounBO get( Integer derivedVerbId, Integer semanticVerbId, SearchProperties options, String mode )
     {
     try
     {
     return get( BLUtil.daoFactory.getDerivedverbDAO().getById( derivedVerbId ), semanticVerbId, options, mode );
     }
     catch ( RawNotFoundException ex )
     {
     Logger.getLogger( VerbBOManager.class.getName() ).log( Level.SEVERE, null, ex );
     return null;
     }
     }
    
     private static NounBO get( Derivednoun derivedVerb, Integer semanticVerbId, String mode )
     {
     return get( derivedVerb, semanticVerbId, SearchProperties.detailedSearchOptions, mode );
     }
    
     private static NounBO get ( Derivedverb byId , Integer semanticVerbId , SearchProperties options , String mode )
     {
     throw new UnsupportedOperationException ( "Not yet implemented" );
     }*/

    public void suggestDeleting(Integer derivedNounId) throws RawNotFoundException {
        Derivednoun derivednoun = BLUtil.daoFactory.getDerivednounDAO().getById(derivedNounId);

        derivednoun.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        derivednoun.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        derivednoun.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        BLUtil.daoFactory.getDerivednounDAO().update(derivednoun);

    }

    public void suggestUpdating(int derivedNounId, NounBO newObj) throws RawNotFoundException {
        Derivednoun derivednoun = BLUtil.daoFactory.getDerivednounDAO().getById(derivedNounId);
        NounBO newNounBO = new NounBO();
        newNounBO.setRoot(derivednoun.getRoot().getRoot());
        newNounBO.setPattern(derivednoun.getPattern().getPattern());
        newNounBO.setGender(derivednoun.getGender().getGender());
        newNounBO.setRawWord(derivednoun.getRawword().getRawWord());
        newNounBO.setOrigin(derivednoun.getOrigin().getOrigin());
        newNounBO.setType(derivednoun.getType().getType());
        newNounBO.setStatus(derivednoun.getInfoStatus());
        newNounBO.setNumber(derivednoun.getNumber().getNumber());

        if (!newObj.equals(newNounBO)) {
            int tempNounId = this.addTempNounBO(newObj);

            derivednoun.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
            derivednoun.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
            derivednoun.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(tempNounId));
            BLUtil.daoFactory.getDerivednounDAO().update(derivednoun);
        }
    }

    private int addTempNounBO(NounBO newNounBO) throws RawNotFoundException {
        String rawWord = FilterDiacritics.execute(newNounBO.getVocalizedNoun());
        Map restrictions = BOManagerUtil.getAddRestrictions();
        int newRawWordId = newWordBOManager.suggestAdding(rawWord);
        restrictions.put("eq_rawword.rawWordId", newRawWordId);
        Rawword newRawword = BLUtil.daoFactory.getRawwordDAO().getById(newRawWordId);

        int newPatternId = newPatternManager.suggestAdding(newNounBO.getPattern());
        Pattern newPattern = BLUtil.daoFactory.getPatternDAO().getById(newPatternId);
        restrictions.put("eq_pattern.patternId", newPatternId);

        int newRootId = newRootManager.suggestAdding(newNounBO.getRoot());
        Root newRoot = BLUtil.daoFactory.getRootDAO().getById(newRootId);
        restrictions.put("eq_root.rootId", newRootId);

        int newNumberId = newNumberManager.suggestAdding(newNounBO.getNumber());
        Number newNumber = BLUtil.daoFactory.getNumberDAO().getById(newNumberId);
        restrictions.put("eq_number.numberId", newNumberId);

        int newGenderId = newGenderManager.suggestAdding(newNounBO.getGender());
        Gender newGender = BLUtil.daoFactory.getGenderDAO().getById(newGenderId);
        restrictions.put("eq_gender.genderId", newGenderId);

        int newOriginId = newOriginManager.suggestAdding(newNounBO.getOrigin());
        Origin newOrigin = BLUtil.daoFactory.getOriginDAO().getById(newOriginId);
        restrictions.put("eq_origin.originId", newOriginId);

        int newTypeId = newTypeManager.suggestAdding(newNounBO.getType());
        Type newType = BLUtil.daoFactory.getTypeDAO().getById(newTypeId);
        restrictions.put("eq_type.typeId", newTypeId);

        Derivednoun derivedNoun = new Derivednoun();
        derivedNoun.setRawword(newRawword);
        derivedNoun.setPattern(newPattern);
        derivedNoun.setRoot(newRoot);
        derivedNoun.setGender(newGender);
        derivedNoun.setNumber(newNumber);
        derivedNoun.setOrigin(newOrigin);
        derivedNoun.setType(newType);
        derivedNoun.setVocalizedNoun(newNounBO.getVocalizedNoun());
        //derivedNoun.setPronunciation (new Pronunciation ());

        derivedNoun.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
        derivedNoun.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());

        int id = BLUtil.daoFactory.getDerivednounDAO().insertWithCheck(derivedNoun, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES,
                restrictions);
        return id;
    }

    public List<NounBO> listNouns(String noun) throws RawNotFoundException {
        noun = noun.trim();
        String filteredNoun = FilterDiacritics.execute(noun);
        List<Rawword> rawwords = BLUtil.daoFactory.getRawwordDAO().getWordsLike(filteredNoun);
        List<NounBO> nouns = new ArrayList<NounBO>();

        for (Rawword rawword : rawwords) {
            List<Derivednoun> derivednouns = new ArrayList<Derivednoun>(rawword.getDerivednouns());

            for (Derivednoun derivednoun : derivednouns) {
                nouns.add(getNounBO(derivednoun, SearchProperties.simpleSearchOptions, ""));
            }
        }
        return nouns;
    }

    public static void setNeedCheck(int nounId) throws RawNotFoundException {
        Derivednoun newDerivednoun = BLUtil.daoFactory.getDerivednounDAO().getById(nounId);

        newDerivednoun.setChechStatus(BOManagerUtil.NEEDS_CHECK_STATUS.getCheckStatus());

        BLUtil.daoFactory.getDerivednounDAO().update(newDerivednoun);
    }

    public List<NounBO> fastListNounLike(String noun, int i) throws RawNotFoundException {
        noun = noun.trim();
        String filteredNoun = FilterDiacritics.execute(noun);
        List<Rawword> rawwords = BLUtil.daoFactory.getRawwordDAO().getWordsLike(filteredNoun + "%");
        List<NounBO> nouns = new ArrayList<NounBO>();
        int c = 0;
        for (Rawword rawword : rawwords) {
            List<Derivednoun> derivednouns = new ArrayList<Derivednoun>(rawword.getDerivednouns());

            for (Derivednoun derivednoun : derivednouns) {
                nouns.add(getNounBO(derivednoun, new SearchProperties(), ""));
            }
            if (i <= c) {
                return nouns;
            }
            c++;
        }
        return nouns;
    }

    public static List<NounBO> getNeedCheck() throws RawNotFoundException {
        List<Derivednoun> nouns = BLUtil.daoFactory.getDerivednounDAO().getNeedCheck();
        List<NounBO> needCheckNouns = new ArrayList<NounBO>();
        for (Derivednoun noun : nouns) {
            needCheckNouns.add(NounBOManager.getNounBO(noun, new SearchProperties(), ""));
        }
        return needCheckNouns;
    }

    public static void affirmAdding(int derivedNounId) throws RawNotFoundException {
        Derivednoun newDerivednoun = BLUtil.daoFactory.getDerivednounDAO().getById(derivedNounId);
        if (newDerivednoun.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newDerivednoun.setInfoStatus("S");
            //newDerivednoun.getRawword ().setInfoStatus( "S" );
            if (newDerivednoun.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newDerivednoun.getSuggestion());
            }
            BLUtil.daoFactory.getDerivednounDAO().update(newDerivednoun);
        }
    }

    public static boolean affirmAddingAU(int derivedNounId, NounBO updatedNoun) throws RawNotFoundException {
        Derivednoun newDerivednoun = BLUtil.daoFactory.getDerivednounDAO().getById(derivedNounId);
        String rawWord = FilterDiacritics.execute(updatedNoun.getVocalizedNoun());
        Map restrictions = BOManagerUtil.getAddRestrictions();

        int newRawWordId = WordBOManager.suggestAdding(rawWord);
        restrictions.put("eq_rawword.rawWordId", newRawWordId);
        Rawword newRawword = BLUtil.daoFactory.getRawwordDAO().getById(newRawWordId);

        int newPatternId = PatternManager.suggestAdding(updatedNoun.getPattern());
        Pattern newPattern = BLUtil.daoFactory.getPatternDAO().getById(newPatternId);
        restrictions.put("eq_pattern.patternId", newPatternId);

        int newRootId = RootManager.suggestAdding(updatedNoun.getRoot());
        Root newRoot = BLUtil.daoFactory.getRootDAO().getById(newRootId);
        restrictions.put("eq_root.rootId", newRootId);
        newDerivednoun.setPattern(newPattern);

        int newNumberId = NumberManager.suggestAdding(updatedNoun.getNumber());
        Number newNumber = BLUtil.daoFactory.getNumberDAO().getById(newNumberId);
        restrictions.put("eq_number.numberId", newNumberId);

        int newGenderId = GenderManager.suggestAdding(updatedNoun.getGender());
        Gender newGender = BLUtil.daoFactory.getGenderDAO().getById(newGenderId);
        restrictions.put("eq_gender.genderId", newGenderId);

        int newOriginId = OriginManager.suggestAdding(updatedNoun.getOrigin());
        Origin newOrigin = BLUtil.daoFactory.getOriginDAO().getById(newOriginId);
        restrictions.put("eq_origin.originId", newOriginId);

        int newTypeId = TypeManager.suggestAdding(updatedNoun.getType());
        Type newType = BLUtil.daoFactory.getTypeDAO().getById(newTypeId);
        restrictions.put("eq_type.typeId", newTypeId);

        if (BLUtil.daoFactory.getDerivednounDAO().getByExample(new Derivednoun(), BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions).size() > 0) {
            return false;
        } else {
            newDerivednoun.setRawword(newRawword);
            newDerivednoun.setPattern(newPattern);
            newDerivednoun.setRoot(newRoot);
            newDerivednoun.setGender(newGender);
            newDerivednoun.setNumber(newNumber);
            newDerivednoun.setOrigin(newOrigin);
            newDerivednoun.setType(newType);
            newDerivednoun.setVocalizedNoun(updatedNoun.getVocalizedNoun());
            //derivedNoun.setPronunciation (new Pronunciation ());
            newDerivednoun.setInfoStatus(BOManagerUtil.CONFIRM_STATUS.getInfoStatus());

            BOManagerUtil.AFFIRM_SUGGESTION(newDerivednoun.getSuggestion());
            BLUtil.daoFactory.getDerivednounDAO().update(newDerivednoun);
            return true;
        }
    }

    public static void clearCheck(int derivedNounId) throws RawNotFoundException {
        Derivednoun newDerivednoun = BLUtil.daoFactory.getDerivednounDAO().getById(derivedNounId);
        newDerivednoun.setChechStatus(0);
        BLUtil.daoFactory.getDerivednounDAO().update(newDerivednoun);
    }

    public static void rejectAdding(int derivedNounId) throws RawNotFoundException {
        Derivednoun newDerivedNoun = BLUtil.daoFactory.getDerivednounDAO().getById(derivedNounId);
        for (Semanticnoun semanticnoun : newDerivedNoun.getSemanticnouns()) {
            SemanticNounBOManager.rejectAdding(semanticnoun.getIdentity());
        }
        BOManagerUtil.REJECT_SUGGESTION(newDerivedNoun.getSuggestion());
        newDerivedNoun.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
    }

    public static void affirmUpdating(int derivedNounId) throws RawNotFoundException {
        Derivednoun oldNoun = BLUtil.daoFactory.getDerivednounDAO().getById(derivedNounId);
        Suggestion updateSuggestion = oldNoun.getSuggestion();
        // get update noun id from old noun suggestion.
        Derivednoun newNoun = BLUtil.daoFactory.getDerivednounDAO().getById(updateSuggestion.getUpdateId());
        Derivednoun copyOfNewNoun = new Derivednoun(null, newNoun.getNumber(), newNoun.getRawword(), newNoun.getPattern(),
                newNoun.getType(), newNoun.getRoot(), newNoun.getOrigin(), newNoun.getGender(), newNoun.getVocalizedNoun());
        // update noun will store old noun values before updates.
        newNoun.setRawword(oldNoun.getRawword());
        newNoun.setPattern(oldNoun.getPattern());
        newNoun.setRoot(oldNoun.getRoot());
        newNoun.setNumber(oldNoun.getNumber());
        newNoun.setGender(oldNoun.getGender());
        newNoun.setType(oldNoun.getType());
        newNoun.setOrigin(oldNoun.getOrigin());
        newNoun.setVocalizedNoun(oldNoun.getVocalizedNoun());
        newNoun.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
        // assign new values to the original noun.
        oldNoun.setRawword(copyOfNewNoun.getRawword());
        oldNoun.setPattern(copyOfNewNoun.getPattern());
        oldNoun.setRoot(copyOfNewNoun.getRoot());
        oldNoun.setNumber(copyOfNewNoun.getNumber());
        oldNoun.setGender(copyOfNewNoun.getGender());
        oldNoun.setType(copyOfNewNoun.getType());
        oldNoun.setOrigin(copyOfNewNoun.getOrigin());
        oldNoun.setVocalizedNoun(copyOfNewNoun.getVocalizedNoun());
        oldNoun.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);

        BOManagerUtil.AFFIRM_SUGGESTION(oldNoun.getSuggestion());

        BLUtil.daoFactory.getDerivednounDAO().update(newNoun);
        BLUtil.daoFactory.getDerivednounDAO().update(oldNoun);
    }

    private static void affirmUpdatingAfterAltering(Derivednoun oldNoun) throws RawNotFoundException {
        Suggestion updateSuggestion = oldNoun.getSuggestion();
        // get update noun id from old noun suggestion.
        Derivednoun newNoun = BLUtil.daoFactory.getDerivednounDAO().getById(updateSuggestion.getUpdateId());
        Derivednoun copyOfNewNoun = new Derivednoun(null, newNoun.getNumber(), newNoun.getRawword(), newNoun.getPattern(),
                newNoun.getType(), newNoun.getRoot(), newNoun.getOrigin(), newNoun.getGender(), newNoun.getVocalizedNoun());
        // update noun will store old noun values before updates.
        newNoun.setRawword(oldNoun.getRawword());
        newNoun.setPattern(oldNoun.getPattern());
        newNoun.setRoot(oldNoun.getRoot());
        newNoun.setNumber(oldNoun.getNumber());
        newNoun.setGender(oldNoun.getGender());
        newNoun.setType(oldNoun.getType());
        newNoun.setOrigin(oldNoun.getOrigin());
        newNoun.setVocalizedNoun(oldNoun.getVocalizedNoun());
        newNoun.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
        // assign new values to the original noun.
        oldNoun.setRawword(copyOfNewNoun.getRawword());
        oldNoun.setPattern(copyOfNewNoun.getPattern());
        oldNoun.setRoot(copyOfNewNoun.getRoot());
        oldNoun.setNumber(copyOfNewNoun.getNumber());
        oldNoun.setGender(copyOfNewNoun.getGender());
        oldNoun.setType(copyOfNewNoun.getType());
        oldNoun.setOrigin(copyOfNewNoun.getOrigin());
        oldNoun.setVocalizedNoun(copyOfNewNoun.getVocalizedNoun());
        oldNoun.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);

        BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldNoun.getSuggestion());

        BLUtil.daoFactory.getDerivednounDAO().update(newNoun);
        BLUtil.daoFactory.getDerivednounDAO().update(oldNoun);
    }

    public static void rejectUpdating(int derivedNounId) throws RawNotFoundException {
        Derivednoun originalDerivednoun = BLUtil.daoFactory.getDerivednounDAO().getById(derivedNounId);
        Derivednoun updatedDerivednoun = BLUtil.daoFactory.getDerivednounDAO().getById(originalDerivednoun.getSuggestion().getUpdateId());

        originalDerivednoun.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        BOManagerUtil.REJECT_SUGGESTION(originalDerivednoun.getSuggestion());
        BLUtil.daoFactory.getDerivednounDAO().update(originalDerivednoun);

        updatedDerivednoun.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
        BLUtil.daoFactory.getDerivednounDAO().update(updatedDerivednoun);
    }

    public static boolean affirmUpdatingAU(int derivedNounId, NounBO updateNoun) throws RawNotFoundException {
        Derivednoun originalDerivednoun = BLUtil.daoFactory.getDerivednounDAO().getById(derivedNounId);
        Derivednoun updatedDerivednoun = BLUtil.daoFactory.getDerivednounDAO().getById(originalDerivednoun.getSuggestion().getUpdateId());

        String rawWord = FilterDiacritics.execute(updateNoun.getVocalizedNoun());
        Map restrictions = new HashMap();
        int newRawWordId = WordBOManager.suggestAdding(rawWord);
        restrictions.put("eq_rawword.rawWordId", newRawWordId);
        Rawword newRawword = BLUtil.daoFactory.getRawwordDAO().getById(newRawWordId);

        int newPatternId = PatternManager.suggestAdding(updateNoun.getPattern());
        Pattern newPattern = BLUtil.daoFactory.getPatternDAO().getById(newPatternId);
        restrictions.put("eq_pattern.patternId", newPatternId);

        int newRootId = RootManager.suggestAdding(updateNoun.getRoot());
        Root newRoot = BLUtil.daoFactory.getRootDAO().getById(newRootId);
        restrictions.put("eq_root.rootId", newRootId);

        int newNumberId = NumberManager.suggestAdding(updateNoun.getNumber());
        Number newNumber = BLUtil.daoFactory.getNumberDAO().getById(newNumberId);
        restrictions.put("eq_number.numberId", newNumberId);

        int newGenderId = GenderManager.suggestAdding(updateNoun.getGender());
        Gender newGender = BLUtil.daoFactory.getGenderDAO().getById(newGenderId);
        restrictions.put("eq_gender.genderId", newGenderId);

        int newOriginId = OriginManager.suggestAdding(updateNoun.getOrigin());
        Origin newOrigin = BLUtil.daoFactory.getOriginDAO().getById(newOriginId);
        restrictions.put("eq_origin.originId", newOriginId);

        int newTypeId = TypeManager.suggestAdding(updateNoun.getType());
        Type newType = BLUtil.daoFactory.getTypeDAO().getById(newTypeId);
        restrictions.put("eq_type.typeId", newTypeId);

        // if the updated noun  already exists in the database then return false and the expert can decide what to do.
        if (BLUtil.daoFactory.getDerivednounDAO().getByExample(new Derivednoun(), BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions).size() > 0) {
            //System.out.println( "the noun already exists in the database....");
            return false;
        } else {
            updatedDerivednoun.setRawword(newRawword);
            updatedDerivednoun.setPattern(newPattern);
            updatedDerivednoun.setRoot(newRoot);
            updatedDerivednoun.setGender(newGender);
            updatedDerivednoun.setNumber(newNumber);
            updatedDerivednoun.setOrigin(newOrigin);
            updatedDerivednoun.setType(newType);
            updatedDerivednoun.setVocalizedNoun(updateNoun.getVocalizedNoun());
            //derivedNoun.setPronunciation (new Pronunciation ());

            BLUtil.daoFactory.getDerivednounDAO().update(updatedDerivednoun);
            affirmUpdatingAfterAltering(originalDerivednoun);
            System.out.println("the noun has been updated");
            return true;
        }

    }

    public static void affirmDeleting(int derivedNounId) throws RawNotFoundException {
        Derivednoun newDerivedNoun = BLUtil.daoFactory.getDerivednounDAO().getById(derivedNounId);
        for (Semanticnoun semanticnoun : newDerivedNoun.getSemanticnouns()) {
            SemanticNounBOManager.affirmDeleting(semanticnoun.getIdentity());
        }
        newDerivedNoun.setInfoStatus(WordStatus.NEED_DELETING);
        newDerivedNoun.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
        BOManagerUtil.AFFIRM_SUGGESTION(newDerivedNoun.getSuggestion());
        BLUtil.daoFactory.getDerivednounDAO().update(newDerivedNoun);
    }

    public static void rejectDeleting(int derivedNounId) throws RawNotFoundException {
        Derivednoun newDerivednoun = BLUtil.daoFactory.getDerivednounDAO().getById(derivedNounId);
        BOManagerUtil.REJECT_SUGGESTION(newDerivednoun.getSuggestion());
        newDerivednoun.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        BLUtil.daoFactory.getDerivednounDAO().update(newDerivednoun);
    }

    public static void main(String[] arg) {
        try {
            User currUser = BLUtil.daoFactory.getUserDAO().getById(5);
            BOManagerUtil.setCurrentUser(currUser);
            /**
             * *
             * test needCheck()
             */
            /*List<NounBO> needCheckNouns = getNeedCheck();
             for ( NounBO noun : needCheckNouns )
             {
             System.out.print( "[" + noun.getDerivedNounId() + "] , " );
             }*/
            /*NounBO newNounBO = new NounBO();
             newNounBO.setVocalizedNoun( "testAAU" );
             newNounBO.setPattern( "testAAU" );
             newNounBO.setRoot( "testAAU" );
             newNounBO.setGender( "testAAU" );
             newNounBO.setNumber( "testAAU" );
             newNounBO.setOrigin( "testAAU" );
             newNounBO.setType( "testAAU" );
             affirmAddingAU( 41368 , newNounBO );*/
            /**
             * test affirmUpdating()
             */
            //affirmUpdating( 23353 );
            /**
             * test rejectUpdating()
             */
            //rejectUpdating( 5006 );
            /**
             * test affirmUpdatingAU()
             */
            /*NounBO newNounBO = new NounBO();
             newNounBO.setVocalizedNoun( "testUAU" );
             newNounBO.setPattern( "testUAU" );
             newNounBO.setRoot( "testUAU" );
             newNounBO.setGender( "testUAU" );
             newNounBO.setNumber( "testUAU" );
             newNounBO.setOrigin( "testUAU" );
             newNounBO.setType( "testUAU" );
             affirmUpdatingAU( 24535 , newNounBO );*/
            /**
             * test rejectDeleting()
             */
            rejectDeleting(4981);

        } catch (Exception ex) {
            Logger.getLogger(VerbBOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getCheckedNounWeight(int nounId) throws RawNotFoundException {
        int weight = 0;
        Derivednoun newDerivednoun = BLUtil.daoFactory.getDerivednounDAO().getById(nounId);
        if ("IUD".contains(newDerivednoun.getInfoStatus())) {
            weight++;
        }
        Set<Semanticnoun> semNouns = newDerivednoun.getSemanticnouns();
        for (Semanticnoun semNoun : semNouns) {
            if ("IUD".contains(semNoun.getInfoStatus())) {
                weight++;
            }
        }
        return weight;
    }
}

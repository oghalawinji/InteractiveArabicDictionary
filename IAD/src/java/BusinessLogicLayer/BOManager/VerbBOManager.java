package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.SemanticVerbBO;
import BusinessLogicLayer.BusinessObjects.VerbBO;
import BusinessLogicLayer.BusinessObjects.UpdatedVerbBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.SystemStateManager;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.CompareWordsBO;
import BusinessLogicLayer.Util.FilterDiacritics;
import BusinessLogicLayer.Util.WordStatus;
import PersistenceLayer.Contextualverb;
import PersistenceLayer.Derivedverb;
import PersistenceLayer.Pattern;
import PersistenceLayer.Rawword;
import PersistenceLayer.Root;
import PersistenceLayer.Semanticverb;
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
public class VerbBOManager {

    private WordBOManager newWordBOManager;
    private PatternManager newPatternManager;
    private RootManager newRootManager;

    public VerbBOManager() {
        newWordBOManager = new WordBOManager();
        newPatternManager = new PatternManager();
        newRootManager = new RootManager();
    }

    public List<VerbBO> fastListVerbLike(String verb, int i) throws RawNotFoundException {
        verb = verb.trim();
        String filteredVerb = FilterDiacritics.execute(verb);
        List<Rawword> rawwords = BLUtil.daoFactory.getRawwordDAO().getWordsLike(filteredVerb + "%");
        List<VerbBO> verbs = new ArrayList<VerbBO>();
        int c = 0;
        for (Rawword rawword : rawwords) {
            List<Derivedverb> derivedverbs = new ArrayList<Derivedverb>(rawword.getDerivedverbs());

            for (Derivedverb derivedverb : derivedverbs) {
                verbs.add(get(derivedverb, new SearchProperties(), ""));
            }
            if (i <= c) {
                return verbs;
            }
            c++;
        }
        return verbs;
    }

    public static boolean hasNewValues(int derivedVerbId) throws RawNotFoundException {
        Derivedverb oldVerb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId);
        return hasNewValues(oldVerb);
    }

    public static boolean hasNewValues(Derivedverb oldVerb) {
        if (oldVerb.getInfoStatus().equals("U")) {
            Suggestion updateSuggestion = oldVerb.getSuggestion();
            if (updateSuggestion != null && !(updateSuggestion.getAffirmStatus().equals("A")) && updateSuggestion.getInfoStatus().equals("U")) {
                return true;
            }
        }
        return false;
    }

    ///<editor-fold defaultstate="collapsed" desc="getters....">
    public static VerbBO get(Integer derivedVerbId, String mode) throws RawNotFoundException {
        return get(derivedVerbId, SearchProperties.detailedSearchOptions, mode);
    }

    public static VerbBO get(Integer derivedVerbId, SearchProperties options, String mode) throws RawNotFoundException {
        return get(BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId), options, mode);
    }

    public static VerbBO get(Derivedverb derivedVerb, String mode) throws RawNotFoundException {
        return get(derivedVerb, SearchProperties.detailedSearchOptions, mode);
    }

    public static VerbBO get(Derivedverb derivedVerb, SearchProperties options, String mode) {
        return get(derivedVerb, -1, options, mode);
    }

    public static VerbBO get(Integer derivedVerbId, Integer semanticVerbId, String mode) {
        return get(derivedVerbId, semanticVerbId, SearchProperties.detailedSearchOptions, mode);
    }

    public static VerbBO get(Integer derivedVerbId, Integer semanticVerbId, SearchProperties options, String mode) {
        try {
            return get(BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId), semanticVerbId, options, mode);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(VerbBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private static VerbBO get(Derivedverb derivedVerb, Integer semanticVerbId, String mode) {
        return get(derivedVerb, semanticVerbId, SearchProperties.detailedSearchOptions, mode);
    }

    private static VerbBO get(Derivedverb derivedVerb, Integer semanticVerbId, SearchProperties options, String mode) {

        if (derivedVerb.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS) && hasNewValues(derivedVerb)) {
            UpdatedVerbBO verb = new UpdatedVerbBO();
            if (!SystemStateManager.available(mode, derivedVerb.getInfoStatus(), derivedVerb.getChechStatus())) {
                return null;
            }
            try {
                verb.setDerivedVerbId(derivedVerb.getIdentity());
                verb.setRoot(derivedVerb.getRoot().getRoot());
                verb.setPattern(derivedVerb.getPattern().getPattern());
                verb.setPresentForm(derivedVerb.getPresentForm());
                verb.setVocalizedVerb(derivedVerb.getVocalizedVerb());
                verb.setStatus(derivedVerb.getInfoStatus());

                Derivedverb newVerb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerb.getSuggestion().getUpdateId());
                verb.setNewDerivedVerbId(newVerb.getIdentity());
                verb.setNewPattern(newVerb.getPattern().getPattern());
                verb.setNewPresentForm(newVerb.getPresentForm());
                verb.setNewRoot(newVerb.getRoot().getRoot());
                verb.setNewVocalizedVerb(newVerb.getVocalizedVerb());

                //verb.setPhonetic(derivedVerb.getPhonetic());
                //Retrieve Semantic Cases:
                if (options.FindSemanticInformations) {
                    List<SemanticVerbBO> semanticCases = new ArrayList<SemanticVerbBO>();

                    Set<Contextualverb> contextVerbList = derivedVerb.getContextualverbs();
                    for (Iterator iter2 = contextVerbList.iterator(); iter2.hasNext();) {
                        Contextualverb contextVerb = (Contextualverb) iter2.next();

                        if (!SystemStateManager.available(mode, contextVerb.getInfoStatus(), contextVerb.getChechStatus())) {
                            continue;
                        }

                        Set<Semanticverb> semVerbSet = contextVerb.getSemanticverbs();
                        for (Iterator iter3 = semVerbSet.iterator(); iter3.hasNext();) {
                            //each derived particle:
                            Semanticverb semVerb = (Semanticverb) iter3.next();

                            if (!SystemStateManager.available(mode, semVerb.getInfoStatus(), semVerb.getChechStatus())) {
                                continue;
                            }

                            SemanticVerbBO semanticVerbBO = SemanticVerbBOManager.getSemanticVerbBO(semVerb, options);
                            int vId = semanticVerbBO.getSemanticVerbId();
                            //Fadel-Note005:why we need this comparison here
                            if (vId == semanticVerbId || semanticVerbId < 0) {
                                semanticCases.add(semanticVerbBO);
                            }
                        }
                    }

                    Collections.sort(semanticCases, new CompareWordsBO());

                    verb.setSemanticCases(semanticCases);
                }
            } catch (RawNotFoundException ex) {
                Logger.getLogger(VerbBOManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            return verb;
        } else {
            VerbBO verb = new VerbBO();
            if (!SystemStateManager.available(mode, derivedVerb.getInfoStatus(), derivedVerb.getChechStatus())) {
                return null;
            }

            verb.setDerivedVerbId(derivedVerb.getIdentity());
            verb.setVocalizedVerb(derivedVerb.getVocalizedVerb());
            verb.setRoot(derivedVerb.getRoot().getRoot());
            verb.setPattern(derivedVerb.getPattern().getPattern());
            verb.setPresentForm(derivedVerb.getPresentForm());
            verb.setStatus(derivedVerb.getInfoStatus());
            //verb.setPhonetic(derivedVerb.getPhonetic());

            //Retrieve Semantic Cases:
            if (options.FindSemanticInformations) {
                List<SemanticVerbBO> semanticCases = new ArrayList<SemanticVerbBO>();

                Set<Contextualverb> contextVerbList = derivedVerb.getContextualverbs();
                for (Iterator iter2 = contextVerbList.iterator(); iter2.hasNext();) {
                    Contextualverb contextVerb = (Contextualverb) iter2.next();

                    if (!SystemStateManager.available(mode, contextVerb.getInfoStatus(), contextVerb.getChechStatus())) {
                        continue;
                    }

                    Set<Semanticverb> semVerbSet = contextVerb.getSemanticverbs();
                    for (Iterator iter3 = semVerbSet.iterator(); iter3.hasNext();) {
                        //each derived particle:
                        Semanticverb semVerb = (Semanticverb) iter3.next();

                        if (!SystemStateManager.available(mode, semVerb.getInfoStatus(), semVerb.getChechStatus())) {
                            continue;
                        }

                        SemanticVerbBO semanticVerbBO = SemanticVerbBOManager.getSemanticVerbBO(semVerb, options);
                        int vId = semanticVerbBO.getSemanticVerbId();
                        //Fadel-Note005:why we need this comparison here
                        if (vId == semanticVerbId || semanticVerbId < 0) {
                            semanticCases.add(semanticVerbBO);
                        }
                    }
                }

                Collections.sort(semanticCases, new CompareWordsBO());

                verb.setSemanticCases(semanticCases);
            }
            return verb;
        }
    }
//</editor-fold>

    public void suggestDeleting(int derivedVerbId) throws RawNotFoundException {
        Derivedverb derivedverb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId);

        derivedverb.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        derivedverb.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        derivedverb.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        BLUtil.daoFactory.getDerivedverbDAO().update(derivedverb);
    }

    public void suggestUpdating(Integer Id, VerbBO newObj) throws RawNotFoundException {
        Derivedverb derivedverb = BLUtil.daoFactory.getDerivedverbDAO().getById(Id);
        VerbBO newVerbBO = new VerbBO();
        newVerbBO.setRoot(derivedverb.getRoot().getRoot());
        newVerbBO.setPattern(derivedverb.getPattern().getPattern());
        newVerbBO.setPresentForm(derivedverb.getPresentForm());
        newVerbBO.setRawWord(derivedverb.getRawword().getRawWord());
        newVerbBO.setStatus(derivedverb.getInfoStatus());

        if (!newObj.equals(newVerbBO)) {
            int tempVerbId = this.addTempVerbBO(newObj);

            derivedverb.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
            derivedverb.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
            derivedverb.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(tempVerbId));
            BLUtil.daoFactory.getDerivedverbDAO().update(derivedverb);
        }
    }

    public int addTempVerbBO(VerbBO verbBO) throws RawNotFoundException {
        String rawWord = FilterDiacritics.execute(verbBO.getVocalizedVerb());
        Map restrictions = BOManagerUtil.getAddRestrictions();
        int newRawWordId = newWordBOManager.suggestAdding(rawWord);
        restrictions.put("eq_rawword.rawWordId", newRawWordId);
        Rawword newRawword = BLUtil.daoFactory.getRawwordDAO().getById(newRawWordId);

        int newPatternId = newPatternManager.suggestAdding(verbBO.getPattern());
        Pattern newPattern = BLUtil.daoFactory.getPatternDAO().getById(newPatternId);
        restrictions.put("eq_pattern.patternId", newPatternId);

        int newRootId = newRootManager.suggestAdding(verbBO.getRoot());
        Root newRoot = BLUtil.daoFactory.getRootDAO().getById(newRootId);
        restrictions.put("eq_root.rootId", newRootId);

        Derivedverb derivedVerb = new Derivedverb();
        derivedVerb.setPattern(newPattern);
        derivedVerb.setPresentForm(verbBO.getPresentForm());
        derivedVerb.setRawword(newRawword);
        derivedVerb.setRoot(newRoot);
        derivedVerb.setVocalizedVerb(verbBO.getVocalizedVerb());
        //derivedVerb.setPronunciation (new Pronunciation ());
        derivedVerb.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
        derivedVerb.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());
        int id = BLUtil.daoFactory.getDerivedverbDAO().insertWithCheck(derivedVerb, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES,
                restrictions);
        return id;
    }

    public int suggestAdding(VerbBO verbBO) throws EntryExistedException, RawNotFoundException {
        String rawWord = FilterDiacritics.execute(verbBO.getVocalizedVerb());
        Map restrictions = BOManagerUtil.getAddRestrictions();
        int newRawWordId = newWordBOManager.suggestAdding(rawWord);
        restrictions.put("eq_rawword.rawWordId", newRawWordId);
        Rawword newRawword = BLUtil.daoFactory.getRawwordDAO().getById(newRawWordId);

        int newPatternId = newPatternManager.suggestAdding(verbBO.getPattern());
        Pattern newPattern = BLUtil.daoFactory.getPatternDAO().getById(newPatternId);
        restrictions.put("eq_pattern.patternId", newPatternId);

        int newRootId = newRootManager.suggestAdding(verbBO.getRoot());
        Root newRoot = BLUtil.daoFactory.getRootDAO().getById(newRootId);
        restrictions.put("eq_root.rootId", newRootId);

        Derivedverb derivedVerb = new Derivedverb();
        derivedVerb.setPattern(newPattern);
        derivedVerb.setPresentForm(verbBO.getPresentForm());
        derivedVerb.setRawword(newRawword);
        derivedVerb.setRoot(newRoot);
        derivedVerb.setVocalizedVerb(verbBO.getVocalizedVerb());
        //derivedVerb.setPronunciation (new Pronunciation ());
        derivedVerb.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        derivedVerb.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        derivedVerb.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        int id = BLUtil.daoFactory.getDerivedverbDAO().insertWithCheck(derivedVerb, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES,
                restrictions, "الفعل موجود مسبقاً.");
        //BLUtil.daoFactory.getDerivedverbDAO().commitTransaction();
        return id;
    }

    public static void setNeedCheck(int verbId) throws RawNotFoundException {
        Derivedverb newDerivedverb = BLUtil.daoFactory.getDerivedverbDAO().getById(verbId);

        newDerivedverb.setChechStatus(1);

        BLUtil.daoFactory.getDerivedverbDAO().update(newDerivedverb);
    }

    public List<VerbBO> listVerbs(String verb) {
        verb = verb.trim();
        String filteredVerb = FilterDiacritics.execute(verb);
        List<Rawword> rawwords = BLUtil.daoFactory.getRawwordDAO().getWordsLike(filteredVerb);
        List<VerbBO> verbs = new ArrayList<VerbBO>();

        for (Rawword rawword : rawwords) {
            List<Derivedverb> derivedverbs = new ArrayList<Derivedverb>(rawword.getDerivedverbs());

            for (Derivedverb derivedverb : derivedverbs) {
                verbs.add(get(derivedverb, SearchProperties.simpleSearchOptions, ""));
            }
        }
        return verbs;

    }

    public static List<VerbBO> getNeedCheck() throws RawNotFoundException {
        List<Derivedverb> verbs = BLUtil.daoFactory.getDerivedverbDAO().getNeedCheck();
        List<VerbBO> needCheckVerbs = new ArrayList<VerbBO>();
        for (Derivedverb verb : verbs) {
            needCheckVerbs.add(VerbBOManager.get(verb, new SearchProperties(), ""));
        }
        return needCheckVerbs;
    }

    public static void affirmAdding(int derivedVerbId) throws RawNotFoundException {
        Derivedverb newDerivedverb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId);
        if (newDerivedverb.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newDerivedverb.setInfoStatus("S");
            //newDerivedverb.getRawword ().setInfoStatus( "S" );
            if (newDerivedverb.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newDerivedverb.getSuggestion());
            }
            BLUtil.daoFactory.getDerivedverbDAO().update(newDerivedverb);
        }
    }

    public static void clearCheck(int derivedVerbId) throws RawNotFoundException {
        Derivedverb newDerivedverb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId);
        newDerivedverb.setChechStatus(0);
        BLUtil.daoFactory.getDerivedverbDAO().update(newDerivedverb);
    }

    public static boolean affirmAddingAU(int derivedVerbId, VerbBO updatedVerb) throws RawNotFoundException {
        Derivedverb newDerivedverb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId);
        String rawWord = FilterDiacritics.execute(updatedVerb.getVocalizedVerb());
        Map restrictions = new HashMap();
        int newRawWordId = WordBOManager.suggestAdding(rawWord);
        restrictions.put("eq_rawword.rawWordId", newRawWordId);
        Rawword newRawword = BLUtil.daoFactory.getRawwordDAO().getById(newRawWordId);

        int newPatternId = PatternManager.suggestAdding(updatedVerb.getPattern());
        Pattern newPattern = BLUtil.daoFactory.getPatternDAO().getById(newPatternId);
        restrictions.put("eq_pattern.patternId", newPatternId);

        int newRootId = RootManager.suggestAdding(updatedVerb.getRoot());
        Root newRoot = BLUtil.daoFactory.getRootDAO().getById(newRootId);
        restrictions.put("eq_root.rootId", newRootId);
        // if the updated verb  already exists in the database then return false and the expert can decide what to do.
        if (BLUtil.daoFactory.getDerivedverbDAO().getByExample(new Derivedverb(), BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions).size() > 0) {
            //System.out.println( "the verb already exists in the database....");
            return false;
        } else {
            newDerivedverb.setPattern(newPattern);
            newDerivedverb.setPresentForm(updatedVerb.getPresentForm());
            newDerivedverb.setRawword(newRawword);
            newDerivedverb.setRoot(newRoot);
            newDerivedverb.setVocalizedVerb(updatedVerb.getVocalizedVerb());
            //derivedVerb.setPronunciation (new Pronunciation ());
            newDerivedverb.setInfoStatus(BOManagerUtil.CONFIRM_STATUS.getInfoStatus());
            BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(newDerivedverb.getSuggestion());
            BLUtil.daoFactory.getDerivedverbDAO().update(newDerivedverb);
            return true;
        }
    }

    public static void rejectAdding(int derivedVerbId) throws RawNotFoundException {
        Derivedverb newDerivedVerb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId);
        for (Contextualverb contextualverb : newDerivedVerb.getContextualverbs()) {
            for (Semanticverb semanticverb : contextualverb.getSemanticverbs()) {
                SemanticVerbBOManager.rejectAdding(semanticverb.getIdentity());
            }
            contextualverb.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
            BOManagerUtil.REJECT_SUGGESTION(contextualverb.getSuggestion());
            BLUtil.daoFactory.getContextualverbDAO().update(contextualverb);
        }
        newDerivedVerb.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
        BOManagerUtil.REJECT_SUGGESTION(newDerivedVerb.getSuggestion());
        BLUtil.daoFactory.getDerivedverbDAO().update(newDerivedVerb);
    }

    public static void affirmUpdating(int derivedVerbId) throws RawNotFoundException {
        Derivedverb oldVerb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId);
        Suggestion updateSuggestion = oldVerb.getSuggestion();
        // get update verb id from old verb suggestion.
        Derivedverb newVerb = BLUtil.daoFactory.getDerivedverbDAO().getById(updateSuggestion.getUpdateId());
        Derivedverb copyOfNewVerb = new Derivedverb(null, newVerb.getRawword(), newVerb.getPattern(), newVerb.getRoot(),
                newVerb.getVocalizedVerb(), newVerb.getPresentForm());
        // update verb will store old verb values before updates.
        newVerb.setRawword(oldVerb.getRawword());
        newVerb.setPattern(oldVerb.getPattern());
        newVerb.setRoot(oldVerb.getRoot());
        newVerb.setVocalizedVerb(oldVerb.getVocalizedVerb());
        newVerb.setPresentForm(oldVerb.getVocalizedVerb());
        newVerb.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
        // assign new values to the original verb.
        oldVerb.setRawword(copyOfNewVerb.getRawword());
        oldVerb.setPattern(copyOfNewVerb.getPattern());
        oldVerb.setRoot(copyOfNewVerb.getRoot());
        oldVerb.setPresentForm(copyOfNewVerb.getPresentForm());
        oldVerb.setVocalizedVerb(copyOfNewVerb.getVocalizedVerb());
        oldVerb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);

        BOManagerUtil.AFFIRM_SUGGESTION(oldVerb.getSuggestion());

        BLUtil.daoFactory.getDerivedverbDAO().update(newVerb);
        BLUtil.daoFactory.getDerivedverbDAO().update(oldVerb);
    }

    private static void affirmUpdatingAfterAltering(Derivedverb oldVerb) throws RawNotFoundException {
        Suggestion updateSuggestion = oldVerb.getSuggestion();
        // get update verb id from old verb suggestion.
        Derivedverb newVerb = BLUtil.daoFactory.getDerivedverbDAO().getById(updateSuggestion.getUpdateId());
        Derivedverb copyOfNewVerb = new Derivedverb(null, newVerb.getRawword(), newVerb.getPattern(), newVerb.getRoot(),
                newVerb.getVocalizedVerb(), newVerb.getPresentForm());
        // update verb will store old verb values before updates.
        newVerb.setRawword(oldVerb.getRawword());
        newVerb.setPattern(oldVerb.getPattern());
        newVerb.setRoot(oldVerb.getRoot());
        newVerb.setVocalizedVerb(oldVerb.getVocalizedVerb());
        newVerb.setPresentForm(oldVerb.getVocalizedVerb());
        newVerb.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
        // assign new values to the original verb.
        oldVerb.setRawword(copyOfNewVerb.getRawword());
        oldVerb.setPattern(copyOfNewVerb.getPattern());
        oldVerb.setRoot(copyOfNewVerb.getRoot());
        oldVerb.setPresentForm(copyOfNewVerb.getPresentForm());
        oldVerb.setVocalizedVerb(copyOfNewVerb.getVocalizedVerb());
        oldVerb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);

        BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldVerb.getSuggestion());

        BLUtil.daoFactory.getDerivedverbDAO().update(newVerb);
        BLUtil.daoFactory.getDerivedverbDAO().update(oldVerb);
    }

    public static void rejectUpdating(int derivedVerbId) throws RawNotFoundException {
        Derivedverb originalDerivedverb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId);
        Derivedverb updatedDerivedverb = BLUtil.daoFactory.getDerivedverbDAO().getById(originalDerivedverb.getSuggestion().getUpdateId());

        originalDerivedverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        BOManagerUtil.REJECT_SUGGESTION(originalDerivedverb.getSuggestion());
        BLUtil.daoFactory.getDerivedverbDAO().update(originalDerivedverb);

        updatedDerivedverb.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
        BLUtil.daoFactory.getDerivedverbDAO().update(updatedDerivedverb);
    }

    public static boolean affirmUpdatingAU(int derivedVerbId, VerbBO updateVerb) throws RawNotFoundException {
        Derivedverb originalDerivedverb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId);
        Derivedverb updatedDerivedverb = BLUtil.daoFactory.getDerivedverbDAO().getById(originalDerivedverb.getSuggestion().getUpdateId());

        String rawWord = FilterDiacritics.execute(updateVerb.getVocalizedVerb());
        Map restrictions = new HashMap();
        int newRawWordId = WordBOManager.suggestAdding(rawWord);
        restrictions.put("eq_rawword.rawWordId", newRawWordId);
        Rawword newRawword = BLUtil.daoFactory.getRawwordDAO().getById(newRawWordId);

        int newPatternId = PatternManager.suggestAdding(updateVerb.getPattern());
        Pattern newPattern = BLUtil.daoFactory.getPatternDAO().getById(newPatternId);
        restrictions.put("eq_pattern.patternId", newPatternId);

        int newRootId = RootManager.suggestAdding(updateVerb.getRoot());
        Root newRoot = BLUtil.daoFactory.getRootDAO().getById(newRootId);
        restrictions.put("eq_root.rootId", newRootId);

//        Derivedverb newDerivedverb = new Derivedverb( null, newRawword, newPattern, newRoot, updateVerb.getVocalizedVerb(),updateVerb.getPresentForm());
        //derivedVerb.setPronunciation (new Pronunciation ());
        // if the updated verb  already exists in the database then return false and the expert can decide what to do.
        if (BLUtil.daoFactory.getDerivedverbDAO().getByExample(new Derivedverb(), BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions).size() > 0) {
            //System.out.println( "the verb already exists in the database....");
            return false;
        } else {
            updatedDerivedverb.setPattern(newPattern);
            updatedDerivedverb.setPresentForm(updateVerb.getPresentForm());
            updatedDerivedverb.setRawword(newRawword);
            updatedDerivedverb.setRoot(newRoot);
            updatedDerivedverb.setVocalizedVerb(updateVerb.getVocalizedVerb());
            BLUtil.daoFactory.getDerivedverbDAO().update(updatedDerivedverb);
            affirmUpdatingAfterAltering(originalDerivedverb);
            return true;
        }
    }

    public static void affirmDeleting(int derivedVerbId) throws RawNotFoundException {
        Derivedverb newDerivedVerb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId);
        for (Contextualverb contextualverb : newDerivedVerb.getContextualverbs()) {
            for (Semanticverb semanticverb : contextualverb.getSemanticverbs()) {
                SemanticVerbBOManager.affirmDeleting(semanticverb.getIdentity());
            }
            contextualverb.setInfoStatus(WordStatus.NEED_DELETING);
            contextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            BOManagerUtil.AFFIRM_SUGGESTION(contextualverb.getSuggestion());
            BLUtil.daoFactory.getContextualverbDAO().update(contextualverb);
        }
        newDerivedVerb.setInfoStatus(WordStatus.NEED_DELETING);
        newDerivedVerb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
        BOManagerUtil.AFFIRM_SUGGESTION(newDerivedVerb.getSuggestion());
        BLUtil.daoFactory.getDerivedverbDAO().update(newDerivedVerb);
    }

    public static void rejectDeleting(int derivedVerbId) throws RawNotFoundException {
        Derivedverb newDerivedverb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId);
        BOManagerUtil.REJECT_SUGGESTION(newDerivedverb.getSuggestion());
        newDerivedverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        BLUtil.daoFactory.getDerivedverbDAO().update(newDerivedverb);
    }

    public static void main(String[] arg) {
        try {
            User currUser = BLUtil.daoFactory.getUserDAO().getById(5);
            BOManagerUtil.setCurrentUser(currUser);
            /**
             * *
             * test needCheck()
             */
            /*List<VerbBO> needCheckVerbs = getNeedCheck();
             for ( VerbBO verb : needCheckVerbs )
             {
             System.out.print( "[" + verb.getDerivedVerbId() + "] , " );
             }*/
            /**
             * test affirmAddingAU()
             */
            /*VerbBO newVerbBO = new VerbBO();
             newVerbBO.setVocalizedVerb( "testAAU" );
             newVerbBO.setPresentForm( "testAAU" );
             newVerbBO.setRoot( "testAAU" );
             newVerbBO.setPattern( "testAAU" );
             affirmAddingAU( 18421 , newVerbBO );*/
            /**
             * *
             * test affirmUpdating()
             */
            //affirmUpdating( 7572 );
            /**
             * test rejectUpdating()
             */
            //rejectUpdating( 8142 );
            /**
             * test affirmUpdatingAU()
             */
            /*VerbBO newVerbBO = new VerbBO();
             newVerbBO.setVocalizedVerb( "testUAU" );
             newVerbBO.setPresentForm( "testUAU" );
             newVerbBO.setRoot( "testUAU" );
             newVerbBO.setPattern( "testUAU" );
             affirmUpdatingAU( 16290 , newVerbBO );*/
            /**
             * test rejectDeleting()
             */
            //rejectDeleting( 7455);
            for (VerbBO verb : getNeedCheck()) {
                System.out.println(verb.getVocalizedVerb());
            }
        } catch (Exception ex) {
            Logger.getLogger(VerbBOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getCheckedVerbWeight(int verbId) throws RawNotFoundException {
        int weight = 0;
        Derivedverb newDerivedverb = BLUtil.daoFactory.getDerivedverbDAO().getById(verbId);
        if ("IUD".contains(newDerivedverb.getInfoStatus())) {
            weight++;
        }
        Set<Contextualverb> conVerbs = newDerivedverb.getContextualverbs();
        for (Contextualverb conVerb : conVerbs) {
            Set<Semanticverb> semVerbs = conVerb.getSemanticverbs();
            for (Semanticverb semVerb : semVerbs) {
                if ("IUD".contains(semVerb.getInfoStatus())) {
                    weight++;
                }
            }
        }
        return weight;
    }
}

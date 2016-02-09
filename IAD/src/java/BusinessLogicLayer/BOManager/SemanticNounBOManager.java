/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.AdjectiveAccompanierBO;
import BusinessLogicLayer.BusinessObjects.AnnexedNounBO;
import BusinessLogicLayer.BusinessObjects.CommonMistakeBO;
import BusinessLogicLayer.BusinessObjects.DiminutiveBO;
import BusinessLogicLayer.BusinessObjects.ExampleBO;
import BusinessLogicLayer.BusinessObjects.FemininityBO;
import BusinessLogicLayer.BusinessObjects.LinguisticBenefitBO;
import BusinessLogicLayer.BusinessObjects.MeaningBO;
import BusinessLogicLayer.BusinessObjects.NounBO;
import BusinessLogicLayer.BusinessObjects.ProperAdjectiveBO;
import BusinessLogicLayer.BusinessObjects.SemanticNounBO;
import BusinessLogicLayer.BusinessObjects.UpdatedSemanticNounBO;
import BusinessLogicLayer.BusinessObjects.VerbAccompanierBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.SemanticnounJPADAO;
import PersistenceLayer.Annexednoun;
import PersistenceLayer.Derivednoun;
import PersistenceLayer.Diminutive;
import PersistenceLayer.Femininity;
import PersistenceLayer.Nounadjectiveaccompanier;
import PersistenceLayer.Nounverbaccompanier;
import PersistenceLayer.Properadjective;
import PersistenceLayer.Semanticentry;
import PersistenceLayer.Semanticnoun;
import PersistenceLayer.Semanticverb;
import PersistenceLayer.Suggestion;
import PersistenceLayer.User;
import Util.RawNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riad
 */
public class SemanticNounBOManager extends SemanticEntryBOManager {

    private SemanticEntryBOManager newSemanticEntryBOManager;
    private MeaningBOManager newMeaningBOManager;
    private static SemanticnounJPADAO SEMANTIC_NOUN_DAO = BLUtil.daoFactory.getSemanticnounDAO();

    public SemanticNounBOManager() {
        newSemanticEntryBOManager = new SemanticEntryBOManager();
        newMeaningBOManager = new MeaningBOManager();
    }
    /// <editor-fold defaultstate="collapsed" desc="getters....">

    public static SemanticNounBO getSemanticNounBO(Semanticnoun semNoun) throws RawNotFoundException {
        return getSemanticNounBO(semNoun, SearchProperties.detailedSearchOptions);
    }

    public static SemanticNounBO getSemanticNounBO(Integer semanticWordId, SearchProperties options) {
        Semanticnoun semNoun;
        try {
            semNoun = SEMANTIC_NOUN_DAO.getById(semanticWordId);
            return getSemanticNounBO(semNoun, options);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(SemanticNounBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static boolean hasNewValues(Semanticnoun newSemanticNoun) {
        Suggestion newSuggestion = newSemanticNoun.getSuggestion();
        if (newSuggestion != null && newSuggestion.getInfoStatus().equals("U") && !(newSuggestion.getAffirmStatus().equals("A"))) {
            return true;
        }
        return false;
    }

    public static boolean hasNewValues(int semanticNounId) throws RawNotFoundException {
        Semanticnoun newSemanticNoun = BLUtil.daoFactory.getSemanticnounDAO().getById(semanticNounId);
        return hasNewValues(newSemanticNoun);
    }

    public static SemanticNounBO getSemanticNounBO(Semanticnoun semNoun, SearchProperties options) throws RawNotFoundException {
        if (semNoun.getInfoStatus().equals("U")) {
            UpdatedSemanticNounBO semanticNounBO = new UpdatedSemanticNounBO();
            //if there is no suggestion then update value is containted in semanticentry update.
            if (!hasNewValues(semNoun)) {
                Semanticentry newSemanticentry = semNoun.getSemanticentry();
                Suggestion newSuggestion = newSemanticentry.getSuggestion();
                if (newSuggestion != null && newSuggestion.getInfoStatus().equals("U") && !(newSuggestion.getAffirmStatus().equals("A"))) {
                    Semanticentry tempSemanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(newSuggestion.getUpdateId());
                    semanticNounBO.setNewDifficultydegree(tempSemanticentry.getDifficultydegree().getDifficultyDegree());
                    semanticNounBO.setNewEpoch(tempSemanticentry.getEpoch().getEpoch());
                    semanticNounBO.setNewRegion(tempSemanticentry.getRegion().getRegion());
                    semanticNounBO.setNewSemanticscop(tempSemanticentry.getSemanticscop().getSemanticScop());
                    semanticNounBO.setNewSpecialization(tempSemanticentry.getSpecialization().getSpecialization());
                    semanticNounBO.setNewSpreadingdegree(tempSemanticentry.getSpreadingdegree().getSpreadingDegree());
                }
            } // there is a suggestion then update values is contained in a temp semantic entry.
            else {
                Suggestion newSuggestion = semNoun.getSuggestion();
                Semanticentry tempSemanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(newSuggestion.getUpdateId());
                semanticNounBO.setNewDifficultydegree(tempSemanticentry.getDifficultydegree().getDifficultyDegree());
                semanticNounBO.setNewEpoch(tempSemanticentry.getEpoch().getEpoch());
                semanticNounBO.setNewRegion(tempSemanticentry.getRegion().getRegion());
                semanticNounBO.setNewSemanticscop(tempSemanticentry.getSemanticscop().getSemanticScop());
                semanticNounBO.setNewSpecialization(tempSemanticentry.getSpecialization().getSpecialization());
                semanticNounBO.setNewSpreadingdegree(tempSemanticentry.getSpreadingdegree().getSpreadingDegree());
            }

            semanticNounBO.setSemanticNounId(semNoun.getIdentity());

            //Retrieve and Save semantic entry info
            Semanticentry semEntry = semNoun.getSemanticentry();
            semanticNounBO.setSemanticEntryId(semEntry.getIdentity());
            semanticNounBO.setDifficultydegree(semEntry.getDifficultydegree().getDifficultyDegree());
            semanticNounBO.setEpoch(semEntry.getEpoch().getEpoch());
            semanticNounBO.setRegion(semEntry.getRegion().getRegion());
            semanticNounBO.setSemanticscop(semEntry.getSemanticscop().getSemanticScop());
            semanticNounBO.setSpecialization(semEntry.getSpecialization().getSpecialization());
            semanticNounBO.setSpreadingdegree(semEntry.getSpreadingdegree().getSpreadingDegree());
            semanticNounBO.setStatus(semNoun.getInfoStatus());
            semanticNounBO.setChecked(semNoun.getChechStatus() == 1 ? true : false);
            //Semantic Entry Related Infos:
            if (options.FindMeanings) {
                semanticNounBO.setMeanings(MeaningBOManager.getMeaningsOfSemanticEntry(semEntry));
            }
            if (options.FindCommonMistakes) {
                semanticNounBO.setCommonMistakes(CommonMistakeBOManager.getCommonMistakesOfSemanticEntry(semEntry));
            }
            if (options.FindLinguisticBenifits) {
                semanticNounBO.setLinguisticBenefits(LinguisticBenefitBOManager.getLinguisticBenefitsOfSemanticEntry(semEntry));
            }
            if (options.FindExamples) {
                semanticNounBO.setExamples(ExampleBOManager.getExamplesOfSemanticEntry(semEntry));
            }
            if (options.FindRelatedIdiom) {
                semanticNounBO.setRelatedidioms(IdiomBOManager.getRelatedIdiom(semEntry));
            }
            if (options.FindNoun) {
                NounBO newNounBO = NounBOManager.getNounBO(semNoun.getDerivednoun().getIdentity(), new SearchProperties(), "");
                semanticNounBO.setNoun(newNounBO);
            }

            //Semantic Noun related Infos:
            if (options.FindNounAdjectiveAccompaniers) {
                semanticNounBO.setAdjectiveAccompaniers(NounAdjectiveAccompanierManager.getAdjectiveAccompaniersForSemanticNoun(semNoun));
            }
            if (options.FindNounDiminutives) {
                semanticNounBO.setDiminutives(DiminutiveManager.getDiminutiveForSemanticNoun(semNoun));
            }
            if (options.FindProperAdjectives) {
                semanticNounBO.setProperAdjectives(ProperAdjectiveManager.getProperAdjectiveForSemanticNoun(semNoun));
            }
            if (options.FindAnnexedNouns) {
                semanticNounBO.setAnnexedNouns(AnnexedNounManager.getAnnexedNounForSemanticNoun(semNoun));
            }
            if (options.FindNounVerbAccompaniers) {
                semanticNounBO.setVerbAccompaniers(NounAccompanierBOManager.getVerbAccompaniersForSemanticNoun(semNoun));
            }
            if (options.FindFimininities) {
                semanticNounBO.setFemininities(FemininityManager.getFemininitiyForSemanticNoun(semNoun));
            }
            if (options.FindPlural) {
                semanticNounBO.setPlurals(PluralManager.getPluralForSemanticNoun(semNoun));
            }

            //multimedia:
            if (options.FindImages) {
                semanticNounBO.setImages(ImageManager.getImage(semEntry));
            }
            if (options.FindSounds) {
                semanticNounBO.setSounds(SoundManager.getSound(semEntry));
            }
            if (options.FindVideos) {
                semanticNounBO.setVideos(VideoManager.getVideo(semEntry));
            }

            return semanticNounBO;

        }

        SemanticNounBO semanticNounBO = new SemanticNounBO();

        //Save semantic Noun ID
        semanticNounBO.setSemanticNounId(semNoun.getIdentity());

        //Retrieve and Save semantic entry info
        Semanticentry semEntry = semNoun.getSemanticentry();
        semanticNounBO.setSemanticEntryId(semEntry.getIdentity());
        semanticNounBO.setDifficultydegree(semEntry.getDifficultydegree().getDifficultyDegree());
        semanticNounBO.setEpoch(semEntry.getEpoch().getEpoch());
        semanticNounBO.setRegion(semEntry.getRegion().getRegion());
        semanticNounBO.setSemanticscop(semEntry.getSemanticscop().getSemanticScop());
        semanticNounBO.setSpecialization(semEntry.getSpecialization().getSpecialization());
        semanticNounBO.setSpreadingdegree(semEntry.getSpreadingdegree().getSpreadingDegree());
        semanticNounBO.setStatus(semNoun.getInfoStatus());
        semanticNounBO.setChecked(semNoun.getChechStatus() == 1 ? true : false);
        //Semantic Entry Related Infos:
        if (options.FindMeanings) {
            semanticNounBO.setMeanings(MeaningBOManager.getMeaningsOfSemanticEntry(semEntry));
        }
        if (options.FindCommonMistakes) {
            semanticNounBO.setCommonMistakes(CommonMistakeBOManager.getCommonMistakesOfSemanticEntry(semEntry));
        }
        if (options.FindLinguisticBenifits) {
            semanticNounBO.setLinguisticBenefits(LinguisticBenefitBOManager.getLinguisticBenefitsOfSemanticEntry(semEntry));
        }
        if (options.FindExamples) {
            semanticNounBO.setExamples(ExampleBOManager.getExamplesOfSemanticEntry(semEntry));
        }
        if (options.FindRelatedIdiom) {
            semanticNounBO.setRelatedidioms(IdiomBOManager.getRelatedIdiom(semEntry));
        }
        if (options.FindNoun) {
            NounBO newNounBO = NounBOManager.getNounBO(semNoun.getDerivednoun().getIdentity(), new SearchProperties(), "");
            semanticNounBO.setNoun(newNounBO);
        }

        //Semantic Noun related Infos:
        if (options.FindNounAdjectiveAccompaniers) {
            semanticNounBO.setAdjectiveAccompaniers(NounAdjectiveAccompanierManager.getAdjectiveAccompaniersForSemanticNoun(semNoun));
        }
        if (options.FindNounDiminutives) {
            semanticNounBO.setDiminutives(DiminutiveManager.getDiminutiveForSemanticNoun(semNoun));
        }
        if (options.FindProperAdjectives) {
            semanticNounBO.setProperAdjectives(ProperAdjectiveManager.getProperAdjectiveForSemanticNoun(semNoun));
        }
        if (options.FindAnnexedNouns) {
            semanticNounBO.setAnnexedNouns(AnnexedNounManager.getAnnexedNounForSemanticNoun(semNoun));
        }
        if (options.FindNounVerbAccompaniers) {
            semanticNounBO.setVerbAccompaniers(NounAccompanierBOManager.getVerbAccompaniersForSemanticNoun(semNoun));
        }
        if (options.FindFimininities) {
            semanticNounBO.setFemininities(FemininityManager.getFemininitiyForSemanticNoun(semNoun));
        }
        if (options.FindPlural) {
            semanticNounBO.setPlurals(PluralManager.getPluralForSemanticNoun(semNoun));
        }

        //multimedia:
        if (options.FindImages) {
            semanticNounBO.setImages(ImageManager.getImage(semEntry));
        }
        if (options.FindSounds) {
            semanticNounBO.setSounds(SoundManager.getSound(semEntry));
        }
        if (options.FindVideos) {
            semanticNounBO.setVideos(VideoManager.getVideo(semEntry));
        }

        return semanticNounBO;
    }
    //</editor-fold>

    public int suggestAdding(SemanticNounBO semanticNounBO, int derivedNounId) throws EntryExistedException, RawNotFoundException {
        int semanticEntryId = newSemanticEntryBOManager.suggestAdding(semanticNounBO);
        Semanticentry newSemanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);

        Derivednoun newDerivednoun = BLUtil.daoFactory.getDerivednounDAO().getById(derivedNounId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_derivednoun.derivedNounId", derivedNounId);
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);

        Semanticnoun newSemanticnoun = new Semanticnoun(newDerivednoun, newSemanticentry);

        newSemanticnoun.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newSemanticnoun.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newSemanticnoun.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

        int semanticNounId = SEMANTIC_NOUN_DAO.insertWithCheck(newSemanticnoun, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

        suggestAddVerbAccompanier(semanticNounId, semanticNounBO.getVerbAccompaniers());
        suggestAddAnnexedNoun(semanticNounId, semanticNounBO.getAnnexedNouns());
        suggestAddAdjectiveAccompanier(semanticNounId, semanticNounBO.getAdjectiveAccompaniers());
        suggestAddFemininity(semanticNounId, semanticNounBO.getFemininities());
        suggestAddDiminutive(semanticNounId, semanticNounBO.getDiminutives());
        suggestAddProperAdjective(semanticNounId, semanticNounBO.getProperAdjectives());

        NounBOManager.setNeedCheck(derivedNounId);
        BLUtil.daoFactory.getIdiomDAO().clearSession();

        return semanticNounId;
    }

    public static void affirmAdding(int semanticNounId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        newSemanticnoun.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        SemanticEntryBOManager.affirmAdding(newSemanticnoun.getSemanticentry().getIdentity());
        BOManagerUtil.AFFIRM_SUGGESTION(newSemanticnoun.getSuggestion());
        SEMANTIC_NOUN_DAO.update(newSemanticnoun);
    }

    public static boolean affirmAddingAU(int semanticNounId, SemanticNounBO updateSemanticNounBO, int derivedNounId) throws RawNotFoundException, EntryExistedException {
        Semanticnoun orginalSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.update(orginalSemanticnoun.getSemanticentry().getIdentity(), updateSemanticNounBO);
        orginalSemanticnoun.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(orginalSemanticnoun.getSuggestion());
        SEMANTIC_NOUN_DAO.update(orginalSemanticnoun);
        return true;
    }

    public static void suggestUpdateInfo(SemanticNounBO newSemanticNounBO, int semanticNounId) throws RawNotFoundException {
        Semanticnoun oldSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        Semanticentry oldSemanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(oldSemanticnoun.getSemanticentry().getIdentity());

        Map restrictions = new HashMap();
        restrictions.put("eq_semanticentry.semanticEntryId", oldSemanticentry.getIdentity());

        List<Semanticnoun> semnticNounList = SEMANTIC_NOUN_DAO.getConfirmedInstance(oldSemanticnoun, restrictions);

        if (semnticNounList.size() == 1) {
            SemanticEntryBOManager.suggestUpdating(oldSemanticentry.getIdentity(), newSemanticNounBO);

            oldSemanticnoun.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
            oldSemanticnoun.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());

            SEMANTIC_NOUN_DAO.update(oldSemanticnoun);
        } else {
            int newSemanticEntryId = SemanticEntryBOManager.addTempSemanticEntry(newSemanticNounBO);

            oldSemanticnoun.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
            oldSemanticnoun.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
            oldSemanticnoun.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(newSemanticEntryId));

            SEMANTIC_NOUN_DAO.update(oldSemanticnoun);
        }

        NounBOManager.setNeedCheck(oldSemanticnoun.getDerivednoun().getIdentity());
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public static void affirmUpdating(int semanticNounId) throws RawNotFoundException {
        Semanticnoun oldSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        if (oldSemanticnoun.getSemanticentry().getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            SemanticEntryBOManager.affirmUpdating(oldSemanticnoun.getSemanticentry().getIdentity());
            oldSemanticnoun.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            SEMANTIC_NOUN_DAO.update(oldSemanticnoun);
        } else {
            //we don't need to implement this part, because  current database structure doesnt
            //have any semanticentry row relates with two semanticnoun or more.
        }
    }

    public static boolean affirmUpdatingAU(int semanticNounId, SemanticNounBO newSemanticNounBO) throws RawNotFoundException {
        Semanticnoun oldSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        if (oldSemanticnoun.getSemanticentry().getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            SemanticEntryBOManager.affirmUpdatingAU(oldSemanticnoun.getSemanticentry().getIdentity(), newSemanticNounBO);
            oldSemanticnoun.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            SEMANTIC_NOUN_DAO.update(oldSemanticnoun);
        }
        oldSemanticnoun.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        SEMANTIC_NOUN_DAO.update(oldSemanticnoun);
        return true;
    }

    public static void rejectUpdating(int semanticVerbId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticVerbId);
        if (newSemanticnoun.getSuggestion() == null) {
            if (newSemanticnoun.getSemanticentry().getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                SemanticEntryBOManager.rejectUpdating(newSemanticnoun.getSemanticentry().getIdentity());
                newSemanticnoun.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                SEMANTIC_NOUN_DAO.update(newSemanticnoun);
            }
        }
    }

    public void suggestDelete(int semanticNounId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);

        newSemanticnoun.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        newSemanticnoun.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        newSemanticnoun.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        SEMANTIC_NOUN_DAO.update(newSemanticnoun);

        NounBOManager.setNeedCheck(newSemanticnoun.getDerivednoun().getIdentity());
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public static void setNeedCheck(int semanticNounId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);

        newSemanticnoun.setChechStatus(BOManagerUtil.NEEDS_CHECK_STATUS.getCheckStatus());

        SEMANTIC_NOUN_DAO.update(newSemanticnoun);
    }

    public static void rejectAdding(int semanticnounId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticnounId);
        if (newSemanticnoun.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            SemanticEntryBOManager.rejectAdding(newSemanticnoun.getSemanticentry().getIdentity());
            if (newSemanticnoun.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newSemanticnoun.getSuggestion());
            }

            newSemanticnoun.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
            newSemanticnoun.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            SEMANTIC_NOUN_DAO.update(newSemanticnoun);
        }
    }

    public static void affirmDeleting(int semanticNounId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        if (newSemanticnoun.getInfoStatus().equals(semanticNounId)) {
            SemanticEntryBOManager.affirmDeleting(newSemanticnoun.getSemanticentry().getIdentity());
            if (newSemanticnoun.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newSemanticnoun.getSuggestion());
            }
            newSemanticnoun.setInfoStatus(WordStatus.NEED_DELETING);
            newSemanticnoun.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            SEMANTIC_NOUN_DAO.update(newSemanticnoun);
        }
    }

    public static void rejectDeleting(int semanticNounId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        if (newSemanticnoun.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            SemanticEntryBOManager.rejectDeleting(newSemanticnoun.getSemanticentry().getIdentity());
            newSemanticnoun.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newSemanticnoun.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

            if (newSemanticnoun.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newSemanticnoun.getSuggestion());
            }
            SEMANTIC_NOUN_DAO.update(newSemanticnoun);
        }
    }

    public static void clearCheck(Integer semanticNounId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        newSemanticnoun.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
        SEMANTIC_NOUN_DAO.update(newSemanticnoun);
    }

    public static int getCheckedSemNounWeight(int semanticNounId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        int weight = SemanticEntryBOManager.getCheckedSemEntryWeight(newSemanticnoun.getSemanticentry().getIdentity());
        Set<Properadjective> properAdjs = newSemanticnoun.getProperadjectivesForNounId();
        for (Properadjective properadjective : properAdjs) {
            if (!properadjective.getInfoStatus().equals(WordStatus.CONFIRMED_INFO_STATUS)) {
                weight++;
            }
        }
        Set<Diminutive> diminutives = newSemanticnoun.getDiminutivesForNounId();
        for (Diminutive diminutive : diminutives) {
            if (!diminutive.getInfoStatus().equals(WordStatus.CONFIRMED_INFO_STATUS)) {
                weight++;
            }
        }
        Set<Nounadjectiveaccompanier> adjs = newSemanticnoun.getNounadjectiveaccompaniersForNounId();
        for (Nounadjectiveaccompanier adj : adjs) {
            if (!adj.getInfoStatus().equals(WordStatus.CONFIRMED_INFO_STATUS)) {
                weight++;
            }
        }
        Set<Annexednoun> annexednouns = newSemanticnoun.getAnnexednounsForNounId();
        for (Annexednoun annexednoun : annexednouns) {
            if (!annexednoun.getInfoStatus().equals(WordStatus.CONFIRMED_INFO_STATUS)) {
                weight++;
            }
        }
        Set<Femininity> femininitys = newSemanticnoun.getFemininitiesForNounId();
        for (Femininity femininity : femininitys) {
            if (!femininity.getInfoStatus().equals(WordStatus.CONFIRMED_INFO_STATUS)) {
                weight++;
            }
        }
        Set<Nounverbaccompanier> verbs = newSemanticnoun.getNounverbaccompaniers();
        for (Nounverbaccompanier verb : verbs) {
            if (!verb.getInfoStatus().equals(WordStatus.CONFIRMED_INFO_STATUS)) {
                weight++;
            }
        }
        return weight;
    }

    /// <editor-fold defaultstate="collapsed" desc="Proper Adjective">
    public int suggestAddProperAdjective(int semanticNounId, ProperAdjectiveBO properAdjectiveBO) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        Semanticnoun newSemanticProperAdjective = SEMANTIC_NOUN_DAO.getById(properAdjectiveBO.getProperAdjectiveMeaning().getSemanticNounId());
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_semanticNoun.semanticNounId", semanticNounId);
        restrictions.put("eq_semanticProperAdjective.semanticNounId", properAdjectiveBO.getProperAdjectiveMeaning().getSemanticNounId());
        Properadjective newProperAdjective = new Properadjective(newSemanticProperAdjective, newSemanticnoun);

        newProperAdjective.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newProperAdjective.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newProperAdjective.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

        int newProperAdjectiveId = BLUtil.daoFactory.getProperadjectiveDAO().insertWithCheck(newProperAdjective, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

        setNeedCheck(semanticNounId);
        int nounId = newSemanticnoun.getDerivednoun().getIdentity();
        NounBOManager.setNeedCheck(nounId);
        SEMANTIC_NOUN_DAO.clearSession();
        return newProperAdjectiveId;
    }

    public void suggestAddProperAdjective(int semanticNounId, List<ProperAdjectiveBO> properAdjectiveBOs) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        if (properAdjectiveBOs != null && properAdjectiveBOs.size() > 0) {
            for (ProperAdjectiveBO properAdjectiveBO : properAdjectiveBOs) {
                Semanticnoun newSemanticProperAdjective = SEMANTIC_NOUN_DAO.getById(properAdjectiveBO.getProperAdjectiveMeaning().getSemanticNounId());
                Map restrictions = BOManagerUtil.getAddRestrictions();
                restrictions.put("eq_semanticNoun.semanticNounId", semanticNounId);
                restrictions.put("eq_semanticProperAdjective.semanticNounId", properAdjectiveBO.getProperAdjectiveMeaning().getSemanticNounId());
                Properadjective newProperAdjective = new Properadjective(newSemanticProperAdjective, newSemanticnoun);

                newProperAdjective.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
                newProperAdjective.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
                newProperAdjective.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

                int newProperAdjectiveId = BLUtil.daoFactory.getProperadjectiveDAO().insertWithCheck(newProperAdjective, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

            }
            setNeedCheck(semanticNounId);
            int nounId = newSemanticnoun.getDerivednoun().getIdentity();
            NounBOManager.setNeedCheck(nounId);
            SEMANTIC_NOUN_DAO.clearSession();
        }
    }

    public static void suggestDeleteProperAdjective(int properAdjectiveId, int semanticNounId) throws RawNotFoundException {
        ProperAdjectiveManager.suggestDeleting(properAdjectiveId);
        setNeedCheck(semanticNounId);
    }

    public static void affirmProperAdjectiveAdding(int properAdjectiveId) throws RawNotFoundException {
        ProperAdjectiveManager.affirmAdding(properAdjectiveId);
    }

    public static void rejectProperAdjectiveAdding(int properAdjectiveId) throws RawNotFoundException {
        ProperAdjectiveManager.rejectAdding(properAdjectiveId);
    }

    public static void affirmProperAdjectiveDeleting(int properAdjectiveId) throws RawNotFoundException {
        ProperAdjectiveManager.affirmDeleting(properAdjectiveId);
    }

    public static void rejectProperAdjectiveDeleting(int properAdjectiveId) throws RawNotFoundException {
        ProperAdjectiveManager.rejectDeleting(properAdjectiveId);
    }
    //</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="Diminutive">
    public int suggestAddDiminutive(int semanticNounId, DiminutiveBO diminutiveBO) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        Semanticnoun newSemanticDiminutive = SEMANTIC_NOUN_DAO.getById(diminutiveBO.getDiminutiveMeaning().getSemanticNounId());
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_semanticNoun.semanticNounId", semanticNounId);
        restrictions.put("eq_semanticDiminutive.semanticNounId", diminutiveBO.getDiminutiveMeaning().getSemanticNounId());
        Diminutive newDiminutive = new Diminutive(newSemanticDiminutive, newSemanticnoun);

        newDiminutive.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newDiminutive.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newDiminutive.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

        int newDiminutiveId = BLUtil.daoFactory.getDiminutiveDAO().insertWithCheck(newDiminutive, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

        setNeedCheck(semanticNounId);
        int nounId = newSemanticnoun.getDerivednoun().getIdentity();
        NounBOManager.setNeedCheck(nounId);
        SEMANTIC_NOUN_DAO.clearSession();
        return newDiminutiveId;
    }

    public void suggestAddDiminutive(int semanticNounId, List<DiminutiveBO> diminutiveBOs) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        if (diminutiveBOs != null && diminutiveBOs.size() > 0) {
            for (DiminutiveBO diminutiveBO : diminutiveBOs) {
                Semanticnoun newSemanticDiminutive = SEMANTIC_NOUN_DAO.getById(diminutiveBO.getDiminutiveMeaning().getSemanticNounId());
                Map restrictions = BOManagerUtil.getAddRestrictions();
                restrictions.put("eq_semanticNoun.semanticNounId", semanticNounId);
                restrictions.put("eq_semanticDiminutive.semanticNounId", diminutiveBO.getDiminutiveMeaning().getSemanticNounId());
                Diminutive newDiminutive = new Diminutive(newSemanticDiminutive, newSemanticnoun);

                newDiminutive.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
                newDiminutive.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
                newDiminutive.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

                int newDiminutiveId = BLUtil.daoFactory.getDiminutiveDAO().insertWithCheck(newDiminutive, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

            }
            setNeedCheck(semanticNounId);
            int nounId = newSemanticnoun.getDerivednoun().getIdentity();
            NounBOManager.setNeedCheck(nounId);
            SEMANTIC_NOUN_DAO.clearSession();
        }
    }

    public static void suggestDeleteDiminutive(int diminutiveId, int semanticNounId) throws RawNotFoundException {
        DiminutiveManager.suggestDeleting(diminutiveId);
        setNeedCheck(semanticNounId);
    }

    public static void affirmDiminutiveAdding(int diminutiveId) throws RawNotFoundException {
        DiminutiveManager.affirmAdding(diminutiveId);
    }

    public static void rejectDiminutiveAdding(int diminutiveId) throws RawNotFoundException {
        DiminutiveManager.rejectAdding(diminutiveId);
    }

    public static void affirmDiminutiveDeleting(int diminutiveId) throws RawNotFoundException {
        DiminutiveManager.affirmDeleting(diminutiveId);
    }

    public static void rejectDiminutiveDeleting(int diminutiveId) throws RawNotFoundException {
        DiminutiveManager.rejectDeleting(diminutiveId);
    }
    //</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="Adjective Accompanier">
    public int suggestAddAdjectiveAccompanier(int semanticNounId, AdjectiveAccompanierBO adjectiveAccompanierBO) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        Semanticnoun newSemanticAdjectiveAccompanier = SEMANTIC_NOUN_DAO.getById(adjectiveAccompanierBO.getAdjectiveAccompanierMeaning().getSemanticNounId());
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_semanticNoun.semanticNounId", semanticNounId);
        restrictions.put("eq_semanticAdjective.semanticNounId", adjectiveAccompanierBO.getAdjectiveAccompanierMeaning().getSemanticNounId());
        Nounadjectiveaccompanier newAdjectiveAccompanier = new Nounadjectiveaccompanier(newSemanticAdjectiveAccompanier, newSemanticnoun);

        newAdjectiveAccompanier.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newAdjectiveAccompanier.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newAdjectiveAccompanier.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

        int newAdjectiveAccompanierId = BLUtil.daoFactory.getNounadjectiveaccompanierDAO().insertWithCheck(newAdjectiveAccompanier, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

        setNeedCheck(semanticNounId);
        int nounId = newSemanticnoun.getDerivednoun().getIdentity();
        NounBOManager.setNeedCheck(nounId);
        SEMANTIC_NOUN_DAO.clearSession();
        return newAdjectiveAccompanierId;
    }

    public void suggestAddAdjectiveAccompanier(int semanticNounId, List<AdjectiveAccompanierBO> adjectiveAccompanierBOs) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        if (adjectiveAccompanierBOs != null && adjectiveAccompanierBOs.size() > 0) {
            for (AdjectiveAccompanierBO adjectiveAccompanierBO : adjectiveAccompanierBOs) {
                Semanticnoun newSemanticAdjectiveAccompanier = SEMANTIC_NOUN_DAO.getById(adjectiveAccompanierBO.getAdjectiveAccompanierMeaning().getSemanticNounId());
                Map restrictions = BOManagerUtil.getAddRestrictions();
                restrictions.put("eq_semanticNoun.semanticNounId", semanticNounId);
                restrictions.put("eq_semanticAdjective.semanticNounId", adjectiveAccompanierBO.getAdjectiveAccompanierMeaning().getSemanticNounId());
                Nounadjectiveaccompanier newAdjectiveAccompanier = new Nounadjectiveaccompanier(newSemanticAdjectiveAccompanier, newSemanticnoun);

                newAdjectiveAccompanier.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
                newAdjectiveAccompanier.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
                newAdjectiveAccompanier.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

                int newAdjectiveAccompanierId = BLUtil.daoFactory.getNounadjectiveaccompanierDAO().insertWithCheck(newAdjectiveAccompanier, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

            }
            setNeedCheck(semanticNounId);
            int nounId = newSemanticnoun.getDerivednoun().getIdentity();
            NounBOManager.setNeedCheck(nounId);
            SEMANTIC_NOUN_DAO.clearSession();
        }
    }

    public static void suggestDeleteNounAdjectiveAccompanier(int adjectiveAccompanierId, int semanticNounId) throws RawNotFoundException {
        NounAdjectiveAccompanierManager.suggestDeleting(adjectiveAccompanierId);
        setNeedCheck(semanticNounId);
    }

    public static void affirmNounAdjectiveAccompanierAdding(int adjectiveAccompanierId) throws RawNotFoundException {
        NounAdjectiveAccompanierManager.affirmAdding(adjectiveAccompanierId);
    }

    public static void rejectNounAdjectiveAccompanierAdding(int adjectiveAccompanierId) throws RawNotFoundException {
        NounAdjectiveAccompanierManager.rejectAdding(adjectiveAccompanierId);
    }

    public static void affirmNounAdjectiveAccompanierDeleting(int adjectiveAccompanierId) throws RawNotFoundException {
        NounAdjectiveAccompanierManager.affirmDeleting(adjectiveAccompanierId);
    }

    public static void rejectNounAdjectiveAccompanierDeleting(int adjectiveAccompanierId) throws RawNotFoundException {
        NounAdjectiveAccompanierManager.rejectDeleting(adjectiveAccompanierId);
    }
    //</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="Annexed Noun">
    public int suggestAddAnnexedNoun(int semanticNounId, AnnexedNounBO annexedNounBO) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        Semanticnoun newSemanticAnnexedNoun = SEMANTIC_NOUN_DAO.getById(annexedNounBO.getAnnexedNounMeaning().getSemanticNounId());
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_semanticNoun.semanticNounId", semanticNounId);
        restrictions.put("eq_semanticAnnexedNoun.semanticNounId", annexedNounBO.getAnnexedNounMeaning().getSemanticNounId());
        Annexednoun newAnnexedNoun = new Annexednoun(newSemanticAnnexedNoun, newSemanticnoun);

        newAnnexedNoun.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newAnnexedNoun.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newAnnexedNoun.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

        int newAnnexedNounId = BLUtil.daoFactory.getAnnexednounDAO().insertWithCheck(newAnnexedNoun, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

        setNeedCheck(semanticNounId);
        int nounId = newSemanticnoun.getDerivednoun().getIdentity();
        NounBOManager.setNeedCheck(nounId);
        SEMANTIC_NOUN_DAO.clearSession();
        return newAnnexedNounId;
    }

    public void suggestAddAnnexedNoun(int semanticNounId, List<AnnexedNounBO> annexedNounBOs) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        if (annexedNounBOs != null && annexedNounBOs.size() > 0) {
            for (AnnexedNounBO annexedNounBO : annexedNounBOs) {
                Semanticnoun newSemanticAnnexedNoun = SEMANTIC_NOUN_DAO.getById(annexedNounBO.getAnnexedNounMeaning().getSemanticNounId());
                Map restrictions = BOManagerUtil.getAddRestrictions();
                restrictions.put("eq_semanticNoun.semanticNounId", semanticNounId);
                restrictions.put("eq_semanticAnnexedNoun.semanticNounId", annexedNounBO.getAnnexedNounMeaning().getSemanticNounId());
                Annexednoun newAnnexedNoun = new Annexednoun(newSemanticAnnexedNoun, newSemanticnoun);

                newAnnexedNoun.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
                newAnnexedNoun.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
                newAnnexedNoun.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

                int newAnnexedNounId = BLUtil.daoFactory.getAnnexednounDAO().insertWithCheck(newAnnexedNoun, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

            }
            setNeedCheck(semanticNounId);
            int nounId = newSemanticnoun.getDerivednoun().getIdentity();
            NounBOManager.setNeedCheck(nounId);
            SEMANTIC_NOUN_DAO.clearSession();
        }
    }

    public static void suggestDeleteAnnexedNoun(int annexedNounId, int semanticNounId) throws RawNotFoundException {
        AnnexedNounManager.suggestDeleting(annexedNounId);
        setNeedCheck(semanticNounId);
    }

    public static void affirmAnnexedNounAdding(int annexedNounId) throws RawNotFoundException {
        AnnexedNounManager.affirmAdding(annexedNounId);
    }

    public static void rejectAnnexedNounAdding(int annexedNounId) throws RawNotFoundException {
        AnnexedNounManager.rejectAdding(annexedNounId);
    }

    public static void affirmAnnexedNounDeleting(int annexedNounId) throws RawNotFoundException {
        AnnexedNounManager.affirmDeleting(annexedNounId);
    }

    public static void rejectAnnexedNounDeleting(int annexedNounId) throws RawNotFoundException {
        AnnexedNounManager.rejectDeleting(annexedNounId);
    }

    //</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Femininity">
    public int suggestAddFemininity(int semanticNounId, FemininityBO femininityBO) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        Semanticnoun newSemanticFemininity = SEMANTIC_NOUN_DAO.getById(femininityBO.getFemininityMeaning().getSemanticNounId());
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_semanticNoun.semanticNounId", semanticNounId);
        restrictions.put("eq_semanticFemininity.semanticNounId", femininityBO.getFemininityMeaning().getSemanticNounId());
        Femininity newFemininity = new Femininity(newSemanticFemininity, newSemanticnoun);

        newFemininity.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newFemininity.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newFemininity.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

        int newFemininityId = BLUtil.daoFactory.getFemininityDAO().insertWithCheck(newFemininity, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

        setNeedCheck(semanticNounId);
        int nounId = newSemanticnoun.getDerivednoun().getIdentity();
        NounBOManager.setNeedCheck(nounId);
        SEMANTIC_NOUN_DAO.clearSession();
        return newFemininityId;
    }

    public void suggestAddFemininity(int semanticNounId, List<FemininityBO> femininityBOs) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        if (femininityBOs != null && femininityBOs.size() > 0) {
            for (FemininityBO femininityBO : femininityBOs) {
                Semanticnoun newSemanticFemininity = SEMANTIC_NOUN_DAO.getById(femininityBO.getFemininityMeaning().getSemanticNounId());
                Map restrictions = BOManagerUtil.getAddRestrictions();
                restrictions.put("eq_semanticNoun.semanticNounId", semanticNounId);
                restrictions.put("eq_semanticFemininity.semanticNounId", femininityBO.getFemininityMeaning().getSemanticNounId());
                Femininity newFemininity = new Femininity(newSemanticFemininity, newSemanticnoun);

                newFemininity.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
                newFemininity.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
                newFemininity.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

                int newFemininityId = BLUtil.daoFactory.getFemininityDAO().insertWithCheck(newFemininity, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

            }
            setNeedCheck(semanticNounId);
            int nounId = newSemanticnoun.getDerivednoun().getIdentity();
            NounBOManager.setNeedCheck(nounId);
            SEMANTIC_NOUN_DAO.clearSession();
        }
    }

    public static void suggestDeleteFemininity(int femininityId, int semanticNounId) throws RawNotFoundException {
        FemininityManager.suggestDeleting(femininityId);
        setNeedCheck(semanticNounId);
    }

    public static void affirmFemininityAdding(int femininityId) throws RawNotFoundException {
        FemininityManager.affirmAdding(femininityId);
    }

    public static void rejectFemininityAdding(int femininityId) throws RawNotFoundException {
        FemininityManager.rejectAdding(femininityId);
    }

    public static void affirmFemininityDeleting(int femininityId) throws RawNotFoundException {
        FemininityManager.affirmDeleting(femininityId);
    }

    public static void rejectFemininityDeleting(int femininityId) throws RawNotFoundException {
        FemininityManager.rejectDeleting(femininityId);
    }
    //</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="Verb Accompanier">
    public int suggestAddVerbAccompanier(int semanticNounId, VerbAccompanierBO verbAccompanierBO) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        Semanticverb newSemanticVerbAccompanier = BLUtil.daoFactory.getSemanticverbDAO().getById(verbAccompanierBO.getVerbAccompanierMeaning().getSemanticVerbId());
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_semanticnoun.semanticNounId", semanticNounId);
        restrictions.put("eq_semanticverb.semanticVerbId", verbAccompanierBO.getVerbAccompanierMeaning().getSemanticVerbId());
        Nounverbaccompanier newVerbAccompanier = new Nounverbaccompanier(newSemanticVerbAccompanier, newSemanticnoun);

        newVerbAccompanier.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newVerbAccompanier.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newVerbAccompanier.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

        int newVerbAccompanierId = BLUtil.daoFactory.getNounverbaccompanierDAO().insertWithCheck(newVerbAccompanier, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

        setNeedCheck(semanticNounId);
        int nounId = newSemanticnoun.getDerivednoun().getIdentity();
        NounBOManager.setNeedCheck(nounId);
        SEMANTIC_NOUN_DAO.clearSession();
        return newVerbAccompanierId;
    }

    public void suggestAddVerbAccompanier(int semanticNounId, List<VerbAccompanierBO> verbAccompanierBOs) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        if (verbAccompanierBOs != null && verbAccompanierBOs.size() > 0) {
            for (VerbAccompanierBO verbAccompanierBO : verbAccompanierBOs) {
                Semanticverb newSemanticVerbAccompanier = BLUtil.daoFactory.getSemanticverbDAO().getById(verbAccompanierBO.getVerbAccompanierMeaning().getSemanticVerbId());
                Map restrictions = BOManagerUtil.getAddRestrictions();
                restrictions.put("eq_semanticnoun.semanticNounId", semanticNounId);
                restrictions.put("eq_semanticverb.semanticVerbId", verbAccompanierBO.getVerbAccompanierMeaning().getSemanticVerbId());
                Nounverbaccompanier newVerbAccompanier = new Nounverbaccompanier(newSemanticVerbAccompanier, newSemanticnoun);

                newVerbAccompanier.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
                newVerbAccompanier.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
                newVerbAccompanier.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

                int newVerbAccompanierId = BLUtil.daoFactory.getNounverbaccompanierDAO().insertWithCheck(newVerbAccompanier, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
            }
            setNeedCheck(semanticNounId);
            int nounId = newSemanticnoun.getDerivednoun().getIdentity();
            NounBOManager.setNeedCheck(nounId);
            SEMANTIC_NOUN_DAO.clearSession();
        }
    }

    public static void suggestDeleteNounAccompanier(int nounAccompanierId, int semanticNounId) throws RawNotFoundException {
        NounAccompanierBOManager.suggestDeleting(nounAccompanierId);
        setNeedCheck(semanticNounId);
    }

    public static void affirmNounAccompanierAdding(int nounAccompanierId) throws RawNotFoundException {
        NounAccompanierBOManager.affirmAdding(nounAccompanierId);
    }

    public static void rejectNounAccompanierAdding(int nounAccompanierId) throws RawNotFoundException {
        NounAccompanierBOManager.rejectAdding(nounAccompanierId);
    }

    public static void affirmNounAccompanierDeleting(int nounAccompanierId) throws RawNotFoundException {
        NounAccompanierBOManager.affirmDeleting(nounAccompanierId);
    }

    public static void rejectNounAccompanierDeleting(int nounAccompanierId) throws RawNotFoundException {
        NounAccompanierBOManager.rejectDeleting(nounAccompanierId);
    }
    //</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="Meaning...">
    public int suggestAddMeaning(MeaningBO newMeaningBO, SemanticNounBO newSemanticNounBO) throws RawNotFoundException, EntryExistedException {
        int newMeaningId = newMeaningBOManager.suggestAdding(newMeaningBO, newSemanticNounBO.getSemanticEntryId());
        int semanticNounId = newSemanticNounBO.getSemanticNounId();
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        setNeedCheck(semanticNounId);
        NounBOManager.setNeedCheck(newSemanticnoun.getDerivednoun().getIdentity());
        BLUtil.daoFactory.getIdiomDAO().clearSession();
        return newMeaningId;
    }

    public static void suggestUpdateMeaning(MeaningBO newMeaningBO, MeaningBO oldMeaningBO, SemanticNounBO newSemanticNounBO) throws RawNotFoundException {
        MeaningBOManager.suggestUpdating(newMeaningBO, oldMeaningBO);
        int semanticNounId = newSemanticNounBO.getSemanticNounId();
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        setNeedCheck(semanticNounId);
        NounBOManager.setNeedCheck(newSemanticnoun.getDerivednoun().getIdentity());
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public static void suggestDeleteMeaning(MeaningBO newMeaningBO, SemanticNounBO newSemanticNounBO) throws RawNotFoundException {
        MeaningBOManager.suggestDeleting(newMeaningBO);
        int semanticNounId = newSemanticNounBO.getSemanticNounId();
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        setNeedCheck(semanticNounId);
        NounBOManager.setNeedCheck(newSemanticnoun.getDerivednoun().getIdentity());
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

///</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="Example...">
    public int suggestAddExample(ExampleBO newExampleBO, SemanticNounBO newSemanticNounBO) throws RawNotFoundException, EntryExistedException {
        int newExampleId = super.suggestAddExample(newExampleBO, newSemanticNounBO.getSemanticEntryId());
        int semanticNounId = newSemanticNounBO.getSemanticNounId();
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        setNeedCheck(semanticNounId);
        NounBOManager.setNeedCheck(newSemanticnoun.getDerivednoun().getIdentity());
        BLUtil.daoFactory.getIdiomDAO().clearSession();
        return newExampleId;
    }

    public void suggestUpdateExample(ExampleBO newExampleBO, ExampleBO oldExampleBO, SemanticNounBO newSemanticNounBO) throws RawNotFoundException {
        super.suggestUpdateExample(newExampleBO, oldExampleBO, newSemanticNounBO.getSemanticEntryId());
        int semanticNounId = newSemanticNounBO.getSemanticNounId();
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        setNeedCheck(semanticNounId);
        NounBOManager.setNeedCheck(newSemanticnoun.getDerivednoun().getIdentity());
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public void suggestDeleteExample(ExampleBO newExampleBO, SemanticNounBO newSemanticNounBO) throws RawNotFoundException {
        super.suggestDeleteExample(newExampleBO, newSemanticNounBO.getSemanticEntryId());
        int semanticNounId = newSemanticNounBO.getSemanticNounId();
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        setNeedCheck(semanticNounId);
        NounBOManager.setNeedCheck(newSemanticnoun.getDerivednoun().getIdentity());
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public static void affirmExampleAdding(int semanticNounId, int exampleId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.affirmExampleAdding(newSemanticnoun.getSemanticentry().getIdentity(), exampleId);
    }

    public static boolean affirmExampleAddingAU(int semanticNounId, int exampleId, ExampleBO newExampleBO) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        return SemanticEntryBOManager.affirmExampleAddingAU(newSemanticnoun.getSemanticentry().getIdentity(), exampleId, newExampleBO);
    }

    public static void rejectExampleAdding(int semanticNounId, int exampleId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.rejectExampleAdding(newSemanticnoun.getSemanticentry().getIdentity(), exampleId);
    }

    public static void affirmExampleUpdating(int semanticNounId, int exampleId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.affirmExampleUpdating(newSemanticnoun.getSemanticentry().getIdentity(), exampleId);
    }

    public static boolean affirmExampleUpdatingAU(int semanticNounId, int exampleId, ExampleBO newExampleBO) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        return SemanticEntryBOManager.affirmExampleUpdatingAU(newSemanticnoun.getSemanticentry().getIdentity(), exampleId, newExampleBO);
    }

    public static void rejectExampleUpdating(int semanticNounId, int exampleId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.rejectExampleUpdating(newSemanticnoun.getSemanticentry().getIdentity(), exampleId);
    }

    public static void affirmExampleDeleting(int semanticNounId, int exampleId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.affirmExampleDeleting(newSemanticnoun.getSemanticentry().getIdentity(), exampleId);
    }

    public static void rejectExampleDeleting(int semanticNounId, int exampleId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.rejectExampleDeleting(newSemanticnoun.getSemanticentry().getIdentity(), exampleId);
    }

///</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="CommonMistake...">
    public int suggestAddCommonMistake(CommonMistakeBO newCommonMistakeBO, SemanticNounBO newSemanticNounBO) throws RawNotFoundException, EntryExistedException {
        int newCommonMistakeId = super.suggestAddCommonMistake(newCommonMistakeBO, newSemanticNounBO.getSemanticEntryId());
        int semanticNounId = newSemanticNounBO.getSemanticNounId();
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        setNeedCheck(semanticNounId);
        newSemanticnoun.getInfoStatus();
        Derivednoun newDerivednoun = newSemanticnoun.getDerivednoun();
        int nounId = newDerivednoun.getIdentity();
        NounBOManager.setNeedCheck(nounId);
        BLUtil.daoFactory.getIdiomDAO().clearSession();
        return newCommonMistakeId;
    }

    public void suggestUpdateCommonMistake(CommonMistakeBO newCommonMistakeBO, CommonMistakeBO oldCommonMistakeBO, SemanticNounBO newSemanticNounBO) throws RawNotFoundException {
        super.suggestUpdateCommonMistake(newCommonMistakeBO, oldCommonMistakeBO, newSemanticNounBO.getSemanticEntryId());
        int semanticNounId = newSemanticNounBO.getSemanticNounId();
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        setNeedCheck(semanticNounId);
        NounBOManager.setNeedCheck(newSemanticnoun.getDerivednoun().getIdentity());
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public void suggestDeleteCommonMistake(CommonMistakeBO newCommonMistakeBO, SemanticNounBO newSemanticNounBO) throws RawNotFoundException {
        super.suggestDeleteCommonMistake(newCommonMistakeBO, newSemanticNounBO.getSemanticEntryId());
        int semanticNounId = newSemanticNounBO.getSemanticNounId();
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        setNeedCheck(semanticNounId);
        NounBOManager.setNeedCheck(newSemanticnoun.getDerivednoun().getIdentity());
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public static boolean affirmCommonMistakeUpdatingAU(int semanticNounId, int commonMistakeId, CommonMistakeBO newCommonMistakeBO) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        return SemanticEntryBOManager.affirmCommonMistakeUpdatingAU(newSemanticnoun.getSemanticentry().getIdentity(), commonMistakeId, newCommonMistakeBO);
    }

    public static void rejectCommonmistakeUpdating(int semanticNounId, int commonmistakeId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.rejectCommonmistakeUpdating(newSemanticnoun.getIdentity(), commonmistakeId);
    }

    public static void affirmCommonmistakeDeleting(int semanticNounId, int commonmistakeId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.affirmCommonmistakeDeleting(newSemanticnoun.getSemanticentry().getIdentity(), commonmistakeId);
    }

    public static void rejectCommonmistakeDeleting(int semanticNounId, int commonmistakeId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.rejectCommonmistakeDeleting(newSemanticnoun.getSemanticentry().getIdentity(), commonmistakeId);
    }

    public static void affirmCommonMistakeAdding(int semanticNounId, int exampleId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.affirmCommonMistakeAdding(newSemanticnoun.getSemanticentry().getIdentity(), exampleId);
    }

    public static boolean affirmCommonMistakeAddingAU(int semanticNounId, int commonMistakeId, CommonMistakeBO newCommonMistakeBO) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        return SemanticEntryBOManager.affirmCommonMistakeAddingAU(newSemanticnoun.getSemanticentry().getIdentity(), commonMistakeId, newCommonMistakeBO);
    }

    public static void rejectCommonMistakeAdding(int semanticNounId, int commonmistakeId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.rejectCommonMistakeAdding(newSemanticnoun.getSemanticentry().getIdentity(), commonmistakeId);
    }
///</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="LinguisticBenefit...">
    public int suggestAddLinguisticBenefit(LinguisticBenefitBO newLinguisticBenefitBO, SemanticNounBO newSemanticNounBO) throws RawNotFoundException, EntryExistedException {
        int newLinguisticBenefitId = super.suggestAddLinguisticBenefit(newLinguisticBenefitBO, newSemanticNounBO.getSemanticEntryId());
        int semanticNounId = newSemanticNounBO.getSemanticNounId();
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        setNeedCheck(semanticNounId);
        NounBOManager.setNeedCheck(newSemanticnoun.getDerivednoun().getIdentity());
        BLUtil.daoFactory.getIdiomDAO().clearSession();
        return newLinguisticBenefitId;
    }

    public void suggestUpdateLinguisticBenefit(LinguisticBenefitBO newLinguisticBenefitBO, LinguisticBenefitBO oldLinguisticBenefitBO, SemanticNounBO newSemanticNounBO) throws RawNotFoundException {
        super.suggestUpdateLinguisticBenefit(newLinguisticBenefitBO, oldLinguisticBenefitBO, newSemanticNounBO.getSemanticEntryId());
        int semanticNounId = newSemanticNounBO.getSemanticNounId();
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        setNeedCheck(semanticNounId);
        NounBOManager.setNeedCheck(newSemanticnoun.getDerivednoun().getIdentity());
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public void suggestDeleteLinguisticBenefit(LinguisticBenefitBO newLinguisticBenefitBO, SemanticNounBO newSemanticNounBO) throws RawNotFoundException {
        super.suggestDeleteLinguisticBenefit(newLinguisticBenefitBO, newSemanticNounBO.getSemanticEntryId());
        int semanticNounId = newSemanticNounBO.getSemanticNounId();
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        setNeedCheck(semanticNounId);
        NounBOManager.setNeedCheck(newSemanticnoun.getDerivednoun().getIdentity());
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public static void affirmLinguisticBenefitAdding(int semanticNounId, int exampleId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.affirmLinguisticBenefitAdding(newSemanticnoun.getSemanticentry().getIdentity(), exampleId);
    }

    public static boolean affirmLinguisticBenefitAddingAU(int semanticNounId, int linguisticBenefitId, LinguisticBenefitBO newLinguisticBenefitBO) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        return SemanticEntryBOManager.affirmLinguisticBenefitAddingAU(newSemanticnoun.getSemanticentry().getIdentity(), linguisticBenefitId, newLinguisticBenefitBO);
    }

    public static void rejectLinguisticBenefitAdding(int semanticNounId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.rejectLinguisticBenefitAdding(newSemanticnoun.getSemanticentry().getIdentity(), linguisticbenefitId);
    }

    public static void affirmLinguisticBenefitUpdating(int semanticNounId, int linguisticBenefitId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.affirmLinguisticBenefitUpdating(newSemanticnoun.getSemanticentry().getIdentity(), linguisticBenefitId);
    }

    public static boolean affirmLinguisticBenefitUpdatingAU(int semanticNounId, int linguisticBenefitId, LinguisticBenefitBO newLinguisticBenefitBO) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        return SemanticEntryBOManager.affirmLinguisticBenefitUpdatingAU(newSemanticnoun.getSemanticentry().getIdentity(), linguisticBenefitId, newLinguisticBenefitBO);
    }

    public static void rejectLinguisticbenefitUpdating(int semanticNounId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.rejectLinguisticBenefitUpdating(newSemanticnoun.getSemanticentry().getIdentity(), linguisticbenefitId);
    }

    public static void affirmLinguisticbenefitDeleting(int semanticNounId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.affirmLinguisticBenefitDeleting(newSemanticnoun.getSemanticentry().getIdentity(), linguisticbenefitId);
    }

    public static void rejectLinguisticbenefitDeleting(int semanticNounId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticnoun newSemanticnoun = SEMANTIC_NOUN_DAO.getById(semanticNounId);
        SemanticEntryBOManager.rejectLinguisticBenefitDeleting(newSemanticnoun.getSemanticentry().getIdentity(), linguisticbenefitId);
    }

///</editor-fold>
    public static void main(String[] str) throws RawNotFoundException {
        User currUser = BLUtil.daoFactory.getUserDAO().getById(5);
        BOManagerUtil.setCurrentUser(currUser);
        /**
         * test affirmAdding()
         */
        //affirmAdding( 80659 );
        /**
         * test suggestUpdate()
         */
        /*SemanticNounBO newSemanticNounBO = getSemanticNounBO( 1 , SearchProperties.commonSearchOptions );
         suggestUpdateInfo(newSemanticNounBO , 5);*/
        /**
         * test affirmUpdating()
         */
        //affirmUpdating( 5 );
    }
}

package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.AssimilateAdjectiveBO;
import BusinessLogicLayer.BusinessObjects.CommonMistakeBO;
import BusinessLogicLayer.BusinessObjects.ExaggerationBO;
import BusinessLogicLayer.BusinessObjects.ExampleBO;
import BusinessLogicLayer.BusinessObjects.GerundBO;
import BusinessLogicLayer.BusinessObjects.LinguisticBenefitBO;
import BusinessLogicLayer.BusinessObjects.MeaningBO;
import BusinessLogicLayer.BusinessObjects.NounAccompanierBO;
import BusinessLogicLayer.BusinessObjects.SemanticVerbBO;
import BusinessLogicLayer.BusinessObjects.UpdatedSemanticVerbBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.SemanticverbJPADAO;
import PersistenceLayer.Assimilateadjective;
import PersistenceLayer.Contextualverb;
import PersistenceLayer.Derivedverb;
import PersistenceLayer.Exaggeration;
import PersistenceLayer.Gerund;
import PersistenceLayer.Nounverbaccompanier;
import PersistenceLayer.Semanticentry;
import PersistenceLayer.Semanticnoun;
import PersistenceLayer.Semanticverb;
import PersistenceLayer.Suggestion;
import PersistenceLayer.Transitivitycase;
import PersistenceLayer.User;
import Util.RawNotFoundException;
import java.util.ArrayList;
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
public class SemanticVerbBOManager extends SemanticEntryBOManager {

    private SemanticEntryBOManager newSemanticEntryBOManager;
    private ContextualVerbManager newContextualVerbManager;
    private MeaningBOManager newMeaningBOManager;
    private static SemanticverbJPADAO SEMANTIC_VERB_DAO = BLUtil.daoFactory.getSemanticverbDAO();

    public SemanticVerbBOManager() {
        newSemanticEntryBOManager = new SemanticEntryBOManager();
        newContextualVerbManager = new ContextualVerbManager();
        newMeaningBOManager = new MeaningBOManager();
    }

    public int suggestAdding(SemanticVerbBO semanticVerbBO, int derivedVerbId) throws EntryExistedException, RawNotFoundException {
        Semanticverb semanticVerb = new Semanticverb();
        //insert semantic entry:
        int semanticEntryId = newSemanticEntryBOManager.suggestAdding(semanticVerbBO);
        Semanticentry semanticEntry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        //insert the contextual verb and relate it with derivedVerb:
        int contextualVerbId = newContextualVerbManager.suggestAddContextualVerb(semanticVerbBO.getTransitivityCase(), derivedVerbId);
        Contextualverb contextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        restrictions.put("eq_contextualverb.contextualVerbId", contextualVerbId);

        //relate between the contextualVerb and the semantic entry:
        semanticVerb.setContextualverb(contextualverb);
        semanticVerb.setSemanticentry(semanticEntry);
        semanticVerb.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        semanticVerb.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        semanticVerb.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        int semanticverbId = SEMANTIC_VERB_DAO.insertWithCheck(semanticVerb, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
        suggestAddNounAccompanier(semanticverbId, semanticVerbBO.getNounAccompaniers());
        suggestAddAssimilateAdjective(semanticverbId, semanticVerbBO.getAssimilateAdjectives());
        suggestAddGerund(semanticverbId, semanticVerbBO.getGerunds());
        suggestAddExaggeration(semanticverbId, semanticVerbBO.getExaggerations());

        VerbBOManager.setNeedCheck(derivedVerbId);

        SEMANTIC_VERB_DAO.clearSession();

        //SEMANTIC_VERB_DAO.commitTransaction();
        return semanticverbId;
    }

    public static void affirmAdding(int semanticVerbId) throws RawNotFoundException, RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        newSemanticverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        ContextualVerbManager.affirmAdding(newSemanticverb.getContextualverb().getIdentity());
        SemanticEntryBOManager.affirmAdding(newSemanticverb.getSemanticentry().getIdentity());
        BOManagerUtil.AFFIRM_SUGGESTION(newSemanticverb.getSuggestion());
        SEMANTIC_VERB_DAO.update(newSemanticverb);
    }

    /**
     * **
     * will be used only for semantic entry info update or transtivity case
     * update.
     *
     * @param semanticVerbId
     * @param updateSemanticVerbBO
     * @param derivedVerbId
     * @return true whene the updated value doesn't exist in the database, and
     * false whene it is.
     * @throws RawNotFoundException
     * @throws EntryExistedException
     */
    public static boolean affirmAddingAU(int semanticVerbId, SemanticVerbBO updateSemanticVerbBO, int derivedVerbId) throws RawNotFoundException, EntryExistedException {
        Semanticverb orginalSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        //insert the contextual verb and relate it with derivedVerb:
        int contextualVerbId = ContextualVerbManager.addContextualVerb(updateSemanticVerbBO.getTransitivityCase(), derivedVerbId);
        Contextualverb contextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);

        SemanticEntryBOManager.update(orginalSemanticverb.getSemanticentry().getIdentity(), updateSemanticVerbBO);
        orginalSemanticverb.setContextualverb(contextualverb);
        orginalSemanticverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(orginalSemanticverb.getSuggestion());
        SEMANTIC_VERB_DAO.update(orginalSemanticverb);
        return true;
    }

    public static void affirmUpdating(int semanticVerbId) throws RawNotFoundException {
        Semanticverb oldSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        Semanticentry oldSemanticentry = oldSemanticverb.getSemanticentry();
        Contextualverb oldContextualverb = oldSemanticverb.getContextualverb();
        String semInfo = oldSemanticentry.getInfoStatus();
        String conInfo = oldContextualverb.getInfoStatus();
        //case U U
        if (semInfo.equals(WordStatus.UPDATE_INFO_STATUS) && conInfo.equals(WordStatus.UPDATE_INFO_STATUS)) {
            int newContextualverbId = ContextualVerbManager.affirmUpdating(oldContextualverb.getIdentity());
            if (newContextualverbId != oldContextualverb.getIdentity()) {
                oldSemanticverb.setContextualverb(BLUtil.daoFactory.getContextualverbDAO().getById(newContextualverbId));
            }
            SemanticEntryBOManager.affirmUpdating(oldSemanticentry.getIdentity());
            oldSemanticverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
//            BOManagerUtil.AFFIRM_SUGGESTION( oldSemanticverb.getSuggestion());
            SEMANTIC_VERB_DAO.update(oldSemanticverb);
        } //case _ U
        else if ((!semInfo.equals(WordStatus.UPDATE_INFO_STATUS)) && conInfo.equals(WordStatus.UPDATE_INFO_STATUS)) {
            Suggestion semVerbSuggestion = oldSemanticverb.getSuggestion();
            if (semVerbSuggestion != null && semVerbSuggestion.getInfoStatus().equals("U") && !(semVerbSuggestion.getAffirmStatus().equals("A"))) {
                Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(oldSemanticverb.getSuggestion().getUpdateId());
                Semanticentry newSemanticentry = newSemanticverb.getSemanticentry();

                SemanticEntryBOManager.grantAllMeaning(oldSemanticentry.getIdentity(), newSemanticentry.getIdentity());
                SemanticEntryBOManager.grantAllExamples(oldSemanticentry.getIdentity(), newSemanticentry.getIdentity());
                SemanticEntryBOManager.grantAllCommonMistakes(oldSemanticentry.getIdentity(), newSemanticentry.getIdentity());
                SemanticEntryBOManager.grantAllLinguisticBenefits(oldSemanticentry.getIdentity(), newSemanticentry.getIdentity());
                newSemanticentry.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newSemanticentry.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                BLUtil.daoFactory.getSemanticentryDAO().update(newSemanticentry);
                //just for rollback operation.
                newSemanticverb.setSemanticentry(oldSemanticentry);
                newSemanticverb.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                newSemanticverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                SEMANTIC_VERB_DAO.update(oldSemanticverb);

                oldSemanticverb.setSemanticentry(newSemanticentry);
                BOManagerUtil.AFFIRM_SUGGESTION(oldSemanticverb.getSuggestion());
            }
            int newContextualVerbId = ContextualVerbManager.affirmUpdating(oldContextualverb.getIdentity());
            //whene the updated value already existed we just change to the new
            if (newContextualVerbId != oldContextualverb.getIdentity()) {
                oldSemanticverb.setContextualverb(BLUtil.daoFactory.getContextualverbDAO().getById(newContextualVerbId));
            }
            oldSemanticverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            SEMANTIC_VERB_DAO.update(oldSemanticverb);
        } //case U _
        else if (semInfo.equals(WordStatus.UPDATE_INFO_STATUS) && (!conInfo.equals(WordStatus.UPDATE_INFO_STATUS))) {
            Suggestion semVerbSuggestion = oldSemanticverb.getSuggestion();
            if (semVerbSuggestion != null && semVerbSuggestion.getInfoStatus().equals("U") && !(semVerbSuggestion.getAffirmStatus().equals("A"))) {
                Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(oldSemanticverb.getSuggestion().getUpdateId());
                Contextualverb newContextualverb = newSemanticverb.getContextualverb();
                if (newContextualverb.getInfoStatus().equals(WordStatus.TEMP_INFO_STATUS)) {
                    newContextualverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getContextualverbDAO().update(newContextualverb);
                }
                oldSemanticverb.setContextualverb(newContextualverb);
                newSemanticverb.setContextualverb(oldContextualverb);
                newSemanticverb.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                newSemanticverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                SEMANTIC_VERB_DAO.update(newSemanticverb);
                BOManagerUtil.AFFIRM_SUGGESTION(oldSemanticverb.getSuggestion());
            }
            SemanticEntryBOManager.affirmUpdating(oldSemanticentry.getIdentity());
            oldSemanticverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            SEMANTIC_VERB_DAO.update(oldSemanticverb);

        } //case _ _
        else {
            Suggestion semVerbSuggestion = oldSemanticverb.getSuggestion();
            if (semVerbSuggestion != null && semVerbSuggestion.getInfoStatus().equals("U") && !(semVerbSuggestion.getAffirmStatus().equals("A"))) {
                Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(oldSemanticverb.getSuggestion().getUpdateId());
                Semanticentry newSemanticentry = newSemanticverb.getSemanticentry();
                Contextualverb newContextualverb = newSemanticverb.getContextualverb();

                SemanticEntryBOManager.grantAllMeaning(oldSemanticentry.getIdentity(), newSemanticentry.getIdentity());
                SemanticEntryBOManager.grantAllExamples(oldSemanticentry.getIdentity(), newSemanticentry.getIdentity());
                SemanticEntryBOManager.grantAllCommonMistakes(oldSemanticentry.getIdentity(), newSemanticentry.getIdentity());
                SemanticEntryBOManager.grantAllLinguisticBenefits(oldSemanticentry.getIdentity(), newSemanticentry.getIdentity());
                newSemanticentry.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newSemanticentry.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                BLUtil.daoFactory.getSemanticentryDAO().update(newSemanticentry);
                if (newContextualverb.getInfoStatus().equals(WordStatus.TEMP_INFO_STATUS)) {
                    newContextualverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BLUtil.daoFactory.getContextualverbDAO().update(newContextualverb);
                }

                //just for rollback operation.
                newSemanticverb.setSemanticentry(oldSemanticentry);
                newSemanticverb.setContextualverb(oldContextualverb);
                newSemanticverb.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                newSemanticverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                SEMANTIC_VERB_DAO.update(oldSemanticverb);

                oldSemanticverb.setContextualverb(newContextualverb);
                oldSemanticverb.setSemanticentry(newSemanticentry);
                oldSemanticverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                BOManagerUtil.AFFIRM_SUGGESTION(oldSemanticverb.getSuggestion());
                SEMANTIC_VERB_DAO.update(oldSemanticverb);
            }
        }
    }

    public static void suggestUpdateInfo(SemanticVerbBO newSemanticVerbBO, int semanticVerbId) throws RawNotFoundException {
        Semanticverb oldSemanticVerb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        Semanticentry oldSemanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(oldSemanticVerb.getSemanticentry().getIdentity());
        Contextualverb oldContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(oldSemanticVerb.getContextualverb().getIdentity());
        Transitivitycase oldTransitivitycase = BLUtil.daoFactory.getTransitivitycaseDAO().getById(oldContextualverb.getTransitivitycase().getIdentity());
        Derivedverb derivedverb = BLUtil.daoFactory.getDerivedverbDAO().getById(oldContextualverb.getDerivedverb().getIdentity());

        Map restrictions0 = new HashMap();
        restrictions0.put("eq_semanticentry.semanticEntryId", oldSemanticentry.getIdentity());
        // restrictions0.put( "eq_contextualverb.derivedverb.derivedVerbId" , derivedverb.getIdentity() );


        /*int newContextualverbId = ContextualVerbManager.suggestAddContextualVerb( newSemanticVerbBO.getTransitivityCase() , derivedverb.getIdentity() );
         Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById( newContextualverbId );*/
        int newTranstivityCaseId = TransitivityCasesManager.suggestAdding(newSemanticVerbBO.getTransitivityCase());
        Transitivitycase newTransitivitycase = BLUtil.daoFactory.getTransitivitycaseDAO().getById(newTranstivityCaseId);

        Map restrictions2 = new HashMap();
        restrictions2.put("eq_contextualverb.contextualVerbId", oldContextualverb.getIdentity());

        //get all semantic verbs that relate with this semantic entry.
        List<Semanticverb> semanticVerbList0 = SEMANTIC_VERB_DAO.getConfirmedInstance(new Semanticverb(oldSemanticentry, new Contextualverb()), restrictions0);
        List<Semanticverb> semanticVerbList1 = new ArrayList<Semanticverb>();
        for (Semanticverb sv : semanticVerbList0) {
            if (sv.getContextualverb().getDerivedverb().getIdentity() == derivedverb.getIdentity()) {
                semanticVerbList1.add(sv);
            }
        }
        //get all semantic verbs that have the same transitivity case.
        List<Semanticverb> semanticverbList2 = SEMANTIC_VERB_DAO.getConfirmedInstance(new Semanticverb(new Semanticentry(), oldContextualverb), restrictions2);

        //there is only one semantic verb relate with this semantic entry,
        //so we can update the semantic entry itself.
        if (semanticVerbList1.size() == 1 && semanticverbList2.size() == 1) {
            if (!oldSemanticentry.equals(newSemanticVerbBO.getSemanticEntry()))// update only whene there is a difference.
            {
                SemanticEntryBOManager.suggestUpdating(oldSemanticentry.getIdentity(), newSemanticVerbBO);
                oldSemanticVerb.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
                oldSemanticVerb.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
                SEMANTIC_VERB_DAO.update(oldSemanticVerb);
            }
            if (!oldTransitivitycase.equals(new Transitivitycase(newTransitivitycase.getTransitivityCase()))) {
                ContextualVerbManager.updateContextualVerb(newTransitivitycase.getTransitivityCase(), oldTransitivitycase.getTransitivityCase(), derivedverb.getIdentity());
                oldSemanticVerb.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
                oldSemanticVerb.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
                SEMANTIC_VERB_DAO.update(oldSemanticVerb);
            }
        } // Case of many transitivity case have same meaning (semantic entry), so we well
        // add new semantic entry as a temporary one
        // and update the relation between this semantic verb and the semantic entry.
        else if (semanticVerbList1.size() > 1 && semanticverbList2.size() == 1) {
            if (!oldSemanticentry.equals(newSemanticVerbBO.getSemanticEntry()))// update only whene there is a difference.
            {
                int tempSemanticEntryId = SemanticEntryBOManager.addTempSemanticEntry(newSemanticVerbBO);
                Semanticentry tempSemanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(tempSemanticEntryId);
                Map restrictions3 = BOManagerUtil.getAddRestrictions();
                restrictions3.put("eq_semanticentry.semanticEntryId", tempSemanticEntryId);
                restrictions3.put("eq_contextualverb.contextualVerbId", oldContextualverb.getIdentity());
                Semanticverb tempSemanticverb = new Semanticverb(tempSemanticentry, oldContextualverb);
                tempSemanticverb.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
                tempSemanticverb.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());

                int tempSemanticVerbId = SEMANTIC_VERB_DAO.insertWithCheck(tempSemanticverb, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions3);

                oldSemanticVerb.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
                oldSemanticVerb.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
                oldSemanticVerb.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(tempSemanticVerbId));
                SEMANTIC_VERB_DAO.update(oldSemanticVerb);

            }
            if (!oldTransitivitycase.equals(newTransitivitycase)) {
                ContextualVerbManager.updateContextualVerb(newTransitivitycase.getTransitivityCase(), oldTransitivitycase.getTransitivityCase(), derivedverb.getIdentity());
                oldSemanticVerb.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
                oldSemanticVerb.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
                SEMANTIC_VERB_DAO.update(oldSemanticVerb);
            }
        } else if (semanticVerbList1.size() == 1 && semanticverbList2.size() > 1) {
            if (!oldSemanticentry.equals(newSemanticVerbBO.getSemanticEntry())) {
                SemanticEntryBOManager.suggestUpdating(oldSemanticentry.getIdentity(), newSemanticVerbBO);
                oldSemanticVerb.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
                oldSemanticVerb.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
                SEMANTIC_VERB_DAO.update(oldSemanticVerb);
            }
            if (!oldTransitivitycase.equals(newTransitivitycase))// update only whene there is a difference.
            {
                int tempContextualVerbId = ContextualVerbManager.addTempContextualVerb(newSemanticVerbBO.getTransitivityCase(), derivedverb.getIdentity());
                Contextualverb tempContextualVerb = BLUtil.daoFactory.getContextualverbDAO().getById(tempContextualVerbId);

                Map restrictions3 = BOManagerUtil.getAddRestrictions();
                restrictions3.put("eq_semanticentry.semanticEntryId", oldSemanticentry.getIdentity());
                restrictions3.put("eq_contextualverb.contextualVerbId", tempContextualVerbId);

                Semanticverb tempSemanticverb = new Semanticverb(oldSemanticentry, tempContextualVerb);
                tempSemanticverb.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
                tempSemanticverb.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());
                tempSemanticverb.setSuggestion(null);
                int tempSemanticVerbId = SEMANTIC_VERB_DAO.insertWithCheck(tempSemanticverb, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions3);

                oldSemanticVerb.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
                oldSemanticVerb.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
                oldSemanticVerb.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(tempSemanticVerbId));
                SEMANTIC_VERB_DAO.update(oldSemanticVerb);
            }
        } else if (semanticVerbList1.size() > 1 && semanticverbList2.size() > 1) {
            if (!oldSemanticentry.equals(newSemanticVerbBO.getSemanticEntry()))// update only whene there is a difference.
            {
                int tempSemanticEntryId = SemanticEntryBOManager.addTempSemanticEntry(newSemanticVerbBO);
                Semanticentry tempSemanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(tempSemanticEntryId);
                Map restrictions3 = BOManagerUtil.getAddRestrictions();
                restrictions3.put("eq_semanticentry.semanticEntryId", tempSemanticEntryId);
                restrictions3.put("eq_contextualverb.contextualVerbId", oldContextualverb.getIdentity());
                Semanticverb tempSemanticverb = new Semanticverb(tempSemanticentry, oldContextualverb);
                tempSemanticverb.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
                tempSemanticverb.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());
                int tempSemanticVerbId = SEMANTIC_VERB_DAO.insertWithCheck(tempSemanticverb, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions3);
                oldSemanticVerb.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
                oldSemanticVerb.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
                oldSemanticVerb.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(tempSemanticVerbId));
                SEMANTIC_VERB_DAO.update(oldSemanticVerb);
            }
            if (!oldTransitivitycase.equals(newTransitivitycase))// update only whene there is a difference.
            {
                oldSemanticVerb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
                //semantic verb has not been updated yet.
                if (oldSemanticVerb.getInfoStatus().compareTo("U") != 0) {
                    int tempContextualVerbId = ContextualVerbManager.addTempContextualVerb(newSemanticVerbBO.getTransitivityCase(), derivedverb.getIdentity());
                    Contextualverb tempContextualVerb = BLUtil.daoFactory.getContextualverbDAO().getById(tempContextualVerbId);

                    Map restrictions3 = BOManagerUtil.getAddRestrictions();
                    restrictions3.put("eq_semanticentry.semanticEntryId", oldSemanticentry.getIdentity());
                    restrictions3.put("eq_contextualverb.contextualVerbId", tempContextualVerbId);

                    Semanticverb tempSemanticverb = new Semanticverb(oldSemanticentry, tempContextualVerb);
                    tempSemanticverb.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
                    tempSemanticverb.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());
                    int tempSemanticVerbId = SEMANTIC_VERB_DAO.insertWithCheck(tempSemanticverb, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions3);

                    oldSemanticVerb.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
                    oldSemanticVerb.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
                    oldSemanticVerb.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(tempSemanticVerbId));
                    SEMANTIC_VERB_DAO.update(oldSemanticVerb);
                } //semantic verb has already updated by semantic entry.
                else {
                    Suggestion updateSuggestion = BLUtil.daoFactory.getSuggestionDAO().getById(oldSemanticVerb.getSuggestion().getIdentity());
                    int tempSemanticVerbId = updateSuggestion.getUpdateId();
                    Semanticverb tempSemanticverb = SEMANTIC_VERB_DAO.getById(tempSemanticVerbId);

                    int tempContextualVerbId = ContextualVerbManager.addTempContextualVerb(newSemanticVerbBO.getTransitivityCase(), derivedverb.getIdentity());
                    Contextualverb tempContextualVerb = BLUtil.daoFactory.getContextualverbDAO().getById(tempContextualVerbId);

                    tempSemanticverb.setContextualverb(tempContextualVerb);
                    SEMANTIC_VERB_DAO.update(tempSemanticverb);
                }
            }
        }
        VerbBOManager.setNeedCheck(oldContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();
    }

    public static boolean affirmUpdatingAU(int semanticVerbId, SemanticVerbBO newSemanticVerbBO) throws RawNotFoundException {
        Semanticverb oldSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        Semanticentry oldSemanticentry = oldSemanticverb.getSemanticentry();
        Contextualverb oldContextualverb = oldSemanticverb.getContextualverb();

        String semInfo = oldSemanticentry.getInfoStatus();
        String conInfo = oldContextualverb.getInfoStatus();
        if (semInfo.equals(WordStatus.UPDATE_INFO_STATUS) && conInfo.equals(WordStatus.UPDATE_INFO_STATUS)) {
            SemanticEntryBOManager.affirmUpdatingAU(oldSemanticentry.getIdentity(), newSemanticVerbBO);
            int contextualverbId = ContextualVerbManager.affirmUpdatingAU(oldContextualverb.getIdentity(), newSemanticVerbBO.getTransitivityCase());
            if (contextualverbId != oldContextualverb.getIdentity()) {
                Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualverbId);
                oldSemanticverb.setContextualverb(newContextualverb);
            }

            oldSemanticverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            SEMANTIC_VERB_DAO.update(oldSemanticverb);
            return true;
        } else if (semInfo.equals(WordStatus.UPDATE_INFO_STATUS) && (!conInfo.equals(WordStatus.UPDATE_INFO_STATUS))) {
            SemanticEntryBOManager.affirmUpdatingAU(oldSemanticentry.getIdentity(), newSemanticVerbBO);

            Semanticverb tempSemanticverb = SEMANTIC_VERB_DAO.getById(oldSemanticverb.getSuggestion().getUpdateId());

            int newContextualVerbId = ContextualVerbManager.affirmUpdatingAU(oldContextualverb.getIdentity(),
                    newSemanticVerbBO.getTransitivityCase(), tempSemanticverb.getContextualverb().getIdentity());
            if (newContextualVerbId != oldContextualverb.getIdentity()) {
                Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(newContextualVerbId);
                oldSemanticverb.setContextualverb(newContextualverb);
            }

            oldSemanticverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldSemanticverb.getSuggestion());

            tempSemanticverb.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
            tempSemanticverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

            SEMANTIC_VERB_DAO.update(tempSemanticverb);
            SEMANTIC_VERB_DAO.update(oldSemanticverb);
            return true;
        } else if ((!semInfo.equals(WordStatus.UPDATE_INFO_STATUS)) && conInfo.equals(WordStatus.UPDATE_INFO_STATUS)) {   // update only transitivity case (not semantic entry info)
            if (oldSemanticverb.getSuggestion() == null) {
                int contextualverbId = ContextualVerbManager.affirmUpdatingAU(oldContextualverb.getIdentity(), newSemanticVerbBO.getTransitivityCase());
                if (contextualverbId != oldContextualverb.getIdentity()) {
                    Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualverbId);
                    oldSemanticverb.setContextualverb(newContextualverb);
                }

                oldSemanticverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                SEMANTIC_VERB_DAO.update(oldSemanticverb);

            }
            return true;
        } else if ((!semInfo.equals(WordStatus.UPDATE_INFO_STATUS)) && (!conInfo.equals(WordStatus.UPDATE_INFO_STATUS))) {//update only transitivity case and this contextual verb has more than one meaning
            if (semInfo.equals(WordStatus.CONFIRMED_INFO_STATUS)) {
                Semanticverb tempSemanticverb = SEMANTIC_VERB_DAO.getById(oldSemanticverb.getSuggestion().getUpdateId());

                int newContextualVerbId = ContextualVerbManager.affirmUpdatingAU(oldContextualverb.getIdentity(),
                        newSemanticVerbBO.getTransitivityCase(), tempSemanticverb.getContextualverb().getIdentity());
                if (newContextualVerbId != oldContextualverb.getIdentity()) {
                    Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(newContextualVerbId);
                    oldSemanticverb.setContextualverb(newContextualverb);
                }

                oldSemanticverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldSemanticverb.getSuggestion());

                tempSemanticverb.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                tempSemanticverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                SEMANTIC_VERB_DAO.update(tempSemanticverb);
                SEMANTIC_VERB_DAO.update(oldSemanticverb);
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public static void rejectUpdating(int semanticVerbId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        Semanticentry newSemanticentry = newSemanticverb.getSemanticentry();
        Contextualverb newContextualverb = newSemanticverb.getContextualverb();

        String semInfo = newSemanticentry.getInfoStatus();
        String conInfo = newContextualverb.getInfoStatus();
        if (semInfo.equals(WordStatus.UPDATE_INFO_STATUS) && conInfo.equals(WordStatus.UPDATE_INFO_STATUS)) {
            SemanticEntryBOManager.rejectUpdating(newSemanticentry.getIdentity());
            ContextualVerbManager.rejectUpdating(newContextualverb.getIdentity());
        } else if (semInfo.equals(WordStatus.UPDATE_INFO_STATUS) && (!conInfo.equals(WordStatus.UPDATE_INFO_STATUS))) {
            SemanticEntryBOManager.rejectUpdating(newSemanticentry.getIdentity());
            if (newSemanticverb.getSuggestion() != null) {
                Semanticverb tempSemanticverb = SEMANTIC_VERB_DAO.getById(newSemanticverb.getSuggestion().getUpdateId());
                Contextualverb tempContextualverb = tempSemanticverb.getContextualverb();

                tempSemanticverb.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                tempSemanticverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                tempContextualverb.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                tempContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                SEMANTIC_VERB_DAO.update(tempSemanticverb);
                BLUtil.daoFactory.getContextualverbDAO().update(tempContextualverb);
            }
        } else if ((!semInfo.equals(WordStatus.UPDATE_INFO_STATUS)) && conInfo.equals(WordStatus.UPDATE_INFO_STATUS)) {   // update only transitivity case (not semantic entry info)
            if (newSemanticverb.getSuggestion() == null) {
                ContextualVerbManager.rejectUpdating(newContextualverb.getIdentity());
            }
        } else if ((!semInfo.equals(WordStatus.UPDATE_INFO_STATUS)) && (!conInfo.equals(WordStatus.UPDATE_INFO_STATUS))) {
            if (semInfo.equals(WordStatus.CONFIRMED_INFO_STATUS) && newSemanticverb.getSuggestion() != null) {
                Semanticverb tempSemanticverb = SEMANTIC_VERB_DAO.getById(newSemanticverb.getSuggestion().getUpdateId());
                Contextualverb tempContextualverb = tempSemanticverb.getContextualverb();

                tempSemanticverb.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                tempSemanticverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                tempContextualverb.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                tempContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                BOManagerUtil.REJECT_SUGGESTION(newSemanticverb.getSuggestion());

                SEMANTIC_VERB_DAO.update(tempSemanticverb);
                BLUtil.daoFactory.getContextualverbDAO().update(tempContextualverb);
            }
        } else {
            //not for this version
        }
        newSemanticverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        SEMANTIC_VERB_DAO.update(newSemanticverb);
    }

    public static void setNeedCheck(int semanticVerbId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);

        newSemanticverb.setChechStatus(BOManagerUtil.NEEDS_CHECK_STATUS.getCheckStatus());

        SEMANTIC_VERB_DAO.update(newSemanticverb);
    }

    public static void rejectAdding(int semanticVerbId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        if (newSemanticverb.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            SemanticEntryBOManager.rejectAdding(newSemanticverb.getSemanticentry().getIdentity());
            ContextualVerbManager.rejectAdding(newSemanticverb.getContextualverb().getIdentity());

            newSemanticverb.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
            newSemanticverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newSemanticverb.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newSemanticverb.getSuggestion());
            }

            SEMANTIC_VERB_DAO.update(newSemanticverb);
        }
    }

    public static void suggestDeleting(int semanticVerbId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        newSemanticverb.setChechStatus(WordStatus.NEED_CHECK_STATUS);
        newSemanticverb.setInfoStatus(WordStatus.DELETE_INFO_STATUS);
        newSemanticverb.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        SEMANTIC_VERB_DAO.update(newSemanticverb);
        int derivedVerbId = newSemanticverb.getContextualverb().getDerivedverb().getIdentity();
        VerbBOManager.setNeedCheck(derivedVerbId);
        BLUtil.daoFactory.getIdiomDAO().clearSession();
    }

    public static void affirmDeleting(int semanticVerbId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        if (newSemanticverb.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            SemanticEntryBOManager.affirmDeleting(newSemanticverb.getIdentity());
            ContextualVerbManager.affirmDeleting(newSemanticverb.getContextualverb().getIdentity());

            if (newSemanticverb.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newSemanticverb.getSuggestion());
            }
            newSemanticverb.setInfoStatus(WordStatus.NEED_DELETING);
            newSemanticverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            SEMANTIC_VERB_DAO.update(newSemanticverb);
        }
    }

    public static void rejectDeleting(int semanticVerbId) throws RawNotFoundException {
        Semanticverb newSemanticVerb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        if (newSemanticVerb.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            SemanticEntryBOManager.rejectDeleting(newSemanticVerb.getSemanticentry().getIdentity());
            ContextualVerbManager.rejectDeleting(newSemanticVerb.getContextualverb().getIdentity());
            newSemanticVerb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newSemanticVerb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

            if (newSemanticVerb.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newSemanticVerb.getSuggestion());
            }
            SEMANTIC_VERB_DAO.update(newSemanticVerb);

        }
    }

    public static void clearCheck(Integer semanticVerbId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        newSemanticverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
    }

    /// <editor-fold defaultstate="collapsed" desc="getters....">
    public static SemanticVerbBO getSemanticVerbBO(Semanticverb semVerb) {
        return getSemanticVerbBO(semVerb, SearchProperties.detailedSearchOptions);
    }

    public static SemanticVerbBO getSemanticVerbBO(Integer semanticWordId, SearchProperties options) {
        try {
            Semanticverb semVerb = SEMANTIC_VERB_DAO.getById(semanticWordId);
            return getSemanticVerbBO(semVerb, options);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(SemanticVerbBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static SemanticVerbBO getSemanticVerbBO(Semanticverb semVerb, SearchProperties options) {
        if (semVerb.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            UpdatedSemanticVerbBO semanticVerbBO = new UpdatedSemanticVerbBO();
            String transitivityCase = semVerb.getContextualverb().getTransitivitycase().getTransitivityCase();
            semanticVerbBO.setTransitivityCase(transitivityCase);
            try {
                Suggestion semVerbSuggestion = semVerb.getSuggestion();
                //Save semantic particle ID
                if (semVerbSuggestion != null && semVerbSuggestion.getInfoStatus().equals("U") && !(semVerbSuggestion.getAffirmStatus().equals("A"))) {
                    Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semVerb.getSuggestion().getUpdateId());
                    semanticVerbBO.setNewTransitivityCase(newSemanticverb.getContextualverb().getTransitivitycase().getTransitivityCase());
                    semanticVerbBO.setNewSemanticVerbId(newSemanticverb.getIdentity());
                    semanticVerbBO.setNewDifficultydegree(newSemanticverb.getSemanticentry().getDifficultydegree().getDifficultyDegree());
                    semanticVerbBO.setNewEpoch(newSemanticverb.getSemanticentry().getEpoch().getEpoch());
                    semanticVerbBO.setNewRegion(newSemanticverb.getSemanticentry().getRegion().getRegion());
                    semanticVerbBO.setNewSemanticscop(newSemanticverb.getSemanticentry().getSemanticscop().getSemanticScop());
                    semanticVerbBO.setNewSpecialization(newSemanticverb.getSemanticentry().getSpecialization().getSpecialization());
                    semanticVerbBO.setNewSpreadingdegree(newSemanticverb.getSemanticentry().getSpreadingdegree().getSpreadingDegree());
                } else {
                    if (semVerb.getSemanticentry().getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                        Semanticentry newSemanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(semVerb.getSemanticentry().getSuggestion().getUpdateId());
                        semanticVerbBO.setNewSemanticVerbId(semVerb.getIdentity());
                        semanticVerbBO.setNewDifficultydegree(newSemanticentry.getDifficultydegree().getDifficultyDegree());
                        semanticVerbBO.setNewEpoch(newSemanticentry.getEpoch().getEpoch());
                        semanticVerbBO.setNewRegion(newSemanticentry.getRegion().getRegion());
                        semanticVerbBO.setNewSemanticscop(newSemanticentry.getSemanticscop().getSemanticScop());
                        semanticVerbBO.setNewSpecialization(newSemanticentry.getSpecialization().getSpecialization());
                        semanticVerbBO.setNewSpreadingdegree(newSemanticentry.getSpreadingdegree().getSpreadingDegree());
                    } else {
                        semanticVerbBO.setNewSemanticVerbId(semVerb.getIdentity());
                        semanticVerbBO.setNewDifficultydegree(semVerb.getSemanticentry().getDifficultydegree().getDifficultyDegree());
                        semanticVerbBO.setNewEpoch(semVerb.getSemanticentry().getEpoch().getEpoch());
                        semanticVerbBO.setNewRegion(semVerb.getSemanticentry().getRegion().getRegion());
                        semanticVerbBO.setNewSemanticscop(semVerb.getSemanticentry().getSemanticscop().getSemanticScop());
                        semanticVerbBO.setNewSpecialization(semVerb.getSemanticentry().getSpecialization().getSpecialization());
                        semanticVerbBO.setNewSpreadingdegree(semVerb.getSemanticentry().getSpreadingdegree().getSpreadingDegree());
                    }
                    if (semVerb.getContextualverb().getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(semVerb.getContextualverb().getSuggestion().getUpdateId());
                        semanticVerbBO.setNewTransitivityCase(newContextualverb.getTransitivitycase().getTransitivityCase());
                    } else {
                        semanticVerbBO.setNewTransitivityCase(semVerb.getContextualverb().getTransitivitycase().getTransitivityCase());
                    }
                }
            } catch (RawNotFoundException ex) {
                Logger.getLogger(SemanticVerbBOManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            semanticVerbBO.setSemanticVerbId(semVerb.getIdentity());
            //Retrieve and Save semantic entry info
            Semanticentry semEntry = semVerb.getSemanticentry();
            semanticVerbBO.setSemanticEntryId(semEntry.getIdentity());
            semanticVerbBO.setDifficultydegree(semEntry.getDifficultydegree().getDifficultyDegree());
            semanticVerbBO.setEpoch(semEntry.getEpoch().getEpoch());
            semanticVerbBO.setRegion(semEntry.getRegion().getRegion());
            semanticVerbBO.setSemanticscop(semEntry.getSemanticscop().getSemanticScop());
            semanticVerbBO.setSpecialization(semEntry.getSpecialization().getSpecialization());
            semanticVerbBO.setSpreadingdegree(semEntry.getSpreadingdegree().getSpreadingDegree());
            semanticVerbBO.setStatus(semVerb.getInfoStatus());
            semanticVerbBO.setChecked(semVerb.getChechStatus() == 1 ? true : false);

            //Semantic Entry Related Infos:
            if (options.FindMeanings) {
                semanticVerbBO.setMeanings(MeaningBOManager.getMeaningsOfSemanticEntry(semEntry));
            }
            if (options.FindCommonMistakes) {
                semanticVerbBO.setCommonMistakes(CommonMistakeBOManager.getCommonMistakesOfSemanticEntry(semEntry));
            }
            if (options.FindLinguisticBenifits) {
                semanticVerbBO.setLinguisticBenefits(LinguisticBenefitBOManager.getLinguisticBenefitsOfSemanticEntry(semEntry));
            }
            if (options.FindExamples) {
                semanticVerbBO.setExamples(ExampleBOManager.getExamplesOfSemanticEntry(semEntry));
            }
            if (options.FindRelatedIdiom) {
                semanticVerbBO.setRelatedidioms(IdiomBOManager.getRelatedIdiom(semEntry));
            }

            //Semantic Verb related Infos:
            if (options.FindRelatedAssimilatedAdjectives) {
                semanticVerbBO.setAssimilateAdjectives(AssimilateAdjectiveManager.getAssimilateAdjective(semVerb));
            }
            if (options.FindRelatedExaggerations) {
                semanticVerbBO.setExaggerations(ExaggerationManager.getExaggerations(semVerb));
            }
            if (options.FindRelatedGerunds) {
                semanticVerbBO.setGerunds(GerundManager.getGerunds(semVerb));
            }
            if (options.FindNounVerbAccompaniers) {
                semanticVerbBO.setNounAccompaniers(NounAccompanierBOManager.getNounAccompaniersForSemanticVerb(semVerb));
            }
            //semanticVerbBO.setContextTypes(ContextActorManager.getContextActor( semVerb ));

            if (options.FindImages) {
                semanticVerbBO.setImages(ImageManager.getImage(semEntry));
            }
            if (options.FindSounds) {
                semanticVerbBO.setSounds(SoundManager.getSound(semEntry));
            }
            if (options.FindVideos) {
                semanticVerbBO.setVideos(VideoManager.getVideo(semEntry));
            }

            return semanticVerbBO;
        } else {
            SemanticVerbBO semanticVerbBO = new SemanticVerbBO();
            String transitivityCase = semVerb.getContextualverb().getTransitivitycase().getTransitivityCase();
            semanticVerbBO.setTransitivityCase(transitivityCase);

            //Save semantic particle ID
            semanticVerbBO.setSemanticVerbId(semVerb.getIdentity());
            //Retrieve and Save semantic entry info
            Semanticentry semEntry = semVerb.getSemanticentry();
            semanticVerbBO.setSemanticEntryId(semEntry.getIdentity());
            semanticVerbBO.setDifficultydegree(semEntry.getDifficultydegree().getDifficultyDegree());
            semanticVerbBO.setEpoch(semEntry.getEpoch().getEpoch());
            semanticVerbBO.setRegion(semEntry.getRegion().getRegion());
            semanticVerbBO.setSemanticscop(semEntry.getSemanticscop().getSemanticScop());
            semanticVerbBO.setSpecialization(semEntry.getSpecialization().getSpecialization());
            semanticVerbBO.setSpreadingdegree(semEntry.getSpreadingdegree().getSpreadingDegree());
            semanticVerbBO.setStatus(semVerb.getInfoStatus());
            semanticVerbBO.setChecked(semVerb.getChechStatus() == 1 ? true : false);

            //Semantic Entry Related Infos:
            if (options.FindMeanings) {
                semanticVerbBO.setMeanings(MeaningBOManager.getMeaningsOfSemanticEntry(semEntry));
            }
            if (options.FindCommonMistakes) {
                semanticVerbBO.setCommonMistakes(CommonMistakeBOManager.getCommonMistakesOfSemanticEntry(semEntry));
            }
            if (options.FindLinguisticBenifits) {
                semanticVerbBO.setLinguisticBenefits(LinguisticBenefitBOManager.getLinguisticBenefitsOfSemanticEntry(semEntry));
            }
            if (options.FindExamples) {
                semanticVerbBO.setExamples(ExampleBOManager.getExamplesOfSemanticEntry(semEntry));
            }
            if (options.FindRelatedIdiom) {
                semanticVerbBO.setRelatedidioms(IdiomBOManager.getRelatedIdiom(semEntry));
            }

            //Semantic Verb related Infos:
            if (options.FindRelatedAssimilatedAdjectives) {
                semanticVerbBO.setAssimilateAdjectives(AssimilateAdjectiveManager.getAssimilateAdjective(semVerb));
            }
            if (options.FindRelatedExaggerations) {
                semanticVerbBO.setExaggerations(ExaggerationManager.getExaggerations(semVerb));
            }
            if (options.FindRelatedGerunds) {
                semanticVerbBO.setGerunds(GerundManager.getGerunds(semVerb));
            }
            if (options.FindNounVerbAccompaniers) {
                semanticVerbBO.setNounAccompaniers(NounAccompanierBOManager.getNounAccompaniersForSemanticVerb(semVerb));
            }
            //semanticVerbBO.setContextTypes(ContextActorManager.getContextActor( semVerb ));

            if (options.FindImages) {
                semanticVerbBO.setImages(ImageManager.getImage(semEntry));
            }
            if (options.FindSounds) {
                semanticVerbBO.setSounds(SoundManager.getSound(semEntry));
            }
            if (options.FindVideos) {
                semanticVerbBO.setVideos(VideoManager.getVideo(semEntry));
            }

            return semanticVerbBO;
        }
    }

    public static int getCheckedSemVerbWeight(int semanticVerbId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        int weight = SemanticEntryBOManager.getCheckedSemEntryWeight(newSemanticverb.getSemanticentry().getIdentity());
        if ("IUD".contains(newSemanticverb.getInfoStatus()) && (!"IUD".contains(newSemanticverb.getSemanticentry().getInfoStatus()))) {
            weight++;
        }

        Set<Nounverbaccompanier> nounAccompaniers = newSemanticverb.getNounverbaccompaniers();
        for (Nounverbaccompanier nounAccompanier : nounAccompaniers) {
            if ("IUD".contains(nounAccompanier.getInfoStatus())) {
                weight++;
            }
        }
        Set<Assimilateadjective> assimilates = newSemanticverb.getAssimilateadjectives();
        for (Assimilateadjective assimilate : assimilates) {
            if ("IUD".contains(assimilate.getInfoStatus())) {
                weight++;
            }
        }
        Set<Exaggeration> exaggerations = newSemanticverb.getExaggerations();
        for (Exaggeration exaggeration : exaggerations) {
            if ("IUD".contains(exaggeration.getInfoStatus())) {
                weight++;
            }
        }
        Set<Gerund> gerunds = newSemanticverb.getGerunds();
        for (Gerund gerund : gerunds) {
            if ("IUD".contains(gerund.getInfoStatus())) {
                weight++;
            }
        }

        return weight;
    }
    //</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="NounAccompanier">
    public void suggestAddNounAccompanier(int semanticVerbId, List<NounAccompanierBO> nounAccompaniers) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        if (nounAccompaniers != null && nounAccompaniers.size() > 0) {
            for (NounAccompanierBO nounAccompanier : nounAccompaniers) {
                Semanticnoun newSemanticnoun = BLUtil.daoFactory.getSemanticnounDAO().getById(nounAccompanier.getAccompanierNounMeaning().getSemanticNounId());
                Map restrictions = BOManagerUtil.getAddRestrictions();
                restrictions.put("eq_semanticverb.semanticVerbId", semanticVerbId);
                restrictions.put("eq_semanticnoun.semanticNounId", nounAccompanier.getAccompanierNounMeaning().getSemanticNounId());
                Nounverbaccompanier newNounverbaccompanier = new Nounverbaccompanier(newSemanticverb, newSemanticnoun);

                newNounverbaccompanier.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
                newNounverbaccompanier.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
                newNounverbaccompanier.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

                BLUtil.daoFactory.getNounverbaccompanierDAO().insertWithCheck(newNounverbaccompanier, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
            }
            setNeedCheck(semanticVerbId);
//        int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
//        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById( contextualVerbId );
            Contextualverb newContextualverb = newSemanticverb.getContextualverb();
            VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());

        }
    }

    public int suggestAddNounAccompanier(int semanticVerbId, NounAccompanierBO nounAccompanier) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        Semanticnoun newSemanticnoun = BLUtil.daoFactory.getSemanticnounDAO().getById(nounAccompanier.getAccompanierNounMeaning().getSemanticNounId());
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_semanticverb.semanticVerbId", semanticVerbId);
        restrictions.put("eq_semanticnoun.semanticNounId", nounAccompanier.getAccompanierNounMeaning().getSemanticNounId());
        Nounverbaccompanier newNounverbaccompanier = new Nounverbaccompanier(newSemanticverb, newSemanticnoun);

        newNounverbaccompanier.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newNounverbaccompanier.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newNounverbaccompanier.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

        int newNounAccompanierId = BLUtil.daoFactory.getNounverbaccompanierDAO().insertWithCheck(newNounverbaccompanier, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

        setNeedCheck(semanticVerbId);
        int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
//        newSemanticverb = SEMANTIC_VERB_DAO.getById( semanticVerbId );
//        Contextualverb newContextualverb = newSemanticverb.getContextualverb();
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();

        return newNounAccompanierId;
    }

    public static void suggestDeleteNounAccompanier(int nounAccompanierId, int semanticVerbId) throws RawNotFoundException {
        NounAccompanierBOManager.suggestDeleting(nounAccompanierId);
        setNeedCheck(semanticVerbId);
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

    /// <editor-fold defaultstate="collapsed" desc="AssimilateAdjective">
    public int suggestAddAssimilateAdjective(int semanticVerbId, AssimilateAdjectiveBO assimilateAdjectiveBO) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);

        Semanticnoun newSemanticnoun = BLUtil.daoFactory.getSemanticnounDAO().getById(assimilateAdjectiveBO.getAssimilateAdjectiveMeaning().getSemanticNounId());
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_semanticverb.semanticVerbId", semanticVerbId);
        restrictions.put("eq_semanticnoun.semanticNounId", assimilateAdjectiveBO.getAssimilateAdjectiveMeaning().getSemanticNounId());
        Assimilateadjective newAssimilateadjective = new Assimilateadjective(newSemanticverb, newSemanticnoun);

        newAssimilateadjective.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newAssimilateadjective.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newAssimilateadjective.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

        int newAssimilateAdjId = BLUtil.daoFactory.getAssimilateadjectiveDAO().insertWithCheck(newAssimilateadjective, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

        setNeedCheck(semanticVerbId);
        int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();

        return newAssimilateAdjId;
    }

    public void suggestAddAssimilateAdjective(int semanticVerbId, List<AssimilateAdjectiveBO> assimilateAdjectiveBOs) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        if (assimilateAdjectiveBOs != null && assimilateAdjectiveBOs.size() > 0) {
            for (AssimilateAdjectiveBO assimilateAdjectiveBO : assimilateAdjectiveBOs) {
                Semanticnoun newSemanticnoun = BLUtil.daoFactory.getSemanticnounDAO().getById(assimilateAdjectiveBO.getAssimilateAdjectiveMeaning().getSemanticNounId());
                Map restrictions = BOManagerUtil.getAddRestrictions();
                restrictions.put("eq_semanticverb.semanticVerbId", semanticVerbId);
                restrictions.put("eq_semanticnoun.semanticNounId", assimilateAdjectiveBO.getAssimilateAdjectiveMeaning().getSemanticNounId());
                Assimilateadjective newAssimilateadjective = new Assimilateadjective(newSemanticverb, newSemanticnoun);

                newAssimilateadjective.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
                newAssimilateadjective.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
                newAssimilateadjective.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

                BLUtil.daoFactory.getAssimilateadjectiveDAO().insertWithCheck(newAssimilateadjective, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
            }
            setNeedCheck(semanticVerbId);
            int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
            Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
            VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        }
    }

    public static void suggestDeleteAssimilateAdjective(int assimilateAdjectiveId, Integer semanticVerbId) throws RawNotFoundException {
        AssimilateAdjectiveManager.suggestDeleting(assimilateAdjectiveId);
        setNeedCheck(semanticVerbId);
    }

    public static void affirmAssimilateAdjectiveAdding(int nounAccompanierId) throws RawNotFoundException {
        AssimilateAdjectiveManager.affirmAdding(nounAccompanierId);
    }

    public static void rejectAssimilateAdjectiveAdding(int nounAccompanierId) throws RawNotFoundException {
        AssimilateAdjectiveManager.rejectAdding(nounAccompanierId);
    }

    public static void affirmAssimilateAdjectiveDeleting(int nounAccompanierId) throws RawNotFoundException {
        AssimilateAdjectiveManager.affirmDeleting(nounAccompanierId);
    }

    public static void rejectAssimilateAdjectiveDeleting(int nounAccompanierId) throws RawNotFoundException {
        AssimilateAdjectiveManager.rejectDeleting(nounAccompanierId);
    }
    //</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="Exaggeration">
    public int suggestAddExaggeration(int semanticVerbId, ExaggerationBO exaggerationBO) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);

        Semanticnoun newSemanticnoun = BLUtil.daoFactory.getSemanticnounDAO().getById(exaggerationBO.getExaggerationMeaning().getSemanticNounId());
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_semanticverb.semanticVerbId", semanticVerbId);
        restrictions.put("eq_semanticnoun.semanticNounId", exaggerationBO.getExaggerationMeaning().getSemanticNounId());
        Exaggeration newExaggeration = new Exaggeration(newSemanticverb, newSemanticnoun);

        newExaggeration.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newExaggeration.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newExaggeration.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

        int newExaggerationId = BLUtil.daoFactory.getExaggerationDAO().insertWithCheck(newExaggeration, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

        setNeedCheck(semanticVerbId);
        int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();

        return newExaggerationId;
    }

    public void suggestAddExaggeration(int semanticVerbId, List<ExaggerationBO> exaggerationBOs) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        if (exaggerationBOs != null && exaggerationBOs.size() > 0) {
            for (ExaggerationBO exaggerationBO : exaggerationBOs) {
                Semanticnoun newSemanticnoun = BLUtil.daoFactory.getSemanticnounDAO().getById(exaggerationBO.getExaggerationMeaning().getSemanticNounId());
                Map restrictions = BOManagerUtil.getAddRestrictions();
                restrictions.put("eq_semanticverb.semanticVerbId", semanticVerbId);
                restrictions.put("eq_semanticnoun.semanticNounId", exaggerationBO.getExaggerationMeaning().getSemanticNounId());
                Exaggeration newExaggeration = new Exaggeration(newSemanticverb, newSemanticnoun);

                newExaggeration.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
                newExaggeration.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
                newExaggeration.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
                BLUtil.daoFactory.getExaggerationDAO().insertWithCheck(newExaggeration, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
            }
            setNeedCheck(semanticVerbId);
            int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
            Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
            VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        }
    }

    public static void suggestDeleteExaggeration(int exaggerationId, Integer semanticVerbId) throws RawNotFoundException {
        ExaggerationManager.suggestDeleting(exaggerationId);
        setNeedCheck(semanticVerbId);
    }

    public static void affirmExaggerationAdding(int nounAccompanierId) throws RawNotFoundException {
        ExaggerationManager.affirmAdding(nounAccompanierId);
    }

    public static void rejectExaggerationAdding(int nounAccompanierId) throws RawNotFoundException {
        ExaggerationManager.rejectAdding(nounAccompanierId);
    }

    public static void affirmExaggerationDeleting(int nounAccompanierId) throws RawNotFoundException {
        ExaggerationManager.affirmDeleting(nounAccompanierId);
    }

    public static void rejectExaggerationDeleting(int nounAccompanierId) throws RawNotFoundException {
        ExaggerationManager.rejectDeleting(nounAccompanierId);
    }
    //</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="Gerund">
    public int suggestAddGerund(int semanticVerbId, GerundBO gerundBO) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        Semanticnoun newSemanticnoun = BLUtil.daoFactory.getSemanticnounDAO().getById(gerundBO.getGerundMeaning().getSemanticNounId());
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_semanticverb.semanticVerbId", semanticVerbId);
        restrictions.put("eq_semanticnoun.semanticNounId", gerundBO.getGerundMeaning().getSemanticNounId());
        Gerund newGerund = new Gerund(newSemanticverb, newSemanticnoun);

        newGerund.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newGerund.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newGerund.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

        int newGerundId = BLUtil.daoFactory.getGerundDAO().insertWithCheck(newGerund, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

        setNeedCheck(semanticVerbId);
        int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();

        return newGerundId;
    }

    public void suggestAddGerund(int semanticVerbId, List<GerundBO> gerundBOs) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        if (gerundBOs != null && gerundBOs.size() > 0) {
            for (GerundBO gerundBO : gerundBOs) {
                Semanticnoun newSemanticnoun = BLUtil.daoFactory.getSemanticnounDAO().getById(gerundBO.getGerundMeaning().getSemanticNounId());
                Map restrictions = BOManagerUtil.getAddRestrictions();
                restrictions.put("eq_semanticverb.semanticVerbId", semanticVerbId);
                restrictions.put("eq_semanticnoun.semanticNounId", gerundBO.getGerundMeaning().getSemanticNounId());
                Gerund newGerund = new Gerund(newSemanticverb, newSemanticnoun);

                newGerund.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
                newGerund.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
                newGerund.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());

                BLUtil.daoFactory.getGerundDAO().insertWithCheck(newGerund, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
            }
            setNeedCheck(semanticVerbId);
            int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
            Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
            VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        }
    }

    public static void suggestDeleteGerund(int gerundId, Integer semanticVerbId) throws RawNotFoundException {
        GerundManager.suggestDeleting(gerundId);
        setNeedCheck(semanticVerbId);
    }

    public static void affirmGerundAdding(int nounAccompanierId) throws RawNotFoundException {
        GerundManager.affirmAdding(nounAccompanierId);
    }

    public static void rejectGerundAdding(int nounAccompanierId) throws RawNotFoundException {
        GerundManager.rejectAdding(nounAccompanierId);
    }

    public static void affirmGerundDeleting(int nounAccompanierId) throws RawNotFoundException {
        GerundManager.affirmDeleting(nounAccompanierId);
    }

    public static void rejectGerundDeleting(int nounAccompanierId) throws RawNotFoundException {
        GerundManager.rejectDeleting(nounAccompanierId);
    }
    //</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="Meaning...">
    public int suggestAddMeaning(MeaningBO newMeaningBO, SemanticVerbBO newSemanticVerbBO) throws RawNotFoundException, EntryExistedException {
        int newMeaningId = SemanticEntryBOManager.suggestAddMeaning(newMeaningBO, newSemanticVerbBO.getSemanticEntryId());
        int semanticVerbId = newSemanticVerbBO.getSemanticVerbId();
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        setNeedCheck(semanticVerbId);
        int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();

        return newMeaningId;
    }

    public void suggestUpdateMeaning(MeaningBO newMeaningBO, MeaningBO oldMeaningBO, SemanticVerbBO newSemanticVerbBO) throws RawNotFoundException {
        SemanticEntryBOManager.suggestUpdateMeaning(newMeaningBO, oldMeaningBO);
        int semanticVerbId = newSemanticVerbBO.getSemanticVerbId();
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        setNeedCheck(semanticVerbId);
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(newSemanticverb.getContextualverb().getIdentity());
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();

    }

    public void suggestDeleteMeaning(MeaningBO newMeaningBO, SemanticVerbBO newSemanticVerbBO) throws RawNotFoundException {
        SemanticEntryBOManager.suggestDeleteMeaning(newMeaningBO);
        int semanticVerbId = newSemanticVerbBO.getSemanticVerbId();
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        setNeedCheck(semanticVerbId);
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(newSemanticverb.getContextualverb().getIdentity());
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();
    }
///</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="Example...">
    public int suggestAddExample(ExampleBO newExampleBO, SemanticVerbBO newSemanticVerbBO) throws RawNotFoundException, EntryExistedException {
        int newExampleId = super.suggestAddExample(newExampleBO, newSemanticVerbBO.getSemanticEntryId());
        int semanticVerbId = newSemanticVerbBO.getSemanticVerbId();
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        setNeedCheck(semanticVerbId);
        int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();

        return newExampleId;
    }

    public void suggestUpdateExample(ExampleBO newExampleBO, ExampleBO oldExampleBO, SemanticVerbBO newSemanticVerbBO) throws RawNotFoundException {
        super.suggestUpdateExample(newExampleBO, oldExampleBO, newSemanticVerbBO.getSemanticEntryId());
        int semanticVerbId = newSemanticVerbBO.getSemanticVerbId();
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        setNeedCheck(semanticVerbId);
        int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();
    }

    public void suggestDeleteExample(ExampleBO newExampleBO, SemanticVerbBO newSemanticVerbBO) throws RawNotFoundException {
        super.suggestDeleteExample(newExampleBO, newSemanticVerbBO.getSemanticEntryId());
        int semanticVerbId = newSemanticVerbBO.getSemanticVerbId();
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        setNeedCheck(semanticVerbId);
        int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();
    }

    public static void affirmExampleAdding(int semanticVerbId, int exampleId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.affirmExampleAdding(newSemanticverb.getSemanticentry().getIdentity(), exampleId);
    }

    public static boolean affirmExampleAddingAU(int semanticVerbId, int exampleId, ExampleBO newExampleBO) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        return SemanticEntryBOManager.affirmExampleAddingAU(newSemanticverb.getSemanticentry().getIdentity(), exampleId, newExampleBO);
    }

    public static void rejectExampleAdding(int semanticVerbId, int exampleId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.rejectExampleAdding(newSemanticverb.getSemanticentry().getIdentity(), exampleId);
    }

    public static void affirmExampleUpdating(int semanticVerbId, int exampleId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.affirmExampleUpdating(newSemanticverb.getSemanticentry().getIdentity(), exampleId);
    }

    public static boolean affirmExampleUpdatingAU(int semanticVerbId, int exampleId, ExampleBO newExampleBO) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        return SemanticEntryBOManager.affirmExampleUpdatingAU(newSemanticverb.getSemanticentry().getIdentity(), exampleId, newExampleBO);
    }

    public static void rejectExampleUpdating(int semanticVerbId, int exampleId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.rejectExampleUpdating(newSemanticverb.getSemanticentry().getIdentity(), exampleId);
    }

    public static void affirmExampleDeleting(int semanticVerbId, int exampleId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.affirmExampleDeleting(newSemanticverb.getSemanticentry().getIdentity(), exampleId);
    }

    public static void rejectExampleDeleting(int semanticVerbId, int exampleId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.rejectExampleDeleting(newSemanticverb.getSemanticentry().getIdentity(), exampleId);
    }

///</editor-fold>
    /// <editor-fold defaultstate="collapsed" desc="CommonMistake...">
    public int suggestAddCommonMistake(CommonMistakeBO newCommonMistakeBO, SemanticVerbBO newSemanticVerbBO) throws RawNotFoundException, EntryExistedException {
        int newCommonMistakeId = super.suggestAddCommonMistake(newCommonMistakeBO, newSemanticVerbBO.getSemanticEntryId());
        int semanticVerbId = newSemanticVerbBO.getSemanticVerbId();
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        setNeedCheck(semanticVerbId);
        int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();

        return newCommonMistakeId;
    }

    public void suggestUpdateCommonMistake(CommonMistakeBO newCommonMistakeBO, CommonMistakeBO oldCommonMistakeBO, SemanticVerbBO newSemanticVerbBO) throws RawNotFoundException {
        super.suggestUpdateCommonMistake(newCommonMistakeBO, oldCommonMistakeBO, newSemanticVerbBO.getSemanticEntryId());
        int semanticVerbId = newSemanticVerbBO.getSemanticVerbId();
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        setNeedCheck(semanticVerbId);
        int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();

    }

    public void suggestDeleteCommonMistake(CommonMistakeBO newCommonMistakeBO, SemanticVerbBO newSemanticVerbBO) throws RawNotFoundException {
        super.suggestDeleteCommonMistake(newCommonMistakeBO, newSemanticVerbBO.getSemanticEntryId());
        int semanticVerbId = newSemanticVerbBO.getSemanticVerbId();
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        setNeedCheck(semanticVerbId);
        int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();

    }

    public static void affirmCommonMistakeUpdating(int semanticVerbId, int commonMistakeId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.affirmCommonMistakeUpdating(newSemanticverb.getSemanticentry().getIdentity(), commonMistakeId);
    }

    public static boolean affirmCommonMistakeUpdatingAU(int semanticVerbId, int commonMistakeId, CommonMistakeBO newCommonMistakeBO) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        return SemanticEntryBOManager.affirmCommonMistakeUpdatingAU(newSemanticverb.getSemanticentry().getIdentity(), commonMistakeId, newCommonMistakeBO);
    }

    public static void rejectCommonmistakeUpdating(int semanticVerbId, int commonmistakeId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.rejectCommonmistakeUpdating(newSemanticverb.getIdentity(), commonmistakeId);
    }

    public static void affirmCommonmistakeDeleting(int semanticVerbId, int commonmistakeId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.affirmCommonmistakeDeleting(newSemanticverb.getSemanticentry().getIdentity(), commonmistakeId);
    }

    public static void rejectCommonmistakeDeleting(int semanticVerbId, int commonmistakeId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.rejectCommonmistakeDeleting(newSemanticverb.getSemanticentry().getIdentity(), commonmistakeId);
    }

    public static void affirmCommonMistakeAdding(int semanticVerbId, int exampleId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.affirmCommonMistakeAdding(newSemanticverb.getSemanticentry().getIdentity(), exampleId);
    }

    public static boolean affirmCommonMistakeAddingAU(int semanticVerbId, int commonMistakeId, CommonMistakeBO newCommonMistakeBO) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        return SemanticEntryBOManager.affirmCommonMistakeAddingAU(newSemanticverb.getSemanticentry().getIdentity(), commonMistakeId, newCommonMistakeBO);
    }

    public static void rejectCommonMistakeAdding(int semanticVerbId, int commonmistakeId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.rejectCommonMistakeAdding(newSemanticverb.getSemanticentry().getIdentity(), commonmistakeId);
    }
///</editor-fold>

    /// <editor-fold defaultstate="collapsed" desc="LinguisticBenefit...">
    public int suggestAddLinguisticBenefit(LinguisticBenefitBO newLinguisticBenefitBO, SemanticVerbBO newSemanticVerbBO) throws RawNotFoundException, EntryExistedException {
        int newLinguisticBenefitId = super.suggestAddLinguisticBenefit(newLinguisticBenefitBO, newSemanticVerbBO.getSemanticEntryId());
        int semanticVerbId = newSemanticVerbBO.getSemanticVerbId();
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        setNeedCheck(semanticVerbId);
        int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();

        return newLinguisticBenefitId;
    }

    public void suggestUpdateLinguisticBenefit(LinguisticBenefitBO newLinguisticBenefitBO, LinguisticBenefitBO oldLinguisticBenefitBO, SemanticVerbBO newSemanticVerbBO) throws RawNotFoundException {
        super.suggestUpdateLinguisticBenefit(newLinguisticBenefitBO, oldLinguisticBenefitBO, newSemanticVerbBO.getSemanticEntryId());
        int semanticVerbId = newSemanticVerbBO.getSemanticVerbId();
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        setNeedCheck(semanticVerbId);
        int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();

    }

    public void suggestDeleteLinguisticBenefit(LinguisticBenefitBO newLinguisticBenefitBO, SemanticVerbBO newSemanticVerbBO) throws RawNotFoundException {
        super.suggestDeleteLinguisticBenefit(newLinguisticBenefitBO, newSemanticVerbBO.getSemanticEntryId());
        int semanticVerbId = newSemanticVerbBO.getSemanticVerbId();
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        setNeedCheck(semanticVerbId);
        int contextualVerbId = newSemanticverb.getContextualverb().getIdentity();
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        VerbBOManager.setNeedCheck(newContextualverb.getDerivedverb().getIdentity());
        SEMANTIC_VERB_DAO.clearSession();
    }

    public static void affirmLinguisticBenefitAdding(int semanticVerbId, int linguisticBenefitId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.affirmLinguisticBenefitAdding(newSemanticverb.getSemanticentry().getIdentity(), linguisticBenefitId);
    }

    public static boolean affirmLinguisticBenefitAddingAU(int semanticVerbId, int linguisticBenefitId, LinguisticBenefitBO newLinguisticBenefitBO) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        return SemanticEntryBOManager.affirmLinguisticBenefitAddingAU(newSemanticverb.getSemanticentry().getIdentity(), linguisticBenefitId, newLinguisticBenefitBO);
    }

    public static void rejectLinguisticBenefitAdding(int semanticVerbId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.rejectLinguisticBenefitAdding(newSemanticverb.getSemanticentry().getIdentity(), linguisticbenefitId);
    }

    public static void affirmLinguisticBenefitUpdating(int semanticVerbId, int linguisticBenefitId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.affirmLinguisticBenefitUpdating(newSemanticverb.getSemanticentry().getIdentity(), linguisticBenefitId);
    }

    public static boolean affirmLinguisticBenefitUpdatingAU(int semanticVerbId, int linguisticBenefitId, LinguisticBenefitBO newLinguisticBenefitBO) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        return SemanticEntryBOManager.affirmLinguisticBenefitUpdatingAU(newSemanticverb.getSemanticentry().getIdentity(), linguisticBenefitId, newLinguisticBenefitBO);
    }

    public static void rejectLinguisticbenefitUpdating(int semanticVerbId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.rejectLinguisticBenefitUpdating(newSemanticverb.getSemanticentry().getIdentity(), linguisticbenefitId);
    }

    public static void affirmLinguisticbenefitDeleting(int semanticVerbId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.affirmLinguisticBenefitDeleting(newSemanticverb.getSemanticentry().getIdentity(), linguisticbenefitId);
    }

    public static void rejectLinguisticbenefitDeleting(int semanticVerbId, int linguisticbenefitId) throws RawNotFoundException {
        Semanticverb newSemanticverb = SEMANTIC_VERB_DAO.getById(semanticVerbId);
        SemanticEntryBOManager.rejectLinguisticBenefitDeleting(newSemanticverb.getSemanticentry().getIdentity(), linguisticbenefitId);
    }
///</editor-fold>

    public static void main(String[] str) throws RawNotFoundException, EntryExistedException {
        User currUser = BLUtil.daoFactory.getUserDAO().getById(5);
        BOManagerUtil.setCurrentUser(currUser);
        /**
         * test affirmAdding()
         */
        //affirmAdding( 51181 );
        /**
         * test affirmAddingAU()
         */
        /*Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById( 3 );
         SemanticVerbBO newSemanticVerbBO = SemanticVerbBOManager.getSemanticVerbBO( 3, SearchProperties.commonSearchOptions );
         affirmAddingAU( 51187 , newSemanticVerbBO , 18430 );*/
        /**
         * test suggestUpdate()
         */
//        SemanticVerbBO newSemanticVerbBO = SemanticVerbBOManager.getSemanticVerbBO( 4 , SearchProperties.commonSearchOptions );
        //suggestUpdateInfo( newSemanticVerbBO , 54 );
        /**
         * test affirmUpdating()
         */
        //affirmUpdating( 47 , 18 );
        /**
         * test affirmUpdatingAU()
         */
        //affirmUpdatingAU( 54 , newSemanticVerbBO );
        /**
         * testRejectUpdating()
         */
        //rejectUpdating( 1);
        /**
         * test rejectAdding()
         */
        // rejectAdding( 51187);
        /**
         * test affirmExampleAdding()
         */
        // SemanticEntryBOManager.affirmExampleAdding( 132134 , 15061 );
        //Semanticentry newSemanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById( 987);
        // affirmExampleUpdating( 93494 , 13291 );
        ExampleBO newExampleBO = new ExampleBO();
        newExampleBO.setExample(" الثانية تجربة التعديل");
        newExampleBO.setSource("المعجم الوسيط");
        affirmExampleUpdatingAU(1985, 7, newExampleBO);
    }
}

package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import PersistenceLayer.Contextualverb;
import PersistenceLayer.Derivedverb;
import PersistenceLayer.Transitivitycase;
import Util.RawNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author al.hasan.fadel@gmail.com
 */
public class ContextualVerbManager {

    private TransitivityCasesManager newTransitivityCasesManager;

    public ContextualVerbManager() {
        newTransitivityCasesManager = new TransitivityCasesManager();
    }

    public static void updateContextualVerb(String newTransitivityCase, String oldTransitivityCase, int derivedVerbId) throws RawNotFoundException {
        Transitivitycase oldTransitivitycaseObj = BLUtil.daoFactory.getTransitivitycaseDAO().getConfirmedInstance(new Transitivitycase(oldTransitivityCase), null).get(0);

        Derivedverb newDrDerivedverb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId);

        Map restrictions = new HashMap();
        restrictions.put("eq_transitivitycase.transitivityCaseId", oldTransitivitycaseObj.getIdentity());
        restrictions.put("eq_derivedverb.derivedVerbId", derivedVerbId);
        Contextualverb oldContextualverb = BLUtil.daoFactory.getContextualverbDAO().getConfirmedInstance(new Contextualverb(newDrDerivedverb, oldTransitivitycaseObj), restrictions).get(0);

        int tempContextualVerb = addTempContextualVerb(newTransitivityCase, derivedVerbId);

        oldContextualverb.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
        oldContextualverb.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());
        oldContextualverb.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(tempContextualVerb));

        BLUtil.daoFactory.getContextualverbDAO().update(oldContextualverb);
    }

    public static int affirmUpdating(int contextualVerbId) throws RawNotFoundException {
        Contextualverb oldContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(oldContextualverb.getSuggestion().getUpdateId());
        if (newContextualverb.getInfoStatus().equals(WordStatus.TEMP_INFO_STATUS)) {
            Contextualverb copyOfNewContextualverb = new Contextualverb(newContextualverb.getDerivedverb(), newContextualverb.getTransitivitycase());

            newContextualverb.setTransitivitycase(oldContextualverb.getTransitivitycase());
            newContextualverb.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
            oldContextualverb.setTransitivitycase(copyOfNewContextualverb.getTransitivitycase());
            oldContextualverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            oldContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

            BOManagerUtil.AFFIRM_SUGGESTION(oldContextualverb.getSuggestion());
            BLUtil.daoFactory.getContextualverbDAO().update(oldContextualverb);
            BLUtil.daoFactory.getContextualverbDAO().update(newContextualverb);
            return contextualVerbId;
        } else {
            oldContextualverb.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
            BLUtil.daoFactory.getContextualverbDAO().update(oldContextualverb);
            return newContextualverb.getIdentity();
        }
    }

    public static int affirmUpdatingAU(int contextualVerbId, String transitivityCase) throws RawNotFoundException {
        Contextualverb oldContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        Contextualverb tempContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(oldContextualverb.getSuggestion().getUpdateId());

        //discuss two cases: temp equals to new contextual verb and new contextual verb already existed.
        if (tempContextualverb.getTransitivitycase().equals(transitivityCase)) {
            Transitivitycase oldTransitivitycase = oldContextualverb.getTransitivitycase();
            oldContextualverb.setTransitivitycase(tempContextualverb.getTransitivitycase());
            oldContextualverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            oldContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            BLUtil.daoFactory.getContextualverbDAO().update(oldContextualverb);
            BOManagerUtil.AFFIRM_SUGGESTION(oldContextualverb.getSuggestion());
            tempContextualverb.setTransitivitycase(oldTransitivitycase);
            tempContextualverb.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
            tempContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            BLUtil.daoFactory.getContextualverbDAO().update(tempContextualverb);
            return oldContextualverb.getIdentity();
        } else {
            int trId = TransitivityCasesManager.suggestAdding(transitivityCase);
            Transitivitycase transCase = BLUtil.daoFactory.getTransitivitycaseDAO().getById(trId);
            Map restrictions = BOManagerUtil.getAddRestrictions();
            restrictions.put("eq_transitivitycase.transitivityCaseId", trId);
            restrictions.put("eq_derivedverb.derivedVerbId", oldContextualverb.getDerivedverb().getIdentity());
            List<Contextualverb> contList = BLUtil.daoFactory.getContextualverbDAO().getConfirmedInstance(new Contextualverb(), restrictions);
            if (contList.size() > 0) {
                Contextualverb newContextualVerb = contList.get(0);
                oldContextualverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                oldContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldContextualverb.getSuggestion());
                tempContextualverb.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                tempContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                BLUtil.daoFactory.getContextualverbDAO().update(oldContextualverb);
                BLUtil.daoFactory.getContextualverbDAO().update(tempContextualverb);
                return newContextualVerb.getIdentity();
            } else {
                Contextualverb newContextualverb = new Contextualverb(oldContextualverb.getDerivedverb(), transCase);
                newContextualverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                int newContextualVerbId = BLUtil.daoFactory.getContextualverbDAO().insert(newContextualverb);

                oldContextualverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                oldContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldContextualverb.getSuggestion());

                tempContextualverb.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                tempContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                BLUtil.daoFactory.getContextualverbDAO().update(oldContextualverb);
                BLUtil.daoFactory.getContextualverbDAO().update(tempContextualverb);
                return newContextualVerbId;
            }
        }
    }

    public static int affirmUpdatingAU(int oldContextualVerbId, String transitivityCase, int tempContextualVerbId) throws RawNotFoundException {
        Contextualverb oldContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(oldContextualVerbId);
        Contextualverb tempContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(tempContextualVerbId);

        //discuss two cases: temp equals to new contextual verb and new contextual verb already existed.
        if (tempContextualverb.getTransitivitycase().equals(transitivityCase)) {
            tempContextualverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            tempContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            BLUtil.daoFactory.getContextualverbDAO().update(tempContextualverb);
            return tempContextualVerbId;
        } else {
            int trId = TransitivityCasesManager.suggestAdding(transitivityCase);
            Transitivitycase transCase = BLUtil.daoFactory.getTransitivitycaseDAO().getById(trId);
            Map restrictions = BOManagerUtil.getAddRestrictions();
            restrictions.put("eq_transitivitycase.transitivityCaseId", trId);
            restrictions.put("eq_derivedverb.derivedVerbId", oldContextualverb.getDerivedverb().getIdentity());
            List<Contextualverb> contList = BLUtil.daoFactory.getContextualverbDAO().getConfirmedInstance(new Contextualverb(), restrictions);
            if (contList.size() > 0) {
                Contextualverb newContextualVerb = contList.get(0);
                oldContextualverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                oldContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                tempContextualverb.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                tempContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                BLUtil.daoFactory.getContextualverbDAO().update(oldContextualverb);
                BLUtil.daoFactory.getContextualverbDAO().update(tempContextualverb);
                return newContextualVerb.getIdentity();
            } else {
                Contextualverb newContextualverb = new Contextualverb(oldContextualverb.getDerivedverb(), transCase);
                newContextualverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                int newContextualVerbId = BLUtil.daoFactory.getContextualverbDAO().insert(newContextualverb);

                tempContextualverb.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                tempContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

                BLUtil.daoFactory.getContextualverbDAO().update(oldContextualverb);
                BLUtil.daoFactory.getContextualverbDAO().update(tempContextualverb);
                return newContextualVerbId;
            }
        }
    }

    public static void rejectUpdating(int contextualVerbId) throws RawNotFoundException {
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        Contextualverb tempContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(newContextualverb.getSuggestion().getUpdateId());

        newContextualverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        newContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
        BOManagerUtil.REJECT_SUGGESTION(newContextualverb.getSuggestion());

        tempContextualverb.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
        tempContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);

        BLUtil.daoFactory.getContextualverbDAO().update(newContextualverb);
        BLUtil.daoFactory.getContextualverbDAO().update(tempContextualverb);
    }

    static void rejectDeleting(int contextualVerbId) throws RawNotFoundException {
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        if (newContextualverb.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            TransitivityCasesManager.rejectDeleting(newContextualverb.getTransitivitycase().getIdentity());
            newContextualverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newContextualverb.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newContextualverb.getSuggestion());
            }
            BLUtil.daoFactory.getContextualverbDAO().update(newContextualverb);
        }
    }

    public static void rejectAdding(int contextualVerbId) throws RawNotFoundException {
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        if (newContextualverb.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            if (newContextualverb.getTransitivitycase().getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
                newContextualverb.getTransitivitycase().setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                newContextualverb.getTransitivitycase().setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                if (newContextualverb.getTransitivitycase().getSuggestion() != null) {
                    BOManagerUtil.REJECT_SUGGESTION(newContextualverb.getTransitivitycase().getSuggestion());
                }
                BLUtil.daoFactory.getTransitivitycaseDAO().update(newContextualverb.getTransitivitycase());
            }
            newContextualverb.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
            newContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newContextualverb.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newContextualverb.getSuggestion());
            }
            BLUtil.daoFactory.getContextualverbDAO().update(newContextualverb);
        }
    }

    public static void affirmDeleting(int contextualVerbId) throws RawNotFoundException {
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        if (newContextualverb.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            TransitivityCasesManager.affirmDeleting(newContextualverb.getTransitivitycase().getIdentity());
            newContextualverb.setInfoStatus(WordStatus.NEED_DELETING);
            newContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newContextualverb.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newContextualverb.getSuggestion());
            }
            BLUtil.daoFactory.getContextualverbDAO().update(newContextualverb);
        }
    }

    public static int addTempContextualVerb(String transitivityCase, int derivedVerbId) throws RawNotFoundException {
        Derivedverb derivedVerb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId);

        int trId = TransitivityCasesManager.suggestAdding(transitivityCase);
        Transitivitycase transCase = BLUtil.daoFactory.getTransitivitycaseDAO().getById(trId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_transitivitycase.transitivityCaseId", trId);
        restrictions.put("eq_derivedverb.derivedVerbId", derivedVerbId);

        Contextualverb contextualVerb = new Contextualverb();
        contextualVerb.setDerivedverb(derivedVerb);
        contextualVerb.setTransitivitycase(transCase);
        contextualVerb.setChechStatus(WordStatus.NEED_CHECK_STATUS);
        contextualVerb.setInfoStatus(WordStatus.TEMP_INFO_STATUS);
        //return BLUtil.daoFactory.getContextualverbDAO().insertWithCheck(contextualVerb, restrictions);
        throw new UnsupportedOperationException();
    }

    public static int suggestAddContextualVerb(String transitivityCase, int derivedVerbId) throws RawNotFoundException {
        Derivedverb derivedVerb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId);
        //test if the contextual verb is found:
        Set<Contextualverb> contextVerbs = derivedVerb.getContextualverbs();
        for (Iterator iter = contextVerbs.iterator(); iter.hasNext();) {
            Contextualverb contextualVerb = (Contextualverb) iter.next();
            String existedTransCase = contextualVerb.getTransitivitycase().getTransitivityCase();
            if (existedTransCase.equals(transitivityCase)) {
                return contextualVerb.getIdentity();
            }
        }
        //the contextual verb is not existed, so add it.
        int trId = TransitivityCasesManager.suggestAdding(transitivityCase);
        Transitivitycase transCase = BLUtil.daoFactory.getTransitivitycaseDAO().getById(trId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_transitivitycase.transitivityCaseId", trId);
        restrictions.put("eq_derivedverb.derivedVerbId", derivedVerbId);

        Contextualverb contextualVerb = new Contextualverb();
        contextualVerb.setDerivedverb(derivedVerb);
        contextualVerb.setTransitivitycase(transCase);
        contextualVerb.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        contextualVerb.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        contextualVerb.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        return BLUtil.daoFactory.getContextualverbDAO().insertWithCheck(contextualVerb, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);

    }

    static void affirmAdding(Integer contextualVerbId) throws RawNotFoundException {
        Contextualverb newContextualverb = BLUtil.daoFactory.getContextualverbDAO().getById(contextualVerbId);
        newContextualverb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        newContextualverb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
        BOManagerUtil.AFFIRM_SUGGESTION(newContextualverb.getSuggestion());
        BLUtil.daoFactory.getContextualverbDAO().update(newContextualverb);
    }

    static int addContextualVerb(String transitivityCase, int derivedVerbId) throws RawNotFoundException {
        Derivedverb derivedVerb = BLUtil.daoFactory.getDerivedverbDAO().getById(derivedVerbId);
        //test if the contextual verb is found:
        Set<Contextualverb> contextVerbs = derivedVerb.getContextualverbs();
        for (Iterator iter = contextVerbs.iterator(); iter.hasNext();) {
            Contextualverb contextualVerb = (Contextualverb) iter.next();
            String existedTransCase = contextualVerb.getTransitivitycase().getTransitivityCase();
            if (existedTransCase.equals(transitivityCase)) {
                return contextualVerb.getIdentity();
            }
        }
        //the contextual verb is not existed, so add it.
        int trId = TransitivityCasesManager.suggestAdding(transitivityCase);
        Transitivitycase transCase = BLUtil.daoFactory.getTransitivitycaseDAO().getById(trId);

        Contextualverb contextualVerb = new Contextualverb();
        contextualVerb.setDerivedverb(derivedVerb);
        contextualVerb.setTransitivitycase(transCase);
        contextualVerb.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
        contextualVerb.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
        contextualVerb.setSuggestion(BOManagerUtil.GET_CONFIRMED_ADD_SUGGESTION());
        return BLUtil.daoFactory.getContextualverbDAO().insert(contextualVerb);
    }
}

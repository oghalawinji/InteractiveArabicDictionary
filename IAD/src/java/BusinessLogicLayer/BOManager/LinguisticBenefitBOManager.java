/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BusinessObjects.LinguisticBenefitBO;
import BusinessLogicLayer.BusinessObjects.UpdatedLinguisticBenefitBO;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.LinguisticbenefitJPADAO;
import PersistenceLayer.Entrylinguisticbenefit;
import PersistenceLayer.Linguisticbenefit;
import PersistenceLayer.Semanticentry;
import PersistenceLayer.Source;
import Util.RawNotFoundException;
import java.util.ArrayList;
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
public class LinguisticBenefitBOManager {

    private SourceBOManager newSourceBOManager;
    private static LinguisticbenefitJPADAO LINGUISTIC_BENEFIT_DAO = BLUtil.daoFactory.getLinguisticbenefitDAO();

    public LinguisticBenefitBOManager() {
        newSourceBOManager = new SourceBOManager();
    }

    public SourceBOManager getNewSourceBOManager() {
        return newSourceBOManager;
    }

    public void setNewSourceBOManager(SourceBOManager newSourceBOManager) {
        this.newSourceBOManager = newSourceBOManager;
    }

    public static List<LinguisticBenefitBO> getLinguisticBenefitsOfSemanticEntry(Integer semanticEntryId) {
        try {
            Semanticentry semEntry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);
            return getLinguisticBenefitsOfSemanticEntry(semEntry);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(LinguisticBenefitBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static List<LinguisticBenefitBO> getLinguisticBenefitsOfSemanticEntry(Semanticentry semEntry) {
        List<LinguisticBenefitBO> linguisticBenefitBOList = new ArrayList<LinguisticBenefitBO>();
        Set<Entrylinguisticbenefit> linguisticBenefitSet = semEntry.getEntrylinguisticbenefits();
        for (Iterator iter3 = linguisticBenefitSet.iterator(); iter3.hasNext();) {
            Entrylinguisticbenefit newEntrylinguisticbenefit = (Entrylinguisticbenefit) iter3.next();
            Linguisticbenefit linguisticBenefit = newEntrylinguisticbenefit.getLinguisticbenefit();
            LinguisticBenefitBO linguisticBenefitBO = new LinguisticBenefitBO();
            linguisticBenefitBO.setDescription(linguisticBenefit.getLinguisticBenefit());
            linguisticBenefitBO.setSource(linguisticBenefit.getSource().getSource());
            linguisticBenefitBO.setStatus(newEntrylinguisticbenefit.getInfoStatus());
            linguisticBenefitBO.setLinguisticBenefitId(linguisticBenefit.getIdentity());
            linguisticBenefitBOList.add(linguisticBenefitBO);
        }
        return linguisticBenefitBOList;
    }

    public static UpdatedLinguisticBenefitBO get(LinguisticBenefitBO linguisticBenefitsBO) {
        UpdatedLinguisticBenefitBO newLinguisticBenefitsBO = new UpdatedLinguisticBenefitBO();

        try {
            Linguisticbenefit linguisticBenefits = LINGUISTIC_BENEFIT_DAO.getById(linguisticBenefitsBO.getLinguisticBenefitId());
            Linguisticbenefit updatedLinguisticBenefits = LINGUISTIC_BENEFIT_DAO.getById(linguisticBenefits.getSuggestion().getUpdateId());
            newLinguisticBenefitsBO.setOldDescription(updatedLinguisticBenefits.getLinguisticBenefit());
            newLinguisticBenefitsBO.setOldSource(updatedLinguisticBenefits.getSource().getSource());
            newLinguisticBenefitsBO.setOldLinguisticBenefitId(updatedLinguisticBenefits.getIdentity());

            newLinguisticBenefitsBO.setDescription(linguisticBenefits.getLinguisticBenefit());
            newLinguisticBenefitsBO.setSource(linguisticBenefits.getSource().getSource());
            newLinguisticBenefitsBO.setStatus(linguisticBenefits.getInfoStatus());
            return newLinguisticBenefitsBO;
        } catch (RawNotFoundException ex) {
            Logger.getLogger(LinguisticBenefitBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static LinguisticBenefitBO get(Linguisticbenefit linguisticBenefits) {
        if (linguisticBenefits.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            UpdatedLinguisticBenefitBO newLinguisticBenefitsBO = new UpdatedLinguisticBenefitBO();

            try {
                Linguisticbenefit updatedLinguisticBenefits = LINGUISTIC_BENEFIT_DAO.getById(linguisticBenefits.getSuggestion().getUpdateId());
                newLinguisticBenefitsBO.setOldDescription(updatedLinguisticBenefits.getLinguisticBenefit());
                newLinguisticBenefitsBO.setOldSource(updatedLinguisticBenefits.getSource().getSource());
                newLinguisticBenefitsBO.setOldLinguisticBenefitId(updatedLinguisticBenefits.getIdentity());
            } catch (RawNotFoundException ex) {
                Logger.getLogger(LinguisticBenefitBOManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            newLinguisticBenefitsBO.setLinguisticBenefitId(linguisticBenefits.getIdentity());
            newLinguisticBenefitsBO.setDescription(linguisticBenefits.getLinguisticBenefit());
            newLinguisticBenefitsBO.setSource(linguisticBenefits.getSource().getSource());
            newLinguisticBenefitsBO.setStatus(linguisticBenefits.getInfoStatus());
            return newLinguisticBenefitsBO;

        } else {
            LinguisticBenefitBO newLinguisticBenefitsBO = new LinguisticBenefitBO();
            newLinguisticBenefitsBO.setLinguisticBenefitId(linguisticBenefits.getIdentity());
            newLinguisticBenefitsBO.setDescription(linguisticBenefits.getLinguisticBenefit());
            newLinguisticBenefitsBO.setSource(linguisticBenefits.getSource().getSource());
            newLinguisticBenefitsBO.setStatus(linguisticBenefits.getInfoStatus());
            return newLinguisticBenefitsBO;
        }
    }

    public int suggestAddLinguisticBenefit(String linguisticBenefitText, String source) throws RawNotFoundException {
        Linguisticbenefit linguisticBenefit = new Linguisticbenefit();
        linguisticBenefit.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        linguisticBenefit.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        linguisticBenefit.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        linguisticBenefit.setLinguisticBenefit(linguisticBenefitText);

        Integer sourceId = newSourceBOManager.suggestAdding(source);
        Source sourceObj = BLUtil.daoFactory.getSourceDAO().getById(sourceId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_source.sourceId", sourceId);
        linguisticBenefit.setSource(sourceObj);

        return LINGUISTIC_BENEFIT_DAO.insertWithCheck(linguisticBenefit, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
    }

    public int suggestAddLinguisticBenefit(LinguisticBenefitBO linguisticBenefitBO) throws RawNotFoundException {
        Linguisticbenefit linguisticBenefit = new Linguisticbenefit();

        linguisticBenefit.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        linguisticBenefit.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        linguisticBenefit.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        linguisticBenefit.setLinguisticBenefit(linguisticBenefitBO.getDescription());

        Integer sourceId = newSourceBOManager.suggestAdding(linguisticBenefitBO.getSource());
        Source sourceObj = BLUtil.daoFactory.getSourceDAO().getById(sourceId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_source.sourceId", sourceId);
        linguisticBenefit.setSource(sourceObj);

        return LINGUISTIC_BENEFIT_DAO.insertWithCheck(linguisticBenefit, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
    }

    public static void affirmAdding(int linguisticbenefitId) throws RawNotFoundException {
        Linguisticbenefit newLinguisticbenefit = LINGUISTIC_BENEFIT_DAO.getById(linguisticbenefitId);
        if (newLinguisticbenefit.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newLinguisticbenefit.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newLinguisticbenefit.getSuggestion());
            }
            LINGUISTIC_BENEFIT_DAO.update(newLinguisticbenefit);
        }
    }

    public static boolean affirmAddingAU(int linguisticbenefitId, LinguisticBenefitBO updateLinguisticbenefitBO) throws RawNotFoundException {
        Linguisticbenefit oldLinguisticbenefit = LINGUISTIC_BENEFIT_DAO.getById(linguisticbenefitId);
        Linguisticbenefit newLinguisticbenefit = new Linguisticbenefit(new Source(updateLinguisticbenefitBO.getSource()), updateLinguisticbenefitBO.getDescription());
        if (!oldLinguisticbenefit.equals(newLinguisticbenefit)) {
            //if the new Linguisticbenefit does not exist, then update old linguisticbenefit values
            if (LINGUISTIC_BENEFIT_DAO.getByExample(newLinguisticbenefit, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES).size() == 0) {
                oldLinguisticbenefit.setLinguisticBenefit(newLinguisticbenefit.getLinguisticBenefit());
                oldLinguisticbenefit.setSource(newLinguisticbenefit.getSource());
                if (oldLinguisticbenefit.getSuggestion() != null) {
                    BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldLinguisticbenefit.getSuggestion());
                }
                LINGUISTIC_BENEFIT_DAO.update(newLinguisticbenefit);
                return true;
            }
        }
        return false;
    }

    static void rejectAdding(int exampleId) throws RawNotFoundException {
        Linguisticbenefit newLinguisticBenefit = LINGUISTIC_BENEFIT_DAO.getById(exampleId);
        if (newLinguisticBenefit.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newLinguisticBenefit.setInfoStatus(WordStatus.NEED_DELETING);
            newLinguisticBenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newLinguisticBenefit.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newLinguisticBenefit.getSuggestion());
            }
            LINGUISTIC_BENEFIT_DAO.update(newLinguisticBenefit);
        }
    }

    public int AddTempLinguisticBenefit(LinguisticBenefitBO linguisticBenefitBO) throws RawNotFoundException {
        Linguisticbenefit linguisticBenefit = new Linguisticbenefit();

        linguisticBenefit.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
        linguisticBenefit.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());
        linguisticBenefit.setLinguisticBenefit(linguisticBenefitBO.getDescription());

        Integer sourceId = newSourceBOManager.suggestAdding(linguisticBenefitBO.getSource());
        Source sourceObj = BLUtil.daoFactory.getSourceDAO().getById(sourceId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_source.sourceId", sourceId);
        linguisticBenefit.setSource(sourceObj);

        return LINGUISTIC_BENEFIT_DAO.insertWithCheck(linguisticBenefit, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
    }

    public void suggestUpdating(LinguisticBenefitBO newLinguisticBenefitBO, LinguisticBenefitBO oldLinguisticBenefitBO) throws RawNotFoundException {
        Linguisticbenefit oldLb = new Linguisticbenefit(new Source(oldLinguisticBenefitBO.getSource()), oldLinguisticBenefitBO.getDescription());
        Linguisticbenefit newLb = new Linguisticbenefit(new Source(newLinguisticBenefitBO.getSource()), newLinguisticBenefitBO.getDescription());
        if (!oldLb.equals(newLb)) {
            int newLinguisticBenefitId = this.AddTempLinguisticBenefit(newLinguisticBenefitBO);

            Source sourceObj = BLUtil.daoFactory.getSourceDAO().getConfirmedInstance(new Source(oldLinguisticBenefitBO.getSource()), null).get(0);
            Map restrictions = new HashMap();
            restrictions.put("eq_source.sourceId", sourceObj.getIdentity());

            Linguisticbenefit oldLinguisticBenefit = LINGUISTIC_BENEFIT_DAO.getConfirmedInstance(new Linguisticbenefit(sourceObj, oldLinguisticBenefitBO.getDescription()), restrictions).get(0);

            oldLinguisticBenefit.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(newLinguisticBenefitId));
            oldLinguisticBenefit.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
            oldLinguisticBenefit.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());

            LINGUISTIC_BENEFIT_DAO.update(oldLinguisticBenefit);
        }
    }

    public static void affirmUpdating(int linguisticbenefitId) throws RawNotFoundException {
        Linguisticbenefit newLinguisticbenefit = LINGUISTIC_BENEFIT_DAO.getById(linguisticbenefitId);
        if (newLinguisticbenefit.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            if (newLinguisticbenefit.getSuggestion() != null) {
                Linguisticbenefit tempLinguisticbenefit = LINGUISTIC_BENEFIT_DAO.getById(newLinguisticbenefit.getSuggestion().getUpdateId());

                Linguisticbenefit copyOfTempLinguisticbenefit = new Linguisticbenefit(tempLinguisticbenefit.getSource(), tempLinguisticbenefit.getLinguisticBenefit());

                tempLinguisticbenefit.setLinguisticBenefit(newLinguisticbenefit.getLinguisticBenefit());
                tempLinguisticbenefit.setSource(newLinguisticbenefit.getSource());
                tempLinguisticbenefit.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                tempLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                LINGUISTIC_BENEFIT_DAO.update(tempLinguisticbenefit);

                newLinguisticbenefit.setLinguisticBenefit(copyOfTempLinguisticbenefit.getLinguisticBenefit());
                newLinguisticbenefit.setSource(copyOfTempLinguisticbenefit.getSource());
                newLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                BOManagerUtil.AFFIRM_SUGGESTION(newLinguisticbenefit.getSuggestion());
                LINGUISTIC_BENEFIT_DAO.update(newLinguisticbenefit);
            }
        }
    }

    public static boolean affirmUpdatingAU(int oldLinguisticbenefitId, LinguisticBenefitBO newLinguisticbenefitBO) throws RawNotFoundException {
        Linguisticbenefit oldLinguisticbenefit = LINGUISTIC_BENEFIT_DAO.getById(oldLinguisticbenefitId);
        int newSourceId = SourceBOManager.suggestAdding(newLinguisticbenefitBO.getSource());
        Source newSource = BLUtil.daoFactory.getSourceDAO().getById(newSourceId);
        Linguisticbenefit newLinguisticbenefit = new Linguisticbenefit(newSource, newLinguisticbenefitBO.getDescription());
        Map restrictions = new HashMap();
        restrictions.put("eq_source.sourceId", newSourceId);
        if (LINGUISTIC_BENEFIT_DAO.getConfirmedInstance(newLinguisticbenefit, restrictions).size() == 0) {
            if (oldLinguisticbenefit.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                if (oldLinguisticbenefit.getSuggestion() != null) {
                    Linguisticbenefit tempLinguisticbenefit = LINGUISTIC_BENEFIT_DAO.getById(oldLinguisticbenefit.getSuggestion().getUpdateId());

                    tempLinguisticbenefit.setLinguisticBenefit(oldLinguisticbenefit.getLinguisticBenefit());
                    tempLinguisticbenefit.setSource(oldLinguisticbenefit.getSource());
                    tempLinguisticbenefit.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                    tempLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    LINGUISTIC_BENEFIT_DAO.update(tempLinguisticbenefit);

                    oldLinguisticbenefit.setLinguisticBenefit(newLinguisticbenefit.getLinguisticBenefit());
                    oldLinguisticbenefit.setSource(newSource);
                    oldLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    oldLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldLinguisticbenefit.getSuggestion());
                    LINGUISTIC_BENEFIT_DAO.update(oldLinguisticbenefit);
                    return true;
                }
            }
        }
        return false;
    }

    public static void rejectUpdating(int linguisticBenefitId) throws RawNotFoundException {
        Linguisticbenefit newLinguisticbenefit = LINGUISTIC_BENEFIT_DAO.getById(linguisticBenefitId);
        if (newLinguisticbenefit.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            if (newLinguisticbenefit.getSuggestion() != null) {
                Linguisticbenefit tempLinguisticbenefit = LINGUISTIC_BENEFIT_DAO.getById(newLinguisticbenefit.getSuggestion().getUpdateId());
                tempLinguisticbenefit.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                tempLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                LINGUISTIC_BENEFIT_DAO.update(tempLinguisticbenefit);

                newLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                BOManagerUtil.REJECT_SUGGESTION(newLinguisticbenefit.getSuggestion());
                LINGUISTIC_BENEFIT_DAO.update(newLinguisticbenefit);
            }
        }
    }

    public void suggestDeletelinguisticBenefit(LinguisticBenefitBO deletedlinguisticBenefitBO) throws RawNotFoundException {
        Source sourceObj = BLUtil.daoFactory.getSourceDAO().getConfirmedInstance(new Source(deletedlinguisticBenefitBO.getSource()), null).get(0);
        Map restrictions = new HashMap();
        restrictions.put("eq_source.sourceId", sourceObj.getIdentity());

        Linguisticbenefit linguisticBenefit = LINGUISTIC_BENEFIT_DAO.getConfirmedInstance(new Linguisticbenefit(sourceObj, deletedlinguisticBenefitBO.getDescription()), restrictions).get(0);
        linguisticBenefit.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        linguisticBenefit.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        linguisticBenefit.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        LINGUISTIC_BENEFIT_DAO.update(linguisticBenefit);
    }

    public static void affirmDeleting(int linguisticbenefitId) throws RawNotFoundException {
        Linguisticbenefit newLinguisticbenefit = LINGUISTIC_BENEFIT_DAO.getById(linguisticbenefitId);

        if (newLinguisticbenefit.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newLinguisticbenefit.setInfoStatus(WordStatus.NEED_DELETING);
            newLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newLinguisticbenefit.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newLinguisticbenefit.getSuggestion());
            }
            LINGUISTIC_BENEFIT_DAO.update(newLinguisticbenefit);
        }
    }

    public static void rejectDeleting(int linguisticbenefitId) throws RawNotFoundException {
        Linguisticbenefit newLinguisticbenefit = LINGUISTIC_BENEFIT_DAO.getById(linguisticbenefitId);

        if (newLinguisticbenefit.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newLinguisticbenefit.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newLinguisticbenefit.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newLinguisticbenefit.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newLinguisticbenefit.getSuggestion());
            }
            LINGUISTIC_BENEFIT_DAO.update(newLinguisticbenefit);
        }
    }
}

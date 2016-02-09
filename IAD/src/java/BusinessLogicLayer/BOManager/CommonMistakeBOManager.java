package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BusinessObjects.CommonMistakeBO;
import BusinessLogicLayer.BusinessObjects.UpdatedCommonMistakeBO;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.CommonmistakeJPADAO;
import DataAccessLayer.JPADAO.EntrycommonmistakeJPADAO;
import PersistenceLayer.Commonmistake;
import PersistenceLayer.Entrycommonmistake;
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
public class CommonMistakeBOManager {

    private SourceBOManager newSourceBOManager;
    private static CommonmistakeJPADAO COMMON_MISTAKE_DAO = BLUtil.daoFactory.getCommonmistakeDAO();

    public CommonMistakeBOManager() {
        newSourceBOManager = new SourceBOManager();
    }

    public SourceBOManager getNewSourceBOManager() {
        return newSourceBOManager;
    }

    public void setNewSourceBOManager(SourceBOManager newSourceBOManager) {
        this.newSourceBOManager = newSourceBOManager;
    }

    public static List<CommonMistakeBO> getCommonMistakesOfSemanticEntry(Integer semanticEntryId) {
        try {
            Semanticentry semEntry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);
            return getCommonMistakesOfSemanticEntry(semEntry);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(CommonMistakeBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static List<CommonMistakeBO> getCommonMistakesOfSemanticEntry(Semanticentry semEntry) {
        List<CommonMistakeBO> commonMistakeBOList = new ArrayList<CommonMistakeBO>();
        Set<Entrycommonmistake> commonMistakeSet = semEntry.getEntrycommonmistakes();
        for (Iterator iter3 = commonMistakeSet.iterator(); iter3.hasNext();) {
            Entrycommonmistake newEntrycommonmistake = (Entrycommonmistake) iter3.next();
            Commonmistake commonmistake = newEntrycommonmistake.getCommonmistake();
            CommonMistakeBO commonmistakeBO = new CommonMistakeBO();
            commonmistakeBO.setDescription(commonmistake.getCommonMistake());
            commonmistakeBO.setSource(commonmistake.getSource().getSource());
            commonmistakeBO.setStatus(newEntrycommonmistake.getInfoStatus());
            commonmistakeBO.setCommonMistakeId(commonmistake.getIdentity());
            commonMistakeBOList.add(commonmistakeBO);
        }
        return commonMistakeBOList;
    }

    public static UpdatedCommonMistakeBO get(CommonMistakeBO commonMistakeBO) {
        UpdatedCommonMistakeBO newCommonMistakeBO = new UpdatedCommonMistakeBO();

        try {
            Commonmistake commonMistake = COMMON_MISTAKE_DAO.getById(commonMistakeBO.getCommonMistakeId());
            Commonmistake updatedCommonMistake = COMMON_MISTAKE_DAO.getById(commonMistake.getSuggestion().getUpdateId());
            newCommonMistakeBO.setOldDescription(updatedCommonMistake.getCommonMistake());
            newCommonMistakeBO.setOldSource(updatedCommonMistake.getSource().getSource());
            newCommonMistakeBO.setOldCommonMistakeId(updatedCommonMistake.getIdentity());

            newCommonMistakeBO.setDescription(commonMistake.getCommonMistake());
            newCommonMistakeBO.setSource(commonMistake.getSource().getSource());
            newCommonMistakeBO.setStatus(commonMistake.getInfoStatus());
            return newCommonMistakeBO;
        } catch (RawNotFoundException ex) {
            Logger.getLogger(CommonMistakeBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static CommonMistakeBO get(Commonmistake commonMistake) {
        if (commonMistake.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            UpdatedCommonMistakeBO newCommonMistakeBO = new UpdatedCommonMistakeBO();

            try {
                Commonmistake updatedCommonMistake = COMMON_MISTAKE_DAO.getById(commonMistake.getSuggestion().getUpdateId());
                newCommonMistakeBO.setOldDescription(updatedCommonMistake.getCommonMistake());
                newCommonMistakeBO.setOldSource(updatedCommonMistake.getSource().getSource());
                newCommonMistakeBO.setOldCommonMistakeId(updatedCommonMistake.getIdentity());
            } catch (RawNotFoundException ex) {
                Logger.getLogger(CommonMistakeBOManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            newCommonMistakeBO.setCommonMistakeId(commonMistake.getIdentity());
            newCommonMistakeBO.setDescription(commonMistake.getCommonMistake());
            newCommonMistakeBO.setSource(commonMistake.getSource().getSource());
            newCommonMistakeBO.setStatus(commonMistake.getInfoStatus());
            return newCommonMistakeBO;

        } else {
            CommonMistakeBO newCommonMistakeBO = new CommonMistakeBO();
            newCommonMistakeBO.setCommonMistakeId(commonMistake.getIdentity());
            newCommonMistakeBO.setDescription(commonMistake.getCommonMistake());
            newCommonMistakeBO.setSource(commonMistake.getSource().getSource());
            newCommonMistakeBO.setStatus(commonMistake.getInfoStatus());
            return newCommonMistakeBO;
        }
    }

    public static boolean affirmUpdatingAU(int oldCommonmistakeId, CommonMistakeBO newCommonmistakeBO) throws RawNotFoundException {
        Commonmistake oldCommonmistake = COMMON_MISTAKE_DAO.getById(oldCommonmistakeId);
        int newSourceId = SourceBOManager.suggestAdding(newCommonmistakeBO.getSource());
        Source newSource = BLUtil.daoFactory.getSourceDAO().getById(newSourceId);
        Commonmistake newCommonmistake = new Commonmistake(newSource, newCommonmistakeBO.getDescription());
        Map restrictions = new HashMap();
        restrictions.put("eq_source.sourceId", newSourceId);
        if (COMMON_MISTAKE_DAO.getConfirmedInstance(newCommonmistake, restrictions).size() == 0) {
            if (oldCommonmistake.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
                if (oldCommonmistake.getSuggestion() != null) {
                    Commonmistake tempCommonmistake = COMMON_MISTAKE_DAO.getById(oldCommonmistake.getSuggestion().getUpdateId());

                    tempCommonmistake.setCommonMistake(oldCommonmistake.getCommonMistake());
                    tempCommonmistake.setSource(oldCommonmistake.getSource());
                    tempCommonmistake.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                    tempCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    COMMON_MISTAKE_DAO.update(tempCommonmistake);

                    oldCommonmistake.setCommonMistake(newCommonmistake.getCommonMistake());
                    oldCommonmistake.setSource(newSource);
                    oldCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    oldCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldCommonmistake.getSuggestion());
                    COMMON_MISTAKE_DAO.update(oldCommonmistake);
                    return true;
                }
            }
        }
        return false;
    }

    public static void deleteCommonMistake(Integer semanticEntryId, Integer mistakeId) {
        try {
            EntrycommonmistakeJPADAO entryMistakeDAO = BLUtil.daoFactory.getEntrycommonmistakeDAO();
            CommonmistakeJPADAO mistakeDAO = COMMON_MISTAKE_DAO;
            Semanticentry semanticEntry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);
            Set<Entrycommonmistake> entryMistakes = semanticEntry.getEntrycommonmistakes();
            for (Iterator iter = entryMistakes.iterator(); iter.hasNext();) {
                Entrycommonmistake entryMistakeRelation = (Entrycommonmistake) iter.next();
                Integer id = entryMistakeRelation.getCommonmistake().getIdentity();
                if (id == mistakeId) {
                    entryMistakeDAO.delete(entryMistakeRelation.getIdentity());
                    Commonmistake commonMistake = mistakeDAO.getById(mistakeId);
                    if (commonMistake.getEntrycommonmistakes().size() == 0) {
                        entryMistakeDAO.delete(mistakeId);
                    }
                    return;
                }
            }
        } catch (RawNotFoundException ex) {
            Logger.getLogger(CommonMistakeBOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int suggestAddCommonMistake(String commonMistakeText, String source) throws RawNotFoundException {
        Commonmistake commonMistake = new Commonmistake();
        commonMistake.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        commonMistake.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        commonMistake.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        commonMistake.setCommonMistake(commonMistakeText);

        Integer sourceId = newSourceBOManager.suggestAdding(source);
        Source sourceObj = BLUtil.daoFactory.getSourceDAO().getById(sourceId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_source.sourceId", sourceId);
        commonMistake.setSource(sourceObj);

        return COMMON_MISTAKE_DAO.insertWithCheck(commonMistake, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
    }

    public static void affirmAdding(int commonmistakeId) throws RawNotFoundException {
        Commonmistake newCommonmistake = COMMON_MISTAKE_DAO.getById(commonmistakeId);
        if (newCommonmistake.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newCommonmistake.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newCommonmistake.getSuggestion());
            }
            COMMON_MISTAKE_DAO.update(newCommonmistake);
        }
    }

    public int suggestAddCommonMistake(CommonMistakeBO commonMistakeBO) throws RawNotFoundException {
        Commonmistake commonMistake = new Commonmistake();

        commonMistake.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        commonMistake.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        commonMistake.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        commonMistake.setCommonMistake(commonMistakeBO.getDescription());

        Integer sourceId = newSourceBOManager.suggestAdding(commonMistakeBO.getSource());
        Source sourceObj = BLUtil.daoFactory.getSourceDAO().getById(sourceId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_source.sourceId", sourceId);
        commonMistake.setSource(sourceObj);

        return COMMON_MISTAKE_DAO.insertWithCheck(commonMistake, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
    }

    public int AddTempCommonMistake(CommonMistakeBO commonMistakeBO) throws RawNotFoundException {
        Commonmistake commonMistake = new Commonmistake();

        commonMistake.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
        commonMistake.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());
        commonMistake.setCommonMistake(commonMistakeBO.getDescription());

        Integer sourceId = newSourceBOManager.suggestAdding(commonMistakeBO.getSource());
        Source sourceObj = BLUtil.daoFactory.getSourceDAO().getById(sourceId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_source.sourceId", sourceId);
        commonMistake.setSource(sourceObj);

        return COMMON_MISTAKE_DAO.insertWithCheck(commonMistake, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
    }

    public void suggestUpdateCommonMistake(CommonMistakeBO newCommonMistakeBO, CommonMistakeBO oldCommonMistakeBO) throws RawNotFoundException {

        Commonmistake newCm = new Commonmistake(new Source(newCommonMistakeBO.getSource()), newCommonMistakeBO.getDescription());
        Commonmistake oldCm = new Commonmistake(new Source(oldCommonMistakeBO.getSource()), oldCommonMistakeBO.getDescription());
        if (!newCm.equals(oldCm)) {
            int newCommonMistakeId = this.AddTempCommonMistake(newCommonMistakeBO);

            Source sourceObj = BLUtil.daoFactory.getSourceDAO().getConfirmedInstance(new Source(oldCommonMistakeBO.getSource()), null).get(0);
            Map restrictions = new HashMap();
            restrictions.put("eq_source.sourceId", sourceObj.getIdentity());

            Commonmistake oldCommonMistake = COMMON_MISTAKE_DAO.getConfirmedInstance(new Commonmistake(sourceObj, oldCommonMistakeBO.getDescription()), restrictions).get(0);

            oldCommonMistake.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(newCommonMistakeId));
            oldCommonMistake.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
            oldCommonMistake.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());

            COMMON_MISTAKE_DAO.update(oldCommonMistake);
        }
    }

    public static void affirmUpdate(int commonmistakeId) throws RawNotFoundException {
        Commonmistake newCommonmistake = COMMON_MISTAKE_DAO.getById(commonmistakeId);
        if (newCommonmistake.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            if (newCommonmistake.getSuggestion() != null) {
                Commonmistake tempCommonmistake = COMMON_MISTAKE_DAO.getById(newCommonmistake.getSuggestion().getUpdateId());

                Commonmistake copyOfTempCommonmistake = new Commonmistake(tempCommonmistake.getSource(), tempCommonmistake.getCommonMistake());

                tempCommonmistake.setCommonMistake(newCommonmistake.getCommonMistake());
                tempCommonmistake.setSource(newCommonmistake.getSource());
                tempCommonmistake.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                tempCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                COMMON_MISTAKE_DAO.update(tempCommonmistake);

                newCommonmistake.setCommonMistake(copyOfTempCommonmistake.getCommonMistake());
                newCommonmistake.setSource(copyOfTempCommonmistake.getSource());
                newCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                BOManagerUtil.AFFIRM_SUGGESTION(newCommonmistake.getSuggestion());
                COMMON_MISTAKE_DAO.update(newCommonmistake);
            }
        }
    }

    public void suggestDeletecommonMistake(CommonMistakeBO deletedcommonMistakeBO) throws RawNotFoundException {
        Source sourceObj = BLUtil.daoFactory.getSourceDAO().getConfirmedInstance(new Source(deletedcommonMistakeBO.getSource()), null).get(0);
        Map restrictions = new HashMap();
        restrictions.put("eq_source.sourceId", sourceObj.getIdentity());

        Commonmistake commonMistake = COMMON_MISTAKE_DAO.getConfirmedInstance(new Commonmistake(sourceObj, deletedcommonMistakeBO.getDescription()), restrictions).get(0);
        commonMistake.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        commonMistake.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        commonMistake.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        COMMON_MISTAKE_DAO.update(commonMistake);
    }

    public static boolean affirmAddingAU(int commonmistakeId, CommonMistakeBO updateCommonmistakeBO) throws RawNotFoundException {
        Commonmistake oldCommonmistake = COMMON_MISTAKE_DAO.getById(commonmistakeId);
        Commonmistake newCommonmistake = new Commonmistake(new Source(updateCommonmistakeBO.getSource()), updateCommonmistakeBO.getDescription());
        if (!oldCommonmistake.equals(newCommonmistake)) {
            //if the new Commonmistake does not exist, then update old commonmistake values
            if (COMMON_MISTAKE_DAO.getByExample(newCommonmistake, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES).size() == 0) {
                oldCommonmistake.setCommonMistake(newCommonmistake.getCommonMistake());
                oldCommonmistake.setSource(newCommonmistake.getSource());
                if (oldCommonmistake.getSuggestion() != null) {
                    BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(oldCommonmistake.getSuggestion());
                }
                COMMON_MISTAKE_DAO.update(newCommonmistake);
                return true;
            }
        }
        return false;
    }

    public static void rejectAdding(int exampleId) throws RawNotFoundException {
        Commonmistake newCommonmistake = COMMON_MISTAKE_DAO.getById(exampleId);
        if (newCommonmistake.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newCommonmistake.setInfoStatus(WordStatus.NEED_DELETING);
            newCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newCommonmistake.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newCommonmistake.getSuggestion());
            }
            COMMON_MISTAKE_DAO.update(newCommonmistake);
        }
    }

    public static void rejectUpdating(int commonMistakeId) throws RawNotFoundException {
        Commonmistake newCommonmistake = COMMON_MISTAKE_DAO.getById(commonMistakeId);
        if (newCommonmistake.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            if (newCommonmistake.getSuggestion() != null) {
                Commonmistake tempCommonmistake = COMMON_MISTAKE_DAO.getById(newCommonmistake.getSuggestion().getUpdateId());
                tempCommonmistake.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                tempCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                COMMON_MISTAKE_DAO.update(tempCommonmistake);

                newCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                BOManagerUtil.REJECT_SUGGESTION(newCommonmistake.getSuggestion());
                COMMON_MISTAKE_DAO.update(newCommonmistake);
            }
        }
    }

    public static void affirmDeleting(int commonmistakeId) throws RawNotFoundException {
        Commonmistake newCommonmistake = COMMON_MISTAKE_DAO.getById(commonmistakeId);

        if (newCommonmistake.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newCommonmistake.setInfoStatus(WordStatus.NEED_DELETING);
            newCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newCommonmistake.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newCommonmistake.getSuggestion());
            }
            COMMON_MISTAKE_DAO.update(newCommonmistake);
        }
    }

    public static void rejectDeleting(int commonmistakeId) throws RawNotFoundException {
        Commonmistake newCommonmistake = COMMON_MISTAKE_DAO.getById(commonmistakeId);

        if (newCommonmistake.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newCommonmistake.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newCommonmistake.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newCommonmistake.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newCommonmistake.getSuggestion());
            }
            COMMON_MISTAKE_DAO.update(newCommonmistake);
        }
    }
}

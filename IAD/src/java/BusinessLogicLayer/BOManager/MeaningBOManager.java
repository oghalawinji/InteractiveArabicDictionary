package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BusinessObjects.MeaningBO;
import BusinessLogicLayer.BusinessObjects.UpdatedMeaningBO;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.MeaningJPADAO;
import PersistenceLayer.Meaning;
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
public class MeaningBOManager {

    private SourceBOManager newSourceBOManager;
    private static MeaningJPADAO MEANING_DAO = BLUtil.daoFactory.getMeaningDAO();

    public MeaningBOManager() {
        newSourceBOManager = new SourceBOManager();
    }

    public SourceBOManager getNewSourceBOManager() {
        return newSourceBOManager;
    }

    public void setNewSourceBOManager(SourceBOManager newSourceBOManager) {
        this.newSourceBOManager = newSourceBOManager;
    }

    public static List<MeaningBO> getMeaningsOfSemanticEntry(Integer semanticEntryId) {
        try {
            Semanticentry semEntry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);
            return getMeaningsOfSemanticEntry(semEntry);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(MeaningBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static List<MeaningBO> getMeaningsOfSemanticEntry(Semanticentry semEntry) {
        List<MeaningBO> meaningBOList = new ArrayList<MeaningBO>();
        Set<Meaning> meaningSet = semEntry.getMeanings();
        for (Iterator iter3 = meaningSet.iterator(); iter3.hasNext();) {
            Meaning meaning = (Meaning) iter3.next();
            MeaningBO meaningBO = new MeaningBO();
            meaningBO.setDescription(meaning.getMeaning());
            meaningBO.setSource(meaning.getSource().getSource());
            meaningBO.setStatus(meaning.getInfoStatus());
            meaningBO.setMeaningId(meaning.getIdentity());
            meaningBOList.add(meaningBO);
        }
        return meaningBOList;
    }

    public static UpdatedMeaningBO get(MeaningBO meaningBO) {
        UpdatedMeaningBO newMeaningBO = new UpdatedMeaningBO();

        try {
            Meaning meaning = MEANING_DAO.getById(meaningBO.getMeaningId());
            Meaning updatedMeaning = MEANING_DAO.getById(meaning.getSuggestion().getUpdateId());
            newMeaningBO.setOldDescription(updatedMeaning.getMeaning());
            newMeaningBO.setOldSource(updatedMeaning.getSource().getSource());
            newMeaningBO.setOldMeaningId(updatedMeaning.getIdentity());

            newMeaningBO.setDescription(meaning.getMeaning());
            newMeaningBO.setSource(meaning.getSource().getSource());
            newMeaningBO.setStatus(meaning.getInfoStatus());
            return newMeaningBO;
        } catch (RawNotFoundException ex) {
            Logger.getLogger(MeaningBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static MeaningBO get(Meaning meaning) {
        if (meaning.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            UpdatedMeaningBO newMeaningBO = new UpdatedMeaningBO();

            try {
                Meaning updatedMeaning = MEANING_DAO.getById(meaning.getSuggestion().getUpdateId());
                newMeaningBO.setOldDescription(updatedMeaning.getMeaning());
                newMeaningBO.setOldSource(updatedMeaning.getSource().getSource());
                newMeaningBO.setOldMeaningId(updatedMeaning.getIdentity());
            } catch (RawNotFoundException ex) {
                Logger.getLogger(MeaningBOManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            newMeaningBO.setMeaningId(meaning.getIdentity());
            newMeaningBO.setDescription(meaning.getMeaning());
            newMeaningBO.setSource(meaning.getSource().getSource());
            newMeaningBO.setStatus(meaning.getInfoStatus());
            return newMeaningBO;

        } else {
            MeaningBO newMeaningBO = new MeaningBO();
            newMeaningBO.setMeaningId(meaning.getIdentity());
            newMeaningBO.setDescription(meaning.getMeaning());
            newMeaningBO.setSource(meaning.getSource().getSource());
            newMeaningBO.setStatus(meaning.getInfoStatus());
            return newMeaningBO;
        }
    }

    public static int suggestAdding(String meaningDescription, String source, int semanticEntryId) throws RawNotFoundException {
        Meaning meaning = new Meaning();
        meaning.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        meaning.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        meaning.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        meaning.setMeaning(meaningDescription);

        Integer sourceId = SourceBOManager.suggestAdding(source);
        Source sourceObj = BLUtil.daoFactory.getSourceDAO().getById(sourceId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_source.sourceId", sourceId);
        meaning.setSource(sourceObj);

        Semanticentry semanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        meaning.setSemanticentry(semanticentry);

        return MEANING_DAO.insertWithCheck(meaning, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
    }

    public static int suggestAdding(MeaningBO meaningBO, int semanticEntryId) throws RawNotFoundException {
        Meaning meaning = new Meaning();

        meaning.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        meaning.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        meaning.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        meaning.setMeaning(meaningBO.getDescription());

        Integer sourceId = SourceBOManager.suggestAdding(meaningBO.getSource());
        Source sourceObj = BLUtil.daoFactory.getSourceDAO().getById(sourceId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_source.sourceId", sourceId);
        meaning.setSource(sourceObj);

        Semanticentry semanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        meaning.setSemanticentry(semanticentry);

        return MEANING_DAO.insertWithCheck(meaning, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
    }

    static int addMeaning(MeaningBO meaningBO, int semanticEntryId) throws RawNotFoundException {
        Meaning meaning = new Meaning();

        meaning.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        meaning.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        meaning.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        meaning.setMeaning(meaningBO.getDescription());

        Integer sourceId = SourceBOManager.suggestAdding(meaningBO.getSource());
        Source sourceObj = BLUtil.daoFactory.getSourceDAO().getById(sourceId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_source.sourceId", sourceId);
        meaning.setSource(sourceObj);

        Semanticentry semanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        meaning.setSemanticentry(semanticentry);

        return MEANING_DAO.insertWithCheck(meaning, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
    }

    public static int AddTempMeaning(MeaningBO meaningBO, int semanticEntryId) throws RawNotFoundException {
        Meaning meaning = new Meaning();

        meaning.setChechStatus(BOManagerUtil.TEMP_STATUS.getCheckStatus());
        meaning.setInfoStatus(BOManagerUtil.TEMP_STATUS.getInfoStatus());
        meaning.setMeaning(meaningBO.getDescription());

        Integer sourceId = SourceBOManager.suggestAdding(meaningBO.getSource());
        Source sourceObj = BLUtil.daoFactory.getSourceDAO().getById(sourceId);
        Map restrictions = BOManagerUtil.getAddRestrictions();
        restrictions.put("eq_source.sourceId", sourceId);
        meaning.setSource(sourceObj);

        Semanticentry semanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);
        restrictions.put("eq_semanticentry.semanticEntryId", semanticEntryId);
        meaning.setSemanticentry(semanticentry);

        return MEANING_DAO.insertWithCheck(meaning, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, restrictions);
    }

    public static void suggestUpdating(MeaningBO newMeaningBO, MeaningBO oldMeaningBO) throws RawNotFoundException {

        /*Source sourceObj = BLUtil.daoFactory.getSourceDAO().getConfirmedInstance( new Source( oldMeaningBO.getSource() ) , null ).get( 0 );
         Map restrictions = new HashMap();
         restrictions.put( "eq_source.sourceId" , sourceObj.getIdentity() );
         Semanticentry semanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById( semanticEntryId );
         restrictions.put( "eq_semanticentry.semanticEntryId" , semanticEntryId );

         Meaning oldMeaning = MEANING_DAO.getConfirmedInstance( new Meaning( semanticentry , sourceObj , oldMeaningBO.getDescription() ) , restrictions ).get( 0 );*/
        Meaning oldMeaning = MEANING_DAO.getById(oldMeaningBO.getMeaningId());
        int newMeaningId = AddTempMeaning(newMeaningBO, oldMeaning.getSemanticentry().getIdentity());

        oldMeaning.setSuggestion(BOManagerUtil.GET_UPDATE_SUGGESTION(newMeaningId));
        oldMeaning.setChechStatus(BOManagerUtil.UPDATING_STATUS.getCheckStatus());
        oldMeaning.setInfoStatus(BOManagerUtil.UPDATING_STATUS.getInfoStatus());

        MEANING_DAO.update(oldMeaning);
    }

    public static void suggestDeleting(MeaningBO deletedMeaningBO) throws RawNotFoundException {
        /*Source sourceObj = BLUtil.daoFactory.getSourceDAO().getConfirmedInstance( new Source( deletedMeaningBO.getSource() ) , null ).get( 0 );
         Map restrictions = new HashMap();
         restrictions.put( "eq_source.sourceId" , sourceObj.getIdentity() );

         Semanticentry semanticentry = BLUtil.daoFactory.getSemanticentryDAO().getById( semanticEntryId );
         restrictions.put( "eq_semanticentry.semanticEntryId" , semanticEntryId );

         Meaning meaning = MEANING_DAO.getConfirmedInstance( new Meaning( semanticentry , sourceObj , deletedMeaningBO.getDescription() ) , restrictions ).get( 0 );*/
        Meaning meaning = MEANING_DAO.getById(deletedMeaningBO.getMeaningId());

        meaning.setInfoStatus(BOManagerUtil.DELETING_STATUS.getInfoStatus());
        meaning.setChechStatus(BOManagerUtil.DELETING_STATUS.getCheckStatus());
        meaning.setSuggestion(BOManagerUtil.GET_DELETE_SUGGESTION());

        MEANING_DAO.update(meaning);
    }

    public static void affirmAdding(int meaningId) throws RawNotFoundException {
        Meaning newMeaning = MEANING_DAO.getById(meaningId);
        if (newMeaning.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newMeaning.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newMeaning.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newMeaning.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newMeaning.getSuggestion());
            }
            MEANING_DAO.update(newMeaning);
            SourceBOManager.affirmAdding(newMeaning.getSource().getIdentity());
        }
    }

    public static void rejectAdding(int meaningId) throws RawNotFoundException {
        Meaning newMeaning = MEANING_DAO.getById(meaningId);
        if (newMeaning.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            newMeaning.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
            newMeaning.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newMeaning.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newMeaning.getSuggestion());
            }
            MEANING_DAO.update(newMeaning);
        }
    }

    public static boolean affirmAddingAU(int meaningId, MeaningBO newMeaningBO) throws RawNotFoundException {
        Meaning newMeaning = MEANING_DAO.getById(meaningId);
        if (newMeaning.getInfoStatus().equals(WordStatus.INSERT_INFO_STATUS)) {
            int newSourceId = SourceBOManager.suggestAdding(newMeaningBO.getSource());
            Map restrictions = new HashMap();
            restrictions.put("eq_source.sourceId", newSourceId);
            List<Meaning> meaningList = MEANING_DAO.getConfirmedInstance(new Meaning(null, null, newMeaningBO.getDescription()), restrictions);
            if (meaningList == null || meaningList.size() == 0) {
                Source newSource = BLUtil.daoFactory.getSourceDAO().getById(newSourceId);
                newMeaning.setMeaning(newMeaningBO.getDescription());
                newMeaning.setSource(newSource);
                newMeaning.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newMeaning.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                if (newMeaning.getSuggestion() != null) {
                    BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(newMeaning.getSuggestion());
                }
                MEANING_DAO.update(newMeaning);
                return true;
            }
        }
        return false;
    }

    public static void affirmUpdating(int meaningId) throws RawNotFoundException {
        Meaning newMeaning = MEANING_DAO.getById(meaningId);
        if (newMeaning.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            if (newMeaning.getSuggestion() != null) {
                Meaning tempMeaning = MEANING_DAO.getById(newMeaning.getSuggestion().getUpdateId());
                Meaning copyOfTempMeaning = new Meaning(null, tempMeaning.getSource(), tempMeaning.getMeaning());

                tempMeaning.setSource(newMeaning.getSource());
                tempMeaning.setMeaning(newMeaning.getMeaning());
                tempMeaning.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                tempMeaning.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                MEANING_DAO.update(tempMeaning);

                newMeaning.setMeaning(copyOfTempMeaning.getMeaning());
                newMeaning.setSource(copyOfTempMeaning.getSource());
                newMeaning.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                newMeaning.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                BOManagerUtil.AFFIRM_SUGGESTION(newMeaning.getSuggestion());
                MEANING_DAO.update(newMeaning);
            }
        }
    }

    public static boolean affirmUpdatingAU(int meaningId, MeaningBO newMeaningBO) throws RawNotFoundException {
        Meaning newMeaning = MEANING_DAO.getById(meaningId);
        if (newMeaning.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            int newSourceId = SourceBOManager.suggestAdding(newMeaningBO.getSource());
            Map restrictions = new HashMap();
            restrictions.put("eq_source.sourceId", newSourceId);
            List<Meaning> meaningList = MEANING_DAO.getConfirmedInstance(new Meaning(null, null, newMeaningBO.getDescription()), restrictions);
            if (meaningList == null || meaningList.size() == 0) {
                if (newMeaning.getSuggestion() != null) {
                    Meaning tempMeaning = MEANING_DAO.getById(newMeaning.getSuggestion().getUpdateId());
                    tempMeaning.setMeaning(newMeaning.getMeaning());
                    tempMeaning.setSource(newMeaning.getSource());
                    tempMeaning.setInfoStatus(WordStatus.OLD_VALUES_INFO_STATUS);
                    tempMeaning.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    MEANING_DAO.update(tempMeaning);

                    Source newSource = BLUtil.daoFactory.getSourceDAO().getById(newSourceId);
                    newMeaning.setMeaning(newMeaningBO.getDescription());
                    newMeaning.setSource(newSource);
                    newMeaning.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
                    newMeaning.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                    BOManagerUtil.AFFIRM_SUGGESTION_AFTER_ALTERING(newMeaning.getSuggestion());
                    MEANING_DAO.update(newMeaning);
                    return true;
                }
            }
        }
        return false;
    }

    public static void rejectUpdating(int meaningId) throws RawNotFoundException {
        Meaning newMeaning = MEANING_DAO.getById(meaningId);
        if (newMeaning.getInfoStatus().equals(WordStatus.UPDATE_INFO_STATUS)) {
            newMeaning.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newMeaning.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            MEANING_DAO.update(newMeaning);
            if (newMeaning.getSuggestion() != null) {
                Meaning tempMeaning = MEANING_DAO.getById(newMeaning.getSuggestion().getUpdateId());
                tempMeaning.setInfoStatus(WordStatus.REJECTED_INFO_STATUS);
                tempMeaning.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
                MEANING_DAO.update(tempMeaning);
                BOManagerUtil.REJECT_SUGGESTION(newMeaning.getSuggestion());
            }
        }
    }

    public static void affirmDeleting(int meaningId) throws RawNotFoundException {
        Meaning newMeaning = MEANING_DAO.getById(meaningId);
        if (newMeaning.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newMeaning.setInfoStatus(WordStatus.NEED_DELETING);
            newMeaning.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newMeaning.getSuggestion() != null) {
                BOManagerUtil.AFFIRM_SUGGESTION(newMeaning.getSuggestion());
            }
            MEANING_DAO.update(newMeaning);
        }
    }

    public static void rejectDeleting(int meaningId) throws RawNotFoundException {
        Meaning newMeaning = MEANING_DAO.getById(meaningId);
        if (newMeaning.getInfoStatus().equals(WordStatus.DELETE_INFO_STATUS)) {
            newMeaning.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newMeaning.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            if (newMeaning.getSuggestion() != null) {
                BOManagerUtil.REJECT_SUGGESTION(newMeaning.getSuggestion());
            }
            MEANING_DAO.update(newMeaning);
        }
    }
}

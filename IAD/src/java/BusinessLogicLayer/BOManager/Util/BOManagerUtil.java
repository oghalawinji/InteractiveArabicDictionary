package BusinessLogicLayer.BOManager.Util;

import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.SuggestionStatus;
import BusinessLogicLayer.Util.WordStatus;
import PersistenceLayer.Suggestion;
import PersistenceLayer.User;
import Util.RawNotFoundException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fadel
 */
public class BOManagerUtil {

    public static User currentUser;

    public final static WordStatus ADDING_STATUS = new WordStatus(WordStatus.INSERT_INFO_STATUS, WordStatus.NEED_CHECK_STATUS);
    public final static WordStatus CONFIRM_STATUS = new WordStatus(WordStatus.CONFIRMED_INFO_STATUS, WordStatus.NOT_NEED_CHECK_STATUS);
    public final static WordStatus UPDATING_STATUS = new WordStatus(WordStatus.UPDATE_INFO_STATUS, WordStatus.NEED_CHECK_STATUS);
    public final static WordStatus DELETING_STATUS = new WordStatus(WordStatus.DELETE_INFO_STATUS, WordStatus.NEED_CHECK_STATUS);
    public final static WordStatus TEMP_STATUS = new WordStatus(WordStatus.TEMP_INFO_STATUS, WordStatus.NOT_NEED_CHECK_STATUS);
    public final static WordStatus NEEDS_CHECK_STATUS = new WordStatus(WordStatus.CONFIRMED_INFO_STATUS, WordStatus.NEED_CHECK_STATUS);
    public final static WordStatus OLD_VALUES_STATUS = new WordStatus(WordStatus.OLD_VALUES_INFO_STATUS, WordStatus.NOT_NEED_CHECK_STATUS);
    public static String[] GENERAL_EXCLUDE_PROPERTIES = new String[]{
        "suggestion", "infoStatus", "chechStatus"
    };

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Suggestion GET_ADD_SUGGESTION() throws RawNotFoundException {
//        Suggestion addSuggestion = new Suggestion();
//        addSuggestion = new Suggestion();
//        addSuggestion.setDate(new Date(System.currentTimeMillis()));
//        addSuggestion.setUser(BOManagerUtil.getCurrentUser());
//        addSuggestion.setInfoStatus(SuggestionStatus.INSERT_INFO_STATUS);
//        addSuggestion.setAffirmStatus(SuggestionStatus.NOT_CHECKED_YET);
//        addSuggestion.setNote("");
//        addSuggestion.setDetails("");
//        addSuggestion.setUpdateId(0);
//        int suggestionId = BLUtil.daoFactory.getSuggestionDAO().insert(addSuggestion);
//        addSuggestion.setSuggestionId(suggestionId);
//        return addSuggestion;
        throw new UnsupportedOperationException("Not Implemented in JPA version.");
    }

    public static Suggestion GET_CONFIRMED_ADD_SUGGESTION() {
//        Suggestion addSuggestion = new Suggestion();
//        addSuggestion = new Suggestion();
//        addSuggestion.setDate(new Date(System.currentTimeMillis()));
//        addSuggestion.setUser(BOManagerUtil.getCurrentUser());
//        addSuggestion.setInfoStatus(SuggestionStatus.INSERT_INFO_STATUS);
//        addSuggestion.setAffirmStatus(SuggestionStatus.NOT_NEED_CHECK);
//        addSuggestion.setNote("");
//        addSuggestion.setDetails("");
//        addSuggestion.setUpdateId(0);
//        int suggestionId = BLUtil.daoFactory.getSuggestionDAO().insert(addSuggestion);
//        addSuggestion.setSuggestionId(suggestionId);
//        return addSuggestion;
        throw new UnsupportedOperationException("Not Implemented in JPA version.");
    }

    public static Suggestion GET_DELETE_SUGGESTION() throws RawNotFoundException {
//        Suggestion deleteSuggestion = new Suggestion();
//        deleteSuggestion = new Suggestion();
//        deleteSuggestion.setDate(new Date(System.currentTimeMillis()));
//        deleteSuggestion.setUser(BOManagerUtil.getCurrentUser());
//        deleteSuggestion.setInfoStatus(SuggestionStatus.DELETE_INFO_STATUS);
//        deleteSuggestion.setAffirmStatus(SuggestionStatus.NOT_CHECKED_YET);
//        deleteSuggestion.setNote("");
//        deleteSuggestion.setDetails("");
//        deleteSuggestion.setUpdateId(0);
//        int suggestionId = BLUtil.daoFactory.getSuggestionDAO().insert(deleteSuggestion);
//        deleteSuggestion.setSuggestionId(suggestionId);
//        return deleteSuggestion;
        throw new UnsupportedOperationException("Not Implemented in JPA version.");
    }

    public static Suggestion GET_UPDATE_SUGGESTION(int updatedEntryId) throws RawNotFoundException {
//        Suggestion updateSuggestion = new Suggestion();
//        updateSuggestion = new Suggestion();
//        updateSuggestion.setDate(new Date(System.currentTimeMillis()));
//        updateSuggestion.setUser(BOManagerUtil.getCurrentUser());
//        updateSuggestion.setInfoStatus(SuggestionStatus.UPDATE_INFO_STATUS);
//        updateSuggestion.setAffirmStatus(SuggestionStatus.NOT_CHECKED_YET);
//        updateSuggestion.setNote("");
//        updateSuggestion.setDetails("");
//        updateSuggestion.setUpdateId(updatedEntryId);
//        int suggestionId = BLUtil.daoFactory.getSuggestionDAO().insert(updateSuggestion);
//        updateSuggestion.setSuggestionId(suggestionId);
//        return updateSuggestion;
        throw new UnsupportedOperationException("Not Implemented in JPA version.");
    }

    public static Suggestion AFFIRM_SUGGESTION(Suggestion oldSuggestion) {
//        oldSuggestion.setAffirmStatus(SuggestionStatus.AFFIRMED_INFO_STATUS);
//        oldSuggestion.setAffirmUser(BOManagerUtil.getCurrentUser());
//        oldSuggestion.setAffirmDate(new Date(System.currentTimeMillis()));
//        BLUtil.daoFactory.getSuggestionDAO().update(oldSuggestion);
//        return oldSuggestion;
        throw new UnsupportedOperationException("Not Implemented in JPA version.");
    }

    public static Suggestion AFFIRM_SUGGESTION_AFTER_ALTERING(Suggestion oldSuggestion) {
//        oldSuggestion.setAffirmStatus(SuggestionStatus.AFFIRMED_AFTER_ALTERING_INFO_STATUS);
//        oldSuggestion.setAffirmUser(BOManagerUtil.getCurrentUser());
//        oldSuggestion.setAffirmDate(new Date(System.currentTimeMillis()));
//        BLUtil.daoFactory.getSuggestionDAO().update(oldSuggestion);
//        return oldSuggestion;
        throw new UnsupportedOperationException("Not Implemented in JPA version.");
    }

    public static Suggestion REJECT_SUGGESTION(Suggestion oldSuggestion) {
//        oldSuggestion.setAffirmStatus(SuggestionStatus.REJECTED_INFO_STATUS);
//        oldSuggestion.setAffirmUser(BOManagerUtil.getCurrentUser());
//        oldSuggestion.setAffirmDate(new Date(System.currentTimeMillis()));
//        BLUtil.daoFactory.getSuggestionDAO().update(oldSuggestion);
//        return oldSuggestion;
        throw new UnsupportedOperationException("Not Implemented in JPA version.");
    }

    public static Map getAddRestrictions() {
        Map addRestrictions = new HashMap();
        String[] status
                = {
                    "S", "I", "s", "i"
                };
        addRestrictions.put("in_infoStatus", status);
        return addRestrictions;
    }

    public static Map getConfirmedRestrictions() {
        Map addRestrictions = new HashMap();
        String[] status
                = {
                    "S", "s"
                };
        addRestrictions.put("in_infoStatus", status);
        addRestrictions.put("eq_chechStatus", 0);
        return addRestrictions;
    }

    public static void setCurrentUser(User currUser) {
        currentUser = currUser;
    }

}

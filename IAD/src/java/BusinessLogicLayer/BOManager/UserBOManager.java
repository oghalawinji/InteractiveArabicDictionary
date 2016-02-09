package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.Util.BLUtil;
import PersistenceLayer.Role;
import PersistenceLayer.Suggestion;
import PersistenceLayer.User;
import Util.RawNotFoundException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gowzancha
 */
public class UserBOManager {

    public void register(User newUser) {
        try {
            if (newUser != null) {
                //Role newRole = new Role("انتظار", false, false, false);
                Role role = BLUtil.daoFactory.getRoleDAO().getById(1);
                //newRole.setRoleId(newRoleId);
                newUser.setRole(role);
                newUser.setPassword("arabic"); // default password
                newUser.setParticipationDate(new Date(System.currentTimeMillis()));
                newUser.setNumOfDeleteOperation(0);
                newUser.setNumOfEnteredEntries(0);
                newUser.setNumOfErrorsInfo(0);
                newUser.setNumOfInsertOperation(0);
                newUser.setNumOfUpdateOperation(0);
                newUser.setNumOfcheckedInfo(0);
                int newUserId = BLUtil.daoFactory.getUserDAO().insertWithCheck(newUser);
            }
        } catch (RawNotFoundException ex) {
            Logger.getLogger(UserBOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<User> getUsers() {
        List<User> list = BLUtil.daoFactory.getUserDAO().getAll();
        return list;
    }

    public User getUser(int userId) {
        try {
            User user = BLUtil.daoFactory.getUserDAO().getById(userId);
            return user;
        } catch (RawNotFoundException ex) {
            Logger.getLogger(UserBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Role> getRoles() {
        List<Role> list = BLUtil.daoFactory.getRoleDAO().getAll();
        return list;
    }

    public Role getRole(int roleId) {
        try {
            Role role = BLUtil.daoFactory.getRoleDAO().getById(roleId);
            return role;
        } catch (RawNotFoundException ex) {
            Logger.getLogger(UserBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void changeRole(int userId, int roleId) {
        try {
            User user = BLUtil.daoFactory.getUserDAO().getById(userId);
            Role role = BLUtil.daoFactory.getRoleDAO().getById(roleId);
            //BLUtil.daoFactory.getUserDAO().delete(userId);
            user.setRole(role);
            BLUtil.daoFactory.getUserDAO().update(user);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(UserBOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changePassword(int userId, String newPassWord) {
        try {
            User user = BLUtil.daoFactory.getUserDAO().getById(userId);
            //BLUtil.daoFactory.getUserDAO().delete(userId);
            user.setPassword(newPassWord);
            BLUtil.daoFactory.getUserDAO().update(user);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(UserBOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteUser(int userId) {
        try {
            User user = BLUtil.daoFactory.getUserDAO().getById(userId);
            Role role = BLUtil.daoFactory.getRoleDAO().getById(4);// موقوف
            //BLUtil.daoFactory.getUserDAO().delete(userId);
            user.setRole(role);
            BLUtil.daoFactory.getUserDAO().update(user);
            //BLUtil.daoFactory.getUserDAO().delete(userId);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(UserBOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkPassword(int userId, String pass) {
        try {
            User user = BLUtil.daoFactory.getUserDAO().getById(userId);
            if (pass.equals(user.getPassword())) {
                return true;
            } else {
                return false;
            }
        } catch (RawNotFoundException ex) {
            Logger.getLogger(UserBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public User login(String userName, String pass) {
        User tempUser = new User();
        tempUser.setUserName(userName);
        tempUser.setPassword(pass);
        String[] excluded = {"userId", "email", "phoneNumber", "webSite", "Country", "City", "educationLevel", "ParticipationDate", "numOfInsertOperation", "numOfUpdateOperation", "numOfDeleteOperation", "numOfEnteredEntries", "numOfErrorsInfo", "numOfcheckedInfo", "roleId", "name"};
        List<User> user = BLUtil.daoFactory.getUserDAO().getByExample(tempUser, excluded);
        if (user.size() > 0) {
            return user.get(0);
        } else {
            return null;
        }
    }

    public int[] getUserWorks(int userId) {
        Map restrection1 = new HashMap();
        restrection1.put("eq_user.userId", userId);
        restrection1.put("eq_infoStatus", "I");

        Map restrection2 = new HashMap();
        restrection2.put("eq_user.userId", userId);
        restrection2.put("eq_infoStatus", "U");
        Map restrection3 = new HashMap();
        restrection3.put("eq_user.userId", userId);
        restrection3.put("eq_infoStatus", "D");
        Map restrection4 = new HashMap();
        restrection4.put("eq_user.userId", userId);
        restrection4.put("eq_affirmStatus", "C");
        Map restrection5 = new HashMap();
        restrection5.put("eq_user.userId", userId);
        restrection5.put("eq_affirmStatus", "R");

        String[] excludeProperties1 = new String[]{"note", "details", "date", "updateId", "affirmstatus"};
        String[] excludeProperties2 = new String[]{"note", "details", "date", "updateId", "infostatus"};

        List<Suggestion> insertSuggestions = BLUtil.daoFactory.getSuggestionDAO().getByExample(new Suggestion(), excludeProperties1, restrection1);
        List<Suggestion> updateSuggestions = BLUtil.daoFactory.getSuggestionDAO().getByExample(new Suggestion(), excludeProperties1, restrection2);
        List<Suggestion> deleteSuggestions = BLUtil.daoFactory.getSuggestionDAO().getByExample(new Suggestion(), excludeProperties1, restrection3);

        List<Suggestion> confirmedSuggestions = BLUtil.daoFactory.getSuggestionDAO().getByExample(new Suggestion(), excludeProperties2, restrection4);
        List<Suggestion> rejectedSuggestions = BLUtil.daoFactory.getSuggestionDAO().getByExample(new Suggestion(), excludeProperties2, restrection5);

        int[] result = new int[5];
        result[0] = insertSuggestions.size();
        result[1] = updateSuggestions.size();
        result[2] = deleteSuggestions.size();
        result[3] = confirmedSuggestions.size();
        result[4] = rejectedSuggestions.size();

        return result;
    }

    public void editPersonalInfo(User user) {
        try {
            User newUser = BLUtil.daoFactory.getUserDAO().getById(user.getIdentity());
            newUser.setName(user.getName());
            newUser.setCountry(user.getCountry());
            newUser.setCity(user.getCity());
            newUser.setEducationLevel(user.getEducationLevel());
            newUser.setPhoneNumber(user.getPhoneNumber());
            newUser.setEmail(user.getEmail());
            newUser.setWebSite(user.getWebSite());
            BLUtil.daoFactory.getUserDAO().update(newUser);
        } catch (Exception ex) {
        }
    }
}

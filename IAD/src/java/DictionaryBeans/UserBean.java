package DictionaryBeans;

import BusinessLogicLayer.BOManager.UserBOManager;
import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import PersistenceLayer.Role;
import PersistenceLayer.User;
import java.util.List;

/**
 *
 * @author Fadel
 */
public class UserBean {

    private User currentUser;
    private UserBOManager userManager;

    public UserBean() {
        currentUser = new User();
        userManager = new UserBOManager();
    }

    public void register(User newUser) {
        userManager.register(newUser);
    }

    public List<User> getUsers() {
        return userManager.getUsers();
    }

    public User getUser(int userId) {
        return userManager.getUser(userId);
    }

    public List<Role> getRoles() {
        return userManager.getRoles();
    }

    public Role getRole(int roleId) {
        return userManager.getRole(roleId);
    }

    public void changePassword(int userId, String newPassWord) {
        userManager.changePassword(userId, newPassWord);
    }

    public void changeRole(int userId, int roleId) {
        userManager.changeRole(userId, roleId);
    }

    public void deleteUser(int userId) {
        userManager.deleteUser(userId);
    }

    public boolean checkPassword(int userId, String pass) {
        return userManager.checkPassword(userId, pass);
    }

    public User login(String userName, String pass) {
        currentUser =  userManager.login(userName, pass);
        BOManagerUtil.currentUser = currentUser;
        return currentUser;
    }

   public int[] getUserWorks(int userId) {
        return userManager.getUserWorks(userId);
    }

    public void editPersonalInfo(User user){
        userManager.editPersonalInfo(user);
    }
}

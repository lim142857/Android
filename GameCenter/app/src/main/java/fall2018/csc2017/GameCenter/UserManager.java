package fall2018.csc2017.GameCenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Manage a user, including sign in, sign up. keeping the record of currentUser and userList
 * which is the connection between each class.
 */

public class UserManager {

    /**
     * An instance of UserManager.
     */
    private static UserManager userManager = null;

    /**
     * The map of user name to their password that already exist.
     */
    private Map<String, User> userList = new HashMap<>();

    /**
     * The current user of the game
     */
    private User currentUser;

    /**
     * the type of the game that the user choose
     */
    private int currentGameType;

    /**
     * To create an instance of UserManager class.
     *
     * @return return the new created instance of UserManager class.
     */
    public static UserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    /**
     * Return the current user who is signing in
     *
     * @return the user name
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Return the current user list
     *
     * @return the current user list
     */
    public Map<String, User> getUserList() {
        return userList;
    }

    /**
     * get the currentGameType, the type of the game that the user choose
     *
     * @return the gameType of the current game
     */
    public int getCurrentGameType() {
        return currentGameType;
    }

    /**
     * Set the currentGameType, the type of the game that the user choose
     *
     * @param gameType the gameType that the user choose
     */
    public void setCurrentGameType(int gameType) {
        currentGameType = gameType;
    }

    /**
     * Sign in method to find a user from the user file or return false
     * if the user does not exist or the password is wrong.
     *
     * @param name        the name that sign in
     * @param password    the password that sign in
     * @param userListNow the userList that downloaded from the file
     * @return whether the name exists or the password is right.
     */
    Boolean signIn(String name, String password, Map<String, User> userListNow) {
        userList = userListNow;

        // user not exists
        if (!userList.containsKey(name)) {
            return false;
        }
        // name and password not match
        if (!userList.get(name).getPassword().equals(password)) {
            return false;
        }
        // set "currentUser" to the current user
        currentUser = userList.get(name);
        return true;
    }

    /**
     * Sign up method to construct a new user or return false if the user name already exist.
     *
     * @param name        the name that sign up
     * @param password    that password that sign up
     * @param userListNow the userList that downloaded form the file
     * @return the opposite of whether the user already exist,
     */
    Boolean signUp(String name, String password, Map<String, User> userListNow) {
        userList = userListNow;

        // user name already existed
        if (userList.containsKey(name)) {
            return false;
        }
        currentUser = new User(name, password);
        userList.put(name, currentUser);
        return true;
    }
}
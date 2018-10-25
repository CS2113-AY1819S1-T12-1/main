package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.storage.JsonUserStorage;

/**
 * Represents user account authentication
 */
public class UserSession {

    private JsonUserStorage userStorage;
    private User user;
    private boolean loginStatus;
    private boolean adminStatus;

    public UserSession() {
        final Path userFilePath = Paths.get("users.json");
        final Username username = new Username("stub");
        final Password password = new Password("stub");
        user = new User(username, password);
        loginStatus = false;
        adminStatus = false;

        try {
            userStorage = new JsonUserStorage(userFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns true if user exists in the JSON file.
     */
    public boolean userExists(User user) {
        String loggedUsername = user.getUsername().toString();
        String loggedPassword = user.getPassword().toString();
        boolean isPresent = false;

        Map<String, String> userAccounts = userStorage.getUserAccounts();

        if (userAccounts.containsKey(loggedUsername)) {
            String password = userAccounts.get(loggedUsername);
            isPresent = loggedPassword.equals(password);
            adminStatus = loggedUsername.equals("admin");
        }

        return isPresent;
    }
    /**
     * Returns true if user is logged in.
     */
    public boolean authenticate() {
        return loginStatus;
    }

    /**
     * Returns true if admin is logged in.
     */
    public boolean getAdminStatus() {
        return adminStatus;
    }

    /**
     * Returns logged in user.
     */
    public User getUser(User user) {
        return user;
    }

    /**
     * Creates a new user profile in the JSON file.
     */
    public void createUser(User user) {
        String loggedUsername = user.getUsername().toString();
        String loggedPassword = user.getPassword().toString();

        try {
            userStorage.createUser(loggedUsername, loggedPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the current user.
     */
    public void logUser(User user) {
        this.user = user;
        loginStatus = true;
    }

    /**
     * Clears the current user.
     */
    public void clearUser() {
        loginStatus = false;
    }
}

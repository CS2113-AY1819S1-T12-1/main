package seedu.address.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import seedu.address.model.user.User;
import seedu.address.storage.JsonUserStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents user account authentication
 */
public class UserAccount {

    private JsonUserStorage userStorage;

    public UserAccount() {
        final Path userFolderPath = Paths.get("data");
        final Path userFilePath = Paths.get("data", "users.json");

        try {
            userStorage = new JsonUserStorage(userFolderPath, userFilePath);
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

        try {
            JsonObject userAccounts = userStorage.getUserAccounts();

            if (userAccounts.has(loggedUsername)) {
                JsonElement password = userAccounts.get(loggedUsername);
                isPresent = loggedPassword.equals(password.getAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isPresent;
    }
}
package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thatcher on 9/19/2016.
 * @author Team Area 51
 * @version  1.0
 */
public class SecurityManager {
    private final List<User> validUsers;
    private static final String pathToUsers = "users.json";

    private final Gson gson;

    /**
     * Constructor for Security Manager
     */
    public SecurityManager() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        this.gson = builder.create();
        validUsers = fetchValidUsers();

    }

    /**
     * Fetches a list of the valid users.
     * @return the users list
     */
    private List<User> fetchValidUsers() {
        List<User> users;
        BufferedReader fileReader;
        try {
            fileReader = new BufferedReader(new FileReader(pathToUsers));
            users = gson.fromJson(fileReader, new TypeToken<List<User>>(){}.getType());
            fileReader.close();

        } catch (IOException | JsonSyntaxException e) {
            users = new ArrayList<>();
        }

        return users;
    }

    /**
     * Saves the list of users in a json file.
     */
    public void saveUsers() {
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(pathToUsers));
            gson.toJson(this.validUsers, new TypeToken<List<User>>(){}.getType(), fileWriter);
            fileWriter.close();

        } catch (IOException e) {
            //Wasn't able to save the purity reports to the file.
            //Do nothing?
            System.out.println("Could not save user list. IO Error!");
        }
    }

    /**
     * Returns null if the credentials are invalid. Otherwise returns
     * the corresponding user for that username.
     * @param  username The user's username
     * @param  password The user's password
     * @return          the user instance if successfully checked, else null.
     */
    public User checkCredentials(String username, String password) {
        if (username == null) {
            return null;
        }
        username = username.toLowerCase();
        for (User user : validUsers) {
            if (user.getUsername().equals(username)) {
                if (user.comparePassword(password)) {
                    return user;
                }
            }
        }
        return null;
    }

    /**
     * Adds the specified user to the list of valid users.
     * @param  newUser the user instance that will be added
     * @return         true if the user was successfully added, else false.
     */
    public boolean addUser(User newUser) {
        //if the user's username already exists in the list, do not add.
        for (User user : validUsers) {
            if (user.getUsername().equals(newUser.getUsername())) {
                //User already present in the list.
                return false;
            }
        }
        validUsers.add(newUser);
        return true;
    }
}

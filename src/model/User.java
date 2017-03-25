package model;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by Thatcher on 9/19/2016.
 * @author Team Area 51
 * @version  1.0
 */
public class User {

    private final String username;
    private String password;
    private String name;
    private AuthorizationLevel authorizationLevel;
    private String email;
    private String address;

    //private ObjectProperty<Date> creationDate = new SimpleObjectProperty<>();
    //private Date creationDate;

    private AccountStatus accountStatus;

    /**
     * Enum to reflect possible user authentication levels.
     */
    public enum AuthorizationLevel {
        USER, WORKER, MANAGER, ADMINISTRATOR
    }

    /**
     * Enum to represent the possible standings of a User account.
     */
    public enum AccountStatus {
        NORMAL, BANNED
    }

    /**
     * Constructor for users. Default authorization level is USER
     *
     * @param username the user's username
     * @param password the user's password, gets hashed and salted
     * @param name     the user's real name
     */
    public User(String username, String password, String name) {
        this.username = username.toLowerCase();
        this.password = generateHash(password);
        this.name = name;
        //this.creationDate.setValue(new Date(System.currentTimeMillis()));
        //creationDate = new Date(System.currentTimeMillis());


        //Default authorization level.
        this.authorizationLevel = AuthorizationLevel.USER;

        //Default values for other fields.
        email = "";
        address = "";
    }

    /**
     * Salts and hashes the string input
     *
     * @param input The input
     * @return the hashed version of that string
     */
    private static String generateHash(String input) {
        try {
            //The Salt
            input = "fx!253" + input + "xklie[2)";

            //The Hash
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(input.getBytes("UTF-16"));
            BigInteger big = new BigInteger(1, digest.digest());
            return big.toString(16);

        } catch (Exception e) {
            //Will never happen because "UTF-16" and "SHA-256" are valid encodings.
            return "" + input.hashCode();
        }
    }

    /**
     * Checks if the hashed password specified matches the saved password hash for this user
     *
     * @param password the password we're comparing this user's password against
     * @return True if the passwords are the same.
     */
    public boolean comparePassword(String password) {
        return this.password.equals(generateHash(password));
    }

    /**
     * Sets the authorization level for this user
     *
     * @param authorizationLevel the user's authorization level.
     */
    public void setAuthorizationLevel(AuthorizationLevel authorizationLevel) {
        this.authorizationLevel = authorizationLevel;
    }

    private String getPassword() {
        return password;
    }

    /**
     * Retrieves the authorization level for this user
     * @return the authorization level
     */
    public AuthorizationLevel getAuthorizationLevel() {
        return this.authorizationLevel;
    }

    /**
     * Retrieves the account status of this user.
     * @return the account status.
     */
    public AccountStatus getAccountStatus() {
        return this.accountStatus;
    }

    /**
     * Gets the user's inputted email address
     * @return the email String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email string
     * @param email the user's new email
     */
    public void setEmail(String email) {
        if (email != null) {
            this.email = email;
        }
    }

    /**
     * Gets the user's address string
     * @return the address as a String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the user's address
     * @param address The new address for the user
     */
    public void setAddress(String address) {
        if (address != null) {
            this.address = address;
        }
    }

    /**
     * Gets the user's username as a String
     * @return String the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the user's real name as a String
     * @return The user's real name
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User other = (User)o;
        return (this.getUsername().equals(other.getUsername()))
                && (this.getPassword().equals(other.getPassword()));
    }

    /**
     * Sets the user's real name
     * @param name the user's new name
     */
    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }
}

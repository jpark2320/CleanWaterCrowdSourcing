package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;

/**
 * Created by Thatcher on 9/27/2016.
 * @author Team Area 51
 * @version  1.0
 */
public class RegistrationDialogController extends BaseController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField name;

    @FXML
    private Label registrationText;

    @FXML
    private ComboBox<User.AuthorizationLevel> authLevel;

    /**
     * Populates the Authorization Level pull down menu when the fxml loader is called.
     */
    public void initialize() {
        ObservableList<User.AuthorizationLevel> items = javafx.collections.FXCollections.observableArrayList(User.AuthorizationLevel.values());

        authLevel.setItems(items);

    }

    /**
     * Executes when the create user button is clicked.
     */
    @FXML
    public void onCreateUserClick() {
        //Preliminary input checks.
        if (name.getText() == null || "".equals(name.getText())) {
            registrationText.setText("Enter a valid name.");
        } else if (username.getText() == null || "".equals(username.getText())) {
            registrationText.setText("Enter a valid username.");
        } else if (password.getText() == null || "".equals(password.getText())) {
            registrationText.setText("Enter a valid password.");
        } else if (authLevel.getValue() == null) {
            registrationText.setText("Select an authorization level.");

        } else {
            //Fields passed basic checks. Try to add user.
            boolean userAdded = getMainApp().addUser(username.getText(), password.getText(), name.getText(), authLevel.getValue());

            if (userAdded) {
                //Success!

                //Should return true.
                getMainApp().authenticateUser(username.getText(), password.getText());

                getStage().close();
                getMainApp().showApplicationScreen();

            } else {
                //Username was already in system probably.
                registrationText.setText("Account with username already exists!");
            }
        }
    }

    /**
     * Executes when the cancel button is clicked.
     */
    @FXML
    public void onCancelClick() {
        getStage().close();
    }
}

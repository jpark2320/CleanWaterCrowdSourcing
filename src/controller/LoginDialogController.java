package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Created by Thatcher on 9/19/2016.
 * @author Team Area 51
 * @version  1.0
 */
public class LoginDialogController extends BaseController {


    private boolean loginClicked = false;

    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    @FXML
    private Label loginText; //if login fails, this shows a message telling the user what they did wrong

    @FXML
    /**
     * Executed when the Login button is clicked.
     */
    public void onLoginClick() {
        //Attempt to log in

        //Verify credentials with security manager.
        boolean valid = getMainApp().authenticateUser(userName.getText(), password.getText());

        if (valid) {
            //Login was successful.
            loginClicked = true;

            //Break out of the login dialogue.
            getStage().close();
            //go to application screen
            getMainApp().showApplicationScreen();

        } else {
            //login was not successful.
        	loginText.setText("The credentials entered were not recognized by the system. Please try again.");
            //Allow user to try again or click "Cancel" button.

        }
    }

    @FXML
    /**
     * Executed when the cancel button is clicked.
     */
    public void onCancelClick() {
        getStage().close();
    }

    /**
     * Returns whether there was a successful login.
     * @return true if the user successfully logged in.
     */
    public boolean isLoginClicked() {
        return loginClicked;
    }
}

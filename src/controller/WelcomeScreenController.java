package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by Thatcher on 9/19/2016.
 * @author Team Area 51
 * @version  1.0
 */
public class WelcomeScreenController extends BaseController {

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    /**
     * Executes when the login button is clicked.
     */
    @FXML
    private void onLoginClick() {
        //Bring up login window prompt.
        getMainApp().showLoginDialog();
    }

    /**
     * Executes when the register button is clicked.
     */
    @FXML
    private void onRegisterClick() {
        //Bring up new user creation prompt.
        getMainApp().showRegisterDialog();
    }

}

package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.User;

/**
 * Created by Thatcher on 9/27/2016.
 * @author Team Area 51
 * @version  1.0
 */
public class EditProfileDialogController extends BaseController {

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private TextField address;


    /**
     * Called to pre-fill the email and address fields
     */
    public void setInitialTextFields() {
        User user = getMainApp().getCurrentUser();
        name.setText(user.getName());
        email.setText(user.getEmail());
        address.setText(user.getAddress());
    }

    /**
     * Executed when the cancel button is clicked
     */
    @FXML
    public void onCancelClick() {
        getStage().close();
    }

    /**
     * Executed when the save changes button is clicked.
     */
    @FXML
    public void onSaveChangesClick() {
        //Check if inputs are valid
        boolean changesMade = false;
        if (!(name.getText() == null || "".equals(name.getText()))) {
            changesMade = true;
            getMainApp().getCurrentUser().setName(name.getText());
        }

        if (!(email.getText() == null || "".equals(email.getText()))) {
            changesMade = true;
            getMainApp().getCurrentUser().setEmail(email.getText());
        }

        if (!(address.getText() == null || "".equals(address.getText()))) {
            changesMade = true;
            getMainApp().getCurrentUser().setAddress(address.getText());
        }


        if (changesMade) {
            getStage().close();
            getMainApp().showApplicationScreen();
            //getMainApp().updateApplicationTitle();
        }
    }

}

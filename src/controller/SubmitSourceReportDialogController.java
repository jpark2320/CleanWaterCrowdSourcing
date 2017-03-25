package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Location;
import model.SourceReport;

/**
 * Created by Thatcher on 10/3/2016.
 * @author Team Area 51
 * @version 1.0
 */
public class SubmitSourceReportDialogController extends BaseController {
    @FXML
    private TextField latitude;      //the variable name "location" breaks it for some reason.

    @FXML
    private TextField longitude;

    @FXML
    private TextField locDesc;

    @FXML
    private ComboBox<SourceReport.WaterSourceType> type;

    @FXML
    private ComboBox<SourceReport.WaterSourceCondition> condition;

    @FXML
    private Label error;


    /**
     * Function called when fxml file is loaded.
     */
    @FXML
    public void initialize() {
        //Populate comboBoxes.
        type.setItems(javafx.collections.FXCollections.observableArrayList(SourceReport.WaterSourceType.values()));
        condition.setItems(javafx.collections.FXCollections.observableArrayList(SourceReport.WaterSourceCondition.values()));

        //Just make sure the error field is empty, just in case.
        error.setText("");
    }

    /**
     * Executed when the user hits the submit button
     */
    @FXML
    public void onSubmitClick() {
        //Check to make sure inputs are valid.
        if (!isValidLatLong(latitude.getText(), longitude.getText())) {
            error.setText("Enter a valid latitude/longitude pair.");
        } else if (locDesc.getText() == null || locDesc.getText().isEmpty()) {
            error.setText("Enter a description for your location.");
        } else if (type.getValue() == null) {
            error.setText("Select a water source type.");
        } else if (condition.getValue() == null) {
            error.setText("Select a water source condition.");

        } else {
            long reportNumber = getMainApp().addSourceReport(
                    new Location(Double.parseDouble(latitude.getText()), Double.parseDouble(longitude.getText()), locDesc.getText()),
                    type.getValue(),
                    condition.getValue());
            getStage().close();

            getMainApp().showViewReportDialog(reportNumber);
            //getMainApp().showApplicationScreen();
        }

    }

    private static boolean isValidLatLong(String latitude, String longitude) {
        boolean returnValue = true; //because it's messy returning from inside a try catch block
        try {
            double la = Double.parseDouble(latitude);
            double lo = Double.parseDouble(longitude);

            if (la < -90.0 || la > 90.0) { //latitude only exists between -90 to 90 inclusive
                returnValue = false;
            }
            if  (lo < -180.0 || lo > 180.0) { //longitude only exists between -180 and 180 inclusive
                returnValue = false;
            }
        } catch (NullPointerException | NumberFormatException exc) {
            returnValue = false; //NullPointerException if either latitude or longitude is null, NumberFormatException if neither is a valid double
        }
        return returnValue;
    }

    /**
     * Executed when the user hits the cancel button.
     */
    @FXML
    public void onCancelClick() {
        getStage().close();
    }

}

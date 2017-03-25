package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Location;
import model.PurityReport;

/**
 * Created by Thatcher on 10/8/2016.
 * @author Team Area 51
 * @version  1.0
 */
public class SubmitPurityReportDialogController extends BaseController {


    @FXML
    private TextField latitude;      //latitude

    @FXML
    private TextField longitude;    //longitude

    @FXML
    private TextField locDesc;  //location description

    @FXML
    private TextField virusPPM;

    @FXML
    private TextField contaminantPPM;

    @FXML
    private ComboBox<PurityReport.WaterPurityCondition> condition;

    @FXML
    private Label error;


    /**
     * Function called when fxml file is loaded.
     */
    @FXML
    public void initialize() {
        //Populate comboBox.
        condition.setItems(javafx.collections.FXCollections.observableArrayList(PurityReport.WaterPurityCondition.values()));

        //Just make sure the error field is empty, just in case.
        error.setText("");
    }

    /**
     * Executed when the user hits the submit button
     */
    @FXML
    public void onSubmitClick() {
        //Check to make sure inputs are valid.
        int virusPPMValue = 0;
        int contaminantPPMValue = 0;
        boolean numericValuesCorrect;
        try {
            virusPPMValue = (int)Double.parseDouble(virusPPM.getText());
            contaminantPPMValue = (int)Double.parseDouble(contaminantPPM.getText());
            numericValuesCorrect = true;
        } catch (Exception e) {
            //Input was invalid.
            numericValuesCorrect = false;
        }

        if (!isValidLatLong(latitude.getText(), longitude.getText())) {
            error.setText("Enter a valid latitude/longitude pair.");
        } else if (locDesc.getText() == null || locDesc.getText().isEmpty()) {
            error.setText("Enter a description for your location.");
        } else if (!numericValuesCorrect) {
            error.setText("Enter a valid numeric value for the PPM.");
        } else if (condition.getValue() == null) {
            error.setText("Select a water source condition.");

        } else {
            long reportNumber = getMainApp().addPurityReport(
                    new Location(Double.parseDouble(latitude.getText()), Double.parseDouble(longitude.getText()), locDesc.getText()),
                    virusPPMValue,
                    contaminantPPMValue,
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

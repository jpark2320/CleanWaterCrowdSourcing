package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Location;

/**
 * Class to control setup for the historical reports.
 * @author Team Area 51
 * @version 1.0
 */
public class HistoricalReportConfigScreenController extends BaseController {

    @FXML
    private Button graphButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField latTextField;

    @FXML
    private TextField longTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private ToggleGroup waterType;

    @FXML
    private Label errorText;

    /**
     * Executed when user click on "Graph"
     * When it calls getMainApp().showHistoricalReportDialog(), guarantees all args are valid and good.
     */
    @FXML
    public void onGraphClick() {
        try {
            if (latTextField.getText() == null || latTextField.getText().isEmpty() || isValidLatLong(latTextField.getText(), "0.0")) {
                errorText.setText("Please enter a valid Latitude.");
            } else if (longTextField.getText() == null || longTextField.getText().isEmpty() || isValidLatLong("0.0", longTextField.getText())) {
                errorText.setText("Please enter a valid Longitude.");
            } else if (waterType.getSelectedToggle() == null) {
                errorText.setText("Please choose either virus or contaminant.");
            } else if (yearTextField.getText() == null || yearTextField.getText().isEmpty()) {
                errorText.setText("Please enter a year.");
            } else {
                int year = Integer.parseInt(yearTextField.getText());
                getMainApp().showHistoricalReportGraphDialog(
                        new Location(
                            Double.parseDouble(latTextField.getText()),
                            Double.parseDouble(longTextField.getText()),
                            ""),
                        ((RadioButton) waterType.getSelectedToggle()).getText().toUpperCase(),
                        year);
            }
        } catch (NullPointerException | NumberFormatException exc) {
            errorText.setText("Please enter a valid year.");
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
        return !returnValue;
    }

    @FXML
    public void onCancelClick() {
        getMainApp().showApplicationScreen();
    }
}

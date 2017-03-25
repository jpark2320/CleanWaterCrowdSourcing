package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.BaseReport;

/**
 * Created by Thatcher on 10/3/2016.
 * @author Team Area 51
 * @version 1.0
 */
public class ViewReportDialogController extends BaseController {


    @FXML
    private Label reportInfo;

    /**
     * Function runs when load function is called.
     */
    @FXML
    public void initialize() {
        //set the text to "" by default. When setting up the controller we'll
        //have to call the setReport() function before showing.
        reportInfo.setText("");
    }

    /**
     * Sets the report to be displayed.
     * @param report the report being displayed.
     */
    public void setReport(BaseReport report) {
        reportInfo.setText(report.getStringRepresentation());
    }

    /**
     * Executed when the user hits the OK button.
     */
    @FXML
    public void onOKClick() {
        //Simply close the dialog so the user can pick up where he left off.
        getStage().close();
    }

}

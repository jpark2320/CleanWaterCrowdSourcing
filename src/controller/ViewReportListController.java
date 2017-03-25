package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.BaseReport;
import model.Location;
import model.PurityReport;
import model.SourceReport;

/**
 * Created by Thatcher on 10/8/2016.
 * @author Team Area 51
 * @version 1.0
 */
public class ViewReportListController extends BaseController {

    @FXML
    private TableView<BaseReport> table;

    @FXML
    private TableColumn<BaseReport, Long> reportNumberColumn;

    @FXML
    private TableColumn<BaseReport, String> reportTypeColumn;

    @FXML
    private TableColumn<BaseReport, String> timeStampColumn;

    @FXML
    private TableColumn<BaseReport, Location> locationColumn;



    /**
     * Fills the table in this screen with the given list of reports.
     * @param reports the list of reports to be shown in the list.
     */
    public void populateTable(ObservableList<BaseReport> reports) {
        table.setItems(reports);

        reportNumberColumn.setCellValueFactory(new PropertyValueFactory<>("reportNumber"));
        reportTypeColumn.setCellValueFactory(p -> {
            BaseReport report = p.getValue();
            if (report instanceof SourceReport) {
                return new SimpleStringProperty("Source Report");
            } else if (report instanceof PurityReport) {
                return new SimpleStringProperty("Purity Report");
            } else {
                return new SimpleStringProperty("");
            }
        });


        timeStampColumn.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        table.getColumns().setAll(reportNumberColumn, reportTypeColumn, timeStampColumn, locationColumn);

        table.setRowFactory(fact -> {
            TableRow<BaseReport> row = new TableRow<>();
            //Make the main application display the report if the row is clicked.
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    long reportNumber = row.getItem().getReportNumber();
                    getMainApp().showViewReportDialog(reportNumber);
                }
            });
            return row;
        });
    }


    /**
     * Executes when the user hits the OK button.
     */
    @FXML
    public void onOKClick() {
        getMainApp().showApplicationScreen();
    }

}

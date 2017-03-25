package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import model.Location;
import model.PurityReport;

import java.util.List;

/**
 * Displays the historical report graph.
 * @author Team Area 51
 * @version 1.0
 */
public class HistoricalReportGraphDialogController extends BaseController {

    @FXML
    private BorderPane HistGraphPane;

    /**
     * Called when user clicks the close button to close the window
     */
    @FXML
    public void onCloseClick() {
        getStage().close();
        getMainApp().showApplicationScreen();
    }

    /**
     * Prepares a line graph in the Dialog
     * @param location The location we are looking at
     * @param waterPurityType Virus or Contaminant
     * @param year The year we look for
     */
    public void setContents(Location location, String waterPurityType, int year) {
        List<PurityReport> reports = getMainApp().getReportDatabase().getPurityReports();

        final NumberAxis xAxis = new NumberAxis("Month", 1, 12, 1); //Label "Month", lower bound 1, upper bound 12, count by 1
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Month");
        yAxis.setLabel("ppm");

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Historical Report for " + year);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Average ppm");

        double[] monthSum = new double[12]; //each element is the sum of reports that month
        int[] monthCount = new int[12]; //each element is the count of reports that month
        //find the sum and count of reports for each month
        reports.stream().filter(r -> (r.getLocation().equals(location)) && r.getTimeStamp().getYear() == year).forEach(r -> {
            int monthVal = r.getTimeStamp().getMonthValue();
            monthSum[monthVal - 1] += (waterPurityType.equals("VIRUS") ? r.getVirusPPM() : r.getContaminantPPM());
            monthCount[monthVal - 1] = monthCount[monthVal - 1] + 1;
        });

        //adds in data for each month, into series
        for (int i = 0; i < 12; i++) {
            series.getData().add(new XYChart.Data<>(i + 1, (monthCount[i] == 0) ? 0 : monthSum[i]/monthCount[i]));
        }

        //Add series into line chart
        lineChart.getData().add(series);

        HistGraphPane.setCenter(lineChart);
    }
}

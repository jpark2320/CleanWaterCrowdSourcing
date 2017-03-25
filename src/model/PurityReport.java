package model;

import java.time.LocalDateTime;


/**
 * Created by Thatcher on 10/3/2016.
 * @author Team Area 51
 * @version  1.0
 */
public class PurityReport extends BaseReport {
    /**
     * Enum to represent the possible water purity conditions.
     */
    public enum WaterPurityCondition {
        SAFE, TREATABLE, UNSAFE
    }


    private final int virusPPM;
    private final int contaminantPPM;
    private final WaterPurityCondition waterPurityCondition;

    /**
     * Constructor for a purity report with all the necessary fields.
     * @param  timeStamp            The date of creation for this report.
     * @param  reportNumber         This report's report number
     * @param  reporterUsername     The reporter's username
     * @param  location             The location of the water for this report
     * @param  waterPurityCondition The condition of the water
     * @param  virusPPM             PPM value for viruses per volume
     * @param  contaminantPPM       PPM value for contaminants per volume
     */
    public PurityReport(LocalDateTime timeStamp,
                        long reportNumber,
                        String reporterUsername,
                        Location location,
                        int virusPPM,
                        int contaminantPPM,
                        WaterPurityCondition waterPurityCondition) {

        super(timeStamp, reportNumber, reporterUsername, location);
        this.waterPurityCondition = waterPurityCondition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
    }

    /**
     * Gets the water purity condition
     * @return the condition.
     */
    public WaterPurityCondition getWaterPurityCondition() {
        return waterPurityCondition;
    }

    /**
     * Gets the Virus PPM value
     * @return the value
     */
    public int getVirusPPM() {
        return virusPPM;
    }

    /**
     * Gets the contaminant PPM value.
     * @return The value.
     */
    public int getContaminantPPM() {
        return contaminantPPM;
    }

    @Override
    public String getStringRepresentation() {
        String output = "";
        output +=   "Report Type: Water Purity Report";
        output += "\nReport Number: " + getReportNumber();
        output += "\nReport Timestamp: " + getTimeStamp();
        output += "\nReporter: " + getReporterUsername();
        output += "\nLocation: " + getLocation();
        output += "\nWater Condition: " + waterPurityCondition;
        output += "\nVirus PPM: " + virusPPM;
        output += "\nContaminant PPM: " + contaminantPPM;

        return output;
    }

}

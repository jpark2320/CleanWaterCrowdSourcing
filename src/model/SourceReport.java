package model;

import java.time.LocalDateTime;

/**
 * Created by Thatcher on 10/3/2016.
 * @author Team Area 51
 * @version 1.0
 */
public class SourceReport extends BaseReport{

    /**
     * Enum to represent the possible Water Source Types.
     */
    public enum WaterSourceType {
        BOTTLED, WELL, STREAM, LAKE, SPRING, OTHER
    }

    /**
     * Enum to represent the possible Water Source Conditions.
     */
    public enum WaterSourceCondition {
        WASTE, TREATABLE_CLEAR, TREATABLE_MUDDY, POTABLE
    }

    private final WaterSourceType waterSourceType;
    private final WaterSourceCondition waterSourceCondition;

    /**
     * Constructor for a Source Report that populates all the fields.
     * @param  timeStamp            The date the report was created.
     * @param  reportNumber         The report number associated with this report.
     * @param  reporterUsername     The username of the reporter who submitted this report.
     * @param  location             The location of the water described in the report.
     * @param  waterSourceType      The type of water source
     * @param  waterSourceCondition The condition of the water source
     */
    public SourceReport(LocalDateTime timeStamp,
                        long reportNumber,
                        String reporterUsername,
                        Location location,
                        WaterSourceType waterSourceType,
                        WaterSourceCondition waterSourceCondition) {

        super(timeStamp, reportNumber, reporterUsername, location);
        this.waterSourceType = waterSourceType;
        this.waterSourceCondition = waterSourceCondition;
    }

    /**
     * Gets the Water Source Type.
     * @return the water source type.
     */
    public WaterSourceType getWaterSourceType() {
        return waterSourceType;
    }

    /**
     * Gets the Water Source Condition.
     * @return The water source condition.
     */
    public WaterSourceCondition getWaterSourceCondition() {
        return waterSourceCondition;
    }

    @Override
    public String getStringRepresentation() {
        String output = "";
        output +=   "Report Type: Water Source Report";
        output += "\nReport Number: " + getReportNumber();
        output += "\nReport Timestamp: " + getTimeStamp();
        output += "\nReporter: " + getReporterUsername();
        output += "\nLocation: " + getLocation();
        output += "\nWater Type: " + waterSourceType;
        output += "\nWater Condition: " + waterSourceCondition;

        return output;
    }
}

package model;


import java.time.LocalDateTime;

/**
 * @author  Team Area 51
 * @version  1.0
 */
public abstract class BaseReport {


    //private final ObjectProperty<Date> timeStamp = new SimpleObjectProperty<>();
   // private Date timeStamp;

    private final LocalDateTime timeStamp;


    //private final ObjectProperty<Long> reportNumber = new SimpleObjectProperty<>();
    private final long reportNumber;

    //private StringProperty reporterUsername = new SimpleStringProperty();
    private final String reporterUsername;

    //private final ObjectProperty<Location> location = new SimpleObjectProperty<>();
    private final Location location;

    //private BooleanProperty active = new SimpleBooleanProperty(true);
    private boolean active = true;

    /**
     * Constructor for a report, populates the necessary fields.
     * @param  timeStamp        Date object representing the time
     * @param  reportNumber     Report number, unique between reports.
     * @param  reporterUsername Username of reporter
     * @param  location         Location of the report
     */
    BaseReport(LocalDateTime timeStamp, long reportNumber, String reporterUsername, Location location) {
        //this.timeStamp.set(timeStamp);
        this.timeStamp = timeStamp;

        //this.reportNumber.set(reportNumber);
        this.reportNumber = reportNumber;

        //this.reporterUsername.set(reporterUsername);
        this.reporterUsername = reporterUsername;

        //this.location.set(location);
        this.location = location;
    }


    /**
     * Sets the value of Active.
     * @param active True if you want this report to be a valid report. False to mark as deleted.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Retrieves the value of Active.
     * @return True if the report is valid. False if the report has been marked as deleted.
     */
    public boolean getActive() {
        return active;
        //return active.get();
    }

    /**
     * Returns the reporter's username.
     * @return the username
     */
    public String getReporterUsername() {
        return reporterUsername;
        //return reporterUsername.get();
    }

    /**
     * Returns the date this report was created.
     * @return the timestamp.
     */
    public LocalDateTime getTimeStamp() {
        return timeStamp;
        // return timeStamp.get();
    }

    /**
     * Returns the report number
     * @return the report number.
     */
    public long getReportNumber() {
        return reportNumber;
        //return reportNumber.get();
    }

    /**
     * Returns the location of the report.
     * @return the location
     */
    public Location getLocation() {
        return location;
        //return location.get();
    }

    /**
     * Returns a human readable String with the important fields in it,
     * so it can easily be read later.
     * @return The string.
     */
    public abstract String getStringRepresentation();

}

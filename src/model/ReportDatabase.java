package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Thatcher on 10/3/2016.
 * @author Team Area 51
 * @version 1.0
 */
public class ReportDatabase {

    private final static String pathToPurityReports = "purityReports.json";
    private final static String pathToSourceReports = "sourceReports.json";

    private List<PurityReport> purityReports;
    private List<SourceReport> sourceReports;

    private final Gson gson;


    /**
     * Creates an instance of a Report Database with which you can access and add different reports.
     */
    public ReportDatabase() {

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        this.gson = builder.create();

        loadReportLists();
    }

    /**
     * Reads the files at the given paths and uses the information at the two files to populate the
     * lists of reports in this database.
     */
    private void loadReportLists() {
        BufferedReader fileReader;
        try {
            fileReader = new BufferedReader(new FileReader(pathToPurityReports));
            this.purityReports = gson.fromJson(fileReader, new TypeToken<List<PurityReport>>(){}.getType());
            fileReader.close();

        } catch (IOException | JsonSyntaxException e) {
            this.purityReports = new ArrayList<>();
        }

        try {
            fileReader = new BufferedReader(new FileReader(pathToSourceReports));
            this.sourceReports = gson.fromJson(fileReader, new TypeToken<List<SourceReport>>(){}.getType());
            fileReader.close();

        } catch (IOException | JsonSyntaxException e) {
            this.sourceReports = new ArrayList<>();
        }
    }

    /**
     * Converts the reports in the database to json files at the specified paths.
     */
    public void saveReportLists() {
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(pathToPurityReports));
            gson.toJson(this.purityReports, new TypeToken<List<PurityReport>>(){}.getType(), fileWriter);
            fileWriter.close();

        } catch (IOException e) {
            //Wasn't able to save the purity reports to the file.
            //Do nothing?
            System.out.println("Could not save purity reports. IO Error!");
        }

        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(pathToSourceReports));
            gson.toJson(this.sourceReports, new TypeToken<List<SourceReport>>(){}.getType(), fileWriter);
            fileWriter.close();

        } catch (IOException e) {
            //Wasn't able to save the purity reports to the file.
            //Do nothing?
            System.out.println("Could not save source reports. IO Error!");
        }
    }

    /**
     * Getter for Purity Reports
     * @return A list of purity reports
     */
    public List<PurityReport> getPurityReports() { return purityReports; }

    /**
     * Getter for Source Reports
     * @return A list of source reports
     */
    public List<SourceReport> getSourceReports() { return sourceReports; }

    /**
     * Adds a SourceReport to the database.
     * @param sourceReport the SourceReport to be added.
     */
    public void addSourceReport(SourceReport sourceReport) {
        this.sourceReports.add(sourceReport);
    }

    /**
     * Adds a Purity report to the database
     * @param purityReport The PurityReport to be added.
     */
    public void addPurityReport(PurityReport purityReport) {
        this.purityReports.add(purityReport);
    }

    /**
     * Gets a List of reports submitted by the specified user.
     * @param  username The user's username
     * @return          The List of reports.
     */
    public List<BaseReport> getReportsForUser(String username) {
        List<BaseReport> toReturn = new ArrayList<>();
        username = username.toLowerCase();

        for (PurityReport purityReport : purityReports) {
            if (purityReport.getReporterUsername().equals(username)) {
                toReturn.add(purityReport);
            }
        }
        for (SourceReport sourceReport : sourceReports) {
            if (sourceReport.getReporterUsername().equals(username)) {
                toReturn.add(sourceReport);
            }
        }
        return stripInactiveReports(toReturn);
    }

    /**
     * Returns a list of all the reports found in the database.
     * @return the List.
     */
    public List<BaseReport> getAllReports() {
        List<BaseReport> toReturn = new ArrayList<>();
        toReturn.addAll(purityReports);
        toReturn.addAll(sourceReports);
        return stripInactiveReports(toReturn);
    }

    /**
     * Gets the report associated with the specified reportNumber.
     * @param  reportNumber The report number associated with the report you're looking for.
     * @return              The report.
     */
    public BaseReport getReportByReportNumber(long reportNumber) {
        for (SourceReport sourceReport : sourceReports) {
            if (sourceReport.getReportNumber() == reportNumber) {
                return sourceReport;
            }
        }
        for (PurityReport purityReport : purityReports) {
            if (purityReport.getReportNumber() == reportNumber) {
                return purityReport;
            }
        }
        return null;
    }

    /**
     * Finds the next report number to be added.
     * @return A long whose value is the current maximum report number + 1.
     */
    public long getNewReportNumber() {
        //Fetch current maximum report number.
        long max = 0;
        for (SourceReport sourceReport : sourceReports) {
            if (sourceReport.getReportNumber() > max) {
                max = sourceReport.getReportNumber();
            }
        }
        for (PurityReport purityReport : purityReports) {
            if (purityReport.getReportNumber() > max) {
                max = purityReport.getReportNumber();
            }
        }
        return max + 1;
    }

    /**
     * Removes the report with the given report number from the database.
     * @param reportNumber the report number of the report to be removed.
     */
    public void removeReport(long reportNumber) {
        for (PurityReport purityReport : purityReports) {
            if (purityReport.getReportNumber() == reportNumber) {
                purityReport.setActive(false);
                return;
            }
        }
        for (SourceReport sourceReport : sourceReports) {
            if (sourceReport.getReportNumber() == reportNumber) {
                sourceReport.setActive(false);
                return;
            }
        }
    }

    /**
     * Strips the reports that have been marked for deletion from the database.
     * @param reports The list of reports that is to be filtered.
     * @return a new list of reports that has only the active ones.
     */
    private List<BaseReport> stripInactiveReports(List<BaseReport> reports) {
        return reports.stream().filter(report -> report.getActive()).collect(Collectors.toList());

    }
}

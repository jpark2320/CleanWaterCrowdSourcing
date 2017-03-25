package mainfxapp;

import controller.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import model.SecurityManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Team Area 51
 * @version  1.0
 */

public class MainFXApplication extends Application {

    private static final Logger LOGGER = Logger.getLogger("MainFXApplication");

    private final SecurityManager securityManager = new SecurityManager();

    private final ReportDatabase reportDatabase = new ReportDatabase();

    private User currentUser;

    private Stage mainStage;


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.mainStage = primaryStage;
        showWelcomeScreen();

        this.mainStage.setOnCloseRequest(event -> {
            reportDatabase.saveReportLists();
            securityManager.saveUsers();
        });
    }

    /**
     * Displays the welcome screen
     */
    public void showWelcomeScreen() {
        createMainScreen("../view/WelcomeScreenView.fxml", "Welcome!");
        mainStage.show();
    }

    /**
     * Displays the main application screen
     */
    public void showApplicationScreen() {
        ApplicationScreenController controller = (ApplicationScreenController)createMainScreen("../view/ApplicationScreenView.fxml", "Logged In"/*nobody will see this title*/);
        if (controller != null) {
            updateApplicationTitle();
            controller.setVisibleItems();
            mainStage.show();
        }
    }

    /**
     * Displays the screen for querying the user about which historical report to display
     */
    public void showHistoricalReportConfigScreen() {
        HistoricalReportConfigScreenController controller = (HistoricalReportConfigScreenController)createMainScreen("../view/HistoricalReportConfigScreenView.fxml", "View Historical Report");
        if (controller != null) {
            updateApplicationTitle();
            mainStage.show();
        }
    }

    /**
     * Give it the data from the config screen and it will display the historical graph in a new dialog
     * @param location The location to look at
     * @param waterPurityType Either "VIRUS" or "CONTAMINANT"
     * @param year The year you want displayed in the graph.
     */
    public void showHistoricalReportGraphDialog(Location location, String waterPurityType, int year) {
        BaseController controller = createDialog("../view/HistoricalReportGraphDialogView.fxml", "Historical Report for " + year);
        if (controller != null) {
            ((HistoricalReportGraphDialogController) controller).setContents(location, waterPurityType, year);
            controller.getStage().show();
        }
    }

    /**
     * Shows the login dialog
     */
    public void showLoginDialog() {
        BaseController controller = createDialog("../view/LoginDialogView.fxml", "Log In!");
        if (controller != null) {
            controller.getStage().show();
        }
    }

    /**
     * Shows the user registration dialog
     */
    public void showRegisterDialog() {
        BaseController controller = createDialog("../view/RegistrationDialogView.fxml", "Register a New User");
        if (controller != null) {
            controller.getStage().show();
        }
    }

    /**
     * Shows a dialog that for editing profiles.
     */
    public void showEditProfileDialog() {
        EditProfileDialogController controller = (EditProfileDialogController)createDialog("../view/EditProfileDialogView.fxml", "Edit Profile");
        if (controller != null) {
            controller.setInitialTextFields();
            controller.getStage().show();
        }
    }

    /**
     * Show a dialog for adding new Source Reports
     */
    public void showSubmitSourceReportDialog() {
        BaseController controller = createDialog("../view/SubmitSourceReportDialogView.fxml", "Submit New Source Report");
        if (controller != null) {
            controller.getStage().show();
        }
    }

    /**
     * Show a dialog for adding new Purity Reports
     */
    public void showSubmitPurityReportDialog() {
        BaseController controller = createDialog("../view/SubmitPurityReportDialogView.fxml", "Submit New Purity Report");
        if (controller != null) {
            controller.getStage().show();
        }
    }


    /**
     * Show a dialog that displays information about a specified report.
     * @param reportNumber the report whose information we want to show in this dialog.
     */
    public void showViewReportDialog(long reportNumber) {
        ViewReportDialogController controller = (ViewReportDialogController)createDialog("../view/ViewReportDialogView.fxml", "Report Summary");
        if (controller != null) {
            controller.setReport(reportDatabase.getReportByReportNumber(reportNumber));
            controller.getStage().show();
        }
    }

    /**
     * Set the screen to a list of the current user's submitted reports in the database.
     */
    public void showUserReportListScreen() {
        ViewReportListController controller = (ViewReportListController)createMainScreen("../view/ViewReportListView.fxml", currentUser.getUsername() + " Reports");
        if (controller != null) {
            controller.populateTable(FXCollections.observableArrayList(reportDatabase.getReportsForUser(currentUser.getUsername())));
            mainStage.show();
        }
    }

    /**
     * Set the screen to a list of all the reports in the database.
     */
    public void showAllReportListScreen() {
        ViewReportListController controller = (ViewReportListController)createMainScreen("../view/ViewReportListView.fxml", "All Reports");
        if (controller != null) {
            controller.populateTable(FXCollections.observableArrayList(reportDatabase.getAllReports()));
            mainStage.show();
        }
    }

    /**
     * Creates dialog windows that are Modal. Returns the controller before showing
     * the display, so you can run additional functions.
     * @param fxmlResource the path to the fxml file to be loaded
     * @param title the title of the generated dialog window.
     * @return BaseController returns the controller so you can run
     *                          additional functions before showing the dialog.
     */
    private BaseController createDialog(String fxmlResource, String title) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource(fxmlResource));
            Pane pane = loader.load();

            Stage stage = new Stage();

            stage.setTitle(title);

            //Gotta set this as modal and block other inputs to the application
            stage.initModality(Modality.WINDOW_MODAL);

            //Set this stage as focused.
            BaseController controller = loader.getController();
            controller.setMainApp(this);
            controller.setStage(stage);

            stage.setScene(new Scene(pane));

            //Allow caller to show the stage after running any additional steps.
            //stage.show();
            return controller;


        } catch (IOException e) {
            //loader failed.
            LOGGER.log(Level.SEVERE, "FXML reading issue with " + fxmlResource);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Similar to createDialog, but this just modifies mainStage.
     * Does not show the mainStage, so you can run some functions before showing.
     * @param mainScreenResource The path to the fxml resource where we're going to load the view info from.
     * @param title The initial title of the screen.
     * @return BaseController - the controller loaded by the fxml file.
     */
    private BaseController createMainScreen(String mainScreenResource, String title) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource(mainScreenResource));
            Pane pane = loader.load();

            mainStage.setTitle(title);

            BaseController controller = loader.getController();
            controller.setMainApp(this);

            mainStage.setScene(new Scene(pane));
            return controller;

        } catch (IOException e) {
            //loader failed.
            LOGGER.log(Level.SEVERE, "FXML reading issue with " + mainScreenResource);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks if the provided credentials are for a valid user and logs them in if so.
     * @param  username The user's username
     * @param  password The user's password
     * @return          true if the user is logged in. False if the specified user
     *                  could not be logged in.
     */
    public boolean authenticateUser(String username, String password) {
        User user = securityManager.checkCredentials(username, password);

        if (user != null) {
            //Valid credentials.

            this.currentUser = user;
            return true;
        } else {
            //invalid credentials.
            this.currentUser = null;
            return false;
        }
    }

    /**
     * Logs the user out by removing its instance from the application.
     */
    public void logout() {
        this.currentUser = null;
        showWelcomeScreen();
    }

    /**
     * Adds the user with the specified information
     * @param  username           the user's username
     * @param  password           the user's password
     * @param  name               the user's real name
     * @param  authorizationLevel the user's authorization level
     * @return                    the user instance if the user is added, or null in case of failure.
     */
    public boolean addUser(String username, String password, String name, User.AuthorizationLevel authorizationLevel) {
        User temp = new User(username, password, name);
        temp.setAuthorizationLevel(authorizationLevel);
        return securityManager.addUser(temp);
    }

    /**
     * Returns the instance of the user currently logged in.
     * @return the user.
     */
    public User getCurrentUser() {
        return currentUser;
    }


    /**
     * Adds a purity report to the database.
     * @param location the location of the water source
     * @param virusPPM the measured virus PPM
     * @param contaminantPPM the measured contaminant PPM
     * @param waterPurityCondition the overall condition of the water.
     * @return the report number.
     */
    public long addPurityReport(Location location, int virusPPM, int contaminantPPM, PurityReport.WaterPurityCondition waterPurityCondition) {
        long reportNumber = reportDatabase.getNewReportNumber();

        PurityReport purityReport = new PurityReport(LocalDateTime.now(),
                reportNumber,
                currentUser.getUsername(),
                location,
                virusPPM,
                contaminantPPM,
                waterPurityCondition);

        reportDatabase.addPurityReport(purityReport);
        return reportNumber;
    }

    /**
     * Adds a source report to the database.
     * @param location the location of the water source
     * @param waterSourceType the type of the water source
     * @param waterSourceCondition the condition of the water source.
     * @return the report number of the added report.
     */
    public long addSourceReport(Location location, SourceReport.WaterSourceType waterSourceType, SourceReport.WaterSourceCondition waterSourceCondition) {
        long reportNumber = reportDatabase.getNewReportNumber();
        SourceReport sourceReport = new SourceReport(LocalDateTime.now(),
                                                    reportNumber,
                                                    currentUser.getUsername(),
                                                    location,
                                                    waterSourceType,
                                                    waterSourceCondition
                                                    );

        reportDatabase.addSourceReport(sourceReport);
        return reportNumber;
    }

    /**
     * Returns the instance of reportDatabase that's currently running.
     * @return the reportDatabase.
     */
    public ReportDatabase getReportDatabase() {
        return reportDatabase;
    }

    /**
     * Sets the title of the application.
     */
    private void updateApplicationTitle() {
        mainStage.setTitle("Main Application - Welcome " + currentUser.getName() + " - " + currentUser.getAuthorizationLevel());
    }

    /**
     * Runs the application.
     * @param args command line parameters.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

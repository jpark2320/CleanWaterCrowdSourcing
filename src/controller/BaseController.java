package controller;

import javafx.stage.Stage;
import mainfxapp.MainFXApplication;

/**
 * Created by Thatcher on 10/3/2016.
 * @author Team Area 51
 * @version 1.0
 */
public abstract class BaseController {
    private MainFXApplication mainFXApplication;
    private Stage stage;

    /**
     * Sets the instance of mainFXApplication
     * @param mainFXApplication the instance.
     */
    public void setMainApp(MainFXApplication mainFXApplication) {
        this.mainFXApplication = mainFXApplication;
    }

    /**
     * Sets the stage this screen is on.
     * @param stage the stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Returns the mainFXApplication in this controller instance
     * @return the mainFXApplication
     */
    MainFXApplication getMainApp() {
        return mainFXApplication;
    }

    /**
     * Gets the stage instance for this controller
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }


}

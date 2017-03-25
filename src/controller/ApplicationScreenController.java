package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import model.BaseReport;
import model.User;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Team Area 51
 * @version  1.0
 */
public class ApplicationScreenController extends BaseController implements Initializable, MapComponentInitializedListener {

    @FXML
    private Menu reportsMenu;

    @FXML
    private MenuItem menuNewPurityReport;

    @FXML
    private MenuItem menuViewHistoricalReport;

    @FXML
    private Menu adminMenu;

    @FXML
    private MenuItem menuNewSourceReport;

    @FXML
    private MenuItem menuViewSubmittedReports;

    @FXML
    private MenuItem menuViewAllReports;

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;


    /**
     * Creates the map listener
     * @param url url
     * @param rb  resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
    }

    /**
     * Populates the map with current markers at each report location.
     */
    @Override
    public void mapInitialized() {
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(33.7756, -84.3963))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(15);

        map = mapView.createMap(mapOptions);

        onRefreshClick();
    }

    /**
     * Executes when the refresh button is clicked.
     */
    @FXML
    public void onRefreshClick() {
        List<BaseReport> reports = getMainApp().getReportDatabase().getReportsForUser(getMainApp().getCurrentUser().getUsername());
        reports.stream().filter(BaseReport::getActive).forEach(report -> {
            LatLong loc = new LatLong(report.getLocation().getLatitude(), report.getLocation().getLongitude());
            MarkerOptions mo = new MarkerOptions();
            mo.position(loc);

            Marker newMarker = new Marker(mo);

            map.addUIEventHandler(newMarker,
                    UIEventType.click,
                    (JSObject obj) -> {
                        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                        infoWindowOptions.content(report.getStringRepresentation().replaceAll("\n", "<br>"));
                        InfoWindow window = new InfoWindow(infoWindowOptions);
                        window.open(map, newMarker);
                    }
            );

            map.addMarker(newMarker);
        });
    }

    /**
     * Executes when the New Source Report menu option is selected
     */
    @FXML
    public void onMenuNewSourceReport() {
        getMainApp().showSubmitSourceReportDialog();
    }

    /**
     * Executes when the New Purity Report menu option is selected
     */
    @FXML
    public void onMenuNewPurityReport() {
        getMainApp().showSubmitPurityReportDialog();
    }

    /**
     * Executes when the View Submitted Reports menu option is selected
     */
    @FXML
    public void onMenuViewSubmittedReports() {
        getMainApp().showUserReportListScreen();
    }

    /**
     * Executes when the View All Reports menu option is selected.
     */
    @FXML
    public void onMenuViewAllReports() {
        getMainApp().showAllReportListScreen();
    }

    /**
     * Executes when the View Historical Report menu option is selected
     */
    @FXML
    public void onMenuViewHistoricalReport() {
        getMainApp().showHistoricalReportConfigScreen();
    }

    /**
     * Executes when the View Profile menu option is selected
     */
    @FXML
    public void onMenuViewProfile() {

    }

    /**
     * Executes when the Edit User menu option is selected
     */
    @FXML
    public void onMenuEditUser() {

    }

    /**
     * Executes when the Security Log menu option is selected
     */
    @FXML
    public void onMenuSecurityLog() {

    }

    /**
     * Executes when the edit profile menu option is selected
     */
    @FXML
    public void onMenuEditProfile() {
        getMainApp().showEditProfileDialog();
    }

    /**
     * Executes when the logout menu option is selected
     */
    @FXML
    public void onMenuLogout() {
        getMainApp().logout();
    }

    /**
     * Configures which menu items are visible based on the authorization level of the current user.
     */
    public void setVisibleItems() {
        User.AuthorizationLevel authorizationLevel = getMainApp().getCurrentUser().getAuthorizationLevel();
        switch(authorizationLevel) {
            case USER:
                //Hide purity reports
                menuNewPurityReport.setVisible(false);
            case WORKER:
                //Hide historical reports.
                menuViewHistoricalReport.setVisible(false);
                //Hide the ability to view all reports.
                menuViewAllReports.setVisible(false);
            case MANAGER:
                //Hide admin menu
                adminMenu.setVisible(false);
                break;
            case ADMINISTRATOR:
                //Hide ability to view reports
                reportsMenu.setVisible(false);
        }
    }
}

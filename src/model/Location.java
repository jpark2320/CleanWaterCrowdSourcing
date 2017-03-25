package model;

/**
 * Created by Thatcher on 10/3/2016.
 * @author Team Area 51
 * @version  1.0
 */
public class Location {
    private String location;

    private double latitude;
    private double longitude;

    /**
     * Constructor for a Location object
     * @param latitude      The latitude
     * @param longitude     The longitude
     * @param  locationDescription The location's description
     */
    public Location(double latitude, double longitude, String locationDescription) {
        this();
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = locationDescription;

    }

    /**
     * Gets the latitude associated with this Location
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * gets the longitude associated with this location.
     * @return the longitude.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Default constructor.
     */
    public Location() {
        location = "";
    }

    /**
     * Returns the location formatted as a String
     * @return The string.
     */
    @Override
    public String toString() {
        return latitude + "\t" + longitude + "\t" + location;
    }

    /**
     * Return true if and only if the otherLocation is not null,
     * and if both this and otherLocation have the same latitude and longitude
     * locationDescription doesn't matter because 2 people could have the differing
     * opinions about a location
     * @param otherLocation the location we want to compare to
     * @return              true if they are the same Location, false if they are not
     */
    @Override
    public boolean equals(Object otherLocation) {
        if (otherLocation == null) {
            return false;
        }
        return (!(otherLocation instanceof Location)) || (this.longitude == ((Location) otherLocation).longitude
                                                        && this.latitude == ((Location) otherLocation).latitude) ;
    }
}

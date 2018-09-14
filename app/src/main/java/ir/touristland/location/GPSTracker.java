package ir.touristland.location;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import static android.content.Context.LOCATION_SERVICE;

public final class GPSTracker implements LocationListener {

    private final static String TAG = "GPSTracker";
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_UPDATES = 10; //  meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 10; //  second
    private final Context mContext;
    //flag of the gateway to get location
    private String provider = "undefined";
    private Location location; // location
    // Declaring a Location Manager
    private LocationManager locationManager;
    private GpsCallback mCallback;


    public GPSTracker(Context context) {
        this.mContext = context;
        initializeLocationManager();
        requestLocation();
    }

    /**
     * init instance of LocationManager
     */
    private void initializeLocationManager() {
        if (locationManager == null) {
            this.locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        }
    }

    /**
     * getting GPS provider status
     *
     * @return {@code boolean} if gps provider enabled return {@code true} else {@code false}
     * Note: if  {@code locationManager == null} return false
     */
    private boolean isGPSEnabled() {
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * getting network provider status
     *
     * @return {@code boolean} if network provider enabled return {@code true} else {@code false}
     * Note: if  {@code locationManager == null} return false
     */
    private boolean isNetworkEnabled() {
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * update last location from network provider
     *
     * @throws SecurityException need to request {@code android.permission.ACCESS_COARSE_LOCATION} permission from user in runtime
     */
    private void requestLocationFromNetwork() throws SecurityException {
        //init instance of LocationManager
        initializeLocationManager();

        provider = "Network";

        locationManager.removeUpdates(this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_UPDATES, this, Looper.getMainLooper());
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * update last location from gps provider
     *
     * @throws SecurityException need to request {@code android.permission.ACCESS_FINE_LOCATION} permission from user in runtime
     */
    private void requestLocationFromGPS() throws SecurityException {
        //init instance of LocationManager
        initializeLocationManager();

        provider = "GPS";

        locationManager.removeUpdates(this);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_UPDATES, this, Looper.getMainLooper());
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public void requestLocation() throws SecurityException {
        if (isGPSEnabled()) {
            //request location from gps provider
            requestLocationFromGPS();
        } else if (isNetworkEnabled()) {
            //request location from network provider
            requestLocationFromNetwork();
        } else if (mCallback != null) {
            // need to enable a provider
            mCallback.onNeedProvider(mContext, GPSTracker.this);
        }
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stop() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     * Function to get location
     *
     * @return {@code android.Location} location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * function to get provider name
     *
     * @return {@code provider} if gps provider enabled return GPS else if network provider enabled return Network
     */
    public String getProvider() {
        return this.provider;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.isNetworkEnabled() || isGPSEnabled();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mCallback != null) {
            mCallback.onLocationChanged(location, this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public GPSTracker setCallback(GpsCallback callback) {
        this.mCallback = callback;
        return this;
    }


    public interface GpsCallback {
        void onLocationChanged(Location location, GPSTracker gpsTracker);

        void onNeedProvider(Context context, GPSTracker gpsTracker);
    }
}

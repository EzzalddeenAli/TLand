package ir.touristland.location;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public final class LocationTracker extends LocationCallback implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, FeaturedLocation.OnFeatureListener {

    // LogCat tag
    private static final String TAG = LocationTracker.class.getSimpleName();
    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FASTEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
    private LocationManager locationManager;
    private FeaturedLocation mLastLocation;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Context mContext;
    private OnTrackingLocationListener mTrackingLocationListener;

    public LocationTracker(Context context) {
        mContext = context;

        locationManager = ((LocationManager) mContext.getSystemService(LOCATION_SERVICE));

        // First we need to check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();

            createLocationRequest();
        }
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        List<Location> locationList = locationResult.getLocations();
        if (locationList.size() > 0) {
            //The last location in the list is the newest
            mLastLocation = new FeaturedLocation(locationList.get(locationList.size() - 1), mContext);
            if (isGPSEnabled()) {
                mLastLocation.setProvider("GPS");
            } else if (isNetworkEnabled()) {
                mLastLocation.setProvider("Network");
            }

            mLastLocation.setOnFeatureListener(this);
        }
    }


    /**
     * getting GPS provider status
     *
     * @return {@code boolean} if gps provider enabled return {@code true} else {@code false}
     * Note: if  {@code locationManager == null} return false
     */
    public boolean isGPSEnabled() {
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * getting network provider status
     *
     * @return {@code boolean} if network provider enabled return {@code true} else {@code false}
     * Note: if  {@code locationManager == null} return false
     */
    public boolean isNetworkEnabled() {
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * Creating google api client object
     */
    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }


    /**
     * check is connected
     *
     * @return {@code boolean} GoogleApiClient object is connected or not
     */
    public boolean isConnected() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }

    /**
     * connect GoogleApiClient
     */
    public LocationTracker connect() {
        if (mGoogleApiClient != null && !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
        return this;
    }

    /**
     * disconnect GoogleApiClient
     */
    public LocationTracker disconnect() {
        if (isConnected()) {
            stopLocationUpdates();
            mGoogleApiClient.disconnect();
        }
        return this;
    }

    /**
     * Creating location request object
     */
    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    /**
     * verify google play services on the device
     */
    private boolean checkPlayServices() {
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mContext);
        if (status != ConnectionResult.SUCCESS) {
            if (status == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
                requestByClassicMethod();
            } else if (mTrackingLocationListener != null) {
                mTrackingLocationListener.onGetLocationFailed(this, new GooglePlayServicesNotAvailableException(-1));
            }
            return false;
        }
        return true;
    }

    private void requestByClassicMethod() {

        new GPSTracker(mContext).setCallback(new GPSTracker.GpsCallback() {
            @Override
            public void onLocationChanged(Location location, GPSTracker tracker) {
                new FeaturedLocation(location, mContext).setOnFeatureListener(LocationTracker.this);

                tracker.stop();
            }

            @Override
            public void onNeedProvider(Context context, GPSTracker tracker) {
                if (turnOnGPS()) {
                    tracker.requestLocation();
                } else if (mTrackingLocationListener != null) {
                    mTrackingLocationListener.onGetLocationFailed(LocationTracker.this, new NotProviderEnabledException());
                }
            }
        }).requestLocation();
    }


    /**
     * automatic turn on the gps
     *
     * @return {@code boolean} if device is rooted and gps turning on successfully return true else return false
     */
    public boolean turnOnGPS() {
       /* if (!DeviceUtils.isRooted()) {
            return false;
        }*/
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        mContext.sendBroadcast(intent);

        String provider = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            mContext.sendBroadcast(poke);

            return true;
        }

        return false;
    }


    /**
     * automatic turn off the gps
     *
     * @return {@code boolean} if device is rooted and gps turning off successfully return true else return false
     */
    public boolean turnOffGPS() {
       /* if (!DeviceUtils.isRooted()) {
            return false;
        }*/
        String provider = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (provider.contains("gps")) { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            mContext.sendBroadcast(poke);

            return true;
        }

        return false;
    }


    /**
     * Starting the location updates
     */
    public void startLocationUpdates() throws SecurityException { // todo android 8.1
        if (isConnected() && mLocationRequest != null) {
            LocationServices.getFusedLocationProviderClient(mContext).requestLocationUpdates(mLocationRequest, this, Looper.myLooper());
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//            Log.e(TAG, "startLocationUpdates: ");
        }
    }

    /**
     * Stopping location updates
     */
    public void stopLocationUpdates() {
        if (isConnected()) {
            LocationServices.getFusedLocationProviderClient(mContext).removeLocationUpdates(this);
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (LocationListener) this);
        }
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
//        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
        if (mTrackingLocationListener != null) {
            mTrackingLocationListener.onGetLocationFailed(this, new Exception("not connect to api"));
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            // Assign the new location
            mLastLocation = new FeaturedLocation(location, mContext);

            if (isGPSEnabled()) {
                mLastLocation.setProvider("GPS");
            } else if (isNetworkEnabled()) {
                mLastLocation.setProvider("Network");
            }

            mLastLocation.setOnFeatureListener(this);
        }
    }


    public LocationTracker setOnTrackingLocationListener(OnTrackingLocationListener listener) {
        this.mTrackingLocationListener = listener;
        return this;
    }

    @Override
    public void onStartPrepare() {

    }

    @Override
    public void onPreparedFeature(FeaturedLocation location) {
        // Displaying the new location on UI
        if (mTrackingLocationListener != null) {
            mTrackingLocationListener.onGetLocation(this, location);
        }
    }


    @Override
    public void onLost(Exception e, FeaturedLocation location) {
        // Displaying the new location on UI
        if (mTrackingLocationListener != null) {
            mTrackingLocationListener.onGetLocation(this, location);
        }
    }

    public interface OnTrackingLocationListener {
        void onGetLocation(LocationTracker tracker, FeaturedLocation location);

        void onGetLocationFailed(LocationTracker tracker, Exception e);
    }

}

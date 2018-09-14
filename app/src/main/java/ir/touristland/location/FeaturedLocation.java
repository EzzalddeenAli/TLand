package ir.touristland.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.List;
import java.util.Locale;

public class FeaturedLocation extends Location {

    public final static String TAG = FeaturedLocation.class.getSimpleName();

    private OnFeatureListener mFeatureListener;

    private String country;
    private String state;
    private String city;
    private String featureName;
    private String subFeatureName;
    private String zone;
    private String district;

    private boolean prepared = false;


    public FeaturedLocation(Location location, Context context) {
        super(location);
        prepareFeature(context, true);
    }

    public FeaturedLocation unprepare() {
        prepared = false;
        country = null;
        state = null;
        city = null;
        featureName = null;
        zone = null;
        district = null;
        return this;
    }

    public void prepareFeature(Context context, boolean refactoring) {
        if (refactoring && isPrepared() && mFeatureListener != null) {
            mFeatureListener.onStartPrepare();
            return;
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                if (mFeatureListener != null) {
                    new Handler(Looper.getMainLooper()).post(() -> mFeatureListener.onStartPrepare());
                }

                Geocoder geocoder = new Geocoder(context, new Locale("fa"));
                try {
                    List<Address> addressList = geocoder.getFromLocation(getLatitude(), getLongitude(), 16);
                    Log.e(TAG, "Addresses Size: " + addressList.size());
//
//
                    int index = 0;
                    for (Address address : addressList) {
                        index++;
                        Log.d(TAG, "Address:----------------------" + index + "----------------------");
                        Log.e(TAG, address.toString());
                    }

                    switch (addressList.size()) {
                        case 1:
                            country = addressList.get(0).getFeatureName();
                            break;
                        case 2:
                            state = addressList.get(0).getAdminArea() != null ? addressList.get(0).getAdminArea() : addressList.get(0).getFeatureName() != null ? addressList.get(0).getFeatureName() : addressList.get(0).getThoroughfare() != null ? addressList.get(0).getThoroughfare() : addressList.get(0).getAdminArea();
                            country = addressList.get(1).getFeatureName();
                            break;
                        case 3:
                            city = addressList.get(0).getSubAdminArea() != null ? addressList.get(0).getSubAdminArea() : addressList.get(0).getFeatureName() != null ? addressList.get(0).getFeatureName() : addressList.get(0).getThoroughfare();
                            state = addressList.get(1).getAdminArea();
                            country = addressList.get(2).getFeatureName();
                            break;
                        case 4:
                            district = addressList.get(0).getFeatureName() != null ? addressList.get(0).getFeatureName() : addressList.get(0).getThoroughfare();
                            city = addressList.get(1).getSubAdminArea();
                            state = addressList.get(2).getAdminArea();
                            country = addressList.get(3).getFeatureName();
                            break;
                        case 5:
                            zone = addressList.get(0).getFeatureName() != null ? addressList.get(0).getFeatureName() : addressList.get(0).getThoroughfare();
                            district = addressList.get(1).getFeatureName();
                            city = addressList.get(2).getSubAdminArea();
                            state = addressList.get(3).getAdminArea();
                            country = addressList.get(4).getFeatureName();
                            break;
                        case 6:
                            if (addressList.get(0).getLocality() == null) {
                                featureName = addressList.get(0).getFeatureName() != null ? addressList.get(0).getFeatureName() : addressList.get(0).getThoroughfare();
                                zone = addressList.get(1).getFeatureName();
                                district = addressList.get(2).getFeatureName();
                                city = addressList.get(3).getSubAdminArea();
                                state = addressList.get(4).getAdminArea();
                                country = addressList.get(5).getFeatureName();
                            } else {
                                zone = addressList.get(0).getFeatureName();
                                district = addressList.get(1).getFeatureName();
                                city = addressList.get(3).getSubAdminArea();
                                state = addressList.get(4).getAdminArea();
                                country = addressList.get(5).getFeatureName();
                            }
                            break;
                        case 7:
                            featureName = addressList.get(0).getThoroughfare();
                            zone = addressList.get(1).getFeatureName();
                            district = addressList.get(2).getFeatureName();
                            city = addressList.get(4).getSubAdminArea();
                            state = addressList.get(5).getAdminArea();
                            country = addressList.get(6).getCountryName();
                            break;

                        case 8:
                            subFeatureName = addressList.get(0).getThoroughfare();
                            featureName = addressList.get(1).getFeatureName();
                            zone = addressList.get(2).getFeatureName();
                            district = addressList.get(3).getFeatureName();
                            city = addressList.get(5).getSubAdminArea();
                            state = addressList.get(6).getAdminArea();
                            country = addressList.get(7).getCountryName();
                            break;

                        case 9:
                            subFeatureName = addressList.get(0).getThoroughfare();
                            subFeatureName += "," + addressList.get(1).getThoroughfare();
                            featureName = addressList.get(2).getFeatureName();
                            zone = addressList.get(3).getFeatureName();
                            district = addressList.get(4).getFeatureName();
                            city = addressList.get(6).getSubAdminArea();
                            state = addressList.get(7).getAdminArea();
                            country = addressList.get(8).getCountryName();
                            break;

                        case 10:
                            subFeatureName = addressList.get(0).getThoroughfare();
                            subFeatureName += "," + addressList.get(1).getThoroughfare();
                            subFeatureName += "," + addressList.get(2).getThoroughfare();
                            featureName = addressList.get(3).getFeatureName();
                            zone = addressList.get(4).getFeatureName();
                            district = addressList.get(5).getFeatureName();
                            city = addressList.get(7).getFeatureName();
                            state = addressList.get(8).getAdminArea();
                            country = addressList.get(9).getCountryName();
                            break;
                    }


                    prepared = true;

                    if (mFeatureListener != null) {
                        new Handler(Looper.getMainLooper()).post(() -> mFeatureListener.onPreparedFeature(FeaturedLocation.this));

                    }

                } catch (Exception e) {
                    prepared = false;
                    if (mFeatureListener != null) {
                        new Handler(Looper.getMainLooper()).post(() -> mFeatureListener.onLost(e, FeaturedLocation.this));
                    }
                }
            }
        };
        thread.start();
    }

    public FeaturedLocation setOnFeatureListener(OnFeatureListener listener) {
        this.mFeatureListener = listener;
        return this;
    }

    public boolean isPrepared() {
        return prepared;
    }

    public String getZone() {
        return zone == null ? "" : zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDistrict() {
        return district == null ? "" : district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubFeatureName() {
        return subFeatureName == null ? "" : subFeatureName;
    }

    public void setSubFeatureName(String subFeatureName) {
        this.subFeatureName = subFeatureName;
    }

    public String getCountry() {
        return country == null ? "" : country;
    }

    public String getState() {
        return state == null ? "" : state;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public String getFeatureName() {
        return featureName == null ? "" : featureName;
    }

    public String getAddress() {
        String text = "";

        if (country != null && !country.isEmpty()) {
            if (text.isEmpty())
                text = text.concat(country);
            else
                text = text.concat("?").concat(country);
        }
        if (state != null && !state.isEmpty()) {
            if (text.isEmpty())
                text = text.concat(state);
            else
                text = text.concat("?").concat(state);
        }

        if (city != null && !city.isEmpty()) {
            if (text.isEmpty())
                text = text.concat(city);
            else
                text = text.concat("?").concat(city);
        }

        if (district != null && !district.isEmpty()) {
            if (text.isEmpty())
                text = text.concat(district);
            else
                text = text.concat("?").concat(district);
        }

        if (zone != null && !zone.isEmpty()) {
            if (text.isEmpty())
                text = text.concat(zone);
            else
                text = text.concat("?").concat(zone);
        }

        if (featureName != null && !featureName.isEmpty()) {
            if (text.isEmpty())
                text = text.concat(featureName);
            else
                text = text.concat("?").concat(featureName);
        }

        if (subFeatureName != null && !subFeatureName.isEmpty()) {
            if (text.isEmpty())
                text = text.concat(subFeatureName);
            else
                text = text.concat("?").concat(subFeatureName);
        }

        return text;
    }

    public interface OnFeatureListener {
        void onStartPrepare();

        void onPreparedFeature(FeaturedLocation location);

        void onLost(Exception e, FeaturedLocation location);
    }

}

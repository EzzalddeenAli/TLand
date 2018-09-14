package ir.touristland.Fragments;


import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.touristland.Activities.Hotels.HotelDetailsActivity;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Models.CenterDetailsModel;
import ir.touristland.Models.HotelDetails.MoreInfoItem;
import ir.touristland.Models.HotelDetails.RulesItem;
import ir.touristland.Models.HotelList.ResultItem;
import ir.touristland.R;
import ir.touristland.location.FeaturedLocation;
import ir.touristland.location.LocationTracker;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static ir.touristland.location.FeaturedLocation.TAG;

public class HotelDetailFragment extends Fragment {

    GoogleMap _googleMap;
    Polyline line;
    //private Double latitude, longitude;
    MapView mMapView;
    ResultItem hotelItem;
    View rootView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_hotel_detail, container, false);
            hotelItem = (ResultItem) getActivity().getIntent().getExtras().getSerializable("Center");
            mMapView = rootView.findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);

            new LocationTracker(getActivity()).setOnTrackingLocationListener(new LocationTracker.OnTrackingLocationListener() {
                @Override
                public void onGetLocation(LocationTracker tracker, FeaturedLocation location) {
                    new FeaturedLocation(location, getActivity()).setOnFeatureListener(new FeaturedLocation.OnFeatureListener() {
                        @Override
                        public void onStartPrepare() {
                        }

                        @Override
                        public void onPreparedFeature(FeaturedLocation location) {
                            Log.e("onPreparedFeature", "onNext: " + location.getAddress());
                            //HSH.showtoast(getActivity(), location.getLatitude());
                            String urlTopass = makeURL(location.getLatitude(), location.getLongitude()
                                    , Double.parseDouble(hotelItem.getLatitude())
                                    , Double.parseDouble(hotelItem.getLongitude()));

                            new connectAsyncTask(urlTopass).execute();

                            //latitude = Double.parseDouble(hotelItem.getLatitude());
                            //longitude = Double.parseDouble(hotelItem.getLongitude());

                            //showMarker(location);
                            getMap(location);
                            tracker.disconnect();
                        }

                        @Override
                        public void onLost(Exception e, FeaturedLocation location) {
                            Log.e(TAG, "onLost: " + e.getMessage());
                        }
                    });
                }

                @Override
                public void onGetLocationFailed(LocationTracker tracker, Exception e) {

                }
            }).connect();

            AdvertisementDetails();
        }
        return rootView;
    }

    public void AdvertisementDetails() {
        //imageLoader.displayImage(fFeed.getImgIds().split(",")[0], imgProfile, options);
        try {
            List<CenterDetailsModel> feed = new ArrayList<>();
            List<MoreInfoItem> result2 = HotelDetailsActivity.fFeed.getResult().getMoreInfo();

            String s = "";
            CenterDetailsModel item;
            for (int i = 0; i < result2.size(); i++) {
                try {
                    item = new CenterDetailsModel();
                    s = result2.get(i).getName() + " : " + (!result2.get(i).getDescription().equals("") ? result2.get(i).getDescription() : result2.get(i).getValue().get(0).getName() + " " + result2.get(i).getValue().get(0).getValue());
                    item.setTitle(s.trim());
                    feed.add(item);
                } catch (Exception e) {
                }
            }
            getProperty(getActivity(), rootView.findViewById(R.id.ll_baseProperty), feed);
            ((TextView) rootView.findViewById(R.id.txt_description)).setText(hotelItem.getDescription());
        } catch (Exception e) {
        }

        try {
            List<CenterDetailsModel> feed = new ArrayList<>();
            List<RulesItem> result2 = HotelDetailsActivity.fFeed.getResult().getRules();

            String s = "";
            CenterDetailsModel item;
            for (int i = 0; i < result2.size(); i++) {
                try {
                    item = new CenterDetailsModel();
                    s = result2.get(i).getName() + " : " + (!result2.get(i).getDescription().equals("") ? result2.get(i).getDescription() : result2.get(i).getValue().get(0).getName() + " " + result2.get(i).getValue().get(0).getValue());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        item.setTitle(Html.fromHtml(s.trim(), Html.FROM_HTML_MODE_LEGACY).toString().replace("<br>", "\n"));
                    } else {
                        item.setTitle(Html.fromHtml(s.trim()).toString().replace("<br>", "\n"));
                    }
                    feed.add(item);
                } catch (Exception e) {
                }
            }
            getProperty(getActivity(), rootView.findViewById(R.id.ll_rules), feed);
        } catch (Exception e) {
        }
    }

    private void getProperty(final Context _cn, final LinearLayout layout, final List<CenterDetailsModel> feed) {

        try {
            int scrollviewposition = 0;
            for (scrollviewposition = 0; scrollviewposition < feed.size(); scrollviewposition++) {

                @SuppressWarnings("static-access")
                LayoutInflater inflater = (LayoutInflater)
                        _cn.getSystemService(_cn.LAYOUT_INFLATER_SERVICE);
                View view1 = inflater.inflate(R.layout.item_center_details, null);

                final TextView txt1 = view1.findViewById(R.id.txt1);
                txt1.setTypeface(Application.font);
                String[] temp = feed.get(scrollviewposition).getTitle().split(":");
                Spannable spannable = new SpannableString(HSH.toPersianNumber(feed.get(scrollviewposition).getTitle()));
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#026d37")), temp[0].length() + 1, feed.get(scrollviewposition).getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                txt1.setText(spannable, TextView.BufferType.SPANNABLE);
                layout.addView(view1);
            }

        } catch (Exception e) {
        }
    }

    private void getMap(FeaturedLocation location) {
        switch (GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity())) {
            case ConnectionResult.SUCCESS:

                try {
                    if (!/*fFeed.getLatitude().trim()*/"12".equals("00")) {
                        mMapView.setVisibility(View.VISIBLE);
                        //latitude = Double.parseDouble(hotelItem.getLatitude());
                        //longitude = Double.parseDouble(hotelItem.getLongitude());
                        mMapView.onResume();
                        try {
                            MapsInitializer.initialize(getActivity());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mMapView.getMapAsync((GoogleMap googleMap) ->
                        {
                            try {
                                _googleMap = googleMap;
                                MarkerOptions marker = new MarkerOptions().position(
                                        new LatLng(location.getLatitude(), location.getLongitude()))
                                        .title("خودم").snippet(location.getAddress());

                                marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.city));
                                googleMap.addMarker(marker);
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                        .zoom(14).build();


                                MarkerOptions marker2 = new MarkerOptions().position(
                                        new LatLng(Double.parseDouble(hotelItem.getLatitude()), Double.parseDouble(hotelItem.getLongitude()))).title(hotelItem.getNameFa()).snippet(hotelItem.getCityNameFa());
                                marker2.icon(BitmapDescriptorFactory.fromResource(R.mipmap.city));
                                googleMap.addMarker(marker2);
                                googleMap.animateCamera(CameraUpdateFactory
                                        .newCameraPosition(cameraPosition));
                                        /*CameraPosition cameraPosition2 = new CameraPosition.Builder()
                                                .target(new LatLng(latitude, longitude)).zoom(15).build();*/

                                      /*  mMap.animateCamera(CameraUpdateFactory
                                                .newCameraPosition(cameraPosition2));*/
                                //setUpMapIfNeeded(); // For setting up the MapFragment

                            } catch (Exception e) {
                            }
                        });
                    }
                } catch (Exception e) {
                }
                break;

            case ConnectionResult.SERVICE_MISSING:
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                break;
            default:
        }
    }

    public String makeURL(double sourcelat, double sourcelog, double destlat,
                          double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString.append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString.append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&language=FA&alternatives=true");
        return urlString.toString();
    }

    public void drawPath(String result) {
        if (line != null) {
            _googleMap.clear();
        }
               /* mMap.addMarker(new MarkerOptions().position(
               new LatLng(Double.longBitsToDouble(preferences.getLong("Latitude", 0)),
               Double.longBitsToDouble(preferences.getLong("Longitude", 0)))).icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.ads)));
                mMap.addMarker(new MarkerOptions().position(new LatLng(35.755950, 50.955547)).icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.aboutus)));*/
        try {
            // Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes
                    .getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            float sum = 0;
            for (int z = 0; z < list.size() - 1; z++) {
                LatLng src = list.get(z);
                LatLng dest = list.get(z + 1);
                line = _googleMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(src.latitude, src.longitude),
                                new LatLng(dest.latitude, dest.longitude))
                        .width(5).color(Color.BLUE).geodesic(true));
                float[] results = new float[1];
                Location.distanceBetween(src.latitude, src.longitude,
                        dest.latitude, dest.longitude,
                        results);
                sum += (results[0]);
            }
           /* txt_address.setText(Html.fromHtml(txt_address.getText().toString()) +
                    "\n" +
                    "فاصله : " + String.valueOf((float) sum / 1000).substring(0, 4) + " کیلومتر");*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    private class connectAsyncTask extends AsyncTask<Void, Void, String> {

        String url;

        connectAsyncTask(String urlPass) {
            url = urlPass;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            JSONParser jParser = new JSONParser();
            String json = jParser.getJSONFromUrl(url);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                drawPath(result);
            }
        }
    }

    public class JSONParser {

        String json = "";

        // constructor
        public JSONParser() {
        }

        public String getJSONFromUrl(String url) {

            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();
                json = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;

        }
    }
}

package ir.touristland.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import ir.touristland.Activities.Kishgardi.KishgardiCenterDetailsActivity;
import ir.touristland.Classes.HSH;
import ir.touristland.Models.PlaceFeedItem;
import ir.touristland.R;

public class PlacesActivity extends BaseActivity {


    private GoogleMap mMap;
    private RelativeLayout rl_details;
    private List<PlaceFeedItem> feed = new ArrayList<>();
    private ProgressBar p;
    private MapView mMapView;
    private FrameLayout fl_details;
    private String Id, Title, Latitude, Longitude;
    private Toolbar toolbar;
    private Button btn_details;
    private TextView txt_details;


    private void DeclareElements() {
        rl_details = findViewById(R.id.rl_details);
        fl_details = findViewById(R.id.fl_details);
        btn_details = findViewById(R.id.btn_details);
        txt_details = findViewById(R.id.txt_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        p = findViewById(R.id.progressBar1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        DeclareElements();
        mMapView = findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(PlacesActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(PlacesActivity.this)) {
            case ConnectionResult.SUCCESS:
                mMapView.getMapAsync(googleMap -> {
                    mMap = googleMap;
                    //new GetCoordinateCentersAsynkTask(PlacesActivity.this, p, mMap).getData();
                    try {
                        String s = "[{\"lat\":\"30.5463\",\"lon\":\"51.12456\"},{\"lat\":\"30.2563541\",\"lon\":\"50.365245\"},{\"lat\":\"30.65325\",\"lon\":\"51.12325\"},{\"lat\":\"30.5465\",\"lon\":\"50.546\"},{\"lat\":\"30.6455\",\"lon\":\"50.5464\"}]";
                        JSONArray result = new JSONArray(s);
                        try {
                            for (int i = 0; i < result.length(); i++) {

                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.parseDouble(result.getJSONObject(i).getString("lat"))
                                                , Double.parseDouble(result.getJSONObject(i).getString("lon"))))
                                        .anchor(0.5f, 0.5f)
                                        .title("مرکز خدماتی"));
                                //.snippet(result.getJSONObject(i).getString("id"))
                                // .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_placeholder)))
                            }

                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(new LatLng(Double.parseDouble(result.getJSONObject(0).getString("lat"))
                                            , Double.parseDouble(result.getJSONObject(0).getString("lon"))))
                                    .zoom(11).build();
                            mMap.animateCamera(CameraUpdateFactory
                                    .newCameraPosition(cameraPosition));
                        } catch (Exception e) {
                        }
                        p.setVisibility(View.GONE);
                    } catch (Exception e) {
                    }
                    googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                        @Override
                        public View getInfoWindow(Marker arg0) {
                            return null;
                        }

                        @Override
                        public View getInfoContents(final Marker arg0) {
                            View v = getLayoutInflater().inflate(R.layout.info_window_item, null);
                            TextView txt_title = v.findViewById(R.id.txt_title);
                            txt_title.setText(arg0.getTitle());
                            return v;
                        }
                    });

                    try {
                        googleMap.setOnMarkerClickListener(arg0 -> {
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(arg0.getPosition());
                            googleMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                            fl_details.setVisibility(View.VISIBLE);
                            Id = (arg0.getSnippet());
                            Title = (arg0.getTitle());
                            txt_details.setText(arg0.getTitle());
                            Latitude = (String.valueOf(arg0.getPosition().latitude));
                            Longitude = (String.valueOf(arg0.getPosition().longitude));
                            arg0.showInfoWindow();
                            return true;
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                break;
            case ConnectionResult.SERVICE_MISSING:
                //rl_details.setVisibility(View.GONE);
                HSH.showtoast(PlacesActivity.this, "برنامه گوگل پلی سرویس را نصب نمایید");
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                HSH.showtoast(PlacesActivity.this, "برنامه گوگل پلی سرویس را بروز رسانی نمایید");
                break;
        }
        /////////////////////MAP////////////////////////
        btn_details.setOnClickListener(v ->
        {
            PlaceFeedItem feedItem = new PlaceFeedItem();
            feedItem.setId(Id);
            feedItem.setName(Title);
            feedItem.setLatitude(Latitude);
            feedItem.setLongitude(Longitude);
            final Bundle bundle = new Bundle();
            bundle.putSerializable("feed", feedItem);
            final Intent intent = new Intent(PlacesActivity.this,
                    KishgardiCenterDetailsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });

    }

    @Override
    public void onResume() {
        //setUpMapIfNeeded();
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

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}


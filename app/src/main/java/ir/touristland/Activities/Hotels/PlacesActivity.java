package ir.touristland.Activities.Hotels;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import ir.touristland.Activities.BaseActivity;
import ir.touristland.Classes.HSH;
import ir.touristland.Classes.PermissionHandler;
import ir.touristland.Models.HotelList.ResultItem;
import ir.touristland.R;
import ir.touristland.location.FeaturedLocation;
import ir.touristland.location.LocationTracker;

import static ir.touristland.location.FeaturedLocation.TAG;

public class PlacesActivity extends BaseActivity {

    //ImageButton imageBack;
    Button btn_details;
    TextView txt_details;
    private GoogleMap _mMap;
    private RelativeLayout rl_details;
    private MapView mMapView;
    private FrameLayout fl_details;
    private TextView title;
    private ResultItem resultItem;
    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        new PermissionHandler().checkPermission(PlacesActivity.this, permissions, new PermissionHandler.OnPermissionResponse() {
            @Override
            public void onPermissionGranted() {
                mMapView = findViewById(R.id.map);
                mMapView.onCreate(savedInstanceState);
                mMapView.onResume();// needed to get the map to display immediately
                try {
                    MapsInitializer.initialize(PlacesActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                DeclareElements();

                switch (GooglePlayServicesUtil
                        .isGooglePlayServicesAvailable(PlacesActivity.this)) {
                    case ConnectionResult.SERVICE_MISSING:
                        rl_details.setVisibility(View.GONE);
                        //HSH.Update_googleplayservice(PlacesActivity.this, true);
                        break;
                    case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                        rl_details.setVisibility(View.GONE);
                        //HSH.Update_googleplayservice(PlacesActivity.this, true);
                        break;
                    case ConnectionResult.SUCCESS:
                        /////////////////////MAP////////////////////////

                        mMapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                _mMap = googleMap;

                                List<ResultItem> list = (List<ResultItem>) getIntent().getExtras().getSerializable("Places");
                                for (int i = 0; i < list.size(); i++) {

                                    _mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Double.parseDouble(list.get(i).getLatitude())
                                                    , Double.parseDouble(list.get(i).getLongitude())))
                                            .anchor(0.5f, 0.5f)
                                            .title(list.get(i).getNameFa())
                                            .snippet(i + "")
                                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.hotel_loc)));

                                }


                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(new LatLng(Double.parseDouble(list.get(0).getLatitude())
                                                , Double.parseDouble(list.get(0).getLongitude())))
                                        .zoom(11).build();
                                _mMap.animateCamera(CameraUpdateFactory
                                        .newCameraPosition(cameraPosition));

                                btn_details.setOnClickListener(v -> {
                                    final Bundle bundle = new Bundle();
                                    bundle.putSerializable("Center", resultItem);
                                    final Intent intent = new Intent(PlacesActivity.this,
                                            HotelDetailsActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                });


                                _mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                                    @Override
                                    public View getInfoWindow(Marker arg0) {
                                        return null;
                                    }

                                    @Override
                                    public View getInfoContents(final Marker arg0) {
                                        View v = getLayoutInflater().inflate(R.layout.info_window_item, null);
                                        TextView txt_title = v.findViewById(R.id.txt_title);
                                        txt_title.setText(arg0.getTitle() + "\n" + resultItem.getHotelDiscountPrice());
                                        return v;
                                    }
                                });

                                try {
                                    _mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(Marker arg0) {
                                            resultItem = list.get(Integer.parseInt(arg0.getSnippet()));
                                            MarkerOptions markerOptions = new MarkerOptions();
                                            markerOptions.position(arg0.getPosition());
                                            _mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));

                                            fl_details.setVisibility(View.VISIBLE);
                                            txt_details.setText(arg0.getTitle());
                                            arg0.showInfoWindow();
                                            return true;
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                }
            }

            @Override
            public void onPermissionDenied() {
                HSH.showtoast(PlacesActivity.this, "برای ورود به اپلیکیشن دسترسی ها را صادر نمایید.");
                finish();
            }
        });

        //title.setText(getIntent().getExtras().getString("title"));

        /*imageBack.setVisibility(View.VISIBLE);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

    }


    private void DeclareElements() {
        rl_details = findViewById(R.id.rl_details);
        fl_details = findViewById(R.id.fl_details);
        title = findViewById(R.id.title_text);
        // imageBack = findViewById(R.id.imageButton);
        btn_details = findViewById(R.id.btn_details);
        txt_details = findViewById(R.id.txt_details);
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


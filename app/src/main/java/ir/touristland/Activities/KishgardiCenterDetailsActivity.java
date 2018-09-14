package ir.touristland.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.util.ArrayList;
import java.util.List;

import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Models.CenterDetailsModel;
import ir.touristland.Models.CenterItem;
import ir.touristland.R;
import ir.touristland.SliderTypes.BaseSliderView;
import ir.touristland.SliderTypes.SliderLayout;
import ir.touristland.SliderTypes.TextSliderView;


public class KishgardiCenterDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static Double latitude, longitude;
    MapView mMapView;
    private CollapsingToolbarLayout i;
    private AppBarLayout appBar;
    private LinearLayout ll_advertisdetails;
    private SliderLayout pager;
    private Button btn_contactWays;
    private TextView toolbar_title, txt_title;
    private CenterItem fFeed;
    private ImageButton back;

    private void DeclareElements() {
        ll_advertisdetails = findViewById(R.id.ll_advertisdetails);
        txt_title = findViewById(R.id.txt_title);
        txt_title.setTypeface(Application.fontBold);

        btn_contactWays = findViewById(R.id.btn_contactWays);
        back = findViewById(R.id.img_back);
        btn_contactWays.setOnClickListener(this);
        back.setOnClickListener(this);

        appBar = findViewById(R.id.app_bar);
        toolbar_title = findViewById(R.id.toolbar_title);
        pager = findViewById(R.id.pager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_center_details);
            fFeed = (CenterItem) getIntent().getExtras().getSerializable("feedItem");
            DeclareElements();

            mMapView = findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);

            float heightDp = (float) (getResources().getDisplayMetrics().heightPixels / 2.5);
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBar.getLayoutParams();
            lp.height = (int) heightDp;
            AdvertisementDetails();

            i = findViewById(R.id.toolbar_layout);
            i.setTitle("");
        } catch (Exception e) {
        }
    }

    private void getMap() {
        switch (GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(KishgardiCenterDetailsActivity.this)) {
            case ConnectionResult.SUCCESS:

                try {
                    if (!/*fFeed.getLatitude().trim()*/"12".equals("00")) {
                        mMapView.setVisibility(View.VISIBLE);
                        latitude = Double.parseDouble(/*fFeed.getLatitude()*/"35.6865");
                        longitude = Double.parseDouble(/*fFeed.getLongitude()*/"51.5145");
                        mMapView.onResume();
                        try {
                            MapsInitializer.initialize(KishgardiCenterDetailsActivity.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mMapView.getMapAsync((GoogleMap googleMap) ->
                        {
                            try {
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(new LatLng(latitude, longitude))
                                        .zoom(13).build();
                                MarkerOptions marker2 = new MarkerOptions().position(
                                        new LatLng(latitude, longitude)).title(fFeed.getName()).snippet(fFeed.getTelCenter());
                                marker2.icon(BitmapDescriptorFactory.fromResource(R.mipmap.city));
                                googleMap.addMarker(marker2);
                                googleMap.animateCamera(CameraUpdateFactory
                                        .newCameraPosition(cameraPosition));

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

    public void AdvertisementDetails() {
        ll_advertisdetails.setVisibility(View.VISIBLE);
        getMap();
        toolbar_title.setText(fFeed.getName());
        //imageLoader.displayImage(fFeed.getImgIds().split(",")[0], imgProfile, options);
        try {
            List<CenterDetailsModel> feed = new ArrayList<>();
            List<String> result2 = fFeed.getListAttributs();

            String s = "";
            CenterDetailsModel item;
            for (int i = 0; i < result2.size(); i++) {
                item = new CenterDetailsModel();
                s = result2.get(i);
                item.setTitle(s.trim());
                feed.add(item);
            }
            getProperty(KishgardiCenterDetailsActivity.this, findViewById(R.id.ll_baseProperty), feed);
            /////////////////////////////////////////////////////////////////////////
            String[] temp = fFeed.getImgIds().split(",");
            if (temp.length > 0 && !temp[0].equals("null") && !temp[0].equals("")) {
            } else {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBar.getLayoutParams();
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                pager.setVisibility(View.GONE);
                appBar.setLayoutParams(lp);
            }
            for (int i = 0; i < fFeed.getImgIds().split(",").length; i++) {
                TextSliderView textSliderView = new TextSliderView(KishgardiCenterDetailsActivity.this);
                textSliderView
                        .image(getString(R.string.image) + fFeed.getImgIds().split(",")[i] + ".jpg")
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);
                pager.addSlider(textSliderView);
                textSliderView.setOnSliderClickListener((BaseSliderView slider) ->
                {
                    final Bundle bundle = new Bundle();
                    Intent in = new Intent(KishgardiCenterDetailsActivity.this, ViewPagerActivity.class);
                    //bundle.putSerializable("feed", item);
                    in.putExtras(bundle);
                    startActivity(in);
                });
            }
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

                txt1.setOnClickListener(v ->
                {
                    try {
                        if (txt1.getText().toString().equals(fFeed.getListAttributs().get(0))) {
                            try {
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:" + Uri.encode(fFeed.getListAttributs().get(0).split(":")[1])));
                                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(callIntent);
                            } catch (Exception e) {
                            }
                        } else {
                            try {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fFeed.getListAttributs().get(1).split(": ")[1]));
                                startActivity(browserIntent);
                            } catch (Exception e1) {
                            }
                        }
                    } catch (Exception e) {
                    }
                });
            }

        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;

            case R.id.btn_contactWays:
                Uri number = Uri.parse("tel:" + fFeed.getModirTel());
                final Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
                break;

        }
    }
}


package ir.touristland.Activities.Kishgardi;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.touristland.Activities.BaseActivity;
import ir.touristland.Activities.ViewPagerActivity;
import ir.touristland.Adapters.RoomsAdapter;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Classes.NetworkUtils;
import ir.touristland.Interfaces.ApiClient;
import ir.touristland.Interfaces.ApiInterface;
import ir.touristland.Models.CenterDetailsModel;
import ir.touristland.Models.CenterItem;
import ir.touristland.Models.HotelPossibilitieItem;
import ir.touristland.Models.RoomItem;
import ir.touristland.Moudle.TagLayoutImageView;
import ir.touristland.R;
import ir.touristland.SliderTypes.BaseSliderView;
import ir.touristland.SliderTypes.SliderLayout;
import ir.touristland.SliderTypes.TextSliderView;
import retrofit2.Call;
import retrofit2.Callback;


public class KishgardiCenterDetailsActivity extends BaseActivity implements View.OnClickListener {

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
    private RecyclerView rvRooms;
    private RoomsAdapter adapter;
    private List<RoomItem> RoomsItems = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private TagLayoutImageView ti;

    public static int getPixelValue(Context context, int dimenId) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId,
                resources.getDisplayMetrics()
        );
    }

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

       /* adapter = new RoomsAdapter(this, RoomsItems, room -> {


        });*/
        rvRooms = findViewById(R.id.rv_rooms);
        rvRooms.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(KishgardiCenterDetailsActivity.this);
        rvRooms.setLayoutManager(layoutManager);
        rvRooms.setAdapter(adapter);

        ti = findViewById(R.id.tl);
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
            GetRooms();
            GetHotelPossibilities();

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
                        .image(getString(R.string.image) + fFeed.getImgIds().split(",")[i])
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);
                pager.addSlider(textSliderView);
                textSliderView.setOnSliderClickListener((BaseSliderView slider) ->
                {
                    final Bundle bundle = new Bundle();
                    Intent in = new Intent(KishgardiCenterDetailsActivity.this, ViewPagerActivity.class);
                    bundle.putString("ImgIds", fFeed.getImgIds());
                    in.putExtras(bundle);
                    startActivity(in);
                });
            }
        } catch (Exception e) {
        }
    }

    public void GetRooms() {
        /*Map<String, String> params = new HashMap<>();
        params.put("CenterId", "" + fFeed.getId());
        Call<List<RoomItem>> call =
                ApiClient.getClient2().create(ApiInterface.class).GetRooms(params);
        call.enqueue(new Callback<List<RoomItem>>() {
            @Override
            public void onResponse(Call<List<RoomItem>> call, retrofit2.Response<List<RoomItem>> response) {
                try {
                    if (response.code() == 200) {
                        RoomsItems.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    } else
                        HSH.showtoast(KishgardiCenterDetailsActivity.this, response.errorBody().string());

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<List<RoomItem>> call, Throwable t) {
                if (NetworkUtils.getConnectivity(KishgardiCenterDetailsActivity.this) != false)
                    GetRooms();
                else {

                }
            }
        });*/
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

    public void GetHotelPossibilities() {
        Map<String, String> params = new HashMap<>();
        params.put("CenterId", "" + fFeed.getId());
        Call<List<HotelPossibilitieItem>> call =
                ApiClient.getClient2().create(ApiInterface.class).GetHotelPossibilities(params);
        call.enqueue(new Callback<List<HotelPossibilitieItem>>() {
            @Override
            public void onResponse(Call<List<HotelPossibilitieItem>> call, retrofit2.Response<List<HotelPossibilitieItem>> response) {
                try {
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().size(); i++)
                            TagLayout(response.body().get(i), i);

                    } else
                        HSH.showtoast(KishgardiCenterDetailsActivity.this, response.errorBody().string());

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<List<HotelPossibilitieItem>> call, Throwable t) {
                if (NetworkUtils.getConnectivity(KishgardiCenterDetailsActivity.this) != false)
                    GetRooms();
                else {

                }
            }
        });
    }

    private void TagLayout(final HotelPossibilitieItem item, final int j) {
        new Handler().postDelayed(() -> {
            try {
                final TextView i = new TextView(KishgardiCenterDetailsActivity.this);
                i.setText(item.getPossibilitiesName());
                i.setTextSize(13);
                i.setTypeface(Application.font);
                i.setTextColor(Color.parseColor("#34A853"));
                i.setBackgroundResource(R.drawable.press_button_stroke);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(4, 0, 4, 0);
                i.setLayoutParams(params);
                i.setPadding(8, 0, 8, 0);
                ti.addTagView(i);
            } catch (Exception e) {
            }
        }, 100 * j);
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


package ir.touristland.Activities.Hotels;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ir.touristland.Activities.BaseActivity;
import ir.touristland.Activities.ViewPagerActivity;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Classes.NetworkUtils;
import ir.touristland.Fragments.HotelDetailFragment;
import ir.touristland.Fragments.HotelPossibilitiesFragment;
import ir.touristland.Fragments.HotelRoomsFragment;
import ir.touristland.Interfaces.ApiInterface;
import ir.touristland.Models.HotelDetails.HotelDetail;
import ir.touristland.Models.HotelList.ResultItem;
import ir.touristland.R;
import ir.touristland.SliderTypes.BaseSliderView;
import ir.touristland.SliderTypes.SliderLayout;
import ir.touristland.SliderTypes.TextSliderView;
import ir.touristland.databinding.ActivityHotelDetailsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class HotelDetailsActivity extends BaseActivity {


    public static TabLayout tabHost;
    public static ViewPager pager;
    //public static TextView txtPrice;
    //public static CardView sumPrice;
    public static HotelDetail fFeed;
    CollapsingToolbarLayout i;
    @Inject
    Retrofit retrofit;
    private ResultItem hotelItem;
    private AppBarLayout appBar;
    private SliderLayout pager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_hotel_details);
        ActivityHotelDetailsBinding binding = DataBindingUtil.setContentView(HotelDetailsActivity.this, R.layout.activity_hotel_details);
        Application.getComponent().Inject(this);
        hotelItem = (ResultItem) getIntent().getExtras().getSerializable("Center");
        binding.setHotelItem(hotelItem);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        pager1 = findViewById(R.id.pager1);
        //txtPrice = findViewById(R.id.txt_price);
        //sumPrice = findViewById(R.id.sum_price);
        appBar = findViewById(R.id.app_bar);
        float heightDp = (float) (getResources().getDisplayMetrics().heightPixels / 2.5);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBar.getLayoutParams();
        lp.height = (int) heightDp;

        tabHost = findViewById(R.id.materialTabHost);
        pager = findViewById(R.id.pager);
        i = findViewById(R.id.toolbar_layout);
        i.setTitle("");
        tabHost.setupWithViewPager(pager);
        findViewById(R.id.txt_map).setOnClickListener(view ->
        {
            Intent intent;
            intent = new Intent(HotelDetailsActivity.this, HotelLocationActivity.class);
                    /*ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) mContext, Holder.imgPost, "transitionn1");*/
            intent.putExtra("Center", hotelItem);
            startActivity(intent);
        });

        GetHotelDetails();
    }

    public void GetHotelDetails() {
        Map<String, String> params = new HashMap<>();
        params.put("city", "" + hotelItem.getCityName());
        params.put("id", "" + hotelItem.getId());
        Call<HotelDetail> call =
                retrofit.create(ApiInterface.class).GetHotelLoad2(params);
        call.enqueue(new Callback<HotelDetail>() {
            @Override
            public void onResponse(Call<HotelDetail> call, retrofit2.Response<HotelDetail> response) {
                try {
                    if (response.isSuccessful()) {
                        appBar.setVisibility(View.VISIBLE);
                        fFeed = response.body();
                        setupViewPager(pager);
                        pager.setCurrentItem(2);
                        HSH.setTypeFace(tabHost, Application.font);
                        tabHost.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                tabHost.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //tab.getCustomView().setBackgroundColor(Color.parseColor("#ededed"));
                                    }
                                });
                            }
                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {
                            }
                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {
                            }
                        });
                        TextSliderView textSliderView;
                        for (int i = 0; i < /*fFeed.getResult().getImages().size()*/5; i++) {
                            textSliderView = new TextSliderView(HotelDetailsActivity.this);
                            textSliderView
                                    .image(getString(R.string.image) + fFeed.getResult().getImages().get(i))
                                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
                            pager1.addSlider(textSliderView);
                            textSliderView.setOnSliderClickListener((BaseSliderView slider) ->
                            {
                                String s = "";
                                for (int j = 0; j < fFeed.getResult().getImages().size(); j++)
                                    s += getString(R.string.image) + fFeed.getResult().getImages().get(j) + ",";
                                final Bundle bundle = new Bundle();
                                Intent in = new Intent(HotelDetailsActivity.this, ViewPagerActivity.class);
                                bundle.putString("ImgIds", s);
                                in.putExtras(bundle);
                                startActivity(in);
                            });
                        }
                    }
                    findViewById(R.id.pb).setVisibility(View.GONE);
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<HotelDetail> call, Throwable t) {
                if (NetworkUtils.getConnectivity(HotelDetailsActivity.this) != false) {
                    GetHotelDetails();
                } else {

                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        try {
            ViewPagerAdapter adapter = null;
            if (adapter == null || adapter.mFragmentList.size() == 0) {
                adapter = new ViewPagerAdapter(getSupportFragmentManager());
                adapter.addFragment(new HotelPossibilitiesFragment(), "امکانات هتل");
                adapter.addFragment(new HotelDetailFragment(), "اطلاعات هتل");
                adapter.addFragment(new HotelRoomsFragment(), "لیست اتاق ها");
                viewPager.setAdapter(adapter);
            }
        } catch (Exception e) {
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private Fragment mCurrentFragment;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public Fragment getCurrentFragment() {
            return mCurrentFragment;
        }

        //...
        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            if (!mFragmentList.get(position).isAdded())
                return mFragmentList.get(position);
            else
                return null;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            if (object != null) {
                return ((Fragment) object).getView() == view;
            } else {
                return false;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            //pager.setObjectForPosition(obj, position);
            return obj;
        }

        @Override
        public int getCount() {
            return 3;
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}


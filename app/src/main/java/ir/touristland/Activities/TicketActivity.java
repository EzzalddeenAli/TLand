package ir.touristland.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Fragments.SearchBusFragment;
import ir.touristland.Fragments.SearchFlightFragment;
import ir.touristland.Fragments.SearchHotelFragment;
import ir.touristland.Fragments.SearchTrainFragment;
import ir.touristland.R;

public class TicketActivity extends BaseActivity {

    public static TabLayout tabHost;
    private Toolbar toolbar;
    private ViewPager pager;

    private void DeclareElements() {
        tabHost = this.findViewById(R.id.materialTabHost);
        pager = this.findViewById(R.id.pager);
        setupViewPager(pager);
        tabHost.setupWithViewPager(pager);
        pager.setCurrentItem(2);
        HSH.setTypeFace(tabHost, Application.font);
        // HSH.setTypeFace(toolbar, Application.font);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        DeclareElements();

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (null != getIntent().getExtras())
            adapter.addFragment(new SearchHotelFragment(), "رزرو هتل");
        else {
            adapter.addFragment(new SearchBusFragment(), "اتوبوس");
            adapter.addFragment(new SearchTrainFragment(), "قطار");
            adapter.addFragment(new SearchFlightFragment(), "پرواز داخلی");
        }
        viewPager.setAdapter(adapter);
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
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
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

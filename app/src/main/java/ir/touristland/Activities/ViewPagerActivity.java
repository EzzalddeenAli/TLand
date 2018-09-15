/*
Copyright 2014 David Morrissey

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package ir.touristland.Activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

import ir.touristland.Classes.BaseFragmentActivity;
import ir.touristland.Classes.HSH;
import ir.touristland.Classes.PermissionHandler;
import ir.touristland.Fragments.ViewPagerFragment;
import ir.touristland.R;

public class ViewPagerActivity extends BaseFragmentActivity {

    public static int number = 0;
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private ViewPager pager;
    //private FlightItem feedgallery = new FlightItem();
    private String ImgIds;
    private TextView RgIndicator;
    private RadioGroup.LayoutParams rprms;
    private String jpg = ".jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);
        RgIndicator = findViewById(R.id.indicator);
        PagerAdapter pagerAdapter = null;
        try {
            /*feedgallery = (FlightItem) getIntent().getExtras().getSerializable("feed");
            if(null == feedgallery)*/
            ImgIds = getIntent().getExtras().getString("ImgIds");
            RgIndicator.setText(1 + " / " + ImgIds.split(",").length);
        } catch (Exception e) {
        }

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

                RgIndicator.setText(position + 1 + " / " + ImgIds.split(",").length);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        try {
            pager.setAdapter(pagerAdapter);
        } catch (Exception e) {
            HSH.showtoast(ViewPagerActivity.this, e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*if (pager.getCurrentItem() == 0) {
            //RgIndicator.check(0);
            super.onBackPressed();
        } else {
            //RgIndicator.check(page.getCurrentItem());
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public void shareimage(final String shareimg) {
        new PermissionHandler().checkPermission(ViewPagerActivity.this, permissions, new PermissionHandler.OnPermissionResponse() {
            @Override
            public void onPermissionGranted() {
                File dir = new File(Environment.getExternalStoragePublicDirectory("Kishtravel/Images").getPath());

                if (!dir.exists())
                    dir.mkdirs();

                File file = new File(Environment.getExternalStoragePublicDirectory("Kishtravel/Images").getPath() + "/" + String.valueOf(System.currentTimeMillis() / 1000) + ".jpg");

                try {

                    FileOutputStream outStream = new FileOutputStream(file);
                    ViewPagerFragment.myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                    HSH.showtoast(getApplicationContext(), "در حافظه داخلی (Sdcard/Kishtravel/Images)ذخیره گردید.");
                } catch (Exception e) {
                }

                if (!shareimg.equals("")) {
                    try {
                        if (file.exists()) {
                            Intent share = new Intent(Intent.ACTION_SEND);
                            share.setType("image/*");
                            Uri uri = Uri.fromFile(file);
                            share.putExtra(Intent.EXTRA_STREAM, uri);
                            startActivity(Intent.createChooser(share, "Share Image via"));
                        } else
                            HSH.showtoast(getApplicationContext(), "تصویری موجود نیست.");
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onPermissionDenied() {
                HSH.showtoast(ViewPagerActivity.this, "برای ذخیره و به اشتراک گذاری عکس دسترسی را صادر نمایید.");
            }
        });
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_save:
                shareimage("");
                break;
            case R.id.img_share:
                shareimage("share");
                break;
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ViewPagerFragment fragment = new ViewPagerFragment();
            if (!ImgIds.contains("https://"))
                fragment.setAsset(getString(R.string.image) +
                        ImgIds.split(",")[position]);
            else
                fragment.setAsset(ImgIds.split(",")[position]);
            return fragment;
        }

        @Override
        public int getCount() {
            number = ImgIds.split(",").length;
            return number;
        }
    }
}

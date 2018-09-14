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

package ir.touristland.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import javax.inject.Inject;

import ir.touristland.Activities.ViewPagerActivity;
import ir.touristland.Application;
import ir.touristland.DI.MainComponent;
import ir.touristland.Models.FlightItem;
import ir.touristland.R;


public class SlideShowFragment extends Fragment {

    private static final String BUNDLE_ASSET = "asset";
    @Inject
    ImageLoader imageLoader;
    @Inject
    DisplayImageOptions options;
    private String asset;
    private FlightItem item;

    public SlideShowFragment() {
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public void setItem(FlightItem item) {
        this.item = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_slideshow, container, false);
        Application.getComponent2().Inject(this);
        if (savedInstanceState != null) {
            if (asset == null && savedInstanceState.containsKey(BUNDLE_ASSET)) {
                asset = savedInstanceState.getString(BUNDLE_ASSET);
            }
        }
        if (asset != null) {
            MainComponent component = Application.get((AppCompatActivity) getActivity()).getComponent();
            final ImageView imageView = rootView.findViewById(R.id.imgView);
            final ProgressBar p = rootView.findViewById(R.id.PrgrsBar);

            imageView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Bundle bundle = new Bundle();
                            Intent i = new Intent(getActivity(), ViewPagerActivity.class);
                            bundle.putSerializable("feed", item);
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    }

            );
            imageLoader.displayImage(asset, imageView, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    p.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    p.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    p.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        }

        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        View rootView = getView();
        if (rootView != null) {
            outState.putString(BUNDLE_ASSET, asset);
        }
    }
}

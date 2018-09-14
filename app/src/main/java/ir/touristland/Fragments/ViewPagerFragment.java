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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;

import javax.inject.Inject;

import ir.touristland.Application;
import ir.touristland.R;

public class ViewPagerFragment extends Fragment {

    private static final String BUNDLE_ASSET = "asset";
    public static SubsamplingScaleImageView imageView;
    public static Bitmap myBitmap;
    @Inject
    ImageLoader loader;
    @Inject
    DisplayImageOptions option;
    ImageView img;
    private String asset;

    public void setAsset(String asset) {
        this.asset = asset;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pager, container, false);
        Application.getComponent().Inject(this);
        if (savedInstanceState != null) {
            if (asset == null && savedInstanceState.containsKey(BUNDLE_ASSET)) {
                asset = savedInstanceState.getString(BUNDLE_ASSET);
            }
        }
        try {
            if (asset != null) {
                imageView = rootView.findViewById(R.id.imageView);
                imageView.setDoubleTapZoomScale(25);
                imageView.setMaxScale(7);
                File file = ImageLoader.getInstance().getDiskCache().get(asset);
                myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                try {
                    imageView.setImage(ImageSource.bitmap(myBitmap));
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            img = new ImageView(getActivity());
            loader.displayImage("", img, option, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    imageView.setImage(ImageSource.bitmap(loadedImage));
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

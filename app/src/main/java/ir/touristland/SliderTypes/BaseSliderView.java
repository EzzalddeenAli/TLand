package ir.touristland.SliderTypes;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;

import javax.inject.Inject;

import ir.touristland.Application;
import ir.touristland.R;


/**
 * When you want to make your own slider view, you must extends from this class.
 * BaseSliderView provides some useful methods.
 * if you want to show progressbar, you just need to set a progressbar id as @+id/loading_bar.
 */
public abstract class BaseSliderView {

    protected Context mContext;
    protected OnSliderClickListener mOnSliderClickListener;
    @Inject
    ImageLoader imageLoader;
    private Bundle mBundle;
    /**
     * Error place holder image.
     */
    private int mErrorPlaceHolderRes;
    /**
     * Empty imageView placeholder.
     */
    private int mEmptyPlaceHolderRes;
    private String mUrl;
    private File mFile;
    private int mRes;
    private boolean mErrorDisappear;

    private ImageLoadListener mLoadListener;

    private String mDescription;

    //private Picasso mPicasso;

    /**
     * Scale type of the image.
     */
    private ScaleType mScaleType = ScaleType.Fit;

    protected BaseSliderView(Context context) {
        mContext = context;
        Application.getComponent().Inject(this);
    }

    /**
     * the placeholder image when loading image from url or file.
     *
     * @param resId Image resource id
     * @return
     */
    public BaseSliderView empty(int resId) {
        mEmptyPlaceHolderRes = resId;
        return this;
    }

    /**
     * determine whether remove the image which failed to download or load from file
     *
     * @param disappear
     * @return
     */
    public BaseSliderView errorDisappear(boolean disappear) {
        mErrorDisappear = disappear;
        return this;
    }

    /**
     * if you set errorDisappear false, this will set a error placeholder image.
     *
     * @param resId image resource id
     * @return
     */
    public BaseSliderView error(int resId) {
        mErrorPlaceHolderRes = resId;
        return this;
    }

    /**
     * the description of a slider image.
     *
     * @param description
     * @return
     */
    public BaseSliderView description(String description) {
        mDescription = description;
        return this;
    }

    /**
     * set a url as a image that preparing to load
     *
     * @param url
     * @return
     */
    public BaseSliderView image(String url) {
        if (mFile != null || mRes != 0) {
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mUrl = url;
        return this;
    }

    /**
     * set a file as a image that will to load
     *
     * @param file
     * @return
     */
    public BaseSliderView image(File file) {
        if (mUrl != null || mRes != 0) {
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mFile = file;
        return this;
    }

    public BaseSliderView image(int res) {
        if (mUrl != null || mFile != null) {
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mRes = res;
        return this;
    }

    /**
     * lets users add a bundle of additional information
     *
     * @param bundle
     * @return
     */
    public BaseSliderView bundle(Bundle bundle) {
        mBundle = bundle;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public boolean isErrorDisappear() {
        return mErrorDisappear;
    }

    public int getEmpty() {
        return mEmptyPlaceHolderRes;
    }

    public int getError() {
        return mErrorPlaceHolderRes;
    }

    public String getDescription() {
        return mDescription;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * set a slider image click listener
     *
     * @param l
     * @return
     */
    public BaseSliderView setOnSliderClickListener(OnSliderClickListener l) {
        mOnSliderClickListener = l;
        return this;
    }

    /**
     * When you want to implement your own slider view, please call this method in the end in `getView()` method
     *
     * @param v               the whole view
     * @param targetImageView where to place image
     */
    protected void bindEventAndShow(final View v, ImageView targetImageView) {
        final BaseSliderView me = this;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSliderClickListener != null) {
                    mOnSliderClickListener.onSliderClick(me);
                }
            }
        });

        if (targetImageView == null)
            return;

        if (mLoadListener != null) {
            mLoadListener.onStart(me);
        }

        //Picasso p = (mPicasso != null) ? mPicasso : Picasso.with(mContext);
        //RequestCreator rq = null;
        if (mUrl != null) {
            imageLoader.displayImage(mUrl, targetImageView, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    if (mLoadListener != null) {
                        mLoadListener.onEnd(false, me);
                    }
                    if (v.findViewById(R.id.loading_bar) != null) {
                        v.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (v.findViewById(R.id.loading_bar) != null) {
                        v.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        } else {
            return;
        }

        /*if(rq == null){
            return;
        }*/

        /*if(getEmpty() != 0){
            rq.placeholder(getEmpty());
        }

        if(getError() != 0){
            rq.error(getError());
        }*/

        /*switch (mScaleType){
            case Fit:
                rq.fit();
                break;
            case CenterCrop:
                rq.fit().centerCrop();
                break;
            case CenterInside:
                rq.fit().centerInside();
                break;
        }*/

        /*rq.into(targetImageView,new Callback() {
            @Override
            public void onSuccess() {
                if(v.findViewById(R.id.loading_bar) != null){
                    v.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onError() {
                if(mLoadListener != null){
                    mLoadListener.onEnd(false,me);
                }
                if(v.findViewById(R.id.loading_bar) != null){
                    v.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
                }
            }
        });*/
    }

    public ScaleType getScaleType() {
        return mScaleType;
    }

    public BaseSliderView setScaleType(ScaleType type) {
        mScaleType = type;
        return this;
    }

    /**
     * the extended class have to implement getView(), which is called by the adapter,
     * every extended class response to render their own view.
     *
     * @return
     */
    public abstract View getView();

    /**
     * set a listener to get a message , if load error.
     *
     * @param l
     */
    public void setOnImageLoadListener(ImageLoadListener l) {
        mLoadListener = l;
    }

    /**
     * when you have some extra information, please put it in this bundle.
     *
     * @return
     */
    public Bundle getBundle() {
        return mBundle;
    }

    public enum ScaleType {
        CenterCrop, CenterInside, Fit, FitCenterCrop
    }

    public interface OnSliderClickListener {
        public void onSliderClick(BaseSliderView slider);
    }

    public interface ImageLoadListener {
        public void onStart(BaseSliderView target);

        public void onEnd(boolean result, BaseSliderView target);
    }

    /**
     * Get the last instance set via setPicasso(), or null if no user provided instance was set
     *
     * @return The current user-provided Picasso instance, or null if none
     */
    /*public Picasso getPicasso() {
        return mPicasso;
    }*/

    /**
     * Provide a Picasso instance to use when loading pictures, this is useful if you have a
     * particular HTTP cache you would like to share.
     *
     * @param picasso The Picasso instance to use, may be null to let the system use the default
     *                instance
     */
    /*public void setPicasso(Picasso picasso) {
        mPicasso = picasso;
    }*/
}

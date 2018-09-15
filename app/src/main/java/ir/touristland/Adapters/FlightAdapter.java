package ir.touristland.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ir.touristland.Activities.Flight.FlightDetailActivity;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Holders.FlightHolder;
import ir.touristland.Models.FlightList.Response;
import ir.touristland.R;


public class FlightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static String Date = "";
    private final int VIEW_TYPE_ITEM = 1;
    @Inject
    ImageLoader imageLoader;
    @Inject
    DisplayImageOptions options;
    private List<Response> feedItemList ;
    private Context mContext;

    public FlightAdapter(Context context, List<Response> feedItemList) {
        this.mContext = context;
        this.feedItemList = feedItemList;
        Application.getComponent2().Inject(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {

        if (i == VIEW_TYPE_ITEM) {
            View v;
            //v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main, null);
            ViewDataBinding item = (DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_main, viewGroup, false));
            return new FlightHolder(item);

        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ITEM;
    }

    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int i) {

        if (holder instanceof FlightHolder) {
            try {

                final FlightHolder Holder = (FlightHolder) holder;
                Holder.setIsRecyclable(false);
                Object obj = feedItemList.get(i);
                Holder.bind(obj);

               /* if (!feedItemList.get(i).getIsSystemic()) {
                    Holder.lbl_state.setVisibility(View.VISIBLE);
                    Holder.lbl_state.setPrimaryText("چارتری");
                }*/
                //HSH.vectorRight(mContext, Holder.txtCount, R.drawable.ic_chair);
                Holder.lblState.setSecondaryText(HSH.toPersianNumber(feedItemList.get(i).getCapLast()+"") + " نفر");
                HSH.vectorRight(mContext, Holder.txtTitle, R.drawable.ic_airline);
                HSH.vectorRight(mContext, Holder.txtStartTime, R.drawable.ic_oclock);

                imageLoader.displayImage(mContext.getString(R.string.url2) + "Files/Airlines/" + feedItemList.get(i).getAirlineCode() + ".png?ver=1", Holder.img_post, options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Drawable drawable = new BitmapDrawable(mContext.getResources(), loadedImage);
                        Holder.txtAirlineTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });

                Holder.itemView.setOnClickListener(v ->
                {
                    Intent intent;
                    intent = new Intent(mContext, FlightDetailActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) mContext, Holder.img_post, "transitionn1");
                    intent.putExtra("feedItem", feedItemList.get(i));
                    intent.putExtra("Date", Date);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        mContext.startActivity(intent, options.toBundle());
                    else
                        mContext.startActivity(intent);

                });
            } catch (Exception e) {
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public void addItem(Response post) {
        this.feedItemList.add(post);
        notifyDataSetChanged();
    }

    public void ClearFeed() {
        feedItemList.clear();
        notifyDataSetChanged();
    }
}
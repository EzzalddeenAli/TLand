package ir.touristland.Adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import javax.inject.Inject;

import ir.touristland.Activities.Hotels.HotelDetailsActivity;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Holders.HotelHolder;
import ir.touristland.Models.HotelList.ResultItem;
import ir.touristland.R;


public class HotelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static String Date = "";
    private final int VIEW_TYPE_ITEM = 1;
    @Inject
    ImageLoader imageLoader;
    @Inject
    DisplayImageOptions options;
    private List<ResultItem> feedItemList;
    private Context mContext;

    public HotelAdapter(Context context, List<ResultItem> feedItemList) {
        this.mContext = context;
        this.feedItemList = feedItemList;
        Application.getComponent2().Inject(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {

        if (i == VIEW_TYPE_ITEM) {
            ViewDataBinding item = (DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_hotel, viewGroup, false));
            return new HotelHolder(item);

        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ITEM;
    }

    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int i) {

        if (holder instanceof HotelHolder) {
            try {

                final HotelHolder Holder = (HotelHolder) holder;
                Holder.setIsRecyclable(false);
                Object obj = feedItemList.get(i);
                Holder.bind(obj);
                Holder.itemView.setOnClickListener(v ->
                {
                    //GetHotelDetails(i, Holder);
                    Intent intent;
                    intent = new Intent(mContext, HotelDetailsActivity.class);
                    /*ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) mContext, Holder.imgPost, "transitionn1");*/
                    intent.putExtra("Center", feedItemList.get(i));
                    intent.putExtra("Date", Date);
                    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        mContext.startActivity(intent, options.toBundle());
                    else*/
                    mContext.startActivity(intent);

                });

                //HSH.vectorRight(mContext, Holder.txtAddress, R.drawable.ic_location);

                Holder.txtDiscount.setText("تا " + HSH.toPersianNumber(feedItemList.get(i).getDiscount()) + "% تخفیف");
                if (feedItemList.get(i).getImages().contains("http"))
                    imageLoader.displayImage(feedItemList.get(i).getImages(), Holder.imgPost, options);
                else
                    imageLoader.displayImage("https://eghamat24.com/app/public/new_images/270x150/" + feedItemList.get(i).getImages(), Holder.imgPost, options);

                if (0 == feedItemList.get(i).getPrice())
                    Holder.txtPrice.setVisibility(View.INVISIBLE);
                else {
                    Holder.txtPrice.setVisibility(View.VISIBLE);
                    Holder.txtPrice.setText(HSH.spannable("شروع قیمت از : " + HSH.Parse(HSH.toPersianNumber("" + feedItemList.get(i).getPrice())) + " تومان"));
                    //HSH.vectorRight(mContext, Holder.txtPrice, R.drawable.ic_price);
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public void ClearFeed() {
        feedItemList.clear();
        notifyDataSetChanged();
    }

    public void addIItem(ResultItem item) {
        feedItemList.add(item);
        notifyItemInserted(feedItemList.size());
    }
}
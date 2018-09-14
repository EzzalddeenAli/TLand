package ir.touristland.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import ir.touristland.Activities.Kishgardi.KishgardiCenterDetailsActivity;
import ir.touristland.Classes.HSH;
import ir.touristland.Holders.CenterHolder;
import ir.touristland.Models.CenterItem;
import ir.touristland.R;


public class KishCentersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 1;
    ImageLoader imageLoader;
    private List<CenterItem> feedItemList = new ArrayList<>();
    private Context mContext;

    public KishCentersAdapter(Context context, ImageLoader imageLoader) {
        this.mContext = context;
        this.imageLoader = imageLoader;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {

        if (i == VIEW_TYPE_ITEM) {
            View v;
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_kish_center, null);
            return new CenterHolder(v);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ITEM;
    }

    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int i) {

        if (holder instanceof CenterHolder) {
            try {

                final CenterHolder Holder = (CenterHolder) holder;
                Holder.setIsRecyclable(false);
                Holder.txtName.setText(feedItemList.get(i).getName());
                Holder.txtAddress.setText(feedItemList.get(i).getAddress());
                Holder.txtTel.setText(feedItemList.get(i).getTelCenter());

                HSH.vectorRight(mContext, Holder.txtAddress, R.drawable.ic_location);
                HSH.vectorRight(mContext, Holder.txtTel, R.drawable.ic_telephone);

                imageLoader.displayImage(mContext.getString(R.string.url2) + "/Center/Img/Show?id=" + feedItemList.get(i).getImgIds().split(",")[0], Holder.img_post);

                holder.itemView.setOnClickListener(v -> {
                    final int pos = i;
                    Intent intent;
                    intent = new Intent(mContext, KishgardiCenterDetailsActivity.class);
                    intent.putExtra("feedItem", feedItemList.get(pos));
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) mContext, Holder.img_post, "transitionn1");
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

    public void addItem(CenterItem post) {
        this.feedItemList.add(post);
        notifyDataSetChanged();
    }

    public void ClearFeed() {
        feedItemList.clear();
        notifyDataSetChanged();
    }
}
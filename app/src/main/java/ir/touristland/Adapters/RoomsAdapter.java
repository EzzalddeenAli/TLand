package ir.touristland.Adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import javax.inject.Inject;

import ir.touristland.Activities.ViewPagerActivity;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Holders.RoomHolder;
import ir.touristland.Interfaces.RoomInterface;
import ir.touristland.Models.HotelDetails.RoomsItem;
import ir.touristland.R;


public class RoomsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Inject
    ImageLoader imageLoader;
    private List<RoomsItem> feedItemList;
    private RoomInterface inteface;
    private Context mContext;

    public RoomsAdapter(Context context, List<RoomsItem> feedItemList, RoomInterface inteface) {
        this.feedItemList = feedItemList;
        this.inteface = inteface;
        this.mContext = context;
        Application.getComponent2().Inject(this);
    }


    @Override
    public RoomHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {

        ViewDataBinding item = (DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_rooms, viewGroup, false));
        return new RoomHolder(item);
    }


    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int i) {

        if (holder instanceof RoomHolder) {
            final RoomHolder Holder = (RoomHolder) holder;
            Holder.setIsRecyclable(false);
            Object obj = feedItemList.get(i);


            HSH.vectorRight(mContext, Holder.txtPrice, R.drawable.ic_price);
            Holder.txtService.setText("خدمات پذیرایی:" + "\n" + (feedItemList.get(i).isFullBoard() ? "صبحانه، ناهار، شام دارد" : "صبحانه دارد|ناهار، شام ندارد"));
            ((RoomHolder) holder).txtBeds.setText("ظرفیت :" + "\n" + HSH.toPersianNumber("" + feedItemList.get(i).getBeds()) + " نفر");
            ((RoomHolder) holder).txtExtraPerson.setText("نفر اضافه:" + "\n" + HSH.toPersianNumber("" + feedItemList.get(i).getExtraPersons()) + " نفر");
            ((RoomHolder) holder).txtPrice.setText(HSH.toPersianNumber(HSH.Parse("" + feedItemList.get(i).getBeds())));
            HSH.vectorRight(mContext, Holder.txtService, R.drawable.ic_room_service);
            HSH.vectorRight(mContext, Holder.txtExtraPerson, R.drawable.ic_add_user);
            HSH.vectorRight(mContext, Holder.txtBeds, R.drawable.ic_add_user2);
            //Holder.txtRoomCount.setTypeface(Application.font);
           /* for (int j = 0; j < HotelDetailsActivity.fFeed.getListRoomPossibilities().size(); j++)
                if (HotelDetailsActivity.fFeed.getListRoomPossibilities().get(j).getHotelRoomId().equals(feedItemList.get(i).getHotelRoomId())) {
                    TextView t = new TextView(mContext);
                    t.setTextSize(11);
                    t.setTypeface(Application.font);
                    t.setBackgroundResource(R.drawable.press_button_stroke);
                    t.setText(HotelDetailsActivity.fFeed.getListRoomPossibilities().get(j).getRoomPossibilitiesName());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(4, 0, 4, 0);
                    t.setLayoutParams(params);
                    t.setPadding(8, 0, 8, 0);
                    Holder.roomPossibilities.addView(t);
                }*/

            //Holder.lbl_state.setVisibility(View.VISIBLE);
            //imageLoader.displayImage(feedItemList.get(i).getImage(), feedListRowHolder.img);
            imageLoader.displayImage(mContext.getString(R.string.image) + feedItemList.get(i).getImage(), Holder.imgRoom);
            Holder.btnIncrease.setOnClickListener(v ->
            {
                try {
                    //TextView view = Holder.txtRoomCount;
                    //view.setText(HSH.toPersianNumber(String.valueOf(Integer.parseInt(view.getText().toString()) + 1)));
                    //feedItemList.get(i).setRoomCount(Integer.parseInt(view.getText().toString()));
                    inteface.getResult(feedItemList.get(i));
                } catch (Exception e) {
                }
            });
            Holder.btnDecrease.setOnClickListener(v ->
            {
                try {
                    //TextView view = Holder.txtRoomCount;
                    /*if (Integer.parseInt(view.getText().toString()) > 0) {
                        view.setText(HSH.toPersianNumber(String.valueOf(Integer.parseInt(view.getText().toString()) - 1)));
                        //feedItemList.get(i).setRoomCount(Integer.parseInt(view.getText().toString()));
                    }*/
                    inteface.getResult(feedItemList.get(i));
                    //((Activity) ctx).finish();
                } catch (Exception e) {
                }
            });

            Holder.btnReserv.setOnClickListener(v ->
            {
                try {
                    Intent in = new Intent(mContext, ir.touristland.Activities.Hotels.PassengersListActivity.class);
                    //in.putExtra("RoomItem", feedItemList.get(i));
                    mContext.startActivity(in);
                    //((Activity) ctx).finish();
                } catch (Exception e) {
                }
            });

            Holder.imgRoom.setOnClickListener(v ->
            {
                final Bundle bundle = new Bundle();
                Intent in = new Intent(mContext, ViewPagerActivity.class);
                //bundle.putString("ImgIds", mContext.getString(R.string.url2) + "HotelRoom/Img/ShowByHotelRoomId?hotelRoomId=" + feedItemList.get(i).getRoomImgIdsStr().split(",")[0]);
                in.putExtras(bundle);
                mContext.startActivity(in);
            });

            Holder.bind(obj);
        }

    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }
}

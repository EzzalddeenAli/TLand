package ir.touristland.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Holders.PassengerHolder;
import ir.touristland.Interfaces.setListenerCity;
import ir.touristland.Models.PassengerItem;
import ir.touristland.Moudle.Roozh;
import ir.touristland.R;


public class PassengersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 1;
    List<PassengerItem> feedItemList;
    setListenerCity onItemCheckListener;
    String[] temp;
    Roozh jCal = new Roozh();
    private Context mContext;

    public PassengersAdapter(Context context, List<PassengerItem> feedItemList) {
        this.mContext = context;
        this.feedItemList = feedItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {

        if (i == VIEW_TYPE_ITEM) {
            View v;
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_passenger_register, null);
            return new PassengerHolder(v);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ITEM;
    }

    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int i) {

        if (holder instanceof PassengerHolder) {
            try {

                final PassengerHolder Holder = (PassengerHolder) holder;
                Holder.setIsRecyclable(false);
                Holder.txtName.setText((feedItemList.get(i).getSex().equals("2") ? "آقای " : "خانم ") + feedItemList.get(i).getFullName());
                Holder.txtNationalCode.setText(HSH.toPersianNumber("کد ملی : " + feedItemList.get(i).getNationalCode()));
                temp = feedItemList.get(i).getBirthDate().split("/");
                Holder.txtBirthDay.setText(HSH.toPersianNumber("تاریخ تولد : " + jCal.GregorianToPersian(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]))));

                holder.itemView.setOnClickListener(v -> {
                    Intent resultData = new Intent();
                    resultData.putExtra("PassengerId", feedItemList.get(i).getId());
                    ((Activity) mContext).setResult(Activity.RESULT_OK, resultData);
                    ((Activity) mContext).finish();
                });

                Holder.txtName.setOnClickListener(v -> {
                    Intent resultData = new Intent();
                    resultData.putExtra("PassengerId", feedItemList.get(i).getId());
                    ((Activity) mContext).setResult(Activity.RESULT_OK, resultData);
                    ((Activity) mContext).finish();
                });

                Holder.linearEdit.setOnClickListener(v ->
                {
                   /* holder.linearUpdate.setVisibility(View.VISIBLE);
                    for (int i = 0; i < holder.linearAddress.getChildCount(); i++) {
                        LinearLayout view = (LinearLayout) holder.linearAddress.getChildAt(i);
                        View a = view.getChildAt(0);
                        a.setBackgroundResource(R.drawable.bottom_stroke_edittext);
                        a.setOnTouchListener(otl2);
                    }
                    ((LinearLayout) holder.linearAddress.getChildAt(0)).getChildAt(0).requestFocus();*/
                });

                Holder.linearDelete.setOnClickListener(v ->
                {
                    try {
                        final SweetAlertDialog dialog = new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE);
                        dialog.setTitleText("حذف مسافر");
                        dialog.setContentText("آیا از حذف مسافر خود اطمینان دارید؟");
                        dialog.setConfirmText("بله");
                        dialog.setCancelText("خیر");
                        dialog.setConfirmClickListener(v2 ->
                        {
                            String query = "DELETE FROM passengers " +
                                    "WHERE Id = '" + feedItemList.get(i).getId() + "' ";
                            Application.database.execSQL(query);
                            feedItemList.remove(i);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        })
                                .setCancelClickListener((SweetAlertDialog sdialog) -> sdialog.dismissWithAnimation());
                        //HSH.dialog(dialog);
                        dialog.show();
                    } catch (Exception e) {
                    }
                });
            } catch (Exception e) {
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }
}
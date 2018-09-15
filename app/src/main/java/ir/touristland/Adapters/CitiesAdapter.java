package ir.touristland.Adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.touristland.Application;
import ir.touristland.Interfaces.setListenerCity;
import ir.touristland.Models.CityItem;
import ir.touristland.R;


public class CitiesAdapter extends BaseAdapter {

    List<CityItem> feedItemList;
    setListenerCity onItemCheckListener;
    Context context;
    Cursor cr;
    private List<CityItem> feed = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public CitiesAdapter(Context context, @NonNull setListenerCity onItemCheckListener) {

        try {
            layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            this.onItemCheckListener = onItemCheckListener;
            this.context = context;

            CityItem item = new CityItem();
            cr = Application.database.rawQuery("SELECT Code,CityNameFa from Citys order by CityNameFa", null);
            while (cr.moveToNext()) {
                item = new CityItem();
                item.setCode(cr.getString(cr.getColumnIndex("Code")));
                item.setCityNameFa(cr.getString(cr.getColumnIndex("CityNameFa")));
                //item.setCityNameEn(cr.getString(cr.getColumnIndex("city_name_en")));
                feed.add(item);
            }
            this.feedItemList = feed;
        } catch (Exception e) {
        }

    }

    @Override
    public int getCount() {

        // Set the total list item count
        try {
            return feedItemList.size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        try {
            return position;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public long getItemId(int position) {
        try {
            return position;
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        // Inflate the item layout and set the views
        try {
            View listItem = convertView;
            final int pos = position;
            if (listItem == null) {
                listItem = layoutInflater.inflate(R.layout.item_city, null);
            }
            // Initialize the views in the layout
            final TextView tvTitle = listItem.findViewById(R.id.lbl_city);
            //final CheckBox check = (CheckBox) listItem.findViewById(R.id.check_city);
            tvTitle.setText(feedItemList.get(pos).getCityNameFa());


            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemCheckListener.onItemCheck(feedItemList.get(position));
                }
            });

            return listItem;
        } catch (Exception e) {
            return null;
        }
    }


    // Do Search...
    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    feedItemList.clear();
                    cr = Application.database.rawQuery("SELECT Code,CityNameFa from Citys where CityNameFa like '%" + text + "%' order by CityNameFa", null);
                    while (cr.moveToNext()) {
                        CityItem item = new CityItem();
                        item.setCode(cr.getString(cr.getColumnIndex("Code")));
                        item.setCityNameFa(cr.getString(cr.getColumnIndex("CityNameFa")));
                        //item.setCityNameEn(cr.getString(cr.getColumnIndex("city_name_en")));
                        feedItemList.add(item);
                    }
                    // Set on UI Thread
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

}
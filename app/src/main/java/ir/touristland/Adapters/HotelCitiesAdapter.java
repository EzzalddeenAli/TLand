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

import java.util.List;

import ir.touristland.Interfaces.setListenerHotelCity;
import ir.touristland.R;
import ir.touristland.Realm.HotelCitiesDAO;
import ir.touristland.Realm.Hotelcity;


public class HotelCitiesAdapter extends BaseAdapter {

    //List<CityItem> feedItemList;
    setListenerHotelCity onItemCheckListener;
    Context context;
    Cursor cr;
    //private List<CityItem> feed = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private HotelCitiesDAO hotelCitiesDAO = new HotelCitiesDAO();
    private List<Hotelcity> cities;

    public HotelCitiesAdapter(Context context, @NonNull setListenerHotelCity onItemCheckListener) {

        try {
            layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            this.onItemCheckListener = onItemCheckListener;
            this.context = context;

            cities = hotelCitiesDAO.findAll();
        } catch (Exception e) {
        }

    }

    @Override
    public int getCount() {

        // Set the total list item count
        try {
            return cities.size();
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
            tvTitle.setText(cities.get(pos).getNameFa());


            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemCheckListener.onItemCheck(cities.get(position));
                }
            });

            return listItem;
        } catch (Exception e) {
            return null;
        }
    }


    // Do Search...
    public void filter(final String text) {

        cities = hotelCitiesDAO.Contain("namefa", text);
        // Set on UI Thread
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

}
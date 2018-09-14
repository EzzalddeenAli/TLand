package ir.touristland.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ir.touristland.Activities.Hotels.HotelDetailsActivity;
import ir.touristland.Classes.HSH;
import ir.touristland.Models.HotelDetails.HotelOptionsItem;
import ir.touristland.Models.HotelDetails.RoomOptionsItem;
import ir.touristland.R;

public class HotelPossibilitiesFragment extends Fragment {

    LinearLayout llPossibilities, llPossibilitiesRooms;
    View rootView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_hotel_possibilities, container, false);
            llPossibilities = rootView.findViewById(R.id.ll_possibilities);
            llPossibilitiesRooms = rootView.findViewById(R.id.ll_possibilities_rooms);
            //GetHotelPossibilities();
            getPossibilities(HotelDetailsActivity.fFeed.getResult().getOptions().getHotelOptions());
            getPossibilitiesRooms(HotelDetailsActivity.fFeed.getResult().getOptions().getRoomOptions());
        }
        return rootView;
    }

    private void getPossibilities(List<HotelOptionsItem> feed) {

        try {
            int scrollviewposition = 0;
            for (scrollviewposition = 0; scrollviewposition < feed.size(); scrollviewposition++) {

                @SuppressWarnings("static-access")
                LayoutInflater inflater = (LayoutInflater)
                        getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                View view1 = inflater.inflate(R.layout.item_center_possibilities, null);
                final TextView txt1 = view1.findViewById(R.id.txt1);
                final TextView txt2 = view1.findViewById(R.id.txt2);
                txt1.setText(feed.get(scrollviewposition).getName());
                HSH.vectorRight(getActivity(), txt1, R.drawable.ic_tick);
                scrollviewposition++;
                if (scrollviewposition < feed.size()) {
                    txt2.setText(feed.get(scrollviewposition).getName());
                    HSH.vectorRight(getActivity(), txt2, R.drawable.ic_tick);
                } else
                    ((LinearLayout) view1).removeView(txt2);
                llPossibilities.addView(view1);
            }

        } catch (Exception e) {
        }
    }

    private void getPossibilitiesRooms(List<RoomOptionsItem> feed) {

        try {
            int scrollviewposition = 0;
            for (scrollviewposition = 0; scrollviewposition < feed.size(); scrollviewposition++) {

                @SuppressWarnings("static-access")
                LayoutInflater inflater = (LayoutInflater)
                        getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                View view1 = inflater.inflate(R.layout.item_center_possibilities, null);
                final TextView txt1 = view1.findViewById(R.id.txt1);
                final TextView txt2 = view1.findViewById(R.id.txt2);
                txt1.setText(feed.get(scrollviewposition).getName());
                HSH.vectorRight(getActivity(), txt1, R.drawable.ic_tick);
                scrollviewposition++;
                if (scrollviewposition < feed.size()) {
                    txt2.setText(feed.get(scrollviewposition).getName());
                    HSH.vectorRight(getActivity(), txt2, R.drawable.ic_tick);
                } else
                    ((LinearLayout) view1).removeView(txt2);
                llPossibilitiesRooms.addView(view1);
            }

        } catch (Exception e) {
        }
    }
}

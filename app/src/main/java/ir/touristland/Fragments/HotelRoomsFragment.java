package ir.touristland.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.touristland.Activities.Hotels.HotelDetailsActivity;
import ir.touristland.Adapters.RoomsAdapter;
import ir.touristland.Classes.HSH;
import ir.touristland.Classes.NetworkUtils;
import ir.touristland.Interfaces.ApiClient;
import ir.touristland.Interfaces.ApiInterface;
import ir.touristland.Models.HotelDetails.RoomsItem;
import ir.touristland.Models.HotelPrices.JsonMember970522;
import ir.touristland.Models.HotelPrices.Response;
import ir.touristland.R;
import retrofit2.Call;
import retrofit2.Callback;

public class HotelRoomsFragment extends Fragment {

    View rootView = null;
    private RecyclerView rvRooms;
    private RoomsAdapter adapter;
    private List<RoomsItem> RoomsItems = new ArrayList<>();
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_hotel_rooms, container, false);
            RoomsItems.addAll(HotelDetailsActivity.fFeed.getResult().getRooms());
            adapter = new RoomsAdapter(getActivity(), RoomsItems, room -> {
                /*int price = Integer.parseInt(room.getPrice().replace(".0", "")) * room.getRoomCount();
                if (price > 0) {
                    HotelDetailsActivity.sumPrice.setVisibility(View.VISIBLE);
                    HotelDetailsActivity.txtPrice.setText(HSH.toPersianNumber(HSH.Parse(String.valueOf(price))));
                    HotelDetailsActivity.txtPrice.setTypeface(Application.font);
                } else
                    HotelDetailsActivity.sumPrice.setVisibility(View.GONE);*/

            });
            rvRooms = rootView.findViewById(R.id.rv_rooms);
            rvRooms.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            rvRooms.setLayoutManager(layoutManager);
            rvRooms.setAdapter(adapter);

            //GetRooms();
        }
        return rootView;
    }

    public void GetRooms() {
        Map<String, String> params = new HashMap<>();
        params.put("hotel", "" + HotelDetailsActivity.fFeed.getResult().getId());
        Call<Response> call =
                ApiClient.getClient2().create(ApiInterface.class).GetRoomPrice(params);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                try {
                    if (response.code() == 200) {
                        for (int i = 0; i < HotelDetailsActivity.fFeed.getResult().getRooms().size(); i++) {
                            for (int j = 0; j < HotelDetailsActivity.fFeed.getResult().getRooms().size(); j++) {
                                int a = HotelDetailsActivity.fFeed.getResult().getRooms().get(j).getId();
                                int b = response.body().getResult().get(j).getId();
                                if (a == b) {
                                    Gson gson = new Gson();
                                    JsonMember970522 s = (JsonMember970522) response.body().getResult().get(j).getPriceList();
                                    //JsonMember970522 item = gson.fromJson(s, JsonMember970522.class);
                                    Object ob = s;
                                    RoomsItems.get(j).setPrice(ob + "");
                                    break;
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else
                        HSH.showtoast(getActivity(), response.errorBody().string());

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                if (NetworkUtils.getConnectivity(getActivity()) != false)
                    GetRooms();
                else {

                }
            }
        });
    }
}

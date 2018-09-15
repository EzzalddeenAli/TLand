package ir.touristland.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.makeramen.roundedimageview.RoundedImageView;

import ir.touristland.Activities.Kishgardi.KishgardiTypesActivity;
import ir.touristland.Activities.TicketActivity;
import ir.touristland.Classes.HSH;
import ir.touristland.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {


    View rootView = null;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);

            RoundedImageView btnFlight = rootView.findViewById(R.id.btn_flight);
            RoundedImageView btnKishgardi = rootView.findViewById(R.id.btn_kishgardi);
            RoundedImageView btnHotel = rootView.findViewById(R.id.btn_hotel);
            btnFlight.setOnClickListener(this);
            btnKishgardi.setOnClickListener(this);
            btnHotel.setOnClickListener(this);

            /*HSH.animate(getActivity(), rootView.findViewById(R.id.ll_main2));
            HSH.animate(getActivity(), rootView.findViewById(R.id.ll_main3));
            HSH.animate(getActivity(), rootView.findViewById(R.id.ll_main4));*/
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.zoom_out);
        v.startAnimation(animation);
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        switch (v.getId()) {
                            case R.id.btn_flight:
                                HSH.onOpenPage(getActivity(), TicketActivity.class);
                                break;
                            case R.id.btn_kishgardi:
                                HSH.onOpenPage(getActivity(), KishgardiTypesActivity.class);
                                break;
                            case R.id.btn_hotel:
                                Intent i = new Intent(getActivity(), TicketActivity.class);
                                i.putExtra("IsHotel", "Hotel");
                                startActivity(i);
                                break;
                        }
                    }
                }, 250);

    }
}

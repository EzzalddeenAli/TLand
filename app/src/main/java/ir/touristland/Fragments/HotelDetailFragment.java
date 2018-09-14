package ir.touristland.Fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.touristland.Activities.Hotels.HotelDetailsActivity;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Models.CenterDetailsModel;
import ir.touristland.Models.HotelDetails.MoreInfoItem;
import ir.touristland.Models.HotelDetails.RulesItem;
import ir.touristland.Models.HotelList.ResultItem;
import ir.touristland.R;

public class HotelDetailFragment extends Fragment {

    ResultItem hotelItem;
    View rootView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_hotel_detail, container, false);
            if (null != HotelDetailsActivity.fFeed.getResult().getRooms()) {
                hotelItem = (ResultItem) getActivity().getIntent().getExtras().getSerializable("Center");
                ((TextView) rootView.findViewById(R.id.txt_price)).setText("ارزش هتل نسبت به قیمت ____________________ " + HSH.toPersianNumber(HotelDetailsActivity.fFeed.getResult().getRate().getPrice()) + " از ۵");
                ((TextView) rootView.findViewById(R.id.txt_facility)).setText("ارزش هتل نسبت به امکانات هتل _______________ " + HSH.toPersianNumber(HotelDetailsActivity.fFeed.getResult().getRate().getFacility()) + " از ۵");
                ((TextView) rootView.findViewById(R.id.txt_room)).setText("ارزش هتل نسبت به کیفیت اتاق ها _____________ " + HSH.toPersianNumber(HotelDetailsActivity.fFeed.getResult().getRate().getRoom()) + " از ۵");
                ((TextView) rootView.findViewById(R.id.txt_position)).setText("ارزش هتل نسبت به موقعیت مکانی _____________ " + HSH.toPersianNumber(HotelDetailsActivity.fFeed.getResult().getRate().getPosition()) + " از ۵");
                ((TextView) rootView.findViewById(R.id.txt_rate)).setText("میانگین رضایت مندی کاربران تاکنون ___________ " + HSH.toPersianNumber(HotelDetailsActivity.fFeed.getResult().getRate().getRate()) + " از ۵");
                AdvertisementDetails();
            }
        }
        return rootView;
    }

    public void AdvertisementDetails() {
        //imageLoader.displayImage(fFeed.getImgIds().split(",")[0], imgProfile, options);
        try {
            List<CenterDetailsModel> feed = new ArrayList<>();
            List<MoreInfoItem> result2 = HotelDetailsActivity.fFeed.getResult().getMoreInfo();

            String s = "";
            CenterDetailsModel item;
            for (int i = 0; i < result2.size(); i++) {
                try {
                    item = new CenterDetailsModel();
                    s = result2.get(i).getName() + " : " + (!result2.get(i).getDescription().equals("") ? result2.get(i).getDescription() : result2.get(i).getValue().get(0).getName() + " " + result2.get(i).getValue().get(0).getValue());
                    item.setTitle(s.trim());
                    feed.add(item);
                } catch (Exception e) {
                }
            }
            getProperty(getActivity(), rootView.findViewById(R.id.ll_baseProperty), feed);
            ((TextView) rootView.findViewById(R.id.txt_description)).setText(hotelItem.getDescription());
        } catch (Exception e) {
        }

        try {
            List<CenterDetailsModel> feed = new ArrayList<>();
            List<RulesItem> result2 = HotelDetailsActivity.fFeed.getResult().getRules();

            String s = "";
            CenterDetailsModel item;
            for (int i = 0; i < result2.size(); i++) {
                try {
                    item = new CenterDetailsModel();
                    s = result2.get(i).getName() + " : " + (!result2.get(i).getDescription().equals("") ? result2.get(i).getDescription() : result2.get(i).getValue().get(0).getName() + " " + result2.get(i).getValue().get(0).getValue());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        item.setTitle(Html.fromHtml(s.trim(), Html.FROM_HTML_MODE_LEGACY).toString().replace("<br>", "\n"));
                    } else {
                        item.setTitle(Html.fromHtml(s.trim()).toString().replace("<br>", "\n"));
                    }
                    feed.add(item);
                } catch (Exception e) {
                }
            }
            getProperty(getActivity(), rootView.findViewById(R.id.ll_rules), feed);
        } catch (Exception e) {
        }
    }

    private void getProperty(final Context _cn, final LinearLayout layout, final List<CenterDetailsModel> feed) {

        try {
            int scrollviewposition = 0;
            for (scrollviewposition = 0; scrollviewposition < feed.size(); scrollviewposition++) {

                @SuppressWarnings("static-access")
                LayoutInflater inflater = (LayoutInflater)
                        _cn.getSystemService(_cn.LAYOUT_INFLATER_SERVICE);
                View view1 = inflater.inflate(R.layout.item_center_details, null);

                final TextView txt1 = view1.findViewById(R.id.txt1);
                txt1.setTypeface(Application.font);
                String[] temp = feed.get(scrollviewposition).getTitle().split(":");
                Spannable spannable = new SpannableString(HSH.toPersianNumber(feed.get(scrollviewposition).getTitle()));
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#026d37")), temp[0].length() + 1, feed.get(scrollviewposition).getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                txt1.setText(spannable, TextView.BufferType.SPANNABLE);
                layout.addView(view1);
            }

        } catch (Exception e) {
        }
    }
}

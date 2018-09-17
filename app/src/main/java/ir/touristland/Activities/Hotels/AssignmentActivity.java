package ir.touristland.Activities.Hotels;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import ir.touristland.Activities.BaseActivity;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Interfaces.ApiClient;
import ir.touristland.Interfaces.ApiInterface;
import ir.touristland.Models.HotelCapacityItem;
import ir.touristland.Models.NumberPassenger;
import ir.touristland.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AssignmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_hotel);

        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        //HotelItem fFeed = HotelDetailsActivity.hotelItem;
        HotelCapacityItem roomItem = (HotelCapacityItem) getIntent().getExtras().getSerializable("RoomItem");
        ((TextView) findViewById(R.id.txt_cities)).setText(HSH.toPersianNumber(NumberPassenger.Companion.getInstance().getParams().get(getString(R.string.PersianFrom))
                + "\n" + "رزرو اتاق برای " + NumberPassenger.Companion.getInstance().getParams().get(getString(R.string.NumberOfNights)) + " شب"));
        ((TextView) findViewById(R.id.txt_date)).setText(NumberPassenger.Companion.getInstance().getParams().get(getString(R.string.PersianDate)) + " تا "
                + NumberPassenger.Companion.getInstance().getParams().get(getString(R.string.PersianDateReturn)));
        ((TextView) findViewById(R.id.txt_RoomNumber)).setText(HSH.toPersianNumber("شماره اتاق : " + roomItem.getHotelRoomId()));
        ((TextView) findViewById(R.id.txt_RoomTypeName)).setText(roomItem.getDescriptRoom());

        LinearLayout linearProductCategory = findViewById(R.id.linear_passengers);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String cnt = getIntent().getExtras().getString("Ids");
        String[] tmp = cnt.split(",");
        int sumPrice = 0;
        for (int i = 0; i < tmp.length; i++) {
            Cursor cr = Application.database.rawQuery("SELECT * from passengers WHERE Id='" + cnt.split(",")[i] + "'", null);
            cr.moveToFirst();
            if (i == 0) {
                ((TextView) findViewById(R.id.txt_name)).setText(cr.getString(cr.getColumnIndex("Name_fa")) + " " + cr.getString(cr.getColumnIndex("LastName_fa")));
                ((TextView) findViewById(R.id.txt_email)).setText(getIntent().getExtras().getString("Email"));
                ((TextView) findViewById(R.id.txt_mobile)).setText(HSH.toPersianNumber(getIntent().getExtras().getString("Mobile")));
            }
            View view = inflater.inflate(R.layout.item_assignment, null);
            TextView name = view.findViewById(R.id.txt_name);
            TextView nationalCode = view.findViewById(R.id.txt_nationalCode);
            TextView price = view.findViewById(R.id.txt_price);

            name.setText(cr.getString(cr.getColumnIndex("Name_en")) + " " + cr.getString(cr.getColumnIndex("LastName_en")));
            nationalCode.setText(HSH.toPersianNumber(cr.getString(cr.getColumnIndex("NationalCode"))));
            // price.setText(Parse(roomItem.getPrice()) + " تومان");
            price.setVisibility(View.GONE);
            linearProductCategory.addView(view);

            sumPrice += Integer.parseInt(roomItem.getPrice().substring(0, roomItem.getPrice().indexOf(".")));
        }
        if (Integer.parseInt(NumberPassenger.Companion.getInstance().getParams().get(getString(R.string.NumberOfNights))) == 0)
            Integer.parseInt(NumberPassenger.Companion.getInstance().getParams().put(getString(R.string.NumberOfNights), "1"));
        ((TextView) findViewById(R.id.txt_sumPrice)).setText(HSH.toPersianNumber(Parse(String.valueOf(sumPrice * Integer.parseInt(NumberPassenger.Companion.getInstance().getParams().get(getString(R.string.NumberOfNights))))) + " تومان"));
    }

    public String Parse(String text) {
        if (text.contains(".")) {
            text = String.valueOf(BigDecimal.valueOf(Double.valueOf(text)));
            text = String.format("%,d", Long.parseLong(String.valueOf(new BigDecimal(text.substring(0, text.indexOf(".") - 1)).toBigIntegerExact())));
            return text;
        } else {
            text = String.format("%,d", Long.parseLong(String.valueOf(new BigDecimal(text.substring(0, text.length() - 1)).toBigIntegerExact())));
            return text;
        }
    }

    private void PreReserv() {
        Map<String, String> params = new HashMap<>();
        params.put("HotelCapacityId", "");
        params.put("FromDatePersian", "");
        params.put("ToDatePersian", "");
        params.put("HotelBookingDetails", "");
        Call<ResponseBody> call =
                ApiClient.getClient().create(ApiInterface.class).PreReserv(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.code() == 200)
                    try {
                        HSH.showtoast(AssignmentActivity.this, "اطلاعات شما با موفقیت ثبت شد");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
}

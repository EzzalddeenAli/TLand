package ir.touristland.Activities.Flight;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;

import ir.touristland.Activities.BaseActivity;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Models.FlightItem;
import ir.touristland.R;

public class AssignmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        FlightItem fFeed = (FlightItem) getIntent().getExtras().getSerializable("feedItem");
        ((TextView) findViewById(R.id.txt_cities)).setText(fFeed.getFromCity() + " > " + fFeed.getToCity());
        ((TextView) findViewById(R.id.txt_date)).setText(HSH.toPersianNumber(getIntent().getExtras().getString("Date") + "  " + fFeed.getStartTime()));
        ((TextView) findViewById(R.id.txt_airline)).setText(fFeed.getAirLineTitle());
        //((TextView) findViewById(R.id.txt_systemic)).setText(fFeed.getIsSystemic() == true ? "سیستمی" : "چارتری");

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
            price.setText(HSH.toPersianNumber(Parse(fFeed.getPrice()) + " تومان"));
            linearProductCategory.addView(view);

            sumPrice += Integer.parseInt(fFeed.getPrice().substring(0, fFeed.getPrice().indexOf(".")));
        }
        ((TextView) findViewById(R.id.txt_sumPrice)).setText(HSH.toPersianNumber(Parse(String.valueOf(sumPrice)) + " تومان"));
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
}

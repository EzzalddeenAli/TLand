package ir.touristland.Activities.Flight;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import ir.touristland.Activities.BaseActivity;
import ir.touristland.Application;
import ir.touristland.Asynktask.AsynctaskReserveFlight;
import ir.touristland.Classes.HSH;
import ir.touristland.Interfaces.IWebservice;
import ir.touristland.Models.FlightList.Response;
import ir.touristland.Models.FlightReserve;
import ir.touristland.Models.NumberPassenger;
import ir.touristland.R;
import ir.touristland.databinding.ActivityFlightDetailBinding;

public class FlightDetailActivity extends BaseActivity {

    @Inject
    ImageLoader imageLoader;
    @Inject
    DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFlightDetailBinding binding = DataBindingUtil.setContentView(FlightDetailActivity.this, R.layout.activity_flight_detail);
        Application.getComponent2().Inject(this);
        Response fFeed = (Response) getIntent().getExtras().getSerializable("feedItem");
        fFeed.setFromCity(NumberPassenger.Companion.getInstance().getParams().get(getString(R.string.PersianFrom)));
        fFeed.setToCity(NumberPassenger.Companion.getInstance().getParams().get(getString(R.string.PersianTo)));
        binding.setFlightItem(fFeed);
        //binding.txtDifference.setText(HSH.toPersianNumber(Calculate(fFeed.getFlightTime().substring(0, 5), fFeed.getArrivalTime().substring(0, 5))));
        imageLoader.displayImage(getString(R.string.url2) + "Files/Airlines/" + fFeed.getAirlineCode() + ".png?ver=1", ((ImageView) findViewById(R.id.img_logo)), options);
        ((TextView) findViewById(R.id.txt_date)).setText(getIntent().getExtras().getString("Date"));
        findViewById(R.id.img_back).setOnClickListener(v -> finish());

        findViewById(R.id.btn_select_ticket).setOnClickListener(v ->
        {
            if(fFeed.getReservable() == 1) {
                findViewById(R.id.pb).setVisibility(View.VISIBLE);
                WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

                Map<String, String> params = new HashMap<>();
                params.put("ParvazId", "" + fFeed.getParvazId());
                params.put("ClassId", "" + fFeed.getClassId());
                params.put("ADL", "" + NumberPassenger.Companion.getInstance().getParams().get(getString(R.string.AdultCount)));
                params.put("CHD", "" + NumberPassenger.Companion.getInstance().getParams().get(getString(R.string.ChildCount)));
                params.put("INF", "" + NumberPassenger.Companion.getInstance().getParams().get(getString(R.string.InfantCount)));
                params.put("KndSys", "" + fFeed.getKndSys());
                params.put("CustomerId", getString(R.string.ApiSiteIDValue));
                params.put("PassengerIP", ip);
                IWebservice.IFlightReserve delegate = new IWebservice.IFlightReserve() {
                    @Override
                    public void getResult(FlightReserve item) throws Exception {
                        NumberPassenger.Companion.getInstance().setReqNo(item.getReqNo());

                        findViewById(R.id.pb).setVisibility(View.GONE);
                        Intent intent = new Intent(FlightDetailActivity.this, PassengersListActivity.class);
                        intent.putExtra("feedItem", fFeed);
                        intent.putExtra("Date", getIntent().getExtras().getString("Date"));
                        startActivity(intent);
                    }

                    @Override
                    public void getError(String s) throws Exception {
                        HSH.showtoast(FlightDetailActivity.this, (new JSONObject(s)).getString("Message"));
                        findViewById(R.id.pb).setVisibility(View.GONE);
                    }
                };
                new AsynctaskReserveFlight(FlightDetailActivity.this, params, delegate).getData();
            }
            else
            {
                findViewById(R.id.pb).setVisibility(View.GONE);
                Intent intent = new Intent(FlightDetailActivity.this, PassengersListActivity.class);
                intent.putExtra("feedItem", fFeed);
                intent.putExtra("Date", getIntent().getExtras().getString("Date"));
                startActivity(intent);
            }
        });
    }
}

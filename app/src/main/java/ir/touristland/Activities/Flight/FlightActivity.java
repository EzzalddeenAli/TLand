package ir.touristland.Activities.Flight;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alirezaafkar.sundatepicker.DatePicker2;
import com.alirezaafkar.sundatepicker.components.JDF;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ir.touristland.Activities.BaseActivity;
import ir.touristland.Activities.UpdateChecker;
import ir.touristland.Adapters.FlightAdapter;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Classes.HidingScrollListener;
import ir.touristland.Classes.NetworkUtils;
import ir.touristland.Interfaces.ApiClient;
import ir.touristland.Interfaces.ApiInterface;
import ir.touristland.Models.FlightList.Response;
import ir.touristland.Models.NumberPassenger;
import ir.touristland.Moudle.Roozh;
import ir.touristland.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class FlightActivity extends BaseActivity implements View.OnClickListener/*, HideActionbar*/ {

    public static LinearLayout ll_bottomNavigation;

    TextView txtBefore, txtAfter, txtSort, txtFilter;
    JDF mToday = new JDF();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    @Inject
    Retrofit retrofit;
    private List<Response> feedItemList = new ArrayList<>();
    private RecyclerView rv;
    private Map<String, String> params = new HashMap<>();
    private SwipeRefreshLayout swipeContainer;
    private ProgressWheel pb;
    private FlightAdapter adapter;
    private LinearLayoutManager layoutManager;

    public void DeclareElements() {
        ll_bottomNavigation = findViewById(R.id.ll_bottom);
        swipeContainer = findViewById(R.id.swipeContainer);
        pb = findViewById(R.id.pb);
        rv = findViewById(R.id.rv_paye);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(FlightActivity.this);
        rv.setLayoutManager(layoutManager);
        adapter = new FlightAdapter(FlightActivity.this, feedItemList);
        rv.setAdapter(adapter);
        rv.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                HSH.hideViews(ll_bottomNavigation);
            }

            @Override
            public void onShow() {
                HSH.showViews(ll_bottomNavigation);
            }
        });

        HSH.vectorRight(FlightActivity.this, findViewById(R.id.lbl_from), R.drawable.ic_take_off);
        HSH.vectorRight(FlightActivity.this, findViewById(R.id.lbl_to), R.drawable.ic_landing);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);
        Application.getComponent2().Inject(this);
        DeclareElements();
        UpdateChecker();
        params = NumberPassenger.getInstance().getParams();
        SearchFlight();
        txtBefore = findViewById(R.id.txt_before);
        txtAfter = findViewById(R.id.txt_after);
        txtSort = findViewById(R.id.txt_sort);
        txtFilter = findViewById(R.id.txt_filter);
        txtBefore.setOnClickListener(this);
        txtAfter.setOnClickListener(this);
        txtSort.setOnClickListener(this);
        txtFilter.setOnClickListener(this);
        try {
            HSH.vectorRight(FlightActivity.this, txtBefore, R.drawable.ic_right_arrow);
            HSH.vectorLeft(FlightActivity.this, txtAfter, R.drawable.ic_left_arrow);
            HSH.vectorRight(FlightActivity.this, txtSort, R.drawable.ic_sort);
            HSH.vectorLeft(FlightActivity.this, txtFilter, R.drawable.ic_filter2);
        } catch (Exception e) {
        }
        swipeContainer.setOnRefreshListener(() -> {
            adapter.ClearFeed();
            SearchFlight();
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        ((TextView) findViewById(R.id.toolbar_title)).setText(HSH.toPersianNumber(params.get(getString(R.string.PersianFrom)) + "  >  " + params.get(getString(R.string.PersianTo))));
        ((TextView) findViewById(R.id.txt_date)).setText(HSH.toPersianNumber(params.get(getString(R.string.PersianDate))));
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
    }

    @Override
    public void onClick(View v) {
        HSH.setMainDrawableColor(ll_bottomNavigation, v);
        try {
            HSH._snackbar.dismiss();
        } catch (Exception e) {
        }
        PopupMenu popup = new PopupMenu(FlightActivity.this, v);
        switch (v.getId()) {
            case R.id.txt_sort:
                popup.getMenuInflater().inflate(R.menu.btn_sort, popup.getMenu());
                for (int i = 0; i < popup.getMenu().size(); i++) {
                    MenuItem mi = popup.getMenu().getItem(i);
                    SubMenu subMenu = mi.getSubMenu();
                    if (subMenu != null && subMenu.size() > 0) {
                        for (int j = 0; j < subMenu.size(); j++) {
                            MenuItem subMenuItem = subMenu.getItem(j);
                            HSH.applyFontToMenuItem(FlightActivity.this, subMenuItem);
                        }
                    }
                    HSH.applyFontToMenuItem(FlightActivity.this, mi);
                }
                popup.show();
                break;
            case R.id.txt_after:
                try {
                    if (pb.getVisibility() == View.GONE) {
                        Date myDate = dateFormat.parse(params.get(getString(R.string.GeorgianDate)));
                        Date newDate = new Date(myDate.getTime() + (24 * 60 * 60 * 1000));
                        String date = dateFormat.format(newDate);
                        if (Calculate(date, params.get(getString(R.string.ReturnDate))) > -1) {
                            Roozh jCal = new Roozh();
                            String[] d = date.split("/");

                            String cur_date = jCal.GregorianToPersian(Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2]));
                            d = cur_date.split("/");
                            if (d[1].length() == 1) d[1] = "0" + d[1];
                            if (d[2].length() == 1) d[2] = "0" + d[2];
                            params.put(getString(R.string.Date), d[0] + d[1] + d[2]);
                            params.put(getString(R.string.GeorgianDate), date);

                            String[] s = date.split("/");
                            s = jCal.GregorianToPersian(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])).split("/");
                            DatePicker2 datePicker = new DatePicker2((FlightActivity.this));
                            datePicker.setDate(Integer.parseInt(s[2]), Integer.parseInt(s[1]), Integer.parseInt(s[0]));
                            ((TextView) findViewById(R.id.txt_date)).setText(HSH.toPersianNumber(datePicker.getDayName() + " " + datePicker.getDay() + " " + datePicker.getMonthName() + " " + datePicker.getYear()));

                            adapter.ClearFeed();
                            SearchFlight();
                            findViewById(R.id.txt_before).setEnabled(true);
                            HSH.setMainDrawableColor(ll_bottomNavigation, findViewById(R.id.txt_after));
                        } else
                            HSH.showtoast(FlightActivity.this, "تاریخ برگشت باید بعد از تاریخ رفت باشد.");
                    }
                } catch (Exception e) {
                    HSH.showtoast(FlightActivity.this, "تاریخ رفت را وارد نمایید");
                }
                break;

            case R.id.txt_before:
                try {
                    if (pb.getVisibility() == View.GONE) {
                        Date myDate = dateFormat.parse(params.get(getString(R.string.GeorgianDate)));
                        Date current = dateFormat.parse(mToday.getGregorianDate());
                        if (current.before(myDate)) {
                            Date newDate = new Date(myDate.getTime() - (24 * 60 * 60 * 1000));
                            String date = dateFormat.format(newDate);
                            if (Calculate(date, params.get(getString(R.string.ReturnDate))) > -1) {
                                Roozh jCal = new Roozh();
                                String[] d = date.split("/");

                                String cur_date = jCal.GregorianToPersian(Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2]));
                                d = cur_date.split("/");
                                if (d[1].length() == 1) d[1] = "0" + d[1];
                                if (d[2].length() == 1) d[2] = "0" + d[2];
                                params.put(getString(R.string.Date), d[0] + d[1] + d[2]);
                                params.put(getString(R.string.GeorgianDate), date);

                                String[] s = date.split("/");
                                s = jCal.GregorianToPersian(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])).split("/");
                                DatePicker2 datePicker = new DatePicker2((FlightActivity.this));
                                datePicker.setDate(Integer.parseInt(s[2]), Integer.parseInt(s[1]), Integer.parseInt(s[0]));
                                ((TextView) findViewById(R.id.txt_date)).setText(HSH.toPersianNumber(datePicker.getDayName() + " " + datePicker.getDay() + " " + datePicker.getMonthName() + " " + datePicker.getYear()));

                                adapter.ClearFeed();
                                SearchFlight();
                                HSH.setMainDrawableColor(ll_bottomNavigation, findViewById(R.id.txt_before));
                            }
                        } else
                            findViewById(R.id.txt_before).setEnabled(false);

                    }
                } catch (Exception e) {
                    HSH.showtoast(FlightActivity.this, "تاریخ رفت را وارد نمایید");
                }
                break;
            default:
                break;
        }


        popup.setOnMenuItemClickListener((MenuItem item) ->
        {
            if (item.getTitleCondensed().toString().equals("flighttime"))
                Collections.sort(feedItemList, new FlightTimeComparator());
            else if (item.getTitleCondensed().toString().equals("lowestprice"))
                Collections.sort(feedItemList, new FlightLowestPriceComparator());
            else if (item.getTitleCondensed().toString().equals("Highestprice"))
                Collections.sort(feedItemList, new FlightHighestPriceComparator());

            adapter.notifyDataSetChanged();
            return true;

        });
    }

    private void SearchFlight() {
        pb.setVisibility(View.VISIBLE);

        Call<List<Response>> call = retrofit.create(ApiInterface.class).getCharter(params);
        call.enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                try {
                    if (response.code() == 200) {
                        {
                            try {
                                swipeContainer.setRefreshing(false);
                                List<Response> list = response.body();

                                if (list.size() == 0)
                                    HSH.showtoast(FlightActivity.this, "موردی یافت نشد");
                                for (Response m : list) {
                                    feedItemList.add(m);
                                    AirLine(m.getAirlineCode(), m);
                                }

                                Collections.sort(feedItemList, new FlightLowestPriceComparator());
                                adapter.notifyDataSetChanged();

                                adapter.Date = params.get(getString(R.string.PersianDate));
                                if (!params.get(getString(R.string.ReturnDate)).isEmpty()) {
                                    params.put(getString(R.string.From), params.get(getString(R.string.To)));
                                    params.put(getString(R.string.To), params.get(getString(R.string.From)));
                                    params.put(getString(R.string.Date), params.get(getString(R.string.Date)));
                                    SearchFlight();
                                } else {
                                    ll_bottomNavigation.setVisibility(View.VISIBLE);
                                    pb.setVisibility(View.GONE);
                                }
                            } catch (Exception e) {
                            }
                        }
                    } else
                        HSH.showtoast(FlightActivity.this, response.errorBody().string());
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {
                if (NetworkUtils.getConnectivity(FlightActivity.this) != false)
                    SearchFlight();
                else {
                    try {
                        HSH.showtoast(FlightActivity.this, t.getLocalizedMessage());
                        swipeContainer.setRefreshing(false);
                        pb.setVisibility(View.GONE);
                    } catch (Exception e) {
                    }
                }
            }
        });
    }

    private void AirLine(String iata, Response m) {
        switch (iata) {
            case "HH":
                m.setAirlineName("تابان");
                break;
            case "IV":
                m.setAirlineName("کاسپین");
                break;
            case "ZV":
                m.setAirlineName("زاگرس");
                break;
            case "QB":
                m.setAirlineName("قشم ایر");
                break;
            case "B9":
                m.setAirlineName("ایر تور");
                break;
            case "EP":
                m.setAirlineName("آسمان");
                break;
            case "IR":
                m.setAirlineName("ایران ایر");
                break;
            case "W5":
                m.setAirlineName("ماهان");
                break;
            case "Y9":
                m.setAirlineName("کیش ایر");
                break;
            case "SR":
                m.setAirlineName("سپهران");
                break;

        }
    }

    private long Calculate(String t1, String t2) {
        try {
            Date startDate = dateFormat.parse(t1);
            Date endDate = dateFormat.parse(t2);

            long diff = endDate.getTime() - startDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            return days;
        } catch (Exception e) {
            return 0;
        }
    }

    private void UpdateChecker() {
        Call<ResponseBody> call =
                ApiClient.getClient().create(ApiInterface.class).GetUpdate();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.code() == 200)
                    try {
                        JSONObject result = new JSONObject(response.body().string().trim());
                        Application.editor.putInt(getString(R.string.Feepayable), result.getInt(getString(R.string.Feepayable)));
                        Application.editor.commit();
                        String _version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                        String newVersion = result.getString(getString(R.string.VersionName));
                        if (!newVersion.equals(_version) && !newVersion.equals("")) {
                            String REMOTE_APK_URL = result.getString(getString(R.string.DownloadUrl)).trim();
                            UpdateChecker uc = new UpdateChecker(FlightActivity.this, newVersion, REMOTE_APK_URL);
                            uc.curVersion = _version;
                            uc.localApkName = "Kishtravel.apk";
                            uc.alertTitle = "بروز رسانی";
                            uc.alertMessage = result.getString(getString(R.string.Description)).trim();
                            uc.alertTitleError = "خطا در بارگذاری";
                            uc.alertMessageError = "امکان دانلود فایل وجود ندارد.";
                            uc.progressMessage = "در حال بارگذاری...";
                            uc.startUpdateChecker();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public class FlightTimeComparator implements Comparator<Response> {
        public int compare(Response left, Response right) {
            return left.getFlightTime().compareTo(right.getFlightTime());
        }
    }

    public class FlightLowestPriceComparator implements Comparator<Response> {
        public int compare(Response left, Response right) {
            return (left.getPriceView() + "").compareTo(right.getPriceView() + "");
        }
    }

    public class FlightHighestPriceComparator implements Comparator<Response> {
        public int compare(Response left, Response right) {
            return (right.getPriceView() + "").compareTo(left.getPriceView() + "");
        }
    }
}

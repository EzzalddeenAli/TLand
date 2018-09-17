package ir.touristland.Activities.Hotels;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alirezaafkar.sundatepicker.DatePicker2;
import com.alirezaafkar.sundatepicker.components.JDF;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONObject;

import java.io.Serializable;
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
import ir.touristland.Adapters.HotelAdapter;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Classes.HidingScrollListener;
import ir.touristland.Classes.NetworkUtils;
import ir.touristland.Interfaces.ApiClient;
import ir.touristland.Interfaces.ApiInterface;
import ir.touristland.Models.HotelList.HotelListResponse;
import ir.touristland.Models.HotelList.ResultItem;
import ir.touristland.Models.NumberPassenger;
import ir.touristland.Moudle.Roozh;
import ir.touristland.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class HotelListActivity extends BaseActivity implements View.OnClickListener/*, HideActionbar*/ {

    public static LinearLayout ll_bottomNavigation;
    TextView txtBefore, txtAfter, txtSort, txtFilter;
    JDF mToday = new JDF();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    FloatingActionButton fb;
    @Inject
    Retrofit retrofit;
    private List<ResultItem> feedItemList = new ArrayList<>();
    private RecyclerView rv;
    private Map<String, String> params = new HashMap<>();
    private SwipeRefreshLayout swipeContainer;
    private ProgressWheel pb;
    private HotelAdapter adapter;
    //private LinearLayoutManager layoutManager;

    public void DeclareElements() {
        fb = findViewById(R.id.fb);
        fb.setOnClickListener(this);
        ll_bottomNavigation = findViewById(R.id.ll_bottom);
        swipeContainer = findViewById(R.id.swipeContainer);
        pb = findViewById(R.id.pb);
        rv = findViewById(R.id.rv_hotel);
        rv.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(HotelListActivity.this);
        //rv.setLayoutManager(layoutManager);
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new HotelAdapter(HotelListActivity.this, feedItemList);
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

        HSH.vectorRight(HotelListActivity.this, findViewById(R.id.lbl_from), R.drawable.ic_take_off);
        HSH.vectorRight(HotelListActivity.this, findViewById(R.id.lbl_to), R.drawable.ic_landing);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        Application.getComponent().Inject(this);
        DeclareElements();
        UpdateChecker();
        params.put(getString(R.string.city), NumberPassenger.Companion.getInstance().getParams().get(getString(R.string.city)));
        txtBefore = findViewById(R.id.txt_before);
        txtAfter = findViewById(R.id.txt_after);
        txtSort = findViewById(R.id.txt_sort);
        txtFilter = findViewById(R.id.txt_filter);
        txtBefore.setOnClickListener(this);
        txtAfter.setOnClickListener(this);
        txtSort.setOnClickListener(this);
        txtFilter.setOnClickListener(this);
        try {
            HSH.vectorRight(HotelListActivity.this, txtBefore, R.drawable.ic_right_arrow);
            HSH.vectorLeft(HotelListActivity.this, txtAfter, R.drawable.ic_left_arrow);
            HSH.vectorRight(HotelListActivity.this, txtSort, R.drawable.ic_sort);
            HSH.vectorLeft(HotelListActivity.this, txtFilter, R.drawable.ic_filter2);
        } catch (Exception e) {
        }
        swipeContainer.setOnRefreshListener(() -> {
            adapter.ClearFeed();
            SearchHotel();
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        ((TextView) findViewById(R.id.toolbar_title)).setText(HSH.toPersianNumber(NumberPassenger.Companion.getInstance().getParams().get(getString(R.string.PersianFrom))));
        ((TextView) findViewById(R.id.txt_date)).setText(HSH.toPersianNumber(NumberPassenger.Companion.getInstance().getParams().get(getString(R.string.PersianDate))));
        findViewById(R.id.img_back).setOnClickListener(v -> finish());

        SearchHotel();
    }

    public void SearchHotel() {
        pb.setVisibility(View.VISIBLE);
        Call<HotelListResponse>
                call = retrofit.create(ApiInterface.class).GetHotelList(params);
        call.enqueue(new Callback<HotelListResponse>() {
            @Override
            public void onResponse(Call<HotelListResponse> call, retrofit2.Response<HotelListResponse> response) {
                try {
                    if (response.code() == 200) {
                        try {
                            swipeContainer.setRefreshing(false);
                            feedItemList.addAll(response.body().getResult());

                            Collections.sort(feedItemList, new FlightLowestPriceComparator());
                            adapter.notifyDataSetChanged();
                            adapter.Date = params.get(getString(R.string.PersianDate));
                            ll_bottomNavigation.setVisibility(View.VISIBLE);
                            pb.setVisibility(View.GONE);
                        } catch (Exception e) {
                        }
                    } else
                        HSH.showtoast(HotelListActivity.this, response.errorBody().string());
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<HotelListResponse> call, Throwable t) {
                if (NetworkUtils.getConnectivity(HotelListActivity.this) != false)
                    SearchHotel();
                else {
                    try {
                        swipeContainer.setRefreshing(false);
                        pb.setVisibility(View.GONE);
                        HSH.showtoast(HotelListActivity.this, t.getLocalizedMessage());
                    } catch (Exception e) {
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        HSH.setMainDrawableColor(ll_bottomNavigation, v);
        try {
            HSH._snackbar.dismiss();
        } catch (Exception e) {
        }
        PopupMenu popup = new PopupMenu(HotelListActivity.this, v);
        switch (v.getId()) {
            case R.id.txt_sort:
                popup.getMenuInflater().inflate(R.menu.btn_sort_hotel, popup.getMenu());
                for (int i = 0; i < popup.getMenu().size(); i++) {
                    MenuItem mi = popup.getMenu().getItem(i);
                    SubMenu subMenu = mi.getSubMenu();
                    if (subMenu != null && subMenu.size() > 0) {
                        for (int j = 0; j < subMenu.size(); j++) {
                            MenuItem subMenuItem = subMenu.getItem(j);
                            HSH.applyFontToMenuItem(HotelListActivity.this, subMenuItem);
                        }
                    }
                    HSH.applyFontToMenuItem(HotelListActivity.this, mi);
                }
                popup.show();
                break;
            case R.id.txt_after:
                try {
                    if (pb.getVisibility() == View.GONE) {
                        Date myDate = dateFormat.parse(params.get(getString(R.string.Date)));
                        Date newDate = new Date(myDate.getTime() + (24 * 60 * 60 * 1000));
                        String date = dateFormat.format(newDate);
                        if (Calculate(date, params.get(getString(R.string.ReturnDate))) > -1) {
                            params.put(getString(R.string.Date), date);
                            Roozh jCal = new Roozh();
                            String[] s = date.split("/");
                            s = jCal.GregorianToPersian(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])).split("/");
                            DatePicker2 datePicker = new DatePicker2((HotelListActivity.this));
                            datePicker.setDate(Integer.parseInt(s[2]), Integer.parseInt(s[1]), Integer.parseInt(s[0]));
                            ((TextView) findViewById(R.id.txt_date)).setText(HSH.toPersianNumber(datePicker.getDayName() + " " + datePicker.getDay() + " " + datePicker.getMonthName() + " " + datePicker.getYear()));
                            adapter.ClearFeed();
                            SearchHotel();
                            findViewById(R.id.txt_before).setEnabled(true);
                            HSH.setMainDrawableColor(ll_bottomNavigation, findViewById(R.id.txt_after));
                        } else
                            HSH.showtoast(HotelListActivity.this, "تاریخ برگشت باید بعد از تاریخ رفت باشد.");
                    }
                } catch (Exception e) {
                    HSH.showtoast(HotelListActivity.this, "تاریخ رفت را وارد نمایید");
                }
                break;

            case R.id.txt_before:
                try {
                    if (pb.getVisibility() == View.GONE) {
                        Date myDate = dateFormat.parse(params.get(getString(R.string.Date)));
                        Date current = dateFormat.parse(mToday.getGregorianDate());
                        if (current.before(myDate)) {
                            Date newDate = new Date(myDate.getTime() - (24 * 60 * 60 * 1000));
                            String date = dateFormat.format(newDate);
                            if (Calculate(date, params.get(getString(R.string.ReturnDate))) > -1) {
                                params.put(getString(R.string.Date), date);
                                DatePicker2 datePicker = new DatePicker2((HotelListActivity.this));
                                //String[] d = date.split("/");
                                Roozh jCal = new Roozh();
                                String[] s = date.split("/");
                                s = jCal.GregorianToPersian(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])).split("/");
                                datePicker.setDate(Integer.parseInt(s[2]), Integer.parseInt(s[1]), Integer.parseInt(s[0]));
                                ((TextView) findViewById(R.id.txt_date)).setText(HSH.toPersianNumber(datePicker.getDayName() + " " + datePicker.getDay() + " " + datePicker.getMonthName() + " " + datePicker.getYear()));
                                adapter.ClearFeed();
                                SearchHotel();
                                HSH.setMainDrawableColor(ll_bottomNavigation, findViewById(R.id.txt_before));
                            }
                        } else
                            findViewById(R.id.txt_before).setEnabled(false);

                    }
                } catch (Exception e) {
                    HSH.showtoast(HotelListActivity.this, "تاریخ رفت را وارد نمایید");
                }
                break;
            case R.id.fb:
                Intent in = new Intent(HotelListActivity.this, PlacesActivity.class);
                Bundle bnd = new Bundle();
                bnd.putSerializable("Places", (Serializable) feedItemList);
                in.putExtras(bnd);
                startActivity(in);
            default:
                break;
        }


        popup.setOnMenuItemClickListener((MenuItem item) ->
        {
            if (item.getTitleCondensed().toString().equals("lowestprice"))
                Collections.sort(feedItemList, new FlightLowestPriceComparator());
            else if (item.getTitleCondensed().toString().equals("Highestprice"))
                Collections.sort(feedItemList, new FlightHighestPriceComparator());

            adapter.notifyDataSetChanged();
            return true;

        });
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
                            UpdateChecker uc = new UpdateChecker(HotelListActivity.this, newVersion, REMOTE_APK_URL);
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

    public class FlightLowestPriceComparator implements Comparator<ResultItem> {
        public int compare(ResultItem left, ResultItem right) {
            return (left.getPrice() + "").compareTo(right.getPrice() + "");
        }
    }

    public class FlightHighestPriceComparator implements Comparator<ResultItem> {
        public int compare(ResultItem left, ResultItem right) {
            return (right.getPrice() + "").compareTo(left.getPrice() + "");
        }
    }
}

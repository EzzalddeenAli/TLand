package ir.touristland.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.jaredrummler.android.widget.AnimatedSvgView;

import javax.inject.Inject;

import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Classes.NetworkUtils;
import ir.touristland.Interfaces.ApiInterface;
import ir.touristland.R;
import ir.touristland.Realm.HotelCities;
import ir.touristland.Realm.HotelCitiesDAO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class SplashActivity extends Activity {

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Application.getComponent().Inject(this);
        GetCities();
        // Transaction();
        AnimatedSvgView svgView = findViewById(R.id.animated_svg_view);
        svgView.start();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                AnimatedSvgView svgView2 = findViewById(R.id.line_svg_view);
                svgView2.start();
                AnimatedSvgView svgView3 = findViewById(R.id.font_svg_view);
                svgView3.start();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (NetworkUtils.getConnectivity(SplashActivity.this) != false) {
                            HSH.onOpenPage(SplashActivity.this, IntroLoginActivity.class);
                            finish();
                        } else if (NetworkUtils.getConnectivity(SplashActivity.this) == false) {
                            HSH.onOpenPage(SplashActivity.this, NoConnectioonActivity.class);
                            finish();
                        }
                        //finish();
                    }
                }, 2000);
            }
        }, 3000);
    }


    private void GetCities() {
        Call<HotelCities> call =
                retrofit.create(ApiInterface.class).loadCity();
        call.enqueue(new Callback<HotelCities>() {
            @Override
            public void onResponse(Call<HotelCities> call, retrofit2.Response<HotelCities> response) {
                if (response.code() == 200)
                    try {
                        final HotelCities result = response.body();
                        HotelCitiesDAO dao = new HotelCitiesDAO();
                        dao.update(result.getResult());
                    } catch (Exception e) {
                        HSH.showtoast(SplashActivity.this, e.getMessage());
                        HSH.showtoast(SplashActivity.this, e.getMessage());
                        HSH.showtoast(SplashActivity.this, e.getMessage());
                    }
                else
                    GetCities();
            }

            @Override
            public void onFailure(Call<HotelCities> call, Throwable t) {
                GetCities();
            }
        });
    }
}

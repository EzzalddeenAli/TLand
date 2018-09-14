package ir.touristland.Asynktask;


import android.app.Activity;

import java.util.Map;

import javax.inject.Inject;

import ir.touristland.Application;
import ir.touristland.Classes.NetworkUtils;
import ir.touristland.Interfaces.ApiInterface;
import ir.touristland.Interfaces.IWebservice;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class AsynctaskReserveFlight {

    IWebservice.FlightReserve delegate;
    @Inject
    Retrofit retrofit;
    private Activity ac;
    private Map<String, String> params ;

    public AsynctaskReserveFlight(Activity ac,
                                  Map<String, String> params,
                                  IWebservice.FlightReserve delegate) {
        this.ac = ac;
        this.params = params;
        this.delegate = delegate;
        Application.GetRavis().Inject(this);
    }

    public void getData() {
        Call<ResponseBody>
                call = retrofit.create(ApiInterface.class).ReserveFlight(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    if (response.code() == 200) {
                        delegate.getResult(response.body().string());
                    } else
                        delegate.getError(response.errorBody().string());
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (NetworkUtils.getConnectivity(ac) != false)
                    getData();
                else {
                    try {
                        delegate.getError(t.getLocalizedMessage());
                    } catch (Exception e) {
                    }
                }
            }
        });
    }
}



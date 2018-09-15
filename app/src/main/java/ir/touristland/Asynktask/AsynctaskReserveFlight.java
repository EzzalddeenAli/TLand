package ir.touristland.Asynktask;


import android.app.Activity;

import java.util.Map;

import javax.inject.Inject;

import ir.touristland.Application;
import ir.touristland.Classes.NetworkUtils;
import ir.touristland.Interfaces.ApiInterface;
import ir.touristland.Interfaces.IWebservice;
import ir.touristland.Models.FlightReserve;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class AsynctaskReserveFlight {

    IWebservice.IFlightReserve delegate;
    @Inject
    Retrofit retrofit;
    private Activity ac;
    private Map<String, String> params;

    public AsynctaskReserveFlight(Activity ac,
                                  Map<String, String> params,
                                  IWebservice.IFlightReserve delegate) {
        this.ac = ac;
        this.params = params;
        this.delegate = delegate;
        Application.GetRavis().Inject(this);
    }

    public void getData() {
        Call<FlightReserve>
                call = retrofit.create(ApiInterface.class).ReserveFlight(params);
        call.enqueue(new Callback<FlightReserve>() {
            @Override
            public void onResponse(Call<FlightReserve> call, retrofit2.Response<FlightReserve> response) {
                try {
                    if (response.code() == 200) {
                        delegate.getResult(response.body());
                    } else
                        delegate.getError(response.errorBody().string());
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<FlightReserve> call, Throwable t) {
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



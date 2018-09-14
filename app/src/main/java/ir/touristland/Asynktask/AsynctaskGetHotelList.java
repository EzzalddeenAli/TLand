package ir.touristland.Asynktask;


import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import ir.touristland.Application;
import ir.touristland.Interfaces.IWebservice;
import retrofit2.Retrofit;

public class AsynctaskGetHotelList {

    IWebservice.IWebservice2 delegate;
    @Inject
    Retrofit retrofit;
    private Activity ac;
    private Map<String, String> params = new HashMap<>();

    public AsynctaskGetHotelList(Activity ac,
                                 Map<String, String> params,
                                 IWebservice.IWebservice2 delegate) {
        this.ac = ac;
        this.params = params;
        this.delegate = delegate;
        Application.getComponent().Inject(this);
    }

    public void getData() {
        /*Call<ResponseBody>
                call = retrofit.create(ApiInterface.class).GetHotelList(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    if (response.code() == 200) {
                        String s = response.body().string();
                        delegate.getResult(s);
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
        });*/
    }
}



package ir.touristland.Asynktask;


import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import ir.touristland.Application;
import retrofit2.Retrofit;

public class AsynctaskGetPost {

    //IWebservice2 delegate;
    @Inject
    Retrofit retrofit;
    private Activity ac;
    private Map<String, String> params = new HashMap<>();

    public AsynctaskGetPost(Activity ac,
                            Map<String, String> params) {
        this.ac = ac;
        this.params = params;
        Application.getComponent2().Inject(this);
    }

    public void getData() {
       /* Call<ResponseBody> call;
        if (params.get("FlightType").equals("Charter"))
            call = retrofit.create(ApiInterface.class).getCharter(params);
        else
            call = retrofit.create(ApiInterface.class).getSystemic(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    if (response.code() == 200) {
                        //String s = response.body().string();
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
        });*/
    }
}



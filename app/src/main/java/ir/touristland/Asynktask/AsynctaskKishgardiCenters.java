package ir.touristland.Asynktask;


import android.app.Activity;

import java.util.List;
import java.util.Map;

import ir.touristland.Classes.NetworkUtils;
import ir.touristland.Interfaces.ApiClient;
import ir.touristland.Interfaces.ApiInterface;
import ir.touristland.Interfaces.KishgardiCenterInteface;
import ir.touristland.Models.CenterItem;
import retrofit2.Call;
import retrofit2.Callback;

public class AsynctaskKishgardiCenters {

    KishgardiCenterInteface delegate;
    private Map<String, String> params;
    private Activity ac;

    public AsynctaskKishgardiCenters(Activity ac,
                                     Map<String, String> params,
                                     KishgardiCenterInteface delegate) {
        this.ac = ac;
        this.params = params;
        this.delegate = delegate;
    }

    public void getData() {
        Call<List<CenterItem>> call =
                ApiClient.getClient2().create(ApiInterface.class).KishgardiCenters(params);
        call.enqueue(new Callback<List<CenterItem>>() {
            @Override
            public void onResponse(Call<List<CenterItem>> call, retrofit2.Response<List<CenterItem>> response) {
                try {
                    if (response.code() == 200) {
                        delegate.getResult(response);
                    } else
                        getData();
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<List<CenterItem>> call, Throwable t) {
                if (NetworkUtils.getConnectivity(ac) != false)
                    getData();
                else {
                    try {
                        delegate.getError();
                    } catch (Exception e) {
                    }
                }
            }
        });
    }
}



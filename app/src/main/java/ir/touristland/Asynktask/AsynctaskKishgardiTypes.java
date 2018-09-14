package ir.touristland.Asynktask;


import android.app.Activity;

import java.util.List;

import ir.touristland.Classes.NetworkUtils;
import ir.touristland.Interfaces.ApiClient;
import ir.touristland.Interfaces.ApiInterface;
import ir.touristland.Interfaces.KishgardiTypeInteface;
import ir.touristland.Models.CenterTypeItem;
import retrofit2.Call;
import retrofit2.Callback;

public class AsynctaskKishgardiTypes {

    KishgardiTypeInteface delegate;
    private Activity ac;

    public AsynctaskKishgardiTypes(Activity ac,
                                   KishgardiTypeInteface delegate) {
        this.ac = ac;
        this.delegate = delegate;
    }

    public void getData() {
        Call<List<CenterTypeItem>> call =
                ApiClient.getClient2().create(ApiInterface.class).KishgardiTypes();
        call.enqueue(new Callback<List<CenterTypeItem>>() {
            @Override
            public void onResponse(Call<List<CenterTypeItem>> call, retrofit2.Response<List<CenterTypeItem>> response) {
                try {
                    if (response.code() == 200) {
                        delegate.getResult(response);
                    } else
                        getData();
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<List<CenterTypeItem>> call, Throwable t) {
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



package ir.touristland.Asynktask;


import android.app.Activity;

import java.util.List;

import javax.inject.Inject;

import ir.touristland.Application;
import ir.touristland.Classes.NetworkUtils;
import ir.touristland.Interfaces.ApiInterface;
import ir.touristland.Interfaces.KishgardiTypeInteface;
import ir.touristland.Models.CenterTypeItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class AsynctaskHotelsTypes {

    KishgardiTypeInteface delegate;
    @Inject
    Retrofit retrofit;
    private Activity ac;

    public AsynctaskHotelsTypes(Activity ac,
                                KishgardiTypeInteface delegate) {
        this.ac = ac;
        this.delegate = delegate;
        Application.getComponent2().Inject(this);
    }

    public void getData() {
        Call<List<CenterTypeItem>> call =
                retrofit.create(ApiInterface.class).HotelTypes();
        call.enqueue(new Callback<List<CenterTypeItem>>() {
            @Override
            public void onResponse(Call<List<CenterTypeItem>> call, retrofit2.Response<List<CenterTypeItem>> response) {
                try {
                    if (response.code() == 200) {
                        delegate.getResult(response);
                    } else
                        delegate.getError();
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



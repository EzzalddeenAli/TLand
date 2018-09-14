package ir.touristland.Asynktask;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Interfaces.ApiClient;
import ir.touristland.Interfaces.ApiInterface;
import ir.touristland.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class GetCoordinateCentersAsynkTask {

    GoogleMap mMap;
    private Context cn;
    private ProgressBar pb;

    public GetCoordinateCentersAsynkTask(Context cn, ProgressBar pb, GoogleMap mMap) {
        this.cn = cn;
        this.pb = pb;
        this.mMap = mMap;
    }


    public void getData() {
        Map<String, String> params = new HashMap<>();
        params.put("CityName", Application.preferences.getString("CityName", "تهران"));
        Call<ResponseBody> call =
                ApiClient.getClient2().create(ApiInterface.class).PlaceHolders();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    if (response.code() == 200) {
                        pb.setVisibility(View.GONE);
                        if (response.toString().equals("[]")) {
                            HSH.showtoast(cn, "موردی یافت نشد.");
                        } else {
                            JSONArray result = new JSONArray(response.body().string());
                            try {
                                for (int i = 0; i < result.length(); i++) {

                                    mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Double.parseDouble(result.getJSONObject(i).getString(cn.getString(R.string.Latitude)))
                                                    , Double.parseDouble(result.getJSONObject(i).getString("Longitude"))))
                                            .anchor(0.5f, 0.5f)
                                            .title(result.getJSONObject(i).getString(cn.getString(R.string.Name)))
                                            .snippet(result.getJSONObject(i).getString("id"))
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_placeholder)));
                                }

                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(new LatLng(Double.parseDouble(result.getJSONObject(0).getString(cn.getString(R.string.Latitude)))
                                                , Double.parseDouble(result.getJSONObject(0).getString("Longitude"))))
                                        .zoom(11).build();
                                mMap.animateCamera(CameraUpdateFactory
                                        .newCameraPosition(cameraPosition));
                            } catch (Exception e) {
                            }
                        }
                    } else
                        getData();
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}



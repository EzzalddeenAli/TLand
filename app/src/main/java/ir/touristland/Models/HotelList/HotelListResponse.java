package ir.touristland.Models.HotelList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotelListResponse {

    @SerializedName("result")
    private List<ResultItem> result;

    public List<ResultItem> getResult() {
        return result;
    }
}
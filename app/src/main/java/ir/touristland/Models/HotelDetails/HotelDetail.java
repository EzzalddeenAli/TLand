package ir.touristland.Models.HotelDetails;

import com.google.gson.annotations.SerializedName;

public class HotelDetail {

    @SerializedName("result")
    private Result result;

    public Result getResult() {
        return result;
    }
}
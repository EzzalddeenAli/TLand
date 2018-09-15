package ir.touristland.Models.HotelDetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Options {

    @SerializedName("hotelOptions")
    private List<HotelOptionsItem> hotelOptions;

    @SerializedName("extraOptions")
    private ExtraOptions extraOptions;

    @SerializedName("roomOptions")
    private List<RoomOptionsItem> roomOptions;

    public List<HotelOptionsItem> getHotelOptions() {
        return hotelOptions;
    }

    public ExtraOptions getExtraOptions() {
        return extraOptions;
    }

    public List<RoomOptionsItem> getRoomOptions() {
        return roomOptions;
    }
}
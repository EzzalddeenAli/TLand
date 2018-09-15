package ir.touristland.Models.HotelDetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExtraOptions {

    @SerializedName("restaurant")
    private List<RestaurantItem> restaurant;

    public List<RestaurantItem> getRestaurant() {
        return restaurant;
    }
}
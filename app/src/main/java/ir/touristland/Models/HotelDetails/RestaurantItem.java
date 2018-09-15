package ir.touristland.Models.HotelDetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantItem {

    @SerializedName("image")
    private String image;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("value")
    private List<ValueItem> value;

    @SerializedName("key")
    private String key;

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<ValueItem> getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
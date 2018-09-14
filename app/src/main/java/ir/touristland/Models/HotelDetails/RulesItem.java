package ir.touristland.Models.HotelDetails;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RulesItem {

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("key")
    private String key;

    @SerializedName("value")
    private List<ValueItem> value;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }

    public List<ValueItem> getValue() {
        return value;
    }
}
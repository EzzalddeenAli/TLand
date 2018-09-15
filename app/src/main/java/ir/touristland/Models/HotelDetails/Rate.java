package ir.touristland.Models.HotelDetails;


import com.google.gson.annotations.SerializedName;

public class Rate {

    @SerializedName("rate")
    private String rate;

    @SerializedName("price")
    private String price;

    @SerializedName("position")
    private String position;

    @SerializedName("facility")
    private String facility;

    @SerializedName("room")
    private String room;

    public String getRate() {
        return rate;
    }

    public String getPrice() {
        return price;
    }

    public String getPosition() {
        return position;
    }

    public String getFacility() {
        return facility;
    }

    public String getRoom() {
        return room;
    }
}
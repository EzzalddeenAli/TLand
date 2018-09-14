package ir.touristland.Models.HotelDetails;


import com.google.gson.annotations.SerializedName;

public class ImageURL {

    @SerializedName("270x150")
    private String jsonMember270x150;

    @SerializedName("150x85")
    private String jsonMember150x85;

    @SerializedName("250x360")
    private String jsonMember250x360;

    @SerializedName("600x460")
    private String jsonMember600x460;

    public String getJsonMember270x150() {
        return jsonMember270x150;
    }

    public String getJsonMember150x85() {
        return jsonMember150x85;
    }

    public String getJsonMember250x360() {
        return jsonMember250x360;
    }

    public String getJsonMember600x460() {
        return jsonMember600x460;
    }
}
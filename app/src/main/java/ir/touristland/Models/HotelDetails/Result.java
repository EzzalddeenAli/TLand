package ir.touristland.Models.HotelDetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("images")
    private List<String> images;

    @SerializedName("rooms")
    private List<RoomsItem> rooms;

    @SerializedName("isMoj")
    private boolean isMoj;

    @SerializedName("onlineReservation")
    private boolean onlineReservation;

    @SerializedName("discount")
    private String discount;

    @SerializedName("numberOfRooms")
    private String numberOfRooms;

    @SerializedName("rules")
    private List<RulesItem> rules;

    @SerializedName("shelik24")
    private boolean shelik24;

    @SerializedName("moreInfo")
    private List<MoreInfoItem> moreInfo;

    @SerializedName("blackFriday")
    private String blackFriday;

    @SerializedName("hotelDiscountPrice")
    private String hotelDiscountPrice;

    @SerializedName("rate")
    private Rate rate;

    @SerializedName("imageURL")
    private ImageURL imageURL;

    @SerializedName("options")
    private Options options;

    @SerializedName("id")
    private String id;

    public List<String> getImages() {
        return images;
    }

    public List<RoomsItem> getRooms() {
        return rooms;
    }

    public boolean isIsMoj() {
        return isMoj;
    }

    public boolean isOnlineReservation() {
        return onlineReservation;
    }

    public String getDiscount() {
        return discount;
    }

    public String getNumberOfRooms() {
        return numberOfRooms;
    }

    public List<RulesItem> getRules() {
        return rules;
    }

    public boolean isShelik24() {
        return shelik24;
    }

    public List<MoreInfoItem> getMoreInfo() {
        return moreInfo;
    }

    public String getBlackFriday() {
        return blackFriday;
    }

    public String getHotelDiscountPrice() {
        return hotelDiscountPrice;
    }

    public Rate getRate() {
        return rate;
    }

    public ImageURL getImageURL() {
        return imageURL;
    }

    public Options getOptions() {
        return options;
    }

    public String getId() {
        return id;
    }
}
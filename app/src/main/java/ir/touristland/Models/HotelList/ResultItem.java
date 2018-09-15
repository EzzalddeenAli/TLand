package ir.touristland.Models.HotelList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultItem implements Serializable {
    public static final long serialVersionUID = 1L;
    @SerializedName("images")
    private String images;

    @SerializedName("address")
    private String address;

    @SerializedName("isMoj")
    private boolean isMoj;

    @SerializedName("star")
    private String star;

    @SerializedName("onlineReservation")
    private boolean onlineReservation;

    @SerializedName("cityNameFa")
    private String cityNameFa;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("description")
    private String description;

    @SerializedName("discount")
    private String discount;

    @SerializedName("numberOfRooms")
    private String numberOfRooms;

    @SerializedName("sort")
    private String sort;

    @SerializedName("type")
    private String type;

    @SerializedName("blackFriday")
    private String blackFriday;

    @SerializedName("hotelDiscountPrice")
    private String hotelDiscountPrice;
    @SerializedName("cityName")
    private String cityName;
    @SerializedName("price")
    private int price = 0;
    @SerializedName("name")
    private String name;
    @SerializedName("nameFa")
    private String nameFa;
    @SerializedName("id")
    private String id;
    @SerializedName("category")
    private String category;
    @SerializedName("class")
    private String jsonMemberClass;
    @SerializedName("longitude")
    private String longitude;

    public String getImages() {
        return images;
    }

    public String getAddress() {
        return address;
    }

    public boolean isIsMoj() {
        return isMoj;
    }

    public String getStar() {
        return star;
    }

    public boolean isOnlineReservation() {
        return onlineReservation;
    }

    public String getCityNameFa() {
        return cityNameFa;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getDescription() {
        return description;
    }

    public String getDiscount() {
        return discount;
    }

    public String getNumberOfRooms() {
        return numberOfRooms;
    }

    public String getSort() {
        return sort;
    }

    public String getType() {
        return type;
    }

    public String getBlackFriday() {
        return blackFriday;
    }

    public String getHotelDiscountPrice() {
        return hotelDiscountPrice;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getNameFa() {
        return nameFa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public String getJsonMemberClass() {
        return jsonMemberClass;
    }

    public String getLongitude() {
        return longitude;
    }
}
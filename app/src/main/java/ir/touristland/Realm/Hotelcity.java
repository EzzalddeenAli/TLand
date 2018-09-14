package ir.touristland.Realm;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.io.Serializable;

public class Hotelcity extends SugarRecord implements Serializable {

    static final long serialVersionUID = 1L;
    @Unique
    String name = "";
    @SerializedName("nameFa")
    String namefa = "";
    @SerializedName("stateFa")
    String statefa = "";
    String latitude = "";
    String longitude = "";
    String hotels = "";
    @SerializedName("onlineHotels")
    String onlinehotels = "";

    public Hotelcity() {

    }

    public Hotelcity(String name, String nameFa, String stateFa, String latitude, String longitude, String hotels, String onlineHotels) {
        this.name = name;
        this.namefa = nameFa;
        this.statefa = stateFa;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hotels = hotels;
        this.onlinehotels = onlineHotels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameFa() {
        return namefa;
    }

    public void setNameFa(String nameFa) {
        this.namefa = nameFa;
    }

    public String getStateFa() {
        return statefa;
    }

    public void setStateFa(String stateFa) {
        this.statefa = stateFa;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getHotels() {
        return hotels;
    }

    public void setHotels(String hotels) {
        this.hotels = hotels;
    }

    public String getOnlineHotels() {
        return onlinehotels;
    }

    public void setOnlineHotels(String onlineHotels) {
        this.onlinehotels = onlineHotels;
    }
}

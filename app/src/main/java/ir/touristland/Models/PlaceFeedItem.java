package ir.touristland.Models;

import java.io.Serializable;

public class PlaceFeedItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id = "";
    private String Name = "";
    private String Latitude = "";
    private String Longitude = "";
    private String ImageUrl = "";
    private String Distance = "";
    private String VisitApi = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getVisitApi() {
        return VisitApi;
    }

    public void setVisitApi(String visitApi) {
        VisitApi = visitApi;
    }
}

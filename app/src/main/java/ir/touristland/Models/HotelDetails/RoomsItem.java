package ir.touristland.Models.HotelDetails;


import com.google.gson.annotations.SerializedName;

public class RoomsItem {

    @SerializedName("doubleBeds")
    private String doubleBeds;

    @SerializedName("image")
    private String image;

    @SerializedName("sofa")
    private String sofa;

    @SerializedName("reservationStatus")
    private boolean reservationStatus;

    @SerializedName("fullBoard")
    private boolean fullBoard;

    @SerializedName("description")
    private String description;

    @SerializedName("extraPersons")
    private int extraPersons;

    @SerializedName("sort")
    private String sort;

    @SerializedName("type")
    private String type;

    @SerializedName("isSpecialDate")
    private String isSpecialDate;

    @SerializedName("persons")
    private String persons;

    @SerializedName("isSpecial")
    private boolean isSpecial;

    @SerializedName("singleBeds")
    private String singleBeds;

    @SerializedName("nameFa")
    private String nameFa;

    @SerializedName("id")
    private int id;

    @SerializedName("beds")
    private String beds;

    @SerializedName("price")
    private String price;

    public String getDoubleBeds() {
        return doubleBeds;
    }

    public String getImage() {
        return image;
    }

    public String getSofa() {
        return sofa;
    }

    public boolean isReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(boolean reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public boolean isFullBoard() {
        return fullBoard;
    }

    public String getDescription() {
        return description;
    }

    public int getExtraPersons() {
        return extraPersons;
    }

    public String getSort() {
        return sort;
    }

    public String getType() {
        return type;
    }

    public String getIsSpecialDate() {
        return isSpecialDate;
    }

    public String getPersons() {
        return persons;
    }

    public boolean isIsSpecial() {
        return isSpecial;
    }

    public String getSingleBeds() {
        return singleBeds;
    }

    public String getNameFa() {
        return nameFa;
    }

    public int getId() {
        return id;
    }

    public String getBeds() {
        return beds;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
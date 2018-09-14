package ir.touristland.Models.HotelPrices;

import com.google.gson.annotations.SerializedName;

public class ResultItem {

    @SerializedName("totalPrice")
    private TotalPrice totalPrice;

    @SerializedName("onlineReservation")
    private boolean onlineReservation;

    @SerializedName("fullBoard")
    private boolean fullBoard;

    @SerializedName("nameFa")
    private String nameFa;

    @SerializedName("discount")
    private Discount discount;

    @SerializedName("id")
    private int id;

    @SerializedName("beds")
    private String beds;

    @SerializedName("reservationStatus")
    private boolean reservationStatus;

    @SerializedName("priceList")
    private Object priceList;

    public TotalPrice getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(TotalPrice totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isOnlineReservation() {
        return onlineReservation;
    }

    public void setOnlineReservation(boolean onlineReservation) {
        this.onlineReservation = onlineReservation;
    }

    public boolean isFullBoard() {
        return fullBoard;
    }

    public void setFullBoard(boolean fullBoard) {
        this.fullBoard = fullBoard;
    }

    public String getNameFa() {
        return nameFa;
    }

    public void setNameFa(String nameFa) {
        this.nameFa = nameFa;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeds() {
        return beds;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }

    public boolean isReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(boolean reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Object getPriceList() {
        return priceList;
    }

    public void setPriceList(Object priceList) {
        this.priceList = priceList;
    }

    @Override
    public String toString() {
        return
                "ResultItem{" +
                        "totalPrice = '" + totalPrice + '\'' +
                        ",onlineReservation = '" + onlineReservation + '\'' +
                        ",fullBoard = '" + fullBoard + '\'' +
                        ",nameFa = '" + nameFa + '\'' +
                        ",discount = '" + discount + '\'' +
                        ",id = '" + id + '\'' +
                        ",beds = '" + beds + '\'' +
                        ",reservationStatus = '" + reservationStatus + '\'' +
                        ",priceList = '" + priceList + '\'' +
                        "}";
    }
}
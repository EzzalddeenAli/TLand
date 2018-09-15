package ir.touristland.Models;

import java.io.Serializable;

public class HotelPrice implements Serializable {

    public static final long serialVersionUID = 1L;
    public String id = "";
    public String nameFa = "";
    public String fullBoard = "";
    public String beds = "";
    public String onlineReservation = "";
    public String reservationStatus = "";
    public String totalPrice;
    public String priceList = "";
    public String discount = "";

    public String getId() {
        return id;
    }

    public String getNameFa() {
        return nameFa;
    }

    public String getFullBoard() {
        return fullBoard;
    }

    public String getBeds() {
        return beds;
    }

    public String getOnlineReservation() {
        return onlineReservation;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getPriceList() {
        return priceList;
    }

    public String getDiscount() {
        return discount;
    }
}

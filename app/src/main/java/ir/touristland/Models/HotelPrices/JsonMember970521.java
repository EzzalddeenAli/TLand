package ir.touristland.Models.HotelPrices;

import com.google.gson.annotations.SerializedName;

public class JsonMember970521 {

    @SerializedName("date")
    private Date date;

    @SerializedName("boardPrice")
    private int boardPrice;

    @SerializedName("webservicePrice")
    private int webservicePrice;

    @SerializedName("extraPersonPrice")
    private int extraPersonPrice;

    @SerializedName("salesPrice")
    private int salesPrice;

    @SerializedName("reserveStatus")
    private boolean reserveStatus;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBoardPrice() {
        return boardPrice;
    }

    public void setBoardPrice(int boardPrice) {
        this.boardPrice = boardPrice;
    }

    public int getWebservicePrice() {
        return webservicePrice;
    }

    public void setWebservicePrice(int webservicePrice) {
        this.webservicePrice = webservicePrice;
    }

    public int getExtraPersonPrice() {
        return extraPersonPrice;
    }

    public void setExtraPersonPrice(int extraPersonPrice) {
        this.extraPersonPrice = extraPersonPrice;
    }

    public int getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(int salesPrice) {
        this.salesPrice = salesPrice;
    }

    public boolean isReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(boolean reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    @Override
    public String toString() {
        return
                "JsonMember970521{" +
                        "date = '" + date + '\'' +
                        ",boardPrice = '" + boardPrice + '\'' +
                        ",webservicePrice = '" + webservicePrice + '\'' +
                        ",extraPersonPrice = '" + extraPersonPrice + '\'' +
                        ",salesPrice = '" + salesPrice + '\'' +
                        ",reserveStatus = '" + reserveStatus + '\'' +
                        "}";
    }
}
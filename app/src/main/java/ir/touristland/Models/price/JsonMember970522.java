package ir.touristland.Models.price;

public class JsonMember970522 {
    private Date date;
    private double boardPrice;
    private double webservicePrice;
    private double extraPersonPrice;
    private double salesPrice;
    private boolean reserveStatus;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getBoardPrice() {
        return boardPrice;
    }

    public void setBoardPrice(double boardPrice) {
        this.boardPrice = boardPrice;
    }

    public double getWebservicePrice() {
        return webservicePrice;
    }

    public void setWebservicePrice(double webservicePrice) {
        this.webservicePrice = webservicePrice;
    }

    public double getExtraPersonPrice() {
        return extraPersonPrice;
    }

    public void setExtraPersonPrice(double extraPersonPrice) {
        this.extraPersonPrice = extraPersonPrice;
    }

    public double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(double salesPrice) {
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
                "JsonMember970522{" +
                        "date = '" + date + '\'' +
                        ",boardPrice = '" + boardPrice + '\'' +
                        ",webservicePrice = '" + webservicePrice + '\'' +
                        ",extraPersonPrice = '" + extraPersonPrice + '\'' +
                        ",salesPrice = '" + salesPrice + '\'' +
                        ",reserveStatus = '" + reserveStatus + '\'' +
                        "}";
    }
}

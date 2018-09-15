package ir.touristland.Models.HotelPrices;

import com.google.gson.annotations.SerializedName;

public class TotalPrice {

    @SerializedName("webservice")
    private int webservice;

    @SerializedName("sales")
    private int sales;

    public int getWebservice() {
        return webservice;
    }

    public void setWebservice(int webservice) {
        this.webservice = webservice;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return
                "TotalPrice{" +
                        "webservice = '" + webservice + '\'' +
                        ",sales = '" + sales + '\'' +
                        "}";
    }
}
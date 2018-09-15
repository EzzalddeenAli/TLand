package ir.touristland.Models.HotelPrices;

import com.google.gson.annotations.SerializedName;

public class Discount {

    @SerializedName("webservice")
    private String webservice;

    @SerializedName("sales")
    private String sales;

    public String getWebservice() {
        return webservice;
    }

    public void setWebservice(String webservice) {
        this.webservice = webservice;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return
                "Discount{" +
                        "webservice = '" + webservice + '\'' +
                        ",sales = '" + sales + '\'' +
                        "}";
    }
}
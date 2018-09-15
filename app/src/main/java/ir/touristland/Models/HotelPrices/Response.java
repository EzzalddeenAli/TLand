package ir.touristland.Models.HotelPrices;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("result")
    private List<ResultItem> result;

    public List<ResultItem> getResult() {
        return result;
    }

    public void setResult(List<ResultItem> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return
                "Response{" +
                        "result = '" + result + '\'' +
                        "}";
    }
}
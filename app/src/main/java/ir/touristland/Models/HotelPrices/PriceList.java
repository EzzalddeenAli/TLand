package ir.touristland.Models.HotelPrices;

import com.google.gson.annotations.SerializedName;

public class PriceList {

    @SerializedName("97-05-20")
    private JsonMember970520 jsonMember970520;

    @SerializedName("97-05-21")
    private JsonMember970521 jsonMember970521;

    @SerializedName("97-05-22")
    private JsonMember970522 jsonMember970522;

    public JsonMember970520 getJsonMember970520() {
        return jsonMember970520;
    }

    public void setJsonMember970520(JsonMember970520 jsonMember970520) {
        this.jsonMember970520 = jsonMember970520;
    }

    public JsonMember970521 getJsonMember970521() {
        return jsonMember970521;
    }

    public void setJsonMember970521(JsonMember970521 jsonMember970521) {
        this.jsonMember970521 = jsonMember970521;
    }

    public JsonMember970522 getJsonMember970522() {
        return jsonMember970522;
    }

    public void setJsonMember970522(JsonMember970522 jsonMember970522) {
        this.jsonMember970522 = jsonMember970522;
    }

    @Override
    public String toString() {
        return
                "PriceList{" +
                        "97-05-20 = '" + jsonMember970520 + '\'' +
                        ",97-05-21 = '" + jsonMember970521 + '\'' +
                        ",97-05-22 = '" + jsonMember970522 + '\'' +
                        "}";
    }
}
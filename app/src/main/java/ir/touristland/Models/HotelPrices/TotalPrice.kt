package ir.touristland.Models.HotelPrices

import com.google.gson.annotations.SerializedName

class TotalPrice {

    @SerializedName("webservice")
    var webservice: Int = 0

    @SerializedName("sales")
    var sales: Int = 0

    override fun toString(): String {
        return "TotalPrice{" +
                "webservice = '" + webservice + '\''.toString() +
                ",sales = '" + sales + '\''.toString() +
                "}"
    }
}
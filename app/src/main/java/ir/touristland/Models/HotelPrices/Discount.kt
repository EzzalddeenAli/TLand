package ir.touristland.Models.HotelPrices

import com.google.gson.annotations.SerializedName

class Discount {

    @SerializedName("webservice")
    var webservice: String? = null

    @SerializedName("sales")
    var sales: String? = null

    override fun toString(): String {
        return "Discount{" +
                "webservice = '" + webservice + '\''.toString() +
                ",sales = '" + sales + '\''.toString() +
                "}"
    }
}
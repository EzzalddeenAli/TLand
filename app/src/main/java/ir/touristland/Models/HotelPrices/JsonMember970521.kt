package ir.touristland.Models.HotelPrices

import com.google.gson.annotations.SerializedName

class JsonMember970521 {

    @SerializedName("date")
    var date: Date? = null

    @SerializedName("boardPrice")
    var boardPrice: Int = 0

    @SerializedName("webservicePrice")
    var webservicePrice: Int = 0

    @SerializedName("extraPersonPrice")
    var extraPersonPrice: Int = 0

    @SerializedName("salesPrice")
    var salesPrice: Int = 0

    @SerializedName("reserveStatus")
    var isReserveStatus: Boolean = false

    override fun toString(): String {
        return "JsonMember970521{" +
                "date = '" + date + '\''.toString() +
                ",boardPrice = '" + boardPrice + '\''.toString() +
                ",webservicePrice = '" + webservicePrice + '\''.toString() +
                ",extraPersonPrice = '" + extraPersonPrice + '\''.toString() +
                ",salesPrice = '" + salesPrice + '\''.toString() +
                ",reserveStatus = '" + isReserveStatus + '\''.toString() +
                "}"
    }
}
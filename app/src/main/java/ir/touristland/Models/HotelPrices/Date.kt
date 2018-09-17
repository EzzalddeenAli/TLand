package ir.touristland.Models.HotelPrices

import com.google.gson.annotations.SerializedName

class Date {

    @SerializedName("timeStamp")
    var timeStamp: String? = null

    @SerializedName("persianDate")
    var persianDate: String? = null

    @SerializedName("englishDate")
    var englishDate: String? = null

    override fun toString(): String {
        return "Date{" +
                "timeStamp = '" + timeStamp + '\''.toString() +
                ",persianDate = '" + persianDate + '\''.toString() +
                ",englishDate = '" + englishDate + '\''.toString() +
                "}"
    }
}
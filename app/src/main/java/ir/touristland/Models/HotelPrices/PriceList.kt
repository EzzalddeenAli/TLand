package ir.touristland.Models.HotelPrices

import com.google.gson.annotations.SerializedName

class PriceList {

    @SerializedName("97-05-20")
    var jsonMember970520: JsonMember970520? = null

    @SerializedName("97-05-21")
    var jsonMember970521: JsonMember970521? = null

    @SerializedName("97-05-22")
    var jsonMember970522: JsonMember970522? = null

    override fun toString(): String {
        return "PriceList{" +
                "97-05-20 = '" + jsonMember970520 + '\''.toString() +
                ",97-05-21 = '" + jsonMember970521 + '\''.toString() +
                ",97-05-22 = '" + jsonMember970522 + '\''.toString() +
                "}"
    }
}
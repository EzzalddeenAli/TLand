package ir.touristland.Models.HotelPrices

import com.google.gson.annotations.SerializedName

class Response {

    @SerializedName("result")
    var result: List<ResultItem>? = null

    override fun toString(): String {
        return "Response{" +
                "result = '" + result + '\''.toString() +
                "}"
    }
}
package ir.touristland.Models.HotelList

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class ResultItem : Serializable {
    @SerializedName("images")
    val images: String? = null

    @SerializedName("address")
    val address: String? = null

    @SerializedName("isMoj")
    val isIsMoj: Boolean = false

    @SerializedName("star")
    val star: String? = null

    @SerializedName("onlineReservation")
    val isOnlineReservation: Boolean = false

    @SerializedName("cityNameFa")
    val cityNameFa: String? = null

    @SerializedName("latitude")
    val latitude: String? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("discount")
    val discount: String? = null

    @SerializedName("numberOfRooms")
    val numberOfRooms: String? = null

    @SerializedName("sort")
    val sort: String? = null

    @SerializedName("type")
    val type: String? = null

    @SerializedName("blackFriday")
    val blackFriday: String? = null

    @SerializedName("hotelDiscountPrice")
    val hotelDiscountPrice: String? = null
    @SerializedName("cityName")
    var cityName: String? = null
    @SerializedName("price")
    val price = 0
    @SerializedName("name")
    val name: String? = null
    @SerializedName("nameFa")
    val nameFa: String? = null
    @SerializedName("id")
    var id: String? = null
    @SerializedName("category")
    val category: String? = null
    @SerializedName("class")
    val jsonMemberClass: String? = null
    @SerializedName("longitude")
    val longitude: String? = null

    companion object {
        const val serialVersionUID = 1L
    }
}
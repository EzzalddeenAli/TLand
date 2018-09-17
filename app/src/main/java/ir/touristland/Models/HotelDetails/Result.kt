package ir.touristland.Models.HotelDetails

import com.google.gson.annotations.SerializedName

class Result {

    @SerializedName("images")
    val images: List<String>? = null

    @SerializedName("rooms")
    val rooms: List<RoomsItem>? = null

    @SerializedName("isMoj")
    val isIsMoj: Boolean = false

    @SerializedName("onlineReservation")
    val isOnlineReservation: Boolean = false

    @SerializedName("discount")
    val discount: String? = null

    @SerializedName("numberOfRooms")
    val numberOfRooms: String? = null

    @SerializedName("rules")
    val rules: List<RulesItem>? = null

    @SerializedName("shelik24")
    val isShelik24: Boolean = false

    @SerializedName("moreInfo")
    val moreInfo: List<MoreInfoItem>? = null

    @SerializedName("blackFriday")
    val blackFriday: String? = null

    @SerializedName("hotelDiscountPrice")
    val hotelDiscountPrice: String? = null

    @SerializedName("rate")
    val rate: Rate? = null

    @SerializedName("imageURL")
    val imageURL: ImageURL? = null

    @SerializedName("options")
    val options: Options? = null

    @SerializedName("id")
    val id: String? = null
}
package ir.touristland.Models.HotelDetails

import com.google.gson.annotations.SerializedName

class RoomOptionsItem {

    @SerializedName("name")
    val name: String? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("value")
    val value: List<ValueItem>? = null

    @SerializedName("key")
    val key: String? = null
}
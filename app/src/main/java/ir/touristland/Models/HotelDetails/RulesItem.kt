package ir.touristland.Models.HotelDetails


import com.google.gson.annotations.SerializedName

class RulesItem {

    @SerializedName("name")
    val name: String? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("key")
    val key: String? = null

    @SerializedName("value")
    val value: List<ValueItem>? = null
}
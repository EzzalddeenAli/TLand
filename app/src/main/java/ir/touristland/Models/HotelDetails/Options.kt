package ir.touristland.Models.HotelDetails

import com.google.gson.annotations.SerializedName

class Options {

    @SerializedName("hotelOptions")
    val hotelOptions: List<HotelOptionsItem>? = null

    @SerializedName("extraOptions")
    val extraOptions: ExtraOptions? = null

    @SerializedName("roomOptions")
    val roomOptions: List<RoomOptionsItem>? = null
}
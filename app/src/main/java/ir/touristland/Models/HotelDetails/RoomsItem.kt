package ir.touristland.Models.HotelDetails


import com.google.gson.annotations.SerializedName

class RoomsItem {

    @SerializedName("doubleBeds")
    val doubleBeds: String? = null

    @SerializedName("image")
    val image: String? = null

    @SerializedName("sofa")
    val sofa: String? = null

    @SerializedName("reservationStatus")
    var isReservationStatus: Boolean = false

    @SerializedName("fullBoard")
    val isFullBoard: Boolean = false

    @SerializedName("description")
    val description: String? = null

    @SerializedName("extraPersons")
    val extraPersons: Int = 0

    @SerializedName("sort")
    val sort: String? = null

    @SerializedName("type")
    val type: String? = null

    @SerializedName("isSpecialDate")
    val isSpecialDate: String? = null

    @SerializedName("persons")
    val persons: String? = null

    @SerializedName("isSpecial")
    val isIsSpecial: Boolean = false

    @SerializedName("singleBeds")
    val singleBeds: String? = null

    @SerializedName("nameFa")
    val nameFa: String? = null

    @SerializedName("id")
    val id: Int = 0

    @SerializedName("beds")
    val beds: String? = null

    @SerializedName("price")
    var price: String? = null
}
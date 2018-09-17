package ir.touristland.Models.HotelPrices

import com.google.gson.annotations.SerializedName

class ResultItem {

    /* @SerializedName("priceList")
    private Object priceList;*/

    @SerializedName("totalPrice")
    var totalPrice: TotalPrice? = null

    @SerializedName("onlineReservation")
    var isOnlineReservation: Boolean = false

    @SerializedName("fullBoard")
    var isFullBoard: Boolean = false

    @SerializedName("nameFa")
    var nameFa: String? = null

    @SerializedName("discount")
    var discount: Discount? = null

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("beds")
    var beds: String? = null

    @SerializedName("reservationStatus")
    var isReservationStatus: Boolean = false

    /*public Object getPriceList() {
        return priceList;
    }

    public void setPriceList(Object priceList) {
        this.priceList = priceList;
    }*/
}
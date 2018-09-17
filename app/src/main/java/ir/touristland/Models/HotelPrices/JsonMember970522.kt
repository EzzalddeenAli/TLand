package ir.touristland.Models.HotelPrices

import com.google.gson.annotations.SerializedName

class JsonMember970522 {

    /*@SerializedName("date")
    private Date date;*/

    /*public void setDate(Date date){
		this.date = date;
	}

	public Date getDate(){
		return date;
	}*/

    @SerializedName("boardPrice")
    var boardPrice: Int = 0

    @SerializedName("webservicePrice")
    var webservicePrice: Int = 0

    @SerializedName("extraPersonPrice")
    var extraPersonPrice: Int = 0

    @SerializedName("salesPrice")
    var salesPrice: Int = 0

    @SerializedName("reserveStatus")
    var isReserveStatus: Boolean = false

    override fun toString(): String {
        return "JsonMember970522{" +
                ",boardPrice = '" + boardPrice + '\''.toString() +
                ",webservicePrice = '" + webservicePrice + '\''.toString() +
                ",extraPersonPrice = '" + extraPersonPrice + '\''.toString() +
                ",salesPrice = '" + salesPrice + '\''.toString() +
                ",reserveStatus = '" + isReserveStatus + '\''.toString() +
                "}"
    }
}
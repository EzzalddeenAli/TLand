package ir.touristland.Models.FlightList

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Response : Serializable {

    @SerializedName("OnlyTour")
    val onlyTour: Int = 0

    @SerializedName("ParvazId")
    val parvazId: Int = 0

    @SerializedName("ClassId")
    val classId: Int = 0

    @SerializedName("PriceINF")
    val priceINF: Int = 0

    @SerializedName("FlightNo")
    val flightNo: String? = null

    @SerializedName("IataCodDestinate")
    val iataCodDestinate: String? = null

    @SerializedName("FlightTime")
    val flightTime: String? = null

    @SerializedName("AirlineCode")
    val airlineCode: String? = null

    @SerializedName("PriceView")
    val priceView: Int = 0

    @SerializedName("FlightDateTime")
    val flightDateTime: String? = null

    @SerializedName("OnlyPhone")
    val onlyPhone: Int = 0

    @SerializedName("OnlyTwoWay")
    val onlyTwoWay: Int = 0

    @SerializedName("Srv")
    val srv: String? = null

    @SerializedName("ServiceType")
    val serviceType: Int = 0

    @SerializedName("DscFlight")
    val dscFlight: String? = null

    @SerializedName("AirPlaneName")
    val airPlaneName: String? = null

    @SerializedName("IataCodSource")
    val iataCodSource: String? = null

    @SerializedName("FlightNoBack")
    val flightNoBack: Any? = null

    @SerializedName("Terminal")
    val terminal: Any? = null

    @SerializedName("SrvPrice")
    val srvPrice: Int = 0

    @SerializedName("KndSys")
    val kndSys: Int = 0

    @SerializedName("Reservable")
    val reservable: Int = 0

    @SerializedName("FlightDateBack")
    val flightDateBack: Any? = null

    @SerializedName("Class")
    val classFlight: String? = null

    @SerializedName("FlightDateM")
    val flightDateM: String? = null

    @SerializedName("CapLast")
    val capLast: Int = 0

    @SerializedName("fromCity")
    var fromCity: String? = null

    @SerializedName("toCity")
    var toCity: String? = null

    @SerializedName("AirlineName")
    var airlineName: String? = null

    companion object {

        const val serialVersionUID = 1L
    }
}
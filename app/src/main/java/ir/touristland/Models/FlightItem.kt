package ir.touristland.Models

import java.io.Serializable

class FlightItem : Serializable {
    var flightInDateID = ""
    var titleFlight = ""
    var fromAirPort = ""
    var fromAbbreviation = ""
    var toAirPort = ""
    var startTime = ""
    var arrivalTime = ""
    var sessionID = ""
    var isSystemic: Boolean = false
    var isRefundable: Boolean = false
    var price = ""
    var airPlaneTitle = ""
    var count = ""
    var fromCity = ""
    var toCity = ""
    var airLineTitle = ""
    var cabinType = ""
    var images = ""

    companion object {

        const val serialVersionUID = 1L
    }

}

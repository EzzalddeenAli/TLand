package ir.touristland.Models;

import java.io.Serializable;

public class FlightItem implements Serializable {

    public static final long serialVersionUID = 1L;
    public String FlightInDateID = "";
    public String TitleFlight = "";
    public String FromAirPort = "";
    public String FromAbbreviation = "";
    public String ToAirPort = "";
    public String StartTime = "";
    public String ArrivalTime = "";
    public String SessionID = "";
    public boolean IsSystemic;
    public boolean IsRefundable;
    public String Price = "";
    public String AirPlaneTitle = "";
    public String Count = "";
    public String FromCity = "";
    public String ToCity = "";
    public String AirLineTitle = "";
    public String CabinType = "";
    public String Images = "";

    public String getImages() {
        return Images;
    }

    public String getFlightInDateID() {
        return FlightInDateID;
    }

    public String getTitleFlight() {
        return TitleFlight;
    }

    public String getFromAirPort() {
        return FromAirPort;
    }

    public String getFromAbbreviation() {
        return FromAbbreviation;
    }

    public String getToAirPort() {
        return ToAirPort;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public String getSessionID() {
        return SessionID;
    }

    public boolean getIsSystemic() {
        return IsSystemic;
    }

    public boolean getIsRefundable() {
        return IsRefundable;
    }

    public String getPrice() {
        return Price;
    }

    public String getAirPlaneTitle() {
        return AirPlaneTitle;
    }

    public String getCount() {
        return Count;
    }

    public String getFromCity() {
        return FromCity;
    }

    public void setFromCity(String FromCity) {
        this.FromCity = FromCity;
    }

    public String getToCity() {
        return ToCity;
    }

    public void setToCity(String ToCity) {
        this.ToCity = ToCity;
    }

    public String getAirLineTitle() {
        return AirLineTitle;
    }

    public String getCabinType() {
        return CabinType;
    }

}

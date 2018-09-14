package ir.touristland.Models.FlightList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Response implements Serializable {

    public static final long serialVersionUID = 1L;

    @SerializedName("OnlyTour")
    private int onlyTour;

    @SerializedName("ParvazId")
    private int parvazId;

    @SerializedName("ClassId")
    private int classId;

    @SerializedName("PriceINF")
    private int priceINF;

    @SerializedName("FlightNo")
    private String flightNo;

    @SerializedName("IataCodDestinate")
    private String iataCodDestinate;

    @SerializedName("FlightTime")
    private String flightTime;

    @SerializedName("AirlineCode")
    private String airlineCode;

    @SerializedName("PriceView")
    private int priceView;

    @SerializedName("FlightDateTime")
    private String flightDateTime;

    @SerializedName("OnlyPhone")
    private int onlyPhone;

    @SerializedName("OnlyTwoWay")
    private int onlyTwoWay;

    @SerializedName("Srv")
    private String srv;

    @SerializedName("ServiceType")
    private int serviceType;

    @SerializedName("DscFlight")
    private String dscFlight;

    @SerializedName("AirPlaneName")
    private String airPlaneName;

    @SerializedName("IataCodSource")
    private String iataCodSource;

    @SerializedName("FlightNoBack")
    private Object flightNoBack;

    @SerializedName("Terminal")
    private Object terminal;

    @SerializedName("SrvPrice")
    private int srvPrice;

    @SerializedName("KndSys")
    private int kndSys;

    @SerializedName("Reservable")
    private int reservable;

    @SerializedName("FlightDateBack")
    private Object flightDateBack;

    @SerializedName("Class")
    private String classFlight;

    @SerializedName("FlightDateM")
    private String flightDateM;

    @SerializedName("CapLast")
    private int capLast;

    @SerializedName("fromCity")
    private String fromCity;

    @SerializedName("toCity")
    private String toCity;

    @SerializedName("AirlineName")
    private String AirlineName;

    public int getOnlyTour() {
        return onlyTour;
    }

    public int getParvazId() {
        return parvazId;
    }

    public int getClassId() {
        return classId;
    }

    public int getPriceINF() {
        return priceINF;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public String getIataCodDestinate() {
        return iataCodDestinate;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public int getPriceView() {
        return priceView;
    }

    public String getFlightDateTime() {
        return flightDateTime;
    }

    public int getOnlyPhone() {
        return onlyPhone;
    }

    public int getOnlyTwoWay() {
        return onlyTwoWay;
    }

    public String getSrv() {
        return srv;
    }

    public int getServiceType() {
        return serviceType;
    }

    public String getDscFlight() {
        return dscFlight;
    }

    public String getAirPlaneName() {
        return airPlaneName;
    }

    public String getIataCodSource() {
        return iataCodSource;
    }

    public Object getFlightNoBack() {
        return flightNoBack;
    }

    public Object getTerminal() {
        return terminal;
    }

    public int getSrvPrice() {
        return srvPrice;
    }

    public int getKndSys() {
        return kndSys;
    }

    public int getReservable() {
        return reservable;
    }

    public Object getFlightDateBack() {
        return flightDateBack;
    }

    public String getClassFlight() {
        return classFlight;
    }

    public String getFlightDateM() {
        return flightDateM;
    }

    public int getCapLast() {
        return capLast;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getAirlineName() {
        return AirlineName;
    }

    public void setAirlineName(String AirlineName) {
        this.AirlineName = AirlineName;
    }
}
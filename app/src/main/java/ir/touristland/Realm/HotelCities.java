package ir.touristland.Realm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HotelCities implements Serializable {

    public static final long serialVersionUID = 1L;
    public List<Hotelcity> result = new ArrayList<>();

    public List<Hotelcity> getResult() {
        return result;
    }
}

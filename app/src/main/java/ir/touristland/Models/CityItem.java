package ir.touristland.Models;

import java.io.Serializable;

public class CityItem implements Serializable {

    public static final long serialVersionUID = 1L;
    public String _Code = "";
    public String _city_name_fa = "";

    public String toString() {
        return (_city_name_fa);
    }

    public String getCityNameFa() {
        return _city_name_fa;
    }

    public void setCityNameFa(String city_name_fa) {
        _city_name_fa = city_name_fa;
    }

    public String getCode() {
        return _Code;
    }

    public void setCode(String Code) {
        _Code = Code;
    }

}

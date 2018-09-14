package ir.touristland.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZAMAN on 5/22/2018.
 */

public class HotelDetail {
    private static final HotelDetail ourInstance = new HotelDetail();
    public int StartDate = 1;
    public int EndDate = 0;
    public int Destination = 0;

    public Map<String, String> params = new HashMap<>();

    private HotelDetail() {
    }

    public static HotelDetail getInstance() {
        return ourInstance;
    }


    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}

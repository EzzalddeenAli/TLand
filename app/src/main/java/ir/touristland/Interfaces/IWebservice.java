package ir.touristland.Interfaces;

import java.util.List;

import ir.touristland.Models.FlightItem;
import ir.touristland.Models.FlightReserve;

/**
 * Created by ZAMAN on 3/17/2018.
 */

public interface IWebservice {
    void getResult(retrofit2.Response<List<FlightItem>> list) throws Exception;

    void getError() throws Exception;

    interface IFlightReserve {
        void getResult(FlightReserve s) throws Exception;

        void getError(String s) throws Exception;
    }

    interface IWebservice2 {
        void getResult(String s) throws Exception;

        void getError(String s) throws Exception;
    }
}

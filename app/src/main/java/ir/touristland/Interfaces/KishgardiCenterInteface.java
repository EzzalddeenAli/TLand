package ir.touristland.Interfaces;

import java.util.List;

import ir.touristland.Models.CenterItem;

/**
 * Created by ZAMAN on 3/17/2018.
 */

public interface KishgardiCenterInteface {
    void getResult(retrofit2.Response<List<CenterItem>> list) throws Exception;

    void getError() throws Exception;
}

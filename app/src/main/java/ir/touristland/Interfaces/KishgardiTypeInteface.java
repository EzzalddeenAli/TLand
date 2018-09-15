package ir.touristland.Interfaces;

import java.util.List;

import ir.touristland.Models.CenterTypeItem;

/**
 * Created by ZAMAN on 3/17/2018.
 */

public interface KishgardiTypeInteface {
    void getResult(retrofit2.Response<List<CenterTypeItem>> list) throws Exception;

    void getError() throws Exception;
}

package ir.touristland.Models;

import java.io.Serializable;

public class HotelCenterImgItem implements Serializable {

    public static final long serialVersionUID = 1L;

    public String Id = "";
    public String FileName = "";
    public String CenterName = "";

    public String getId() {
        return Id;
    }

    public String getFileName() {
        return FileName;
    }

    public String getCenterName() {
        return CenterName;
    }

}

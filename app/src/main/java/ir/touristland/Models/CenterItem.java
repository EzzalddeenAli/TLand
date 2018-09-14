package ir.touristland.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CenterItem implements Serializable {

    public static final long serialVersionUID = 1L;

    public int Id = 0;
    public String Name = "";
    public String CreateDate = "";
    public String TelCenter = "";
    public String Address = "";
    public String ModirName = "";
    public String ModirTel = "";
    public String Email = "";
    public String Fax = "";
    public String ImgIds = "";
    public String Descript = "";
    public List<String> ListAttributs = new ArrayList<>();

    public List<String> getListAttributs() {
        return ListAttributs;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public String getTelCenter() {
        return TelCenter;
    }

    public String getAddress() {
        return Address;
    }

    public String getModirName() {
        return ModirName;
    }

    public String getModirTel() {
        return ModirTel;
    }

    public String getEmail() {
        return Email;
    }

    public String getFax() {
        return Fax;
    }

    public String getDescript() {
        return Descript;
    }

    public String getImgIds() {
        return ImgIds;
    }

}

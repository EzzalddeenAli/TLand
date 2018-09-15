package ir.touristland.Models;

import java.io.Serializable;

public class CenterTypeItem implements Serializable {

    public static final long serialVersionUID = 1L;
    public int Id = 0;
    public String Name = "";
    public String Status = "";

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getStatus() {
        return Status;
    }
}

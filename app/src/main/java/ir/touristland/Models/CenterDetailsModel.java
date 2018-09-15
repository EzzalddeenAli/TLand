package ir.touristland.Models;

import java.io.Serializable;

public class CenterDetailsModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private String _Id = "";
    private String _title = "";

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        _title = title;
    }

    public String getId() {
        return _Id;
    }

    public void setId(String Id) {
        _Id = Id;
    }


}

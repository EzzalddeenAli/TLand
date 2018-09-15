package ir.touristland.Models;

import java.io.Serializable;

public class HotelPossibilitieItem implements Serializable {

    public static final long serialVersionUID = 1L;
    public String PossibilitiesName = "";
    public int RoomPossibilitiesId = 0;

    public String getPossibilitiesName() {
        return PossibilitiesName;
    }


}

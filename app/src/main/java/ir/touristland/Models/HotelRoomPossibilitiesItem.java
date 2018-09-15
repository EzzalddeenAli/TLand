package ir.touristland.Models;

import java.io.Serializable;

public class HotelRoomPossibilitiesItem implements Serializable {

    public static final long serialVersionUID = 1L;
    public String Id = "";
    public String HotelRoomId = "";
    public String CenterId = "";
    public String RoomPossibilitiesId = "";
    public String RoomPossibilitiesName = "";
    public String IconName = "";

    public String getId() {
        return Id;
    }

    public String getHotelRoomId() {
        return HotelRoomId;
    }

    public String getCenterId() {
        return CenterId;
    }

    public String getRoomPossibilitiesId() {
        return RoomPossibilitiesId;
    }

    public String getRoomPossibilitiesName() {
        return RoomPossibilitiesName;
    }

    public String getIconName() {
        return IconName;
    }

}

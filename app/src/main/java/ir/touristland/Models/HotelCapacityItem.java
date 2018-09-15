package ir.touristland.Models;

import java.io.Serializable;

public class HotelCapacityItem implements Serializable {

    public static final long serialVersionUID = 1L;
    public String Id = "";
    public String HotelRoomId = "";
    public String CenterId = "";
    public String CapicityId = "";
    public String CityCode = "";
    public String Price = "";
    public String RoomTypeId = "";
    public String DescriptRoom;
    public String BedCount;
    public String MaxAdult = "";
    public String MaxChild = "";
    public int RoomCount = 0;

    public String getId() {
        return Id;
    }

    public String getHotelRoomId() {
        return HotelRoomId;
    }

    public String getCenterId() {
        return CenterId;
    }

    public String getCapicityId() {
        return CapicityId;
    }

    public String getCityCode() {
        return CityCode;
    }

    public String getRoomTypeId() {
        return RoomTypeId;
    }

    public String getBedCount() {
        return BedCount;
    }

    public String getMaxAdult() {
        return MaxAdult;
    }

    public String getMaxChild() {
        return MaxChild;
    }

    public String getPrice() {
        return Price;
    }

    public String getDescriptRoom() {
        return DescriptRoom;
    }

    public int getRoomCount() {
        return RoomCount;
    }

    public void setRoomCount(int roomCount) {
        RoomCount = roomCount;
    }
}

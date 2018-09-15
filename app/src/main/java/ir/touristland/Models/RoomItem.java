package ir.touristland.Models;

import java.io.Serializable;
import java.util.List;

public class RoomItem implements Serializable {

    public static final long serialVersionUID = 1L;

    public String RoomId = "";
    public String RoomTypeName = "";
    public String RoomNumber = "";
    public String Price = "";
    public String BedCount = "";
    public String MaxAdult = "";
    public String MaxChild = "";
    public String RoomImgIdsStr = "";
    public int roomCount = 0;
    public List<String> RoomAttributes;

    public String getRoomId() {
        return RoomId;
    }

    public void setRoomId(String roomId) {
        RoomId = roomId;
    }

    public String getRoomTypeName() {
        return RoomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        RoomTypeName = roomTypeName;
    }

    public String getRoomNumber() {
        return RoomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        RoomNumber = roomNumber;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getBedCount() {
        return BedCount;
    }

    public void setBedCount(String bedCount) {
        BedCount = bedCount;
    }

    public String getMaxAdult() {
        return MaxAdult;
    }

    public void setMaxAdult(String maxAdult) {
        MaxAdult = maxAdult;
    }

    public String getMaxChild() {
        return MaxChild;
    }

    public void setMaxChild(String maxChild) {
        MaxChild = maxChild;
    }

    public String getRoomImgIdsStr() {
        return RoomImgIdsStr;
    }

    public void setRoomImgIdsStr(String roomImgIdsStr) {
        RoomImgIdsStr = roomImgIdsStr;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public List<String> getRoomAttributes() {
        return RoomAttributes;
    }

    public void setRoomAttributes(List<String> roomAttributes) {
        RoomAttributes = roomAttributes;
    }
}

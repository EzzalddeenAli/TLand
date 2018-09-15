package ir.touristland.Models.HotelPrices;

import com.google.gson.annotations.SerializedName;

public class Date {

    @SerializedName("timeStamp")
    private String timeStamp;

    @SerializedName("persianDate")
    private String persianDate;

    @SerializedName("englishDate")
    private String englishDate;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPersianDate() {
        return persianDate;
    }

    public void setPersianDate(String persianDate) {
        this.persianDate = persianDate;
    }

    public String getEnglishDate() {
        return englishDate;
    }

    public void setEnglishDate(String englishDate) {
        this.englishDate = englishDate;
    }

    @Override
    public String toString() {
        return
                "Date{" +
                        "timeStamp = '" + timeStamp + '\'' +
                        ",persianDate = '" + persianDate + '\'' +
                        ",englishDate = '" + englishDate + '\'' +
                        "}";
    }
}
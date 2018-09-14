package ir.touristland.Models.price;

public class Date {
    private int timeStamp;
    private int persianDate;
    private String englishDate;

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getPersianDate() {
        return persianDate;
    }

    public void setPersianDate(int persianDate) {
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

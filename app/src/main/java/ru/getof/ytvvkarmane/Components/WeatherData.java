package ru.getof.ytvvkarmane.Components;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherData {

    @SerializedName("STATUS")
    @Expose
    private String Status;
    @SerializedName("TEMP")
    @Expose
    private String Temp;
    @SerializedName("HUM")
    @Expose
    private String Hum;
    @SerializedName("PRESS")
    @Expose
    private String Press;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTemp() {
        return Temp;
    }

    public void setTemp(String temp) {
        Temp = temp;
    }

    public String getHum() {
        return Hum;
    }

    public void setHum(String hum) {
        Hum = hum;
    }

    public String getPress() {
        return Press;
    }

    public void setPress(String press) {
        Press = press;
    }
}

package ru.getof.ytvvkarmane.Components;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RunId {

    @SerializedName("id")
    @Expose
    private String idRun;
    @SerializedName("url")
    @Expose
    private String urlRun;


    public String getUrlRun() {
        return urlRun;
    }

    public void setUrlRun(String urlRun) {
        this.urlRun = urlRun;
    }

    public String getIdRun() {
        return idRun;
    }

    public void setIdRun(String idRun) {
        this.idRun = idRun;
    }
}

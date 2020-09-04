package ru.getof.ytvvkarmane.Components.RunData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RunData {

    @SerializedName("runStartDate")
    @Expose
    private String runStartDate;

    @SerializedName("runText")
    @Expose
    private String runText;

    public String getRunStartDate() {
        return runStartDate;
    }

    public void setRunStartDate(String runStartDate) {
        this.runStartDate = runStartDate;
    }

    public String getRunText() {
        return runText;
    }

    public void setRunText(String runText) {
        this.runText = runText;
    }

}

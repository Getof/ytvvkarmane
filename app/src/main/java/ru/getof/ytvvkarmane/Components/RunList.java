package ru.getof.ytvvkarmane.Components;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.getof.ytvvkarmane.Components.RunData.RunData;

public class RunList {

    @SerializedName("runMessList")
    @Expose
    private List<RunData> runList = null;

    public List<RunData> getRunList() {
        return runList;
    }

    public void setRunList(List<RunData> runList) {
        this.runList = runList;
    }
}

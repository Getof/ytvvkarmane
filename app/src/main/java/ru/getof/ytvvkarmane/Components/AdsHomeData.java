package ru.getof.ytvvkarmane.Components;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.getof.ytvvkarmane.Components.AdsData.AdsHomeList;

public class AdsHomeData {

    @SerializedName("adsHome")
    @Expose
    private List<AdsHomeList> adsBannerHome = null;

    public List<AdsHomeList> getAdsBannerHome() {
        return adsBannerHome;
    }

    public void setAdsBannerHome(List<AdsHomeList> adsBannerHome) {
        this.adsBannerHome = adsBannerHome;
    }
}

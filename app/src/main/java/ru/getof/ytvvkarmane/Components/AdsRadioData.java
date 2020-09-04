package ru.getof.ytvvkarmane.Components;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.getof.ytvvkarmane.Components.AdsData.AdsRadioList;

public class AdsRadioData {

    @SerializedName("adsRadio")
    @Expose
    private List<AdsRadioList> adsBannerRadio = null;

    public List<AdsRadioList> getAdsBannerRadio() {
        return adsBannerRadio;
    }

    public void setAdsBannerRadio(List<AdsRadioList> adsBannerRadio) {
        this.adsBannerRadio = adsBannerRadio;
    }
}

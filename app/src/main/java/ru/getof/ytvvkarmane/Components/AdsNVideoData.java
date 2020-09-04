package ru.getof.ytvvkarmane.Components;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.getof.ytvvkarmane.Components.AdsData.AdsNVideoList;

public class AdsNVideoData {

    @SerializedName("adsNewvideo")
    @Expose
    private List<AdsNVideoList> adsBannerNVideo = null;

    public List<AdsNVideoList> getAdsBannerNVideo() {
        return adsBannerNVideo;
    }

    public void setAdsBannerNVideo(List<AdsNVideoList> adsBannerNVideo) {
        this.adsBannerNVideo = adsBannerNVideo;
    }
}

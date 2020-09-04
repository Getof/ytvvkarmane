package ru.getof.ytvvkarmane.Components.AdsData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdsRadioList {

    @SerializedName("imgUrl")
    @Expose
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

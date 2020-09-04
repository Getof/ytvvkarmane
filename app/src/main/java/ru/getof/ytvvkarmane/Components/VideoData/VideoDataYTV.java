package ru.getof.ytvvkarmane.Components.VideoData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.getof.ytvvkarmane.Components.VideoData.NewsList;

public class VideoDataYTV {

    @SerializedName("news")
    @Expose
    private List<NewsList> newsData = null;

    public List<NewsList> getNewsData() {
        return newsData;
    }

    public void setNewsData(List<NewsList> newsData) {
        this.newsData = newsData;
    }

}

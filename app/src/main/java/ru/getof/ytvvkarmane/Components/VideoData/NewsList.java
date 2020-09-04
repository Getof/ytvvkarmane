package ru.getof.ytvvkarmane.Components.VideoData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsList {

    @SerializedName("title")
    @Expose
    private String title_news;
    @SerializedName("video_id")
    @Expose
    private String id_video;
    @SerializedName("data_pub")
    @Expose
    private String data_news;
    @SerializedName("tumb_url")
    @Expose
    private String tumb_news;

    public String getTitle_news() {
        return title_news;
    }

    public void setTitle_news(String title_news) {
        this.title_news = title_news;
    }

    public String getId_video() {
        return id_video;
    }

    public void setId_video(String id_video) {
        this.id_video = id_video;
    }

    public String getData_news() {
        return data_news;
    }

    public void setData_news(String data_news) {
        this.data_news = data_news;
    }

    public String getTumb_news() {
        return tumb_news;
    }

    public void setTumb_news(String tumb_news) {
        this.tumb_news = tumb_news;
    }

}

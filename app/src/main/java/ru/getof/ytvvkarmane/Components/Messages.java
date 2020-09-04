package ru.getof.ytvvkarmane.Components;

import java.util.Date;

public class Messages {


    private Date dateMessage;
    private String nameUser;
    private String urlVideo;
    private String descMess;
    private int confirmMess;
    private String thumb;

    public Messages() {
        // Empty constructor need
    }

    public Messages(Date dateMessage, String nameUser, String urlVideo, String descMess, int confirmMess, String thumb) {
        this.dateMessage = dateMessage;
        this.nameUser = nameUser;
        this.urlVideo = urlVideo;
        this.descMess = descMess;
        this.confirmMess = confirmMess;
        this.thumb = thumb;
    }

    public Date getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(Date dateMessage) {
        this.dateMessage = dateMessage;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getDescMess() {
        return descMess;
    }

    public void setDescMess(String descMess) {
        this.descMess = descMess;
    }

    public int getConfirmMess() {
        return confirmMess;
    }

    public void setConfirmMess(int confirmMess) {
        this.confirmMess = confirmMess;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}

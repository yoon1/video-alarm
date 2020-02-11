package com.example.videoalarm.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class idYoutube {

    @SerializedName("kind")
    @Expose
    private String kind;

    @SerializedName("videoId")
    @Expose
    private String videoId;

    public idYoutube() {
    }

    public idYoutube(String kind, String videoId) {
        this.kind = kind;
        this.videoId = videoId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}

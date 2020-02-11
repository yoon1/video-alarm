package com.example.videoalarm.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SnippetYoutube {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("thumbnails")
    @Expose
    private ThumbnailYoutube thumbnails;

    public SnippetYoutube() {
    }

    public SnippetYoutube(String title, ThumbnailYoutube thumbnails) {
        this.title = title;
        this.thumbnails = thumbnails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ThumbnailYoutube getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(ThumbnailYoutube thumbnails) {
        this.thumbnails = thumbnails;
    }
}

package com.example.videoalarm.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoYoutube {
    @SerializedName("id")
    @Expose
    private idYoutube id;

    @SerializedName("snippet")
    @Expose
    private SnippetYoutube snippet;

    public VideoYoutube() {
    }

    public VideoYoutube(idYoutube id, SnippetYoutube snippet) {
        this.id = id;
        this.snippet = snippet;
    }

    public idYoutube getId() {
        return id;
    }

    public void setId(idYoutube id) {
        this.id = id;
    }

    public SnippetYoutube getSnippet() {
        return snippet;
    }

    public void setSnippet(SnippetYoutube snippet) {
        this.snippet = snippet;
    }
}


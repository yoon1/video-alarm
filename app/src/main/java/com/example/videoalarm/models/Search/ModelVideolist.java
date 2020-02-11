package com.example.videoalarm.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelVideolist {
    @SerializedName("pageInfo")
    @Expose
    private PageInfoYoutube pageinfo;

    @SerializedName("items")
    @Expose
    private List<VideoYoutube> items;

    public ModelVideolist() {
    }

    public ModelVideolist(PageInfoYoutube pageinfo, List<VideoYoutube> items) {
        this.pageinfo = pageinfo;
        this.items = items;
    }

    public PageInfoYoutube getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(PageInfoYoutube pageinfo) {
        this.pageinfo = pageinfo;
    }

    public List<VideoYoutube> getItems() {
        return items;
    }

    public void setItems(List<VideoYoutube> items) {
        this.items = items;
    }
}

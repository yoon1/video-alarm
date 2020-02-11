package com.example.videoalarm.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageInfoYoutube {
    @SerializedName("totalResults")
    @Expose
    private String totalResults;

    @SerializedName("resultsPerPage")
    @Expose
    private String resultsPerPage;

    public PageInfoYoutube() {
    }

    public PageInfoYoutube(String totalResults, String resultsPerPage) {
        this.totalResults = totalResults;
        this.resultsPerPage = resultsPerPage;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(String resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }
}

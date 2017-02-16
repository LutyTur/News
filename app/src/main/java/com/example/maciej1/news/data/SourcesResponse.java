package com.example.maciej1.news.data;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.List;


public class SourcesResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("sources")
    private List<SourceEntry> sources;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SourceEntry> getSources() {
        return sources;
    }

    public void setSources(List<SourceEntry> sources) {
        this.sources = sources;
    }
}

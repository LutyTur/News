package com.example.maciej1.news.data;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.List;


public class ApiResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("sources")
    private List<SourceEntry> sources;

    @SerializedName("articles")
    private List<ArticleEntry> articles;


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

    public List<ArticleEntry> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleEntry> articles) {
        this.articles = articles;
    }
}

package com.example.maciej1.news.data;

import org.json.JSONArray;

/**
 * Created by Maciej1 on 2017-02-12.
 */

public class SourceEntry {

    private String id, name, description, url, category, language;
    private JSONArray logos, sortBysAvailable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public JSONArray getLogos() {
        return logos;
    }

    public void setLogos(JSONArray logos) {
        this.logos = logos;
    }

    public JSONArray getSortBysAvailable() {
        return sortBysAvailable;
    }

    public void setSortBysAvailable(JSONArray sortBysAvailable) {
        this.sortBysAvailable = sortBysAvailable;
    }
}

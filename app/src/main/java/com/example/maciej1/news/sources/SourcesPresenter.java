package com.example.maciej1.news.sources;

import com.example.maciej1.news.data.SourceEntry;
import com.example.maciej1.news.data.SourcesLoaderListener;
import com.example.maciej1.news.data.SourcesAsyncLoader;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej1 on 2017-02-11.
 */

public class SourcesPresenter extends MvpBasePresenter<SourcesView> implements SourcesLoaderListener {

    public void loadSources(){
        SourcesAsyncLoader sources = new SourcesAsyncLoader();
        sources.delegate = this;
        sources.execute();
    }

    @Override
    public void onProcessFinish(String output) {
        getView().showSources(jsonToSourceEntriesList(output));
    }

    private List<SourceEntry> jsonToSourceEntriesList(String jsonString){
        List<SourceEntry> sourceEntries = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray sources = jsonObject.getJSONArray("sources");

            for (int i = 0; i < sources.length(); i++){
                JSONObject source = sources.getJSONObject(i);
                SourceEntry entry = new SourceEntry();
                entry.setName(source.getString("name"));
                entry.setUrl(source.getString("url"));
                sourceEntries.add(entry);
            }

        } catch (final JSONException e){

        }

        return sourceEntries;
    }
}

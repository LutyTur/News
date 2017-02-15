package com.example.maciej1.news.sources;

import android.util.Log;

import com.example.maciej1.news.data.ApiClient;
import com.example.maciej1.news.data.ApiInterface;
import com.example.maciej1.news.data.SourceEntry;
import com.example.maciej1.news.data.SourcesLoaderListener;
import com.example.maciej1.news.data.SourcesAsyncLoader;
import com.example.maciej1.news.data.SourcesResponse;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Maciej1 on 2017-02-11.
 */

public class SourcesPresenter extends MvpBasePresenter<SourcesView> implements SourcesLoaderListener {

    private static final String API_KEY = "c170c634ec2a4381aac741f46d9aee4d";

    public void loadSources(){
        SourcesAsyncLoader sources = new SourcesAsyncLoader();
        sources.delegate = this;
        sources.execute();
    }

    @Override
    public void onProcessFinish(String output) {
        //getView().showSources(jsonToSourceEntriesList(output));
    }

    private List<SourceEntry> jsonToSourceEntriesList(String jsonString){
        List<SourceEntry> sourceEntries = new ArrayList<>();
        /*
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
        */
        return sourceEntries;
    }

    public void setupApiListener(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<SourcesResponse> call = apiService.getEnglishSources(API_KEY);
        call.enqueue(new Callback<SourcesResponse>() {
            @Override
            public void onResponse(Call<SourcesResponse> call, Response<SourcesResponse> response) {
                List<SourceEntry> sourceEntries = response.body().getSources();
                getView().showSources(sourceEntries);
            }

            @Override
            public void onFailure(Call<SourcesResponse> call, Throwable t) {
                Log.e("onFailure: ", t.toString());
            }
        });
    }
}

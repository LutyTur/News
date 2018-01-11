package com.example.maciej1.news.ui.sources;

import android.util.Log;

import com.example.maciej1.news.data.ApiClient;
import com.example.maciej1.news.data.ApiInterface;
import com.example.maciej1.news.data.ApiResponse;
import com.example.maciej1.news.data.SourceEntry;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SourcesPresenter extends MvpBasePresenter<SourcesView> {


    public void startApiService(String apiKey) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ApiResponse> call = apiService.getEnglishSources(apiKey);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                List<SourceEntry> sourceEntries = response.body().getSources();
                getView().showSources(sourceEntries);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("onFailure: ", t.toString());
            }
        });
    }

    public void startArticlesFragment(String id) {
        getView().inflateArticlesFragment(id);
    }

    public void reloadFragment(){
        getView().reloadFragment();
    }
}

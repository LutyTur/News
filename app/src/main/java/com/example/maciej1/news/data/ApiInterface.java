package com.example.maciej1.news.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("sources?language=en&")
    Call<SourcesResponse> getEnglishSources(@Query("api_key") String apiKey);
}

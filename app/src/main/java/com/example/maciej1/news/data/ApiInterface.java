package com.example.maciej1.news.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("sources?language=en")
    Call<ApiResponse> getEnglishSources(@Query("api_key") String apiKey);

    @GET("articles?")
    Call<ApiResponse> getArticlesFromSource(@Query("source") String source,
                                            @Query("apiKey") String apiKey);
}

package com.example.maciej1.news.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Maciej1 on 2017-02-13.
 */

public interface ApiInterface {

    @GET("sources?language=en&")
    Call<SourcesResponse> getEnglishSources(@Query("api_key")String apiKey);
}

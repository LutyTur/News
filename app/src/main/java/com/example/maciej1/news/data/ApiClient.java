package com.example.maciej1.news.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Maciej1 on 2017-02-13.
 */

public class ApiClient {

    private static final String API_URL = "https://newsapi.org/v1/";
    private static final String API_KEY = "c170c634ec2a4381aac741f46d9aee4d";
    private static Retrofit mRetrofit = null;

    public static Retrofit getClient(){
        if (mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

}

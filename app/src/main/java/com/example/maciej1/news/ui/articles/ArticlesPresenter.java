package com.example.maciej1.news.ui.articles;

import android.util.Log;

import com.example.maciej1.news.data.ApiClient;
import com.example.maciej1.news.data.ApiInterface;
import com.example.maciej1.news.data.ApiResponse;
import com.example.maciej1.news.data.ArticleEntry;
import com.example.maciej1.news.main.BaseArticlesPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticlesPresenter extends BaseArticlesPresenter<ArticlesView> {

    private static final String TAG = ArticlesPresenter.class.getSimpleName();


    public void startApiService(String source, String apiKey) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ApiResponse> call = apiService.getArticlesFromSource(source, apiKey);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                List<ArticleEntry> articleEntries = response.body().getArticles();

                getView().showArticles(articleEntries);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("onFailure: ", t.toString());
            }
        });
    }

    void addToFavourites(ArticleEntry articleEntry) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/" + user.getUid());

        databaseReference.push().setValue(articleEntry);
    }

}
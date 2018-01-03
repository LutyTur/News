package com.example.maciej1.news.ui.articles;

import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;

import com.example.maciej1.news.data.ApiClient;
import com.example.maciej1.news.data.ApiInterface;
import com.example.maciej1.news.data.ApiResponse;
import com.example.maciej1.news.data.ArticleEntry;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticlesPresenter extends MvpBasePresenter<ArticlesView> {

    private static final String API_KEY = "c170c634ec2a4381aac741f46d9aee4d";
    private static final String CHROME_PACKAGE = "com.android.chrome";

    public void startApiService(String source) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ApiResponse> call = apiService.getArticlesFromSource(source, API_KEY);

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


    public void showArticleDetails(View view) {
        int position = (int) view.getTag();
        ArticleEntry articleEntry = getView().getArticlesList().get(position);

        PackageManager packageManager = view.getContext().getPackageManager();

        if (isPackageInstalled(CHROME_PACKAGE, packageManager)) {
            getView().showDetailsInCustomTab(articleEntry.getUrl());
        } else {
            getView().showDetailsInWebView(articleEntry.getUrl());
        }
    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}

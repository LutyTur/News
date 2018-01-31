package com.example.maciej1.news.ui.articles;

import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.example.maciej1.news.data.ApiClient;
import com.example.maciej1.news.data.ApiInterface;
import com.example.maciej1.news.data.ApiResponse;
import com.example.maciej1.news.data.ArticleEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticlesPresenter extends MvpBasePresenter<ArticlesView> {

    private static final String TAG = ArticlesPresenter.class.getSimpleName();
    private static final String CHROME_PACKAGE = "com.android.chrome";

//    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//    int counter = 0;

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

    public void showArticleDetails(View view) {
        int position = (int) view.getTag();
        ArticleEntry articleEntry = getView().getArticlesList().get(position);

        PackageManager packageManager = view.getContext().getPackageManager();

        if (isPackageInstalled(CHROME_PACKAGE, packageManager)) {
            getView().showDetailsInCustomTab(articleEntry);
        } else {
            getView().showDetailsInWebView(articleEntry);
        }
    }

    void refreshList() {
        getView().reloadFragment();
    }

    void addToFavourites(ArticleEntry articleEntry){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/" + user.getUid());

        databaseReference.push().setValue(articleEntry);
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

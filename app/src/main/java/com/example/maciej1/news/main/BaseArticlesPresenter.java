package com.example.maciej1.news.main;


import android.content.pm.PackageManager;
import android.view.View;

import com.example.maciej1.news.data.ArticleEntry;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

public class BaseArticlesPresenter<V extends BaseArticlesView> extends MvpBasePresenter<V> {

    private static final String CHROME_PACKAGE = "com.android.chrome";


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

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void refreshList() {
        getView().reloadFragment();
    }


}
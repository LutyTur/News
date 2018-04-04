package com.example.maciej1.news.main;


import android.content.Context;

import com.example.maciej1.news.data.ArticleEntry;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

public interface BaseArticlesView extends MvpView {

    void showArticles(List<ArticleEntry> articleEntries);

    void showDetailsInCustomTab(ArticleEntry articleEntry);

    void showDetailsInWebView(ArticleEntry articleEntry);

    void reloadFragment();

    List<ArticleEntry> getArticlesList();

    Context getContext();
}
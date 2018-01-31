package com.example.maciej1.news.ui.favourites;


import com.example.maciej1.news.data.ArticleEntry;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

public interface FavouritesView extends MvpView{

    void reloadFragment();
    void showFavouritesList(List<ArticleEntry> articleEntries);
    void showDetailsInCustomTab(ArticleEntry articleEntry);
    void showDetailsInWebView(ArticleEntry articleEntry);
    List<ArticleEntry> getArticlesList();
}

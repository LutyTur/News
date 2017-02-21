package com.example.maciej1.news.ui.articles;

import com.example.maciej1.news.data.ArticleEntry;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;


public interface ArticlesView extends MvpView {
    void showArticles(List<ArticleEntry> articleEntries);
}

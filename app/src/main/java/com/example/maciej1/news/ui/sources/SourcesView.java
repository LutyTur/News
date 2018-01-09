package com.example.maciej1.news.ui.sources;

import com.example.maciej1.news.data.SourceEntry;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;


public interface SourcesView extends MvpView {

    void showSources(List<SourceEntry> sourceEntries);

    void inflateArticlesFragment(String id);

    void reloadFragment();
}

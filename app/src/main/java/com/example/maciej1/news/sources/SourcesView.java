package com.example.maciej1.news.sources;

import com.example.maciej1.news.data.SourceEntry;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by Maciej1 on 2017-02-11.
 */

public interface SourcesView extends MvpView{
    void showSources(List<SourceEntry> sourceEntries);
}

package com.example.maciej1.news.sources;

import com.example.maciej1.news.data.SourcesLoaderListener;
import com.example.maciej1.news.data.SourcesAsyncLoader;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by Maciej1 on 2017-02-11.
 */

public class SourcesPresenter extends MvpBasePresenter<SourcesView> implements SourcesLoaderListener {

    public void loadSources(){
        SourcesAsyncLoader sources = new SourcesAsyncLoader();
        sources.delegate = this;
        sources.execute();
    }

    @Override
    public void onProcessFinish(String output) {
        getView().showSources(output);
    }
}

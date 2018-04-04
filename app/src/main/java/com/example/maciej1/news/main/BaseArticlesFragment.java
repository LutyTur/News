package com.example.maciej1.news.main;


import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.hannesdorfmann.mosby.mvp.MvpView;

public class BaseArticlesFragment<V extends MvpView, P extends MvpBasePresenter<V>>
        extends MvpFragment<V, P> {

    @NonNull
    @Override
    public P createPresenter() {
        return null;
    }
}
package com.example.maciej1.news.sources;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.maciej1.news.R;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Maciej1 on 2017-02-11.
 */

public class SourcesFragment extends MvpFragment<SourcesView, SourcesPresenter>
                                implements SourcesView{


    @BindView(R.id.sources_tv)TextView mTextView;
    @BindView(R.id.sources_progress_bar)ProgressBar mProgressBar;

    @NonNull
    @Override
    public SourcesPresenter createPresenter() {
        return new SourcesPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sources, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressBar.setVisibility(View.VISIBLE);
        presenter.loadSources();
    }

    @Override
    public void showSources(String sources) {
        mProgressBar.setVisibility(View.GONE);
        mTextView.setText(sources);
    }
}

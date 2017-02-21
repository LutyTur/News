package com.example.maciej1.news.ui.articles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.TextView;

import com.example.maciej1.news.R;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ArticlesFragment extends MvpFragment<ArticlesView, ArticlesPresenter>
        implements ArticlesView {

    @BindView(R.id.articles_tv)
    TextView articlesTextView;

    @NonNull
    @Override
    public ArticlesPresenter createPresenter() {
        return new ArticlesPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String id = this.getArguments().getString("id");
        articlesTextView.setText("Source id: " + id);
    }
}

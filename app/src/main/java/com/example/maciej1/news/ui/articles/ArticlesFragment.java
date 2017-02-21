package com.example.maciej1.news.ui.articles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.maciej1.news.R;
import com.example.maciej1.news.data.ArticleEntry;
import com.example.maciej1.news.data.SourceEntry;
import com.example.maciej1.news.ui.sources.SourcesRecyclerAdapter;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ArticlesFragment extends MvpFragment<ArticlesView, ArticlesPresenter>
        implements ArticlesView, View.OnClickListener {

    private ArticlesRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;

    @BindView(R.id.articles_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.articles_recycler_view)
    RecyclerView recyclerViewList;
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

        progressBar.setVisibility(View.VISIBLE);
        setupAdapter(new ArrayList<ArticleEntry>());
        presenter.startApiService();
    }

    private void setupAdapter(ArrayList<ArticleEntry> articleEntries) {
        recyclerAdapter = new ArticlesRecyclerAdapter(articleEntries, this);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewList.setLayoutManager(layoutManager);
        recyclerViewList.setAdapter(recyclerAdapter);
    }

    @Override
    public void showArticles(List<ArticleEntry> articleEntries) {

    }

    @Override
    public void onClick(View view) {

    }
}

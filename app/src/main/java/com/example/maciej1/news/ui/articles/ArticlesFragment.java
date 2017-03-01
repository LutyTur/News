package com.example.maciej1.news.ui.articles;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.maciej1.news.R;
import com.example.maciej1.news.data.ArticleEntry;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ArticlesFragment extends MvpFragment<ArticlesView, ArticlesPresenter>
        implements ArticlesView, View.OnClickListener {

    private static final String POSITION_TAG = "articles_position_tag";
    private static final String CHROME_PACKAGE_NAME = "com.android.chrome";
    //private static final String CHROME_PACKAGE_NAME = "com.android.chrome";
    private ArticlesRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;
    private int listPosition;
    private List<ArticleEntry> articlesList;

    CustomTabsClient customTabsClient;
    CustomTabsSession customTabsSession;
    CustomTabsServiceConnection customTabsServiceConnection;
    CustomTabsIntent customTabsIntent;

    @BindView(R.id.articles_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.articles_recycler_view)
    RecyclerView recyclerViewList;


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

        progressBar.setVisibility(View.VISIBLE);
        setupAdapter(new ArrayList<ArticleEntry>());
        presenter.startApiService(id);
        //presenter.startCustomTabService();
        startCustomTabService();
    }

    private void startCustomTabService() {
        customTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                customTabsClient = client;
                customTabsClient.warmup(0L);
                customTabsSession = customTabsClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                customTabsClient = null;
            }
        };

        CustomTabsClient.bindCustomTabsService(
                getContext(), CHROME_PACKAGE_NAME, customTabsServiceConnection);


        /*
        CustomTabsClient.bindCustomTabsService(getContext(), CHROME_PACKAGE_NAME,
                new CustomTabsServiceConnection() {
                    @Override
                    public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                        customTabsClient = client;
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName componentName) {
                        customTabsClient = null;
                    }
                });
        customTabsClient.warmup(0);
        customTabsSession = customTabsClient.newSession(new CustomTabsCallback());
        customTabsSession.mayLaunchUrl(Uri.parse(""), null, null);
        */
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        listPosition = layoutManager.findFirstVisibleItemPosition();
        outState.putInt(POSITION_TAG, listPosition);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            listPosition = savedInstanceState.getInt(POSITION_TAG);
        }
    }

    private void setupAdapter(List<ArticleEntry> articleEntries) {
        articlesList = articleEntries;
        recyclerAdapter = new ArticlesRecyclerAdapter(articleEntries, this);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewList.setLayoutManager(layoutManager);
        recyclerViewList.setAdapter(recyclerAdapter);
    }

    @Override
    public void showArticles(List<ArticleEntry> articleEntries) {
        progressBar.setVisibility(View.GONE);
        setupAdapter(articleEntries);
        recyclerAdapter.notifyDataSetChanged();
        layoutManager.scrollToPositionWithOffset(listPosition, 0);

        //startCustomTabService();
        String url = articleEntries.get(0).getUrl();
        customTabsSession.mayLaunchUrl(Uri.parse(url), null, null);
    }

    @Override
    public void showDetailsInCustomTab(String url) {
        customTabsIntent = new CustomTabsIntent.Builder(customTabsSession)
                .setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .setShowTitle(true)
                .build();
        customTabsIntent.launchUrl(getContext(), Uri.parse(url));
        //CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        //builder.setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        //CustomTabsIntent customTabsIntent = builder.build();
        //customTabsIntent.launchUrl(getContext(), Uri.parse(url));



    }

    @Override
    public void showDetailsInWebView(String url) {

    }

    @Override
    public List<ArticleEntry> getArticlesList() {
        return articlesList;
    }

    @Override
    public void onClick(View view) {
        presenter.showArticleDetails(view);
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }
}

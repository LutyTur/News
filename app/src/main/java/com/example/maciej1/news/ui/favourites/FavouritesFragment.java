package com.example.maciej1.news.ui.favourites;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.maciej1.news.R;
import com.example.maciej1.news.data.ArticleEntry;
import com.example.maciej1.news.main.BaseArticlesFragment;
import com.example.maciej1.news.ui.articles.DetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouritesFragment extends BaseArticlesFragment<FavouritesView, FavouritesPresenter>
        implements FavouritesView, View.OnClickListener {

    private static final String TAG = FavouritesFragment.class.getSimpleName();
    private FavouritesRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;
    private List<ArticleEntry> articlesList;
    CustomTabsIntent customTabsIntent;

    @BindView(R.id.favourites_recycler_view)
    RecyclerView recyclerViewList;
    @BindView(R.id.swipe_refresh_favourites)
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        setActionBar();
        setOnSwipeRefreshListener();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout.setRefreshing(true);

        setupAdapter(new ArrayList<ArticleEntry>());

        presenter.loadFavouritesFromDB();
    }

    @NonNull
    @Override
    public FavouritesPresenter createPresenter() {
        return new FavouritesPresenter();
    }

    @Override
    public void reloadFragment() {
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    @Override
    public void showArticles(List<ArticleEntry> articleEntries) {
        setupAdapter(articleEntries);
        recyclerAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showDetailsInCustomTab(ArticleEntry articleEntry) {
        customTabsIntent = new CustomTabsIntent.Builder()
                .setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .setShowTitle(true)
                .build();
        customTabsIntent.launchUrl(getContext(), Uri.parse(articleEntry.getUrl()));
    }

    @Override
    public void showDetailsInWebView(ArticleEntry articleEntry) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle extras = new Bundle();
        extras.putString("url", articleEntry.getUrl());
        detailsFragment.setArguments(extras);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, detailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                // refresh
                presenter.refreshList();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupAdapter(List<ArticleEntry> articleEntries) {
        articlesList = articleEntries;
        recyclerAdapter = new FavouritesRecyclerAdapter(articleEntries, this);
        layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
            }
        };

        recyclerViewList.setLayoutManager(layoutManager);
        recyclerViewList.setAdapter(recyclerAdapter);
    }

    @Override
    public void onClick(View v) {
        presenter.showArticleDetails(v);
    }

    private void setOnSwipeRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadFragment();
            }
        });
    }

    private void setActionBar() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_favourites_fragment);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public List<ArticleEntry> getArticlesList() {
        return articlesList;
    }
}

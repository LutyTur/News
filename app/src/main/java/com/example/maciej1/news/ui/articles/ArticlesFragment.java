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
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.maciej1.news.R;
import com.example.maciej1.news.data.ArticleEntry;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ArticlesFragment extends MvpFragment<ArticlesView, ArticlesPresenter>
        implements ArticlesView, View.OnClickListener {

    private static final String TAG = ArticlesFragment.class.getSimpleName();
    private static final String POSITION_TAG = "articles_position_tag";
    private static final String SCREEN_ARTICLES = "articles_screen";
    private static final String CHROME_PACKAGE_NAME = "com.android.chrome";
    private ArticlesRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;
    private String sourceId;
    private int listPosition;
    private List<ArticleEntry> articlesList;
    private SparseArray<String> preLoadedUrlsList = new SparseArray<>();

    FirebaseAnalytics firebaseAnalytics;

    CustomTabsClient customTabsClient;
    CustomTabsSession customTabsSession;
    CustomTabsServiceConnection customTabsServiceConnection;
    CustomTabsIntent customTabsIntent;


    @BindView(R.id.articles_recycler_view)
    RecyclerView recyclerViewList;
    @BindView(R.id.swipe_refresh_articles)
    SwipeRefreshLayout swipeRefreshLayout;


    @NonNull
    @Override
    public ArticlesPresenter createPresenter() {
        return new ArticlesPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        firebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        setupOnScrollListener();
        setOnSwipeRefreshListener();

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sourceId = this.getArguments().getString("id");
        createFirebaseEvent(sourceId);
        loadNewsFromSource(sourceId);
    }

    private void loadNewsFromSource(String id) {
        swipeRefreshLayout.setRefreshing(true);
        setupAdapter(new ArrayList<ArticleEntry>());
        presenter.startApiService(id, getResources().getString(R.string.news_api_key));
    }

    private void startCustomTabService() {
        customTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                customTabsClient = client;
                customTabsClient.warmup(0);
                customTabsSession = customTabsClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                customTabsClient = null;
            }
        };

        CustomTabsClient.bindCustomTabsService(
                getContext(), CHROME_PACKAGE_NAME, customTabsServiceConnection);
    }

    @Override
    public void onStart() {
        super.onStart();
        startCustomTabService();
    }

    @Override
    public void onStop() {
        super.onStop();
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

    private void setupOnScrollListener() {
        recyclerViewList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                preLoadUrls();
            }
        });
    }

    private void preLoadUrls() {
        int firstItemPos = layoutManager.findFirstVisibleItemPosition();
        int lastItemPos = layoutManager.findLastVisibleItemPosition();

        if (firstItemPos > -1) {
            for (int i = firstItemPos; i < lastItemPos + 1; i++) {
                if (preLoadedUrlsList.indexOfKey(i) < 0) {
                    String url = recyclerAdapter.getArticleEntry(i).getUrl();
                    preLoadedUrlsList.put(i, url);

                    if (customTabsSession != null) {
                        customTabsSession.mayLaunchUrl(Uri.parse(url), null, null);
                    } else {
                        Log.e("customTabsSession", String.valueOf(customTabsSession));
                    }

                }
            }
        }
    }

    private void setupAdapter(List<ArticleEntry> articleEntries) {
        articlesList = articleEntries;
        recyclerAdapter = new ArticlesRecyclerAdapter(articleEntries, this);
        layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                preLoadUrls();
            }
        };

        recyclerViewList.setLayoutManager(layoutManager);
        recyclerViewList.setAdapter(recyclerAdapter);
    }

    private void createFirebaseEvent(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, SCREEN_ARTICLES);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, id);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    @Override
    public void showArticles(List<ArticleEntry> articleEntries) {
        setupAdapter(articleEntries);
        recyclerAdapter.notifyDataSetChanged();
        layoutManager.scrollToPositionWithOffset(listPosition, 0);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showDetailsInCustomTab(String url) {
        customTabsIntent = new CustomTabsIntent.Builder(customTabsSession)
                .setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .setShowTitle(true)
                .build();
        customTabsIntent.launchUrl(getContext(), Uri.parse(url));
    }

    @Override
    public void showDetailsInWebView(String url) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle extras = new Bundle();
        extras.putString("url", url);
        detailsFragment.setArguments(extras);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, detailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void reloadFragment() {
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
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

    private void setOnSwipeRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadFragment();
            }
        });
    }


}

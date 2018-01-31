package com.example.maciej1.news.ui.sources;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
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
import com.example.maciej1.news.data.SourceEntry;
import com.example.maciej1.news.ui.articles.ArticlesFragment;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SourcesFragment extends MvpFragment<SourcesView, SourcesPresenter>
        implements SourcesView, View.OnClickListener {


    private static final String TAG = SourcesFragment.class.getSimpleName();
    private static final String POSITION_TAG = "sources_position_tag";
    private static final String SCREEN_SOURCES = "sources_screen";
    private int listPosition;

    private SourcesRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;

    private FirebaseAnalytics firebaseAnalytics;

    @BindView(R.id.swipe_refresh_sources)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerViewList;


    @NonNull
    @Override
    public SourcesPresenter createPresenter() {
        return new SourcesPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sources, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        setupFirebaseAnalytics();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setOnSwipeRefreshListener();
        setActionBar();
        setupAdapter(new ArrayList<SourceEntry>());
        presenter.startApiService(getResources().getString(R.string.news_api_key));

        swipeRefreshLayout.setRefreshing(true);
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

    @Override
    public void showSources(List<SourceEntry> sourceEntries) {
        setupAdapter(sourceEntries);
        recyclerAdapter.notifyDataSetChanged();
        layoutManager.scrollToPositionWithOffset(listPosition, 0);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void inflateArticlesFragment(String id) {
        ArticlesFragment articlesFragment = new ArticlesFragment();

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        articlesFragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, articlesFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        presenter.startArticlesFragment(String.valueOf(view.getTag()));
    }

    @Override
    public void reloadFragment() {
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                presenter.reloadFragment();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupAdapter(List<SourceEntry> sourceEntries) {
        recyclerAdapter = new SourcesRecyclerAdapter(sourceEntries, this);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewList.setLayoutManager(layoutManager);
        recyclerViewList.setAdapter(recyclerAdapter);
    }

    private void setupFirebaseAnalytics() {
        firebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, SCREEN_SOURCES);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, this.getClass().getSimpleName());
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    private void setOnSwipeRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.reloadFragment();
            }
        });
    }

    private void setActionBar() {
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_sources_fragment);
        }
    }
}

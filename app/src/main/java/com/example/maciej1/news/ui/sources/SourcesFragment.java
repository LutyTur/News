package com.example.maciej1.news.ui.sources;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.maciej1.news.R;
import com.example.maciej1.news.data.SourceEntry;
import com.example.maciej1.news.ui.articles.ArticlesFragment;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SourcesFragment extends MvpFragment<SourcesView, SourcesPresenter>
        implements SourcesView, View.OnClickListener {


    public static final String POSITION_TAG = "position_tag";
    private int listPosition;

    private SourcesRecyclerAdapter recyclerAdapter;
    private GridLayoutManager layoutManager;

    @BindView(R.id.sources_progress_bar)
    ProgressBar progressBar;
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
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar.setVisibility(View.VISIBLE);
        setupAdapter(new ArrayList<SourceEntry>());
        presenter.startApiService();
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
        progressBar.setVisibility(View.GONE);
        setupAdapter(sourceEntries);
        recyclerAdapter.notifyDataSetChanged();
        layoutManager.scrollToPositionWithOffset(listPosition, 0);
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

    private void setupAdapter(List<SourceEntry> sourceEntries) {
        recyclerAdapter = new SourcesRecyclerAdapter(sourceEntries, this);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewList.setLayoutManager(layoutManager);
        recyclerViewList.setAdapter(recyclerAdapter);
    }

    @Override
    public void onClick(View view) {
        //Log.i("onClick: ", String.valueOf(view.getTag()));
        presenter.startArticlesFragment((Integer) view.getTag());
    }

}

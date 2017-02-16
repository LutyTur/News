package com.example.maciej1.news.ui.sources;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.maciej1.news.R;
import com.example.maciej1.news.data.SourceEntry;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SourcesFragment extends MvpFragment<SourcesView, SourcesPresenter>
        implements SourcesView {


    public static final String POSITION_TAG = "position_tag";
    private int listPosition;

    private SourcesRecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

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

        if (savedInstanceState != null) {
            listPosition = savedInstanceState.getInt(POSITION_TAG);
        } else {
            //listPosition = ;
        }

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
        //listPosition = recyclerAdapter
        outState.putInt(POSITION_TAG, listPosition);
    }

    @Override
    public void showSources(List<SourceEntry> sourceEntries) {
        progressBar.setVisibility(View.GONE);
        recyclerAdapter.setSourcesList(sourceEntries);
        recyclerAdapter.notifyDataSetChanged();
    }

    private void setupAdapter(List<SourceEntry> sourceEntries) {
        recyclerAdapter = new SourcesRecyclerAdapter(sourceEntries);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewList.setLayoutManager(layoutManager);
        recyclerViewList.setAdapter(recyclerAdapter);
    }
}

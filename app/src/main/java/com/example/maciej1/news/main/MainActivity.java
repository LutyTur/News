package com.example.maciej1.news.main;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.maciej1.news.R;
import com.example.maciej1.news.sources.SourcesFragment;
import com.example.maciej1.news.sources.SourcesPresenter;
import com.example.maciej1.news.sources.SourcesView;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.ButterKnife;

public class MainActivity extends MvpActivity<SourcesView, SourcesPresenter> implements SourcesView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SourcesFragment sourcesFragment = new SourcesFragment();
        fragmentTransaction.add(R.id.content_frame, sourcesFragment).commit();
    }

    @NonNull
    @Override
    public SourcesPresenter createPresenter() {
        return new SourcesPresenter();
    }


}

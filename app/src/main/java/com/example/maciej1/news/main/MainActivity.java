package com.example.maciej1.news.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.maciej1.news.R;
import com.example.maciej1.news.ui.sources.SourcesFragment;
import com.google.firebase.crash.FirebaseCrash;

import butterknife.ButterKnife;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private static final String SOURCES_FRAGMENT_TAG = "sources_fragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            loadSourcesFragment(new SourcesFragment());
        }

        //FirebaseCrash.report(new Exception("My first Android non-fatal error"));
    }

    private void loadSourcesFragment(SourcesFragment sourcesFragment) {
        sourcesFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, sourcesFragment, SOURCES_FRAGMENT_TAG).commit();
    }
}


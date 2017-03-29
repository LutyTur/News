package com.example.maciej1.news.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.maciej1.news.R;
import com.example.maciej1.news.ui.sources.SourcesFragment;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private static final String SOURCES_FRAGMENT_TAG = "sources_fragment_tag";
    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    //@BindView(R.id.app_bar)
    //Toolbar appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        setupFirebaseAnalytics();
        setupFirebaseAuthentication();

        setContentView(R.layout.activity_main);
        //setSupportActionBar(appBar);


        if (savedInstanceState == null) {
            loadSourcesFragment(new SourcesFragment());
        }

        //FirebaseCrash.report(new Exception("My first Android non-fatal error"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    private void loadSourcesFragment(SourcesFragment sourcesFragment) {
        sourcesFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, sourcesFragment, SOURCES_FRAGMENT_TAG).commit();
    }

    private void setupFirebaseAnalytics() {
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Fabric.with(this, new Crashlytics());
    }

    private void setupFirebaseAuthentication() {
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User signed in
                } else {
                    // User signed out
                }
            }
        };
    }
}


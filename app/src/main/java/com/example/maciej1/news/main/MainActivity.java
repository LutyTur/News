package com.example.maciej1.news.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.maciej1.news.R;
import com.example.maciej1.news.ui.sources.SourcesFragment;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;

import com.crashlytics.android.Crashlytics;

import java.util.Arrays;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private static final String SOURCES_FRAGMENT_TAG = "sources_fragment_tag";
    private static final int RC_SIGN_IN = 123;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                // refresh
                Log.i("onOptionsItemSelected", "refresh");
                return true;
            case R.id.action_log_in:
                // log in
                //Log.i("onOptionsItemSelected", "log_in");
                inflateSignInActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                //startActivity(SignedInActivity.createIntent(this, response));
                //finish();
                Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    //showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    //showSnackbar(R.string.no_internet_connection);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    //showSnackbar(R.string.unknown_error);
                    return;
                }
            }
        }
    }

    private void inflateSignInActivity(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .build(),
                RC_SIGN_IN);
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


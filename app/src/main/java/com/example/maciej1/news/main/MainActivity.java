package com.example.maciej1.news.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.maciej1.news.R;
import com.example.maciej1.news.ui.favourites.FavouritesFragment;
import com.example.maciej1.news.ui.sources.SourcesFragment;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SOURCES_FRAGMENT_TAG = "sources_fragment_tag";
    private static final String FAVOURITES_FRAGMENT_TAG = "favourites_fragment_tag";
    private static final int RC_SIGN_IN = 123;
    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupFirebaseAnalytics();
        setupFirebaseAuthentication();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        if (savedInstanceState == null) {
            loadSourcesFragment(new SourcesFragment());
        }

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
            case android.R.id.home:

                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }

                return true;
            case R.id.action_log_in:
                if (firebaseAuth.getCurrentUser() == null) {
                    inflateSignInActivity();
                } else {
                    signOut();
                }
                return true;
            case R.id.action_favourites:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new FavouritesFragment(), FAVOURITES_FRAGMENT_TAG)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (firebaseAuth.getCurrentUser() == null) {
            menu.findItem(R.id.action_log_in).setTitle(R.string.sign_in);
        } else {
            menu.findItem(R.id.action_log_in).setTitle(R.string.sign_out);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();

                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
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

    private void inflateSignInActivity() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setProviders(Collections.singletonList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .build(),
                RC_SIGN_IN);
    }

    private void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplication(), "Signed out", Toast.LENGTH_SHORT).show();
                    }
                });
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


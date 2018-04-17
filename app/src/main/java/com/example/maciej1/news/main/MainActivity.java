package com.example.maciej1.news.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.maciej1.news.R;
import com.example.maciej1.news.ui.favourites.FavouritesFragment;
import com.example.maciej1.news.ui.sources.SourcesFragment;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

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
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private TextView navDrawerTextView;
    private ImageView navDrawerImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupNavDrawer();
        setupFirebaseAnalytics();
        setupFirebaseAuthentication();
        setupActionBar();

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
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
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
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
            } else {
                // Sign in failed
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        attachAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        detachAuthStateListener(authStateListener);
    }

    private void inflateSignInActivity() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
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

    private void setupNavDrawer() {
        navDrawerTextView = navigationView
                .getHeaderView(0)
                .findViewById(R.id.nav_header_title);
        navDrawerImageView = navigationView
                .getHeaderView(0)
                .findViewById(R.id.profile_picture_id);

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);

            switch (menuItem.getItemId()) {
                case R.id.nav_sign_in:
                    inflateSignInActivity();
                    break;
                case R.id.nav_sign_out:
                    signOut();
                    break;
                case R.id.nav_favourites:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new FavouritesFragment(), FAVOURITES_FRAGMENT_TAG)
                            .commit();
                    break;
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void setupFirebaseAnalytics() {
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Fabric.with(this, new Crashlytics());
    }

    private void setupFirebaseAuthentication() {
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Picasso.with(navigationView.getContext())
                        .load(user.getPhotoUrl()).into(navDrawerImageView);

                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.drawer_signed_in);
                navDrawerTextView.setText(user.getDisplayName());
            } else {
                navDrawerImageView.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
                navDrawerTextView.setText("");

                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.drawer_signed_out);
            }
        };
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
    }

    private void attachAuthStateListener(FirebaseAuth.AuthStateListener authStateListener) {
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    private void detachAuthStateListener(FirebaseAuth.AuthStateListener authStateListener) {
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }

    }
}


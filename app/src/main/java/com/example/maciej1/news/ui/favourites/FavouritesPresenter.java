package com.example.maciej1.news.ui.favourites;


import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;

import com.example.maciej1.news.data.ArticleEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FavouritesPresenter extends MvpBasePresenter<FavouritesView> {

    private static final String TAG = FavouritesPresenter.class.getSimpleName();
    private static final String CHROME_PACKAGE = "com.android.chrome";

    public void loadFavouritesFromDB() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance()
                .getReference("users/" + user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ArticleEntry> articleEntries = new ArrayList<>();

                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();

                while (iterator.hasNext()) {
                    ArticleEntry entry = iterator.next().getValue(ArticleEntry.class);
                    articleEntries.add(entry);
                }

                getView().showFavouritesList(articleEntries);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    public void refreshList() {
        getView().reloadFragment();
    }

    public void showArticleDetails(View view) {
        int position = (int) view.getTag();
        ArticleEntry articleEntry = getView().getArticlesList().get(position);

        PackageManager packageManager = view.getContext().getPackageManager();

        if (isPackageInstalled(CHROME_PACKAGE, packageManager)) {
            getView().showDetailsInCustomTab(articleEntry);
        } else {
            getView().showDetailsInWebView(articleEntry);
        }
    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}

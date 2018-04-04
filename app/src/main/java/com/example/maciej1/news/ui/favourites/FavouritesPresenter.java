package com.example.maciej1.news.ui.favourites;


import com.example.maciej1.news.data.ArticleEntry;
import com.example.maciej1.news.main.BaseArticlesPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FavouritesPresenter extends BaseArticlesPresenter<FavouritesView> {

    private static final String TAG = FavouritesPresenter.class.getSimpleName();

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

                getView().showArticles(articleEntries);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}

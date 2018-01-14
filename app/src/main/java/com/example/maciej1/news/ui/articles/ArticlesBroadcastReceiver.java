package com.example.maciej1.news.ui.articles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class ArticlesBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String test = intent.getExtras().getString("FAV_URL");
//        Log.i("receiver", test);

        ObservableFavourites.getInstance().updateValue(test);

    }
}

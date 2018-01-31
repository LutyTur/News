package com.example.maciej1.news.ui.articles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.maciej1.news.data.ArticleEntry;
import com.google.gson.Gson;


public class ArticlesBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getExtras() != null){
            String json = intent.getExtras().getString("article_entry");
            ArticleEntry articleEntry = new Gson().fromJson(json, ArticleEntry.class);

            ObservableFavourites.getInstance().updateValue(articleEntry);
        }
    }
}

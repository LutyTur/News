package com.example.maciej1.news;

import android.app.Application;

import com.google.firebase.crash.FirebaseCrash;


public class NewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                FirebaseCrash.report(ex);
            }
        });
    }
}

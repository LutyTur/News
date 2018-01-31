package com.example.maciej1.news.ui.articles;


import java.util.Observable;
import java.util.Observer;

public class ObservableFavourites extends Observable {

    private static ObservableFavourites instance = new ObservableFavourites();


    public static ObservableFavourites getInstance() {
        return instance;
    }

    private ObservableFavourites() {

    }

    public void updateValue(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }


}

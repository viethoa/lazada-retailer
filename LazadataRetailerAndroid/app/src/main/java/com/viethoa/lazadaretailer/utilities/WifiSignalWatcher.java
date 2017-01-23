package com.viethoa.lazadaretailer.utilities;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by admin on 8/7/16.
 */
public class WifiSignalWatcher extends Observable {
    protected Observer observer;
    private static WifiSignalWatcher wifiSignalWatcher = new WifiSignalWatcher();

    public static WifiSignalWatcher getInstance() {
        return wifiSignalWatcher;
    }

    @Override
    public void addObserver(Observer observer) {
        this.observer = observer;
        super.addObserver(observer);
    }

    public void updateValue() {
        synchronized (this) {
            setChanged();
            notifyObservers();
        }
    }

}

package com.viethoa.lazadaretailer.caches;

import com.orhanobut.hawk.Hawk;

/**
 * Created by VietHoa on 22/01/2017.
 */

public abstract class BaseMemoryCache<T> {

    private T currentData;

    protected abstract String key();

    // Todo: default value always return null :scream:
    protected abstract T defaultValue();

    public BaseMemoryCache() {
        currentData = Hawk.get(key(), defaultValue());
    }

    public synchronized T get() {
        if (currentData != null) {
            return currentData;
        }

        currentData = Hawk.get(key());
        return currentData;
    }

    public synchronized void set(T data) {
        currentData = data;
        Hawk.put(key(), data);
    }

    public synchronized void clear() {
        currentData = null;
        set(null);
    }
}

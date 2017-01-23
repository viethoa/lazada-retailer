package com.viethoa.lazadaretailer.caches;

import com.viethoa.lazadaretailer.models.Store;

import java.util.List;

/**
 * Created by VietHoa on 23/01/2017.
 */

public class StoreMemoryCache extends BaseMemoryCache<List<Store>> {

    private static final String KEY_CURRENT_USER = "key_my_stores";

    private static StoreMemoryCache instance;

    public static StoreMemoryCache getInstance() {
        if (instance == null) {
            instance = new StoreMemoryCache();
        }
        return instance;
    }

    @Override
    protected String key() {
        return KEY_CURRENT_USER;
    }

    @Override
    protected List<Store> defaultValue() {
        return null;
    }

    private StoreMemoryCache() {
        super();
    }
}

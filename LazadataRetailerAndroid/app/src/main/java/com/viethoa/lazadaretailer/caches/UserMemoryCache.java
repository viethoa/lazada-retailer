package com.viethoa.lazadaretailer.caches;

import com.viethoa.lazadaretailer.models.User;

public class UserMemoryCache extends BaseMemoryCache<User> {

    private static final String KEY_CURRENT_USER = "key_current_user";

    private static UserMemoryCache instance;

    public static UserMemoryCache getInstance() {
        if (instance == null) {
            instance = new UserMemoryCache();
        }
        return instance;
    }

    @Override
    protected String key() {
        return KEY_CURRENT_USER;
    }

    @Override
    protected User defaultValue() {
        return null;
    }

    private UserMemoryCache() {
        super();
    }

    //----------------------------------------------------------------------------------------------
    // Getters
    //----------------------------------------------------------------------------------------------

    public synchronized String getDriverToken() {
        if (get() != null) {
            return get().getToken();
        }
        return "";
    }

    public synchronized long getUserID() {
        if (get() == null) {
            return 0;
        } else {
            return get().getId();
        }
    }

    //----------------------------------------------------------------------------------------------
    // Setters
    //----------------------------------------------------------------------------------------------

}

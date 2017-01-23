package com.viethoa.lazadaretailer.models;

/**
 * Created by VietHoa on 22/01/2017.
 */

public class User {

    private long id;
    private String token;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

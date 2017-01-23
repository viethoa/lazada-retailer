package com.viethoa.lazadaretailer.models;

import java.io.Serializable;

/**
 * Created by VietHoa on 23/01/2017.
 */

public class Store implements Serializable {

    private long id;
    private String name;
    private long userID;
    private long createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}

package com.viethoa.models;

/**
 * Created by VietHoa on 16/01/2017.
 */
public class Store {

    private long id;
    private String name;
    private long userID;
    private long createdAt;

    public Store(long id, String name, long userID, long createdAt) {
        this.id = id;
        this.name = name;
        this.userID = userID;
        this.createdAt = createdAt;
    }

    public Store(String name, long userID) {
        this.name = name;
        this.userID = userID;
        this.createdAt = System.currentTimeMillis();
    }

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

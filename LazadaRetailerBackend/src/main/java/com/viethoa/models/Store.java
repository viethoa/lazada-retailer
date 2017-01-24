package com.viethoa.models;

/**
 * Created by VietHoa on 16/01/2017.
 */
public class Store {

    private long id;
    private String name;
    private long user_id;
    private long created_at;

    public Store(long id, String name, long user_id, long created_at) {
        this.id = id;
        this.name = name;
        this.user_id = user_id;
        this.created_at = created_at;
    }

    public Store(String name, long user_id) {
        this.name = name;
        this.user_id = user_id;
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
        return user_id;
    }

    public void setUserID(long user_id) {
        this.user_id = user_id;
    }

    public long getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(long created_at) {
        this.created_at = created_at;
    }
}

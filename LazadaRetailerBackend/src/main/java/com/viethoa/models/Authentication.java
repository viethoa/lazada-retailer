package com.viethoa.models;

import java.util.Date;

/**
 * Created by VietHoa on 19/01/2017.
 */
public class Authentication {

    private String issuer;
    private Date expireTime;
    private User user;

    public Authentication(String issuer, Date expireTime, User user) {
        this.issuer = issuer;
        this.expireTime = expireTime;
        this.user = user;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

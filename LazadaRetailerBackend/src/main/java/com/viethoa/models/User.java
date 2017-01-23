package com.viethoa.models;

/**
 * Created by VietHoa on 16/01/2017.
 */
public class User {

    private long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
    @Authority
    private String authority;
    private long createdAt;
    private String token;

    public User() {

    }

    public User(String email, String password, String name, String phone, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.authority = Authority.Nil;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    //------------------------------------------------------------------------------------------------------------------
    // Types
    //------------------------------------------------------------------------------------------------------------------

    public @interface Authority {
        String Admin = "admin";
        String Staff = "staff";
        String Nil = "nil";
    }
}

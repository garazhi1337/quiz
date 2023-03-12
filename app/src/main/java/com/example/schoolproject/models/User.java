package com.example.schoolproject.models;

public class User {

    private String username, password, email, uid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User(String username, String password, String email, String uid) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.uid = uid;
    }

    public User() {

    }
}

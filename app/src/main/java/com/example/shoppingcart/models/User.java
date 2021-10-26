package com.example.shoppingcart.models;

import java.io.Serializable;

public class User implements Serializable {

    private String uid, token, fullname, address, email;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void updateUser(String fullname, String address, String email) {

        this.fullname = fullname;
        this.address = address;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "token=" + token +
                ", uid=" + uid +
                ", fullname='" + fullname + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

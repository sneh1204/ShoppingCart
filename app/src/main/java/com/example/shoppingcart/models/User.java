package com.example.shoppingcart.models;

import java.io.Serializable;

public class User implements Serializable {

    private int weight, age;

    private String uid, token, fullname, address, email;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void updateUser(int weight, int age, String fullname, String address, String email) {
        this.weight = weight;
        this.age = age;
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
                "weight=" + weight +
                ", age=" + age +
                ", token=" + token +
                ", uid=" + uid +
                ", fullname='" + fullname + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

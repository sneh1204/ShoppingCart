package com.example.shoppingcart.models;

import com.google.gson.Gson;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Transaction implements Serializable {

    String tId, amount, stamp, products;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String prettyDate(){
        Date d = new Date(Long.valueOf(this.stamp));
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return f.format(d);
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "tId='" + tId + '\'' +
                ", amount='" + amount + '\'' +
                ", stamp='" + stamp + '\'' +
                ", products='" + products + '\'' +
                '}';
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public Transaction() {
    }

}

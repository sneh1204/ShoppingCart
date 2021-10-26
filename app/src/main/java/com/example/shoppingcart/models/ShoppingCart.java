package com.example.shoppingcart.models;

import java.util.ArrayList;

public class ShoppingCart {
    ArrayList<Product> productArrayList;
    double amount;

    public ShoppingCart() {
        this.productArrayList = productArrayList;
        this.amount = amount;
    }

    public ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "productArrayList=" + productArrayList +
                ", amount=" + amount +
                '}';
    }
}

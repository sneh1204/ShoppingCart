package com.example.shoppingcart.models;

import java.util.HashMap;

public class ShoppingCart {

    HashMap<String, Product> productArrayList = new HashMap<>();

    public ShoppingCart() {
    }

    public HashMap<String, Product> getProductArrayList() {
        return productArrayList;
    }

    public int quantity(Product product) {
        if (getProductArrayList().containsKey(product.get_id()))
            return getProductArrayList().get(product.get_id()).qty;
        else return 0;
    }

    public void setProductArrayList(HashMap<String, Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "productArrayList=" + productArrayList +
                '}';
    }
}

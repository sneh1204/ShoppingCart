package com.example.shoppingcart.models;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    HashMap<String, Product> productList = new HashMap<>();

    public ShoppingCart() {
    }

    public HashMap<String, Product> getProductList() {
        return productList;
    }

    public int quantity(Product product) {
        if (getProductList().containsKey(product.get_id()))
            return getProductList().get(product.get_id()).qty;
        else return 0;
    }

    public void setProductList(HashMap<String, Product> productList) {
        this.productList = productList;
    }

    public double getTotalCartCost(){
        double cost = 0.00;
        for (Map.Entry<String, Product> map:
             productList.entrySet()) {
            Product product = map.getValue();
            cost += product.getUpdatedPrice() * product.getQty();
        }

        return cost;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "productList=" + productList +
                '}';
    }
}

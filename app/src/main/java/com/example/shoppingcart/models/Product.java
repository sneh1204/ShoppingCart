package com.example.shoppingcart.models;

public class Product {
    String name;
    String photoUrl;
    String region;
    double price;
    double discount;

    public Product(String name, String photoUrl, String region, double price, double discount) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.region = region;
        this.price = price;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", region='" + region + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}

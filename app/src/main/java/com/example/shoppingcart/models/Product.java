package com.example.shoppingcart.models;

public class Product {
    String name;
    String photo;
    String region;
    double price;
    double discount;
    int qty;
    double total_product_price;

    public Product(String name, String photo, String region, double price, double discount) {
        this.name = name;
        this.photo = photo;
        this.region = region;
        this.price = price;
        this.discount = discount;
    }

    public Product(String name, String photo, String region, double price, double discount, int qty,double total_product_price ) {
        this.name = name;
        this.photo = photo;
        this.region = region;
        this.price = price;
        this.discount = discount;
        this.qty = qty;
        this.total_product_price = total_product_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal_product_price() {
        return total_product_price;
    }

    public void setTotal_product_price(double total_product_price) {
        this.total_product_price = total_product_price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", region='" + region + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", qty=" + qty +
                ", total_product_price=" + total_product_price +
                '}';
    }

    public  static double calculateProductPrice(double price, double discount, int updatedQuantity) {
        double discountPrice ;
        double total_amount;
        discountPrice = (price * discount)/100;
        total_amount = (price - discountPrice) * updatedQuantity;
        return total_amount;
    }
}

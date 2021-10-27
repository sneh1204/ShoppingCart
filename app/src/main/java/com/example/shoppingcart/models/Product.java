package com.example.shoppingcart.models;

public class Product {
    String _id;
    String name;
    String photo;
    String region;
    double price;
    double discount;
    int qty = 1;

    public String get_id() {
        return _id;
    }

    public void incr_qty(){
        ++qty;
    }

    public void decr_qty(){
        --qty;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Product() {
    }

    public String getTotalCostString(){
        return String.format("%.2f", qty * getUpdatedPrice());
    }

    public double getUpdatedPrice(){
        return ((100 - discount) * this.price) / 100;
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

    @Override
    public String toString() {
        return "Product{" +
                "id='" + _id + '\'' +
                "name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", region='" + region + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", qty=" + qty +
                '}';
    }
}

package com.example.courseproject;

public class CartModel {

    public final String name;
    public final String description;
    public final String quantity;
    public final String price;
    public final String imageURI;
    public final String res_name;
    public final String user_id;

    public CartModel(String name, String description, String quantity, String price, String imageURI, String res_name, String user_id) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.imageURI = imageURI;
        this.res_name = res_name;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getImageURI() {
        return imageURI;
    }

    public String getPrice() { return  price; }

    public String getResName() { return res_name;}

    public String getUserId() { return user_id; }
}

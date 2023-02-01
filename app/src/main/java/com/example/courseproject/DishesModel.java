package com.example.courseproject;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DishesModel {

    public  String name;
    public  String description;
    public  String price;
    public  String imageURI;
    public  boolean inCart;
    public  String res_name;
    public  String user_id;
    public DishesModel(){

    }

    public DishesModel(String name, String description, String price, String imageURI, boolean inCart,String res_name, String user_id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageURI = imageURI;
        this.inCart = inCart;
        this.res_name = res_name;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImageURI() {
        return imageURI;
    }

    public String getResName() { return res_name; }

    public String getUserId() { return user_id; }
}

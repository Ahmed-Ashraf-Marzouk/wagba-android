package com.example.courseproject;

public class HistoryModel {

    public final String state;
    public final String date;
    public final String description;
    public final String price;

    public HistoryModel( String state, String description, String price, String date) {
        this.state = state;
        this.description = description;
        this.price = price;
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

}

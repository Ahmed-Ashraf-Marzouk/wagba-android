package com.example.courseproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @NonNull
    @PrimaryKey (autoGenerate = true)
    public int id;

    @ColumnInfo(name = "uid")
    public String uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;


    @ColumnInfo(name = "age")
    public String age;


    @ColumnInfo(name = "telephone")
    public String telephone;

    public User()
    {

    }

    public User(String user_id, String firstName, String lastName, String age, String telephone) {
        this.uid = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.telephone = telephone;
    }
}

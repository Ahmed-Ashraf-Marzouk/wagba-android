package com.example.courseproject;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.net.URI;

public class RestaurantModel implements Serializable {
    public  String name;
    public  String description;
    public  String location;
    public  String imageURI;


    public RestaurantModel(String name, String description, String location, String imageURI) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.imageURI = imageURI;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getImageURI() {
        return imageURI;
    }
}
//
//        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
//        StorageReference pathReference = firebaseStorage.getReference();
//        pathReference.child(imageURI).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//            }
//        });
//    }
//}
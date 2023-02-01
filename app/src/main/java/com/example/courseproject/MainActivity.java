package com.example.courseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.courseproject.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) updateUI(currentUser);
        activityMainBinding.welcomeSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                updateUI(currentUser);
            }
        });
        View view = activityMainBinding.getRoot();
        setContentView(view);
//        Intent intent = new Intent(this, SignIn.class);
//        startActivity(intent);
//        ActivityMainBinding activity_main = ActivityMainBinding.inflate(getLayoutInflater());
//        activity_main.welcomeSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(MainActivity.this, SignUp.class);
////                startActivity(intent);
//
//            }
//        });

//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.fragmentContainerView, new Restaurants_list());
//        ft.commit();


    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//
//    }

    private void updateUI(FirebaseUser currentUser) {
        Intent intent;
        if (currentUser != null)
        {
            String name = currentUser.getDisplayName();
            String id = currentUser.getUid();
            intent = new Intent(this, UserScreen.class);
            finish();
        }
        else
        {
            intent = new Intent(this, SignIn.class);
            finish();
        }
        startActivity(intent);
    }
}
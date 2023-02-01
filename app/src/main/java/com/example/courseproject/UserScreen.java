package com.example.courseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.courseproject.databinding.UserScreenBinding;
import com.google.android.material.tabs.TabItem;
import com.google.firebase.auth.FirebaseAuth;

public class UserScreen extends AppCompatActivity {


//    Bundle extras = getIntent().getExtras();
//    String user_id = extras.getString("user_id");
//
    String user_id = FirebaseAuth.getInstance().getUid();
    Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserScreenBinding userScreenBinding = UserScreenBinding.inflate(getLayoutInflater());
        userScreenBinding.navigationTab.getTabAt(0).view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Restaurants_list();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                fragmentTransaction.commit();
            }
        });

        userScreenBinding.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Payment.class);
                startActivity(intent);
            }
        });
        userScreenBinding.navigationTab.getTabAt(1).view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Cart_list();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                fragmentTransaction.commit();

            }
        });
//        userScreenBinding.shopee.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = new Cart_list();
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
//                fragmentTransaction.commit();
//            }
//        });


        userScreenBinding.userProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserScreen.this, RUserProfile.class);
                startActivity(intent);
//                FirebaseAuth.getInstance().signOut();
            }
        });
        userScreenBinding.orderHistory.setOnClickListener(view -> {
//                FirebaseAuth.getInstance().signOut();
            Fragment fragment = new History_list();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerView, fragment, null);
            fragmentTransaction.addToBackStack(String.valueOf(getSupportFragmentManager().getClass()));
            fragmentTransaction.commit();
        });

        View view = userScreenBinding.getRoot();
        setContentView(view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Fragment fragment = new Restaurants_list();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment, null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment fragment = new Restaurants_list();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment, null);
        fragmentTransaction.commit();
        Log.d("RESUMEED", "ASD");
    }
}
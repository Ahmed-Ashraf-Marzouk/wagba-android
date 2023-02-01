package com.example.courseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.courseproject.databinding.ActivityRuserProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class RUserProfile extends AppCompatActivity {

    String user_id = FirebaseAuth.getInstance().getUid();
    ActivityRuserProfileBinding activityRuserProfileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRuserProfileBinding = ActivityRuserProfileBinding.inflate(getLayoutInflater());



        activityRuserProfileBinding.userProfileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(RUserProfile.this, SignIn.class);
                startActivity(intent);
            }
        });

        activityRuserProfileBinding.userProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityRuserProfileBinding.userFirstName.setEnabled(true);
                activityRuserProfileBinding.userLastName.setEnabled(true);
                activityRuserProfileBinding.userAge.setEnabled(true);
                activityRuserProfileBinding.userTelephone.setEnabled(true);
                activityRuserProfileBinding.userProfileSave.setVisibility(View.VISIBLE);
            }
        });

        activityRuserProfileBinding.userProfileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(user_id,
                        activityRuserProfileBinding.userFirstName.getText().toString(),
                        activityRuserProfileBinding.userLastName.getText().toString(),
                        activityRuserProfileBinding.userAge.getText().toString(),
                        activityRuserProfileBinding.userTelephone.getText().toString());
                activityRuserProfileBinding.userFirstName.setEnabled(false);
                activityRuserProfileBinding.userLastName.setEnabled(false);
                activityRuserProfileBinding.userAge.setEnabled(false);
                activityRuserProfileBinding.userTelephone.setEnabled(false);
                updateUser(user);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        getUser(user_id);

        setContentView(activityRuserProfileBinding.getRoot());
    }

    public void getUser(String user_id)
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                UserDao userDao = db.UserDao();
                User user = userDao.findById(user_id);
                activityRuserProfileBinding.userFirstName.setText(user.firstName);
                activityRuserProfileBinding.userLastName.setText(user.lastName);
                activityRuserProfileBinding.userAge.setText(user.age);
                activityRuserProfileBinding.userTelephone.setText(user.telephone);
            }
        });
        thread.start();
    }



    public void updateUser(User user)
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                UserDao userDao = db.UserDao();
                User our_user = userDao.findById(user.uid);
                our_user.firstName = user.firstName;
                our_user.lastName = user.lastName;
                our_user.age = user.age;
                our_user.telephone = user.telephone;
                userDao.updateUser(our_user);
            }
        }).start();
    }

}
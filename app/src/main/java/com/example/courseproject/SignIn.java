package com.example.courseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.courseproject.databinding.SignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    FirebaseAuth mAuth;
    String email;
    String password;
    SignInBinding signInBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        signInBinding = SignInBinding.inflate(getLayoutInflater());


        signInBinding.userSignIn.setOnClickListener(view -> {

            email = signInBinding.emailIn.getText().toString();
            password = signInBinding.passwordIn.getText().toString();
            if(!(email.isEmpty() || password.isEmpty())) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignIn.this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG_sign_in", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("new_tag", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(this, "Wrong email or password",
                                            Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        });
            }
            else
            {
                Log.d("empty_string", "asdas");
                Log.d("string_1", email);
                Log.d("string_2", password);
            }
        });

        signInBinding.signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });


        View view = signInBinding.getRoot();
        setContentView(view);
    }


    public void updateUI(FirebaseUser currentUser){
        if(currentUser != null)
        {
        Intent intent = new Intent(SignIn.this, UserScreen.class);
        startActivity(intent);
        finish();
        }
        else
        {
            Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT).show();
        }
    }

}
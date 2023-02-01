package com.example.courseproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseproject.databinding.SignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.regex.Pattern;

import javax.xml.transform.Result;

public class SignUp extends AppCompatActivity {

    FirebaseAuth mAuth;
    SignUpBinding sign_up;
    String email;
    String password_1;
    String password_2;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sign_up = SignUpBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();

//        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "database-name").build();
//        userDao = db.UserDao();
        sign_up.signUp.setOnClickListener(view -> {
//                Intent intent = new Intent(SignUp.this, MainActivity.class);
//                startActivity(intent);
//                finish();
            Log.d("CLICKED", "OH WOW");
            email = sign_up.emailSignIn.getText().toString();
            password_1 = sign_up.passwordSignIn.getText().toString();
            password_2 = sign_up.confirmPassword.getText().toString();
            String first_name = sign_up.signUpUserFirstName.getText().toString();
            String last_name = sign_up.signUpUserLastName.getText().toString();
            String age = sign_up.signUpUserAge.getText().toString();
            String telephone = sign_up.signUpUserTelephone.getText().toString();
            createAccount(email, password_1, password_2, first_name, last_name, age, telephone);


        });

        sign_up.emailSignIn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String email = sign_up.emailSignIn.getText().toString();
                if(isValidEmail(email))
                {
                    sign_up.hintEmail.setErrorEnabled(false);
                }
                else if(!email.isEmpty()) {
                        sign_up.hintEmail.setErrorEnabled(true);
                        sign_up.emailSignIn.setError("Wrong email format");
                    }
                }
        });

        View view = sign_up.getRoot();
        setContentView(view);
    }



    private void createAccount(String email,String password_1,String password_2, String first_name, String last_name, String age, String telephone){
        if(password_1.equals(password_2) && !password_1.isEmpty()) {
            sign_up.hintConfirmPassword.setHelperTextEnabled(true);
            sign_up.hintConfirmPassword.setHelperText(" ");
            if (isValidEmail(email)) {
                mAuth.createUserWithEmailAndPassword(email, password_1)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("DONE_MAN", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                insertUser(mAuth.getUid(), first_name, last_name, age, telephone);
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("NOT_DONE_MAN", "createUserWithEmail:failure", task.getException());
                                String error = task.getException().getMessage();
                                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                                Log.d("error_pass", error);
//                                Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        });
            }
            else {
                Log.d("email_problem", "Wrong email format");
                sign_up.hintEmail.setErrorEnabled(true);
                sign_up.emailSignIn.setError("Wrong email format");
            }
        }
        else {
            Log.d("password_problem", "Password doesn't match");
            sign_up.hintConfirmPassword.setHelperTextEnabled(true);
            sign_up.hintConfirmPassword.setHelperText("Password doesn't match");

        }
    }


    public void updateUI(FirebaseUser currenUser)
    {
        if(currenUser != null) {
            Intent intent = new Intent(this, SignIn.class);
//            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                    AppDatabase.class, "user-database").build();
//            User user = new User();
//            user.uid = "Ahmed";
//            user.age = "17";
//            user.firstName = "Ahs";
//            user.lastName = "ash";
//            user.telephone = "0101";
//            userDao.insertAll(user);
//            db.UserDao().insertAll(user);
            startActivity(intent);
            finish();
        }
    }
    public static boolean isValidEmail(String emailAddress) {
        String regexPattern = "^(.+)@(.+)$";
//                "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }


    public void insertUser(String uid, String firs_name, String last_name, String age, String telephone)
    {
        User user = new User(uid, firs_name, last_name, age, telephone);
        InsertAsyncUser insertAsyncUser = new InsertAsyncUser();
        insertAsyncUser.execute(user);
    }


    class InsertAsyncUser extends AsyncTask<User, Void, Void>
    {

        @Override
        protected Void doInBackground(User... users) {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            userDao = db.UserDao();
            userDao.insertUser(users[0]);
            return null;
        }
    }

}
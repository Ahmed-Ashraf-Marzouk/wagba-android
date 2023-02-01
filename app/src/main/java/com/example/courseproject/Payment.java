package com.example.courseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.courseproject.databinding.PaymentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

import javax.security.auth.PrivateCredentialPermission;

public class Payment extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    String user_id = FirebaseAuth.getInstance().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PaymentBinding paymentBinding = PaymentBinding.inflate(getLayoutInflater());


        paymentBinding.confirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Payment.this, UserScreen.class);
                Date currentTime = Calendar.getInstance().getTime();
                String gate = " ";
                if(paymentBinding.gate3.isSelected())
                {
                     gate = "GATE 3";
                }
                else
                {
                    gate = "GATE 4";
                }
                String time = " ";
                if(paymentBinding.time1.isSelected())
                {
                    time = "12:00 PM";
                }
                else
                {
                    time = "3:00 PM";
                }


                databaseReference.child(user_id).child("cartTotal").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        String totalPrice = task.getResult().getValue().toString();
                        if(!totalPrice.equals("0.0")) {
                            HistoryModel historyModel = new HistoryModel("Ordered", "Hurry up", task.getResult().getValue().toString(), currentTime.toString());
                            databaseReference.child(user_id).child("history").child(currentTime.toString()).setValue(historyModel);
                            databaseReference.child(user_id).child("cartTotal").setValue("0");
                            databaseReference.child(user_id).child("cartItems").removeValue();
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(Payment.this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        Fragment fragment = new Cart_list();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.p_fragmentContainerView, fragment);
        fragmentTransaction.commit();

        setContentView(paymentBinding.getRoot());

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                paymentBinding.paymentTotalPrice.setText(snapshot.getValue().toString() + " $");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseReference.child(user_id).child("cartTotal").addValueEventListener(valueEventListener);
    }

}
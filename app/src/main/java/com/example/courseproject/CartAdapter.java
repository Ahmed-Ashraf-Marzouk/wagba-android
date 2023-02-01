package com.example.courseproject;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {


    private ArrayList<CartModel> CartList;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    public CartAdapter(ArrayList<CartModel> CartList){
        this.CartList = CartList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.cart_item, parent, false);
        CartAdapter.ViewHolder viewHolder = new CartAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final CartModel myListData = CartList.get(position);
        holder.nameView.setText(myListData.getName());
        holder.descriptionView.setText(myListData.getDescription());
        holder.quantityView.setText("X" + myListData.getQuantity());
        Double total_price = Double.parseDouble(myListData.getPrice()) * Double.parseDouble(myListData.getQuantity());
        holder.totalPriceView.setText(total_price.toString());
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference pathReference = firebaseStorage.getReference();
        pathReference.child(myListData.getImageURI()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.imageView);
            }
        });
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"click on item: "+myListData.getName(),Toast.LENGTH_SHORT).show();
//            }
//        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"click on item: "+myListData.getName(),Toast.LENGTH_SHORT).show();
                removeDish(myListData.res_name, myListData.name, myListData.getPrice(), myListData.user_id);
            }
        });

//
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String quantity = snapshot.child("dish" + String.valueOf(position)).child("quantity").getValue().toString();
//                holder.quantityView.setText("X" + quantity);
//                String price = snapshot.child("dish" + String.valueOf(position)).child("price").getValue().toString();
//                Double total_price = Double.parseDouble(price) * Integer.parseInt(quantity);
//                holder.totalPriceView.setText(total_price.toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//        databaseReference.child("cartItems").child("dish" + String.valueOf(position)).addValueEventListener(valueEventListener);
//

    }
    @Override
    public int getItemCount() {
        return CartList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public TextView descriptionView;
        public TextView quantityView;
        public TextView totalPriceView;
        public ImageView imageView;
        public CardView cardView;
        //        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.nameView = (TextView) itemView.findViewById((R.id.c_dish_name));
            this.descriptionView = (TextView) itemView.findViewById((R.id.c_dish_description));
            this.quantityView = (TextView) itemView.findViewById((R.id.c_dish_quantity));
            this.totalPriceView = (TextView) itemView.findViewById((R.id.c_total_price));
            this.imageView = (ImageView) itemView.findViewById(R.id.c_dish_image);
            cardView = (CardView) itemView.findViewById(R.id.cart_element);
        }
    }

    public void removeDish(String res_name, String dish_name, String dish_price, String user_id)
    {
        Log.d("fdscxz", res_name);
        Log.d("Asdasdafdsdsg", dish_name);
        databaseReference.child(user_id).child("cartItems").child(res_name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> it  = task.getResult().getChildren();
                for(DataSnapshot i: it)
                {
                    Log.d("ACVHYT", i.child("name").getValue().toString());
                    if(i.child("name").getValue().toString().equals(dish_name))
                    {
                        String dish_parent = i.getKey().toString();
                        Log.d("CVB", dish_parent);
                        int quantity = Integer.parseInt(i.child("quantity").getValue().toString());
                        if(quantity == 1)
                        {
                            databaseReference.child(user_id).child("cartItems").child(res_name).child(dish_parent).removeValue();
                        }
                        else
                        {
                            quantity = quantity - 1;
                            databaseReference.child(user_id).child("cartItems").child(res_name).child(dish_parent).child("quantity").setValue(quantity);
                        }
                    }
//                    Log.d("ASDASCVD", dish_parent);
                }
            }
        });
//
//
//        databaseReference.child("cartItems").child(res_name).child(dish_parent).child(dish_name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                int quantity = 1;
//                if (task.isSuccessful()) {
//                    Log.d("AWVBRH", "FDMA");
//                    quantity = Integer.parseInt(String.valueOf(task.getResult().getValue().toString()));
//                    if (quantity == 1) {
//                        databaseReference.child("cartItems").child(res_name).child(dish_parent).removeValue();
//                    } else {
//                        quantity = quantity -1;
//                        Log.d("fsdfsdfsd ew fwef ", String.valueOf(quantity));
//                        databaseReference.child("cartItems").child(res_name).child(dish).child("quantity").setValue(String.valueOf(quantity));
////                    CartModel cartModel = new CartModel(name, description, String.valueOf(quantity), price, imageURI);
////                    databaseReference.child("cartItems").childchild("dish" + String.valueOf(pos)).setValue(cartModel);
//                    }
//                }
//
//            }
//        });


    }
}

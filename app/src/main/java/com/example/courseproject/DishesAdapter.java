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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.ViewHolder> {

    private ArrayList<DishesModel> DishesList;
    public DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    public DishesAdapter(ArrayList<DishesModel> DishesList){
        this.DishesList = DishesList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.dish_item, parent, false);
        DishesAdapter.ViewHolder viewHolder = new DishesAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DishesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final DishesModel myListData = DishesList.get(position);
        holder.nameView.setText(myListData.getName());
        holder.descriptionView.setText(myListData.getDescription());
        holder.priceView.setText(myListData.getPrice()+ "$");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myListData.inCart)
                {
                    writeDish(position, myListData.getResName(), myListData.user_id);
                }
                else
                {
                    int quantity = 1;
                    CartModel cartModel = new CartModel(myListData.getName(), myListData.getDescription(), String.valueOf(quantity), myListData.getPrice(), myListData.getImageURI(), myListData.getResName(), myListData.user_id);
//                    databaseReference.child("cartItems").push().setValue("dish" + String.valueOf(position));
                    databaseReference.child(myListData.user_id).child("cartItems").child(myListData.getResName()).child("dish" + String.valueOf(position)).setValue(cartModel);
                    myListData.inCart = true;
                }
            }
        });
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

    }
    @Override
    public int getItemCount() {
        return DishesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public TextView descriptionView;
        public TextView priceView;
        public ImageView imageView;
        public CardView cardView;
        public ImageView btn;
        //        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.nameView = (TextView) itemView.findViewById((R.id.dish_name));
            this.descriptionView = (TextView) itemView.findViewById((R.id.dish_description));
            this.priceView = (TextView) itemView.findViewById((R.id.dish_price));
            this.imageView = (ImageView) itemView.findViewById(R.id.dish_image);
            this.btn = (ImageView) itemView.findViewById(R.id.add_to_cart_btn);
            cardView = (CardView) itemView.findViewById(R.id.dish_element);
        }
    }
//
    public void writeDish(int pos, String res_name, String user_id) {

        String dish = "dish" + String.valueOf(pos);
        databaseReference.child(user_id).child("cartItems").child(res_name).child(dish).child("quantity").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    int quantity = Integer.parseInt(String.valueOf(task.getResult().getValue())) + 1;
                    Log.d("fsdfsdfsd ew fwef ", String.valueOf(quantity));
                    databaseReference.child(user_id).child("cartItems").child(res_name).child(dish).child("quantity").setValue(String.valueOf(quantity));
//                    CartModel cartModel = new CartModel(name, description, String.valueOf(quantity), price, imageURI);
//                    databaseReference.child("cartItems").childchild("dish" + String.valueOf(pos)).setValue(cartModel);
                }

            }
        });
    }



//    public void addCount(String name, String description, String price, String imageURI)
//    {
//        ValueEventListener countLisnter = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                int count = 1;
//                if(snapshot.hasChild(name))
//                {
//                    count = Integer.parseInt(snapshot.child(name).getValue().toString());
//                    count++;
//                    databaseReference.child("cartCount").child(name).setValue(count);
//                }
//                else
//                {
//                    HashMap<String, String> cartCountMap = new HashMap<>();
//                    cartCountMap.put(name, String.valueOf(count));
////                    writeDish(name, description, price, imageURI, pos);
//                    databaseReference.child("cartCount").child(name).setValue(count);
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//        databaseReference.child("cartCount").addValueEventListener(countLisnter);
//    }

}













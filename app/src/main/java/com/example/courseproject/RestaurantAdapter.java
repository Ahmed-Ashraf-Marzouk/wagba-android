package com.example.courseproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private ArrayList<RestaurantModel> restaurantList;
    private final RestaurantRecyclerViewInterface restaurantRecyclerViewInterface;
//    private Context context;
    public RestaurantAdapter(ArrayList<RestaurantModel> restaurantList, RestaurantRecyclerViewInterface restaurantRecyclerViewInterface){
        this.restaurantList  =restaurantList;
        this.restaurantRecyclerViewInterface = restaurantRecyclerViewInterface;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("sdvsdvdsvd","ASDASd");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.restaurant_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem, restaurantRecyclerViewInterface);
        return viewHolder;
    }

//    final RestaurantModel myListData = restaurantList.get(position);
//        holder.nameView.setText(myListData.getName());
//        holder.descriptionView.setText(myListData.getDescription());
//        holder.locationView.setText(myListData.getLocation());
//        holder.imageView.setImageURI(myListData.getImageURI());
//        Log.d("image_uri", myListData.getImageURI().toString());
//
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("FEFE", "SDFDSF");
        final RestaurantModel myListData = restaurantList.get(position);
        holder.nameView.setText(myListData.getName());
        holder.descriptionView.setText(myListData.getDescription());
        holder.locationView.setText(myListData.getLocation());
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference pathReference = firebaseStorage.getReference();
        pathReference.child(myListData.getImageURI()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.imageView);
            }
        });


//        Glide.with(context).load("http://goo.gl/gEgYUd").into(holder.imageView);

//        ProgressBar progressBar =
//        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
//        StorageReference storageReference = firebaseStorage.getReference().child(myListData.getImageURI());
//        String img = myListData.getImageURI();
//        img = img.substring(img.lastIndexOf("/") + 1);
//        String []splits = img.split("\\.");
//        try {
//            File localFile = File.createTempFile(splits[0] , "." + splits[1]);
//            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                    holder.imageView.setImageBitmap(bitmap);
//                }
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAbsoluteAdapterPosition();
                restaurantRecyclerViewInterface.onItemClick(pos);
//                Toast.makeText(view.getContext(),"click on item: "+myListData.getName() + " " + Integer.toString(pos),Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public TextView descriptionView;
        public TextView locationView;
        public ImageView imageView;
        public CardView cardView;
//        public LinearLayout linearLayout;
        public ViewHolder(View itemView, RestaurantRecyclerViewInterface restaurantRecyclerViewInterface) {
            super(itemView);
            this.nameView = itemView.findViewById((R.id.res_name));
            this.descriptionView = itemView.findViewById((R.id.res_description));
            this.locationView =  itemView.findViewById((R.id.res_location));
            this.imageView =  itemView.findViewById(R.id.res_image);
            cardView = itemView.findViewById(R.id.restuarant_element);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d("Before if", "man");
//                    if(restaurantRecyclerViewInterface != null)
//                    {
//                        Log.d("restau_not_null", "MSLG");
//                        int pos = getAbsoluteAdapterPosition();
//                        if (pos != RecyclerView.NO_POSITION)
//                        {
//                            Log.d("Values of", "DF");
//                            restaurantRecyclerViewInterface.onItemClick(pos);
//                        }
//                    }
//                }
//            });
        }
    }
}







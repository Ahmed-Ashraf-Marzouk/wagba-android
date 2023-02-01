package com.example.courseproject;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Dishes_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dishes_list extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    String dish_name;
    String dish_description;
    String dish_price;
    String dish_image;
    String res_name;
    ArrayList<DishesModel> dishModels = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String user_id = FirebaseAuth.getInstance().getUid();
    int pos;
    public Dishes_list(int pos) {
        this.pos = pos;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dishes_list.
     */
    // TODO: Rename and change types and number of parameters
    public static Dishes_list newInstance(String param1, String param2) {
        Dishes_list fragment = new Dishes_list(0);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dish_item_list, container, false);
        dishModels.clear();
        Log.d("asdsaljdlas", "ASdkdlas00");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dishes_list);
        DishesAdapter adapter = new DishesAdapter(dishModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        ValueEventListener dishesListner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot temp = dataSnapshot;
                Iterable<DataSnapshot> iterable = dataSnapshot.child(res_name).getChildren();
                for(DataSnapshot it2: iterable)
                {
                    dish_name = it2.child("name").getValue().toString();
                    dish_description = it2.child("description").getValue().toString();
                    dish_price = it2.child("price").getValue().toString();
                    dish_image = it2.child("imageURI").getValue().toString();
                    dishModels.add(new DishesModel(dish_name, dish_description, dish_price, dish_image, false, res_name, user_id));
                }

                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("dishesModel").addValueEventListener(dishesListner);
//
//        DishesModel[][] dishesModels = new DishesModel [][]{
//                {
//                        new DishesModel("Toka", "Toka is not healthy", 120, R.drawable.res_9),
//                        new DishesModel("Toka", "Toka is ", 49, R.drawable.res_6),
//                        new DishesModel("Toka", "Toka is healthy", 53, R.drawable.res_4),
//                        new DishesModel("Toka", "Toka is healthy", 45, R.drawable.res_5)}
//                ,
//                {
//                        new DishesModel("Toka", "Toka is not healthy", 120, R.drawable.res_9),
//                        new DishesModel("Toka", "Toka is healthy", 49, R.drawable.res_7),
//                        new DishesModel("Toka", "Toka is healthy", 53, R.drawable.res_10),
//                        new DishesModel("Toka", "Toka is healthy", 45, R.drawable.res_1)}
//                ,
//                {
//                        new DishesModel("Toka", "Toka is not healthy", 120, R.drawable.res_6),
//                        new DishesModel("Toka", "Toka is healthy", 49, R.drawable.res_6),
//                        new DishesModel("Toka", "Toka is ", 53, R.drawable.res_4),
//                        new DishesModel("Toka", "Toka is healthy", 45, R.drawable.res_3)}
//                ,
//                {
//                        new DishesModel("Toka", "Toka is not healthy", 120, R.drawable.res_10),
//                        new DishesModel("Toka", "Toka is healthy", 49, R.drawable.res_4),
//                        new DishesModel("Toka", "Toka is healthy", 53, R.drawable.res_7),
//                        new DishesModel("Toka", "Toka is ", 45, R.drawable.res_8)}
//                ,
//
//                {
//                        new DishesModel("Toka", " is not healthy", 120, R.drawable.res_1),
//                        new DishesModel("Toka", "Toka is healthy", 49, R.drawable.res_4),
//                        new DishesModel("Toka", "Toka is healthy", 53, R.drawable.res_6),
//                        new DishesModel("Toka", "Toka is healthy", 45, R.drawable.res_9)}
//                ,
//
//                {
//                        new DishesModel("Toka", "Toka is not healthy", 120, R.drawable.res_7),
//                        new DishesModel("Toka", " is healthy", 49, R.drawable.res_10),
//                        new DishesModel("Toka", "Toka is healthy", 53, R.drawable.res_4),
//                        new DishesModel("Toka", "Toka is healthy", 45, R.drawable.res_7)}
//                ,
//
//                {
//                        new DishesModel("Toka", "Toka is not healthy", 120, R.drawable.res_6),
//                        new DishesModel("Toka", "Toka is healthy", 49, R.drawable.res_2),
//                        new DishesModel("Toka", " is healthy", 53, R.drawable.res_8),
//                        new DishesModel("Toka", "Toka is healthy", 45, R.drawable.res_7)}
//                ,
//
//                {
//                        new DishesModel("Toka", "Toka is not healthy", 120, R.drawable.res_6),
//                        new DishesModel("Toka", "Toka is healthy", 49, R.drawable.res_2),
//                        new DishesModel("Toka", "Toka is healthy", 53, R.drawable.res_10),
//                        new DishesModel("Toka", " is healthy", 45, R.drawable.res_7)}
//                ,
//
//                {
//                        new DishesModel("Toka", "Toka  not healthy", 120, R.drawable.res_8),
//                        new DishesModel("Toka", "Toka is healthy", 49, R.drawable.res_7),
//                        new DishesModel("Toka", "Toka is healthy", 53, R.drawable.res_6),
//                        new DishesModel("Toka", "Toka is healthy", 45, R.drawable.res_2)}
//                ,
//
//                {
//                        new DishesModel("Toka", "Toka is not healthy", 120, R.drawable.res_4),
//                        new DishesModel("Toka", "Toka  healthy", 49, R.drawable.res_1),
//                        new DishesModel("Toka", "Toka is healthy", 53, R.drawable.res_6),
//                        new DishesModel("Toka", "Toka is healthy", 45, R.drawable.res_7)}
//        };



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        RestaurantModel restaurantModel= (RestaurantModel) bundle.getSerializable("restaurant");
//        user_id = bundle.getString("user_id");
//        Log.d("ASDASFASFCX", user_id);
        ImageView imageView = getView().findViewById(R.id.res_dish_image);
        TextView tx = getView().findViewById(R.id.res_dish_name);
        tx.setText(restaurantModel.getName());
        res_name = restaurantModel.getName();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference pathReference = firebaseStorage.getReference();
        pathReference.child(restaurantModel.getImageURI()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("FCSC", "ASDAS");
                Picasso.get().load(uri).into(imageView);
            }
        });

    }
}
package com.example.courseproject;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cart_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cart_list extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<CartModel> cartModels = new ArrayList<>();
    String cart_dish_name;
    String cart_dish_description;
    String cart_dish_quantity;
    String cart_dish_price;
    String cart_dish_imageURI;
    String cart_dish_res_name;
    String cart_dish_user_id = FirebaseAuth.getInstance().getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Cart_list() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cart_list.
     */
    // TODO: Rename and change types and number of parameters
    public static Cart_list newInstance(String param1, String param2) {
        Cart_list fragment = new Cart_list();
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

        View view = inflater.inflate(R.layout.cart_item_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cart_list);
        CartAdapter adapter = new CartAdapter(cartModels);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ValueEventListener dishesListner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot temp = dataSnapshot;
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                cartModels.clear();
                Double cartTotal  = Double.valueOf(0);
                for(DataSnapshot child: children)
                {
                    dataSnapshot = dataSnapshot.child(child.getKey());
                    Log.d("ASasdasdDAS", child.getKey());
                    Iterable<DataSnapshot> children_2 = dataSnapshot.getChildren();
                    for(DataSnapshot child_2: children_2)
                    {
                        DataSnapshot temp_2 = dataSnapshot;
                        dataSnapshot = dataSnapshot.child(child_2.getKey());
                        Log.d("ASasdasdDAS", child_2.getKey());
                        cart_dish_name = dataSnapshot.child("name").getValue().toString();
                        cart_dish_description = dataSnapshot.child("description").getValue().toString();
                        cart_dish_quantity = dataSnapshot.child("quantity").getValue().toString();
                        cart_dish_price = dataSnapshot.child("price").getValue().toString();
                        cart_dish_imageURI = dataSnapshot.child("imageURI").getValue().toString();
                        cart_dish_res_name = dataSnapshot.child("res_name").getValue().toString();
                        cartModels.add(new CartModel(cart_dish_name, cart_dish_description, cart_dish_quantity, cart_dish_price, cart_dish_imageURI, cart_dish_res_name, cart_dish_user_id));
                        cartTotal += Double.parseDouble(cart_dish_price) * Integer.parseInt(cart_dish_quantity);
                        dataSnapshot = temp_2;
                    }
                    dataSnapshot = temp;
                }
                databaseReference.child(cart_dish_user_id).child("cartTotal").setValue(String.valueOf(cartTotal));

                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.child(cart_dish_user_id).child("cartItems").addValueEventListener(dishesListner);

//        ArrayList<CartModel> cartModels = new ArrayList<CartModel>();
//        cartModels.add(new CartModel("asdsa","asda", 56, R.drawable.logo));

//        CartModel[] cartModels = new CartModel[]{
//            new CartModel("Toka", "Toka is not healthy", 120, R.drawable.res_9),
//            new CartModel("Toka", "Toka is ", 49, R.drawable.res_6),
//            new CartModel("Toka", "Toka is healthy", 53, R.drawable.res_4),
//            new CartModel("Toka", "Toka is healthy", 45, R.drawable.res_5)
//        };
//

//        recyclerView.setAdapter(adapter);
        return view;
    }


}
package com.example.courseproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Restaurants_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Restaurants_list extends Fragment implements RestaurantRecyclerViewInterface{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Context context;

    String res_name;
    String res_description;
    String res_location;
    String res_image;
    String user_id = FirebaseAuth.getInstance().getUid();
    ArrayList<RestaurantModel> restaurantModels = new ArrayList<>();
//    RestaurantModel[] restaurantModels = new RestaurantModel[10];

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Restaurants_list() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Restaurants_list.
     */
    // TODO: Rename and change types and number of parameters
    public static Restaurants_list newInstance(String param1, String param2) {
        Restaurants_list fragment = new Restaurants_list();
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

        View view = inflater.inflate(R.layout.restaurant_item_list, container, false);
        context = container.getContext();
        // Inflate the layout for this fragment



//                getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//
//            }
//        });


//        ArrayList<RestaurantModel> restaurantModels = new ArrayList<>();
//        for (int i = 0; i < 1; i++) {
//        Log.d("success_real_2", "ASdasda d");
//        StorageReference gsReference = storage.getReferenceFromUrl("gs://wagba-abc0e.appspot.com");
//        gsReference.child("/logo.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Log.d("success_real_1", "ASdasda d");
//                image_uri[0] = uri;
////                RestaurantModel restaurantModel = new RestaurantModel("Ahmed Ashraf", " asdsad sakdl sad", "asd sad sal asd; ", uri);
////                restaurantModels.add(restaurantModel);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("error_name", e.getMessage());
//            }
//        });
//        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Log.d("success_real", "ASdasda d");
//                RestaurantModel restaurantModel = new RestaurantModel("Ahmed Ashraf", " asdsad sakdl sad", "asd sad sal asd; ", uri);
//                restaurantModels.add(restaurantModel);
//            }
//        });

//        Log.d("MMMM", image_uri[0].toString());



//        restaurantModels =
//                new RestaurantModel[] {
//                new RestaurantModel("Koshari Hend", "Fakos food for fast food", "Abdu-basha, Cairo", "/restaurants/res1.jpg"),
//                new RestaurantModel("Koshari Hend", "Fakos food for fast food", "Abdu-basha, Cairo", "/restaurants/res2.jpg"),
//                new RestaurantModel("Koshari Hend", "Fakos food for fast food", "Abdu-basha, Cairo", "/restaurants/res3.jpg"),
//                new RestaurantModel("Koshari Hend", "Fakos food for fast food", "Abdu-basha, Cairo", "/restaurants/res4.jpg"),
//                new RestaurantModel("Koshari Hend", "Fakos food for fast food", "Abdu-basha, Cairo", "/restaurants/res5.jpg"),
//                new RestaurantModel("Koshari Hend", "Fakos food for fast food", "Abdu-basha, Cairo", "/restaurants/res6.jpg"),
//                new RestaurantModel("Koshari Hend", "Fakos food for fast food", "Abdu-basha, Cairo", "/restaurants/res7.jpg"),
//                new RestaurantModel("Koshari Hend", "Fakos food for fast food", "Abdu-basha, Cairo", "/restaurants/res8.jpg"),
//                new RestaurantModel("Koshari Hend", "Fakos food for fast food", "Abdu-basha, Cairo", "/restaurants/res9.jpg"),
//                new RestaurantModel("Koshari Hend", "Fakos food for fast food", "Abdu-basha, Cairo", "/restaurants/res10.jpg"),
//
//                };

//                new RestaurantModel("Nagaf", "Fakos food for fast food", "Abdu-basha, Cairo", R.drawable.res_7 ),
//                new RestaurantModel("Tayebat El-Shaam", "Fakos food for fast food", "Abdu-basha, Cairo", R.drawable.res_4 ),
//                new RestaurantModel("Shabrawy", "Fakos food for fast food", "Abdu-basha, Cairo", R.drawable.res_5 ),
//                new RestaurantModel("El-Za3eem", "Fakos food for fast food", "Abbasyia, Cairo", R.drawable.res_6 ),
//                new RestaurantModel("Pizza Farouk", "Fakos food for fast food", "Abbasyia, Cairo", R.drawable.res_7 ),
//                new RestaurantModel("Kased Kareem", "Fakos food for fast food", "Abbasyia, Cairo", R.drawable.res_8 ),//                new RestaurantModel("Prego", "Fakos food for fast food", "Abbasyia, Cairo", R.drawable.res_9 ),
//                new RestaurantModel("3am Ayoub", "Fakos food for fast food", "Abbasyia, Cairo", R.drawable.res_10 ),
//                new RestaurantModel("3arbeet Kebda", "Fakos food for fast food", "Abbasyia, Cairo", R.drawable.res_1 )

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.res_list);
        RestaurantAdapter adapter = new RestaurantAdapter(restaurantModels, this);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ValueEventListener restaurantListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the
                restaurantModels.clear();
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot it: iterable)
                {
                    res_name = it.child("name").getValue().toString();
                    res_description = it.child("description").getValue().toString();
                    res_location = it.child("location").getValue().toString();
                    res_image = it.child("imageURI").getValue().toString();
                    RestaurantModel restaurantModel = new RestaurantModel(res_name, res_description, res_location, res_image);
                    restaurantModels.add(restaurantModel);
                }

                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

//                res_description = da
//                Object restaurantsModels = dataSnapshot.getValue();
                Log.d("OBBJ", "ASDASDAS");
                // ..
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("ERRROR", "loadPost:onCancelled", databaseError.toException());
            }
        };
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("restaurantModel").addValueEventListener(restaurantListener);



        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemClick(int position) {
//        Bundle bundle = new Bundle();
//        bundle.putInt("key",position); // Put anything what you want
        Log.d("Test-1", "Sorry for that");
        Fragment fragment = new Dishes_list(position);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Bundle bundle = new Bundle();
        RestaurantModel restaurantModel = restaurantModels.get(position);
        bundle.putSerializable("restaurant", restaurantModel);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.addToBackStack(String.valueOf(fragmentManager.getClass()));
        fragmentTransaction.commit();


//
//        Dishes_list dishes_list_fragment = new Dishes_list(position);
//        FragmentTransaction fr = getFragmentManager().beginTransaction();
//        fr.replace(R.id.fragmentContainerView, dishes_list_fragment);
//        fr.commit();
//        dishes_list_fragment.setArguments(bundle);
    }
}


//
//                dataSnapshot = temp;
//                        dataSnapshot = dataSnapshot.child("restaurant2");
//                        res_name = dataSnapshot.child("name").getValue().toString();
//                        res_description = dataSnapshot.child("description").getValue().toString();
//                        res_location = dataSnapshot.child("location").getValue().toString();
//                        res_image = dataSnapshot.child("imageURI").getValue().toString();
//                        restaurantModels.add(new RestaurantModel(res_name, res_description, res_location, res_image));

//                dataSnapshot = temp;
//                dataSnapshot = dataSnapshot.child("restaurant3");
//                res_name = dataSnapshot.child("name").getValue().toString();
//                res_description = dataSnapshot.child("description").getValue().toString();
//                res_location = dataSnapshot.child("location").getValue().toString();
//                res_image = dataSnapshot.child("imageURI").getValue().toString();
//                restaurantModels.add(new RestaurantModel(res_name, res_description, res_location, res_image));
//
//
//                dataSnapshot = temp;
//                dataSnapshot = dataSnapshot.child("restaurant4");
//                res_name = dataSnapshot.child("name").getValue().toString();
//                res_description = dataSnapshot.child("description").getValue().toString();
//                res_location = dataSnapshot.child("location").getValue().toString();
//                res_image = dataSnapshot.child("imageURI").getValue().toString();
//                restaurantModels.add(new RestaurantModel(res_name, res_description, res_location, res_image));
//
//
//                dataSnapshot = temp;
//                dataSnapshot = dataSnapshot.child("restaurant5");
//                res_name = dataSnapshot.child("name").getValue().toString();
//                res_description = dataSnapshot.child("description").getValue().toString();
//                res_location = dataSnapshot.child("location").getValue().toString();
//                res_image = dataSnapshot.child("imageURI").getValue().toString();
//                restaurantModels.add(new RestaurantModel(res_name, res_description, res_location, res_image));
//
//
//                dataSnapshot = temp;
//                dataSnapshot = dataSnapshot.child("restaurant6");
//                res_name = dataSnapshot.child("name").getValue().toString();
//                res_description = dataSnapshot.child("description").getValue().toString();
//                res_location = dataSnapshot.child("location").getValue().toString();
//                res_image = dataSnapshot.child("imageURI").getValue().toString();
//                restaurantModels.add(new RestaurantModel(res_name, res_description, res_location, res_image));

package com.example.courseproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
 * Use the {@link History_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class History_list extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<HistoryModel> historyModels = new ArrayList<>();
    String state, description, price, date;
    String user_id = FirebaseAuth.getInstance().getUid();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public History_list() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment History_list.
     */
    // TODO: Rename and change types and number of parameters
    public static History_list newInstance(String param1, String param2) {
        History_list fragment = new History_list();
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
        View view = inflater.inflate(R.layout.history_list, container, false);

//        HistoryModel[] historyModels = new HistoryModel []
//                {
//                        new HistoryModel("Not delivered", "Bad service", 120, "12/7/2022"),
//                        new HistoryModel("Canceled", "Too late", 49,"12/7/2022"),
//                        new HistoryModel("Canceled", "MAANN!", 53, "12/7/2022"),
//                        new HistoryModel("delivered", "Order was made on time", 45, "12/7/2022")
//        };

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.history_list_rec);
        HistoryAdapter adapter = new HistoryAdapter(historyModels);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                historyModels.clear();
                Iterable<DataSnapshot> iterable = snapshot.getChildren();
                for(DataSnapshot it: iterable)
                {
                    state = it.child("state").getValue().toString();
                    description = it.child("description").getValue().toString();
                    price = it.child("price").getValue().toString();
                    date = it.child("date").getValue().toString();
                    HistoryModel historyModel = new HistoryModel(state, description, price, date);
                    historyModels.add(historyModel);
                }
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseReference.child(user_id).child("history").addValueEventListener(valueEventListener);
        return view;
    }

}
package com.highfliers.high_fliers_android_hack2k21.TabFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.highfliers.high_fliers_android_hack2k21.RestrictedSection.CreateRestrictedActivity;
import com.highfliers.high_fliers_android_hack2k21.R;
import com.highfliers.high_fliers_android_hack2k21.RestrictedSection.RestrictedRoomAdapter;
import com.highfliers.high_fliers_android_hack2k21.RoomDetails;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RestrictedFragment extends Fragment {

    Context context;
    ImageButton restricted_fab;
    ArrayList<RoomDetails> list;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public RestrictedFragment(){}

    public RestrictedFragment(Context context) {
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_restricted, container, false);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restricted_fab=view.findViewById(R.id.restricted_fab);
        restricted_fab.setOnClickListener(v -> startActivity(new Intent(context, CreateRestrictedActivity.class)));

        list=new ArrayList<>();
        recyclerView=view.findViewById(R.id.restrictedRv);
        layoutManager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        adapter=new RestrictedRoomAdapter(context,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        createList();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void createList() {
        FirebaseFirestore.getInstance().collection("Restricted").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        QuerySnapshot snapshots=task.getResult();

                        for (QueryDocumentSnapshot snapshot : snapshots){
                            list.add(new RoomDetails(snapshot.get("roomName").toString(),
                                    snapshot.get("category").toString(),"restricted"));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
package com.example.furniturestore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Buy_Fragment extends Fragment {

    DatabaseReference reference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_buy_, container, false);
        reference= FirebaseDatabase.getInstance().getReference("Sell");
        final TextView nodata=v.findViewById(R.id.nodata);
        final List<String> imgname=new ArrayList<>();
        final List<String> product_name=new ArrayList<>();
        final RecyclerView recyclerView=v.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    nodata.setVisibility(View.GONE);
                    for (DataSnapshot data : snapshot.getChildren()) {
                        imgname.add(data.child("url").getValue().toString());
                        product_name.add(data.child("product").getValue().toString());
                    }

                    if (imgname.size()==0){
                        nodata.setVisibility(View.VISIBLE);
                    }

                    Buy_product_Adapter buy_product_adapter = new Buy_product_Adapter(imgname, product_name);
                    recyclerView.setAdapter(buy_product_adapter);
                }
                catch (Exception e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v;
    }
}
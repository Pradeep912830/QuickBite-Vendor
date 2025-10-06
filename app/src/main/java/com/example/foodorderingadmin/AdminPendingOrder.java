package com.example.foodorderingadmin;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingadmin.Adapter.PendingOrderAdapter;
import com.example.foodorderingadmin.Model.PendingOrder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminPendingOrder extends AppCompatActivity {
    private RecyclerView pendingRecyclerView;
    private PendingOrderAdapter adapter;
    private List<PendingOrder> orderList;
    String orderId;
    String userId;
    String itemId;
    private DatabaseReference reference;
    ImageView backButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pending_order);

        pendingRecyclerView = findViewById(R.id.pendingRecyclerView);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> onBackPressed());

        pendingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();

        adapter = new PendingOrderAdapter(this, orderList);
        pendingRecyclerView.setAdapter(adapter);


        reference = FirebaseDatabase.getInstance().getReference("Orders");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();

                for(DataSnapshot userSnapshot : snapshot.getChildren()){
                    userId = userSnapshot.getKey();

                    for(DataSnapshot orderSnapshot : userSnapshot.getChildren()){

                        orderId = orderSnapshot.getKey();
                        String status = orderSnapshot.child("status").getValue(String.class);
                        Integer totalAmount = orderSnapshot.child("totalAmount").getValue(Integer.class);
                        Integer totalQuantity = orderSnapshot.child("totalQuantity").getValue(Integer.class);

                        for(DataSnapshot itemSnapshot : orderSnapshot.child("item").getChildren()){
                            itemId = itemSnapshot.getKey();
                            String imageUrl = itemSnapshot.child("imageUrl").getValue(String.class);
                            String name = itemSnapshot.child("name").getValue(String.class);

                            PendingOrder order = new PendingOrder(imageUrl, name, totalAmount != null ? totalAmount : 0, totalQuantity != null ? totalQuantity : 0, status, orderId, userId, itemId);

                            if(!order.getPendingButton().equals("Accepted") && !order.getPendingButton().equals("Item Dispatched")&& (!orderList.contains(order))){
                                orderList.add(order);
                            }

                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", error.getMessage());
            }
        });
    }

}
package com.example.foodorderingadmin;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingadmin.Adapter.OrderItemAdapter;
import com.example.foodorderingadmin.Model.OrderItem;
import com.example.foodorderingadmin.Model.PendingOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminOutForDelivery extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderItemAdapter adapter;
    private List<PendingOrder> orderList;
    private DatabaseReference reference;
    private String userId;
    private String orderId;
    private String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_out_for_delivery);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        recyclerView = findViewById(R.id.recyclerViewAllItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImageView backBtn = findViewById(R.id.backPressed);
        backBtn.setOnClickListener(v -> onBackPressed());


        orderList = new ArrayList<>();
        adapter = new OrderItemAdapter(this, orderList);
        recyclerView.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference("Orders");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();

                for(DataSnapshot userSnapshot : snapshot.getChildren()){
                    userId = userSnapshot.getKey();

                    for(DataSnapshot orderSnapshot: userSnapshot.getChildren()){
                        orderId = orderSnapshot.getKey();
                        String status = orderSnapshot.child("status").getValue(String.class);
                        Integer price = orderSnapshot.child("totalAmount").getValue(Integer.class);
                        Integer totalQuantity = orderSnapshot.child("totalQuantity").getValue(Integer.class);

                        for(DataSnapshot itemSnapshot : orderSnapshot.child("item").getChildren()){
                            itemId = itemSnapshot.getKey();
                            String imageUrl = itemSnapshot.child("imageUrl").getValue(String.class);
                            String name = itemSnapshot.child("name").getValue(String.class);

                            PendingOrder order = new PendingOrder(imageUrl, name, price != null ? price : 0, totalQuantity != null ? totalQuantity : 0, status, orderId, userId, itemId);

                            if(!order.getPendingButton().equals("Pending") && !order.getPendingButton().equals("Accepted")){
                                orderList.add(order);
                            }
                        }
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}
package com.example.foodorderingadmin;

import android.os.Bundle;
import android.webkit.WebView;
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

import com.example.foodorderingadmin.Adapter.AcceptedAdapter;
import com.example.foodorderingadmin.Model.PendingOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AcceptedItem extends AppCompatActivity {
    private ImageView backButton;
    private RecyclerView recyclerView;
    private AcceptedAdapter adapter;
    private List<PendingOrder> orderList;
    String orderId;
    String userId;
    String itemId;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accepted_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backButton = findViewById(R.id.backButton);
        recyclerView = findViewById(R.id.recyclerViewItems);
        backButton.setOnClickListener(v -> onBackPressed());

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        orderList = new ArrayList<>();

        adapter = new AcceptedAdapter(this, orderList);
        recyclerView.setAdapter(adapter);


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

                            if(!order.getPendingButton().equals("Pending")){
                                orderList.add(order);
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AcceptedItem.this, "Failed to fetch the data!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
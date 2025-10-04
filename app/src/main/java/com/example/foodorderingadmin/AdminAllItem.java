package com.example.foodorderingadmin;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingadmin.Adapter.FoodItemAdapter;
import com.example.foodorderingadmin.Model.FoodItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminAllItem extends AppCompatActivity {

    ImageView backPressed;
    private RecyclerView recyclerView;
    private FoodItemAdapter adapter;
    private List<FoodItem> itemList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_item);

        recyclerView = findViewById(R.id.recyclerViewAllItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backPressed = findViewById(R.id.backPressed);
        backPressed.setOnClickListener(v -> finish());

        itemList = new ArrayList<>();
        adapter = new FoodItemAdapter(this, itemList);
        recyclerView.setAdapter(adapter);

        // Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("FoodItems");

        // Fetch data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodItem item = dataSnapshot.getValue(FoodItem.class);
                    if (item != null) {
                        item.setKey(dataSnapshot.getKey());
                        if(!itemList.contains(item)){
                            itemList.add(item);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}

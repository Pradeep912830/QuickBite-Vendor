package com.example.foodorderingadmin;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingadmin.Adapter.PendingOrderAdapter;
import com.example.foodorderingadmin.Model.PendingOrder;

import java.util.ArrayList;
import java.util.List;

public class AdminPendingOrder extends AppCompatActivity {

    private RecyclerView pendingRecyclerView;
    private PendingOrderAdapter adapter;
    private List<PendingOrder> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pending_order);

        pendingRecyclerView = findViewById(R.id.pendingRecyclerView);
        pendingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderList = new ArrayList<>();
        orderList.add(new PendingOrder("John Doe", 2, R.drawable.food, "Accept"));
        orderList.add(new PendingOrder("Alice Smith", 5, R.drawable.food, "Dispatch"));
        orderList.add(new PendingOrder("David Lee", 7, R.drawable.food, "Dispatch"));
        orderList.add(new PendingOrder("John Doe", 2, R.drawable.food, "Accept"));
        orderList.add(new PendingOrder("Alice Smith", 5, R.drawable.food, "Dispatch"));
        orderList.add(new PendingOrder("David Lee", 7, R.drawable.food, "Dispatch"));
        orderList.add(new PendingOrder("John Doe", 2, R.drawable.food, "Accept"));
        orderList.add(new PendingOrder("Alice Smith", 5, R.drawable.food, "Dispatch"));
        orderList.add(new PendingOrder("David Lee", 7, R.drawable.food, "Dispatch"));
        orderList.add(new PendingOrder("John Doe", 2, R.drawable.food, "Accept"));
        orderList.add(new PendingOrder("Alice Smith", 5, R.drawable.food, "Dispatch"));
        orderList.add(new PendingOrder("David Lee", 7, R.drawable.food, "Dispatch"));
        orderList.add(new PendingOrder("John Doe", 2, R.drawable.food, "Accept"));
        orderList.add(new PendingOrder("Alice Smith", 5, R.drawable.food, "Dispatch"));
        orderList.add(new PendingOrder("David Lee", 7, R.drawable.food, "Dispatch"));
        orderList.add(new PendingOrder("John Doe", 2, R.drawable.food, "Accept"));
        orderList.add(new PendingOrder("Alice Smith", 5, R.drawable.food, "Dispatch"));
        orderList.add(new PendingOrder("David Lee", 7, R.drawable.food, "Dispatch"));
        orderList.add(new PendingOrder("John Doe", 2, R.drawable.food, "Accept"));
        orderList.add(new PendingOrder("Alice Smith", 5, R.drawable.food, "Dispatch"));
        orderList.add(new PendingOrder("David Lee", 7, R.drawable.food, "Dispatch"));

        adapter = new PendingOrderAdapter(this, orderList);
        pendingRecyclerView.setAdapter(adapter);
    }
}
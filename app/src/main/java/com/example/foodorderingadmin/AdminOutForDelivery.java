package com.example.foodorderingadmin;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingadmin.Adapter.OrderItemAdapter;
import com.example.foodorderingadmin.Model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class AdminOutForDelivery extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderItemAdapter adapter;
    private List<OrderItem> orderList;

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

        orderList = new ArrayList<>();
        orderList.add(new OrderItem("Alice", "UPI", "Received"));
        orderList.add(new OrderItem("Bob", "Cash", "Received"));
        orderList.add(new OrderItem("Charlie", "Card", "Received"));
        orderList.add(new OrderItem("Alice", "UPI", "Received"));
        orderList.add(new OrderItem("Bob", "Cash", "Received"));
        orderList.add(new OrderItem("Charlie", "Card", "Received"));
        orderList.add(new OrderItem("Alice", "UPI", "Received"));
        orderList.add(new OrderItem("Bob", "Cash", "Received"));
        orderList.add(new OrderItem("Charlie", "Card", "Received"));
        orderList.add(new OrderItem("Alice", "UPI", "Received"));
        orderList.add(new OrderItem("Bob", "Cash", "Received"));
        orderList.add(new OrderItem("Charlie", "Card", "Received"));
        orderList.add(new OrderItem("Alice", "UPI", "Received"));
        orderList.add(new OrderItem("Bob", "Cash", "Received"));
        orderList.add(new OrderItem("Charlie", "Card", "Received"));
        orderList.add(new OrderItem("Alice", "UPI", "Received"));
        orderList.add(new OrderItem("Bob", "Cash", "Received"));
        orderList.add(new OrderItem("Charlie", "Card", "Received"));
        orderList.add(new OrderItem("Alice", "UPI", "Received"));
        orderList.add(new OrderItem("Bob", "Cash", "Received"));
        orderList.add(new OrderItem("Charlie", "Card", "Received"));
        orderList.add(new OrderItem("Alice", "UPI", "Received"));
        orderList.add(new OrderItem("Bob", "Cash", "Received"));
        orderList.add(new OrderItem("Charlie", "Card", "Received"));
        orderList.add(new OrderItem("Alice", "UPI", "Received"));
        orderList.add(new OrderItem("Bob", "Cash", "Received"));
        orderList.add(new OrderItem("Charlie", "Card", "Received"));

        adapter = new OrderItemAdapter(this, orderList);
        recyclerView.setAdapter(adapter);

        ImageView backBtn = findViewById(R.id.backPressed);
        backBtn.setOnClickListener(v -> onBackPressed());
    }
}
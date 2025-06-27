package com.example.foodorderingadmin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingadmin.Adapter.FoodItemAdapter;
import com.example.foodorderingadmin.Model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class AdminAllItem extends AppCompatActivity {

    ImageView backPressed;

    private RecyclerView recyclerView;
    private FoodItemAdapter adapter;
    private List<FoodItem> itemList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_item);

        recyclerView = findViewById(R.id.recyclerViewAllItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backPressed = findViewById(R.id.backPressed);

        backPressed.setOnClickListener(v -> finish());

        itemList = new ArrayList<>();
        itemList.add(new FoodItem(R.drawable.food, "Spicy Fresh Crab", "Waroenk Kita", "$35", 1));
        itemList.add(new FoodItem(R.drawable.food, "Chicken Salad", "Green Bowl", "$20", 1));
        itemList.add(new FoodItem(R.drawable.food, "Burger", "Burger Shack", "$25", 1));
        itemList.add(new FoodItem(R.drawable.food, "Spicy Fresh Crab", "Waroenk Kita", "$35", 1));
        itemList.add(new FoodItem(R.drawable.food, "Chicken Salad", "Green Bowl", "$20", 1));
        itemList.add(new FoodItem(R.drawable.food, "Burger", "Burger Shack", "$25", 1));
        itemList.add(new FoodItem(R.drawable.food, "Spicy Fresh Crab", "Waroenk Kita", "$35", 1));
        itemList.add(new FoodItem(R.drawable.food, "Chicken Salad", "Green Bowl", "$20", 1));
        itemList.add(new FoodItem(R.drawable.food, "Burger", "Burger Shack", "$25", 1));
        itemList.add(new FoodItem(R.drawable.food, "Spicy Fresh Crab", "Waroenk Kita", "$35", 1));
        itemList.add(new FoodItem(R.drawable.food, "Chicken Salad", "Green Bowl", "$20", 1));
        itemList.add(new FoodItem(R.drawable.food, "Burger", "Burger Shack", "$25", 1));
        itemList.add(new FoodItem(R.drawable.food, "Spicy Fresh Crab", "Waroenk Kita", "$35", 1));
        itemList.add(new FoodItem(R.drawable.food, "Chicken Salad", "Green Bowl", "$20", 1));
        itemList.add(new FoodItem(R.drawable.food, "Burger", "Burger Shack", "$25", 1));
        itemList.add(new FoodItem(R.drawable.food, "Spicy Fresh Crab", "Waroenk Kita", "$35", 1));
        itemList.add(new FoodItem(R.drawable.food, "Chicken Salad", "Green Bowl", "$20", 1));
        itemList.add(new FoodItem(R.drawable.food, "Burger", "Burger Shack", "$25", 1));
        itemList.add(new FoodItem(R.drawable.food, "Spicy Fresh Crab", "Waroenk Kita", "$35", 1));
        itemList.add(new FoodItem(R.drawable.food, "Chicken Salad", "Green Bowl", "$20", 1));
        itemList.add(new FoodItem(R.drawable.food, "Burger", "Burger Shack", "$25", 1));
        itemList.add(new FoodItem(R.drawable.food, "Spicy Fresh Crab", "Waroenk Kita", "$35", 1));
        itemList.add(new FoodItem(R.drawable.food, "Chicken Salad", "Green Bowl", "$20", 1));
        itemList.add(new FoodItem(R.drawable.food, "Burger", "Burger Shack", "$25", 1));


        adapter = new FoodItemAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }
}

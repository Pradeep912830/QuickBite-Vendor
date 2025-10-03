package com.example.foodorderingadmin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailsItemActivity extends AppCompatActivity {


    ImageView backButton, itemImage;
    TextView itemName, itemPrice, customerName, orderStatus, totalQuantity, totalPrice, customerAddress,MobileNumber;
    AppCompatButton pending;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        backButton = findViewById(R.id.backButton);
        itemImage = findViewById(R.id.showImage);
        MobileNumber = findViewById(R.id.MobileNumber);
        itemName = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);
        customerName = findViewById(R.id.CustomerName);
        orderStatus = findViewById(R.id.status);
        totalQuantity = findViewById(R.id.totalItemQuantity);
        totalPrice = findViewById(R.id.totalItemPrice);
        customerAddress = findViewById(R.id.customerAddress);
        pending = findViewById(R.id.pendingButton);

        backButton.setOnClickListener(v-> onBackPressed());

        String userId = getIntent().getStringExtra("userId");
        String orderId = getIntent().getStringExtra("orderId");
        String itemId = getIntent().getStringExtra("itemId");
        reference = FirebaseDatabase.getInstance().getReference("Orders").child(userId).child(orderId);


        pending.setOnClickListener(v ->{
            if(userId != null && orderId != null){
                DatabaseReference statusRef = FirebaseDatabase.getInstance()
                        .getReference("Orders").child(userId)
                        .child(orderId).child("status");

                statusRef.setValue("Accepted").addOnCompleteListener(task ->{
                      if (task.isSuccessful()){
                          pending.setText("Accepted");
                          Toast.makeText(DetailsItemActivity.this, "Item status changed!", Toast.LENGTH_SHORT).show();
                      }else {
                          Toast.makeText(DetailsItemActivity.this, "Failed to changed the status!", Toast.LENGTH_SHORT).show();
                      }
                });

            }
        });








        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // ✅ Order-level fields
                    String address = snapshot.child("address").getValue(String.class);
                    String customerNames = snapshot.child("name").getValue(String.class);
                    String phone = snapshot.child("phone").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);
                    Integer totalAmount = snapshot.child("totalAmount").getValue(Integer.class);
                    Integer totalQuantities = snapshot.child("totalQuantity").getValue(Integer.class);

                    customerAddress.setText(address);
                    customerName.setText(customerNames);
                    orderStatus.setText(status);
                    MobileNumber.setText(phone);

                    if (totalAmount != null) totalPrice.setText(String.valueOf(totalAmount));
                    if (totalQuantities != null) totalQuantity.setText(String.valueOf(totalQuantities));

                    // ✅ Fetch only that itemId
                    DataSnapshot itemSnap = snapshot.child("item").child(itemId);
                    if (itemSnap.exists()) {
                        String foodName = itemSnap.child("name").getValue(String.class);
                        String imageUrl = itemSnap.child("imageUrl").getValue(String.class);
                        Integer singlePrice = itemSnap.child("price").getValue(Integer.class);

                        itemName.setText(foodName);
                        Glide.with(DetailsItemActivity.this).load(imageUrl).into(itemImage);

                        if (singlePrice != null) itemPrice.setText(String.valueOf(singlePrice));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailsItemActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
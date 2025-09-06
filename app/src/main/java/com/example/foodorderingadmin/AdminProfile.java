package com.example.foodorderingadmin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminProfile extends AppCompatActivity {

    ImageView backButton, editProfileIcon;
    EditText ownerNameUpdate, emailUpdate, restaurantNameUpdate, locationUpdate;
    AppCompatButton btnUpdate;
    FirebaseAuth auth;
    DatabaseReference reference;
    String uid;
    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editProfileIcon = findViewById(R.id.editProfileIcon);
        ownerNameUpdate = findViewById(R.id.ownerNameUpdate); // fixed
        emailUpdate = findViewById(R.id.emailUpdate);
        restaurantNameUpdate = findViewById(R.id.restaurantNameUpdate);
        locationUpdate = findViewById(R.id.locationUpdate);
        btnUpdate = findViewById(R.id.btnUpdate); // fixed


        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if(user != null){
            uid = user.getUid();
            reference = FirebaseDatabase.getInstance().getReference("AdminUsers").child(uid);
        }else{
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            finish();
        }


        editProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchProfileData();
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileData();
            }
        });



        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void fetchProfileData(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Profile loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                if(snapshot.exists()){
                    String ownerName = snapshot.child("ownerName").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String restaurantName = snapshot.child("restaurantName").getValue(String.class);
                    String location = snapshot.child("location").getValue(String.class);


                    ownerNameUpdate.setText(ownerName);
                    emailUpdate.setText(email);
                    restaurantNameUpdate.setText(restaurantName);
                    locationUpdate.setText(location);

                }else {
                    Toast.makeText(AdminProfile.this, "Profile not found!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                  progressDialog.dismiss();
                  Toast.makeText(AdminProfile.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void updateProfileData(){

        String ownerName = ownerNameUpdate.getText().toString().trim();
        String email = emailUpdate.getText().toString().trim();
        String location = locationUpdate.getText().toString().trim();
        String restaurantName = restaurantNameUpdate.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailUpdate.setError("Invalid email format");
            return;
        }

        if(ownerName.isEmpty() || email.isEmpty() || location.isEmpty() || restaurantName.isEmpty()){
            Toast.makeText(this, "Please Fill the fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Profile updating...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        HashMap<String, Object> map = new HashMap<>();
        map.put("ownerName", ownerName);
        map.put("email", email);
        map.put("restaurantName", restaurantName);
        map.put("location", location);

        reference.updateChildren(map).addOnCompleteListener(aVoid->{
            progressDialog.dismiss();
            if(aVoid.isSuccessful()){
                Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Failed to update the details!", Toast.LENGTH_SHORT).show();
            }
        });

    }





}
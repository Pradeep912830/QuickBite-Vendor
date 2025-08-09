package com.example.foodorderingadmin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminSignup extends AppCompatActivity {

    private EditText ownerName, restaurantName, emailOrPhone, password;
    private AutoCompleteTextView chooseLocation;
    private AppCompatButton btnSignup;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private TextView alreadyHaveAccount;
    FrameLayout loadingOverLay;

    String[] locations = {"Mumbai", "Delhi", "Bangalore", "Hyderabad", "Ahmedabad", "Chennai", "Kolkata", "Pune", "Jaipur", "Surat",
            "Lucknow", "Kanpur git ", "Nagpur", "Indore", "Thane", "Bhopal", "Visakhapatnam", "Patna", "Vadodara", "Ghaziabad",
            "Ludhiana", "Agra", "Nashik", "Faridabad", "Meerut", "Rajkot", "Kalyan-Dombivli", "Vasai-Virar", "Varanasi", "Srinagar",
            "Aurangabad", "Dhanbad", "Amritsar", "Navi Mumbai", "Allahabad", "Ranchi", "Howrah", "Coimbatore", "Jabalpur", "Gwalior",
            "Vijayawada", "Jodhpur", "Madurai", "Raipur", "Kota", "Guwahati", "Chandigarh", "Solapur", "Hubli–Dharwad", "Tiruchirappalli"}; // example cities

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup); // Update with your layout name

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("AdminUsers");

        // Initialize views
        ownerName = findViewById(R.id.signupOwnerName);
        restaurantName = findViewById(R.id.signupRestaurantName);
        emailOrPhone = findViewById(R.id.signupPhoneOrEmail);
        password = findViewById(R.id.signupPassword);
        chooseLocation = findViewById(R.id.signupChooseLocation);
        btnSignup = findViewById(R.id.btnSignup);
        alreadyHaveAccount = findViewById(R.id.alreadyHaveAnAccount);
        loadingOverLay = findViewById(R.id.LoadingOverLay);

        // Set up dropdown menu
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        chooseLocation.setAdapter(adapter);

        btnSignup.setOnClickListener(view -> registerAdmin());

        alreadyHaveAccount.setOnClickListener(v -> {
            startActivity(new Intent(AdminSignup.this, AdminLogin.class));
            finish();
        });
    }

    private void registerAdmin() {

        String ownerVal = ownerName.getText().toString().trim();
        String restaurantVal = restaurantName.getText().toString().trim();
        String emailVal = emailOrPhone.getText().toString().trim();
        String locationVal = chooseLocation.getText().toString().trim();

        String pass = password.getText().toString().trim();


        if (ownerVal.isEmpty() || restaurantVal.isEmpty() || emailVal.isEmpty() || pass.isEmpty() || locationVal.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        loadingOverLay.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(emailVal, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();

                        com.example.foodorderingadmin.Model.AdminUser adminUser =
                                new com.example.foodorderingadmin.Model.AdminUser(ownerVal, restaurantVal, emailVal, locationVal);
                        databaseReference.child(userId).setValue(adminUser)
                                .addOnCompleteListener(dbTask -> {
                                    loadingOverLay.setVisibility(View.GONE);
                                    if (dbTask.isSuccessful()) {
                                        Log.d("SignupDebug", "Data saved to DB");
                                        startActivity(new Intent(AdminSignup.this, MainActivity.class));
                                        finish();
                                    } else {
                                        Log.e("SignupDebug", "DB write failed: " + dbTask.getException().getMessage());
                                        Toast.makeText(this, "Database error: " + dbTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    loadingOverLay.setVisibility(View.GONE);
                                    Log.e("SignupDebug", "DB failure: " + e.getMessage());
                                    Toast.makeText(this, "DB error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                });


                    } else {
                        loadingOverLay.setVisibility(View.GONE);
                        Toast.makeText(this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(e -> {
                    loadingOverLay.setVisibility(View.GONE);
                    Toast.makeText(this, "Signup failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

}

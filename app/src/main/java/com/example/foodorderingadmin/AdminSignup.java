package com.example.foodorderingadmin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
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

    String[] locations = {"Mumbai", "Delhi", "Bangalore", "Hyderabad", "Ahmedabad", "Chennai", "Kolkata", "Pune", "Jaipur", "Surat",
            "Lucknow", "Kanpur git ", "Nagpur", "Indore", "Thane", "Bhopal", "Visakhapatnam", "Patna", "Vadodara", "Ghaziabad",
            "Ludhiana", "Agra", "Nashik", "Faridabad", "Meerut", "Rajkot", "Kalyan-Dombivli", "Vasai-Virar", "Varanasi", "Srinagar",
            "Aurangabad", "Dhanbad", "Amritsar", "Navi Mumbai", "Allahabad", "Ranchi", "Howrah", "Coimbatore", "Jabalpur", "Gwalior",
            "Vijayawada", "Jodhpur", "Madurai", "Raipur", "Kota", "Guwahati", "Chandigarh", "Solapur", "Hubli–Dharwad", "Tiruchirappalli"}; // example cities

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
        String owner = ownerName.getText().toString().trim();
        String restaurant = restaurantName.getText().toString().trim();
        String email = emailOrPhone.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String location = chooseLocation.getText().toString().trim();

        if (owner.isEmpty() || restaurant.isEmpty() || email.isEmpty() || pass.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase email-password signup
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();

                        // Create a new AdminUser object
                        com.example.foodorderingadmin.AdminUser adminUser = new com.example.foodorderingadmin.AdminUser(owner, restaurant, email, location);

                        // Save to Firebase Realtime Database
                        databaseReference.child(userId).setValue(adminUser)
                                .addOnCompleteListener(dbTask -> {
                                    if (dbTask.isSuccessful()) {
                                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AdminSignup.this, MainActivity.class)); // Update to your next activity
                                        finish();
                                    } else {
                                        Toast.makeText(this, "Database error: " + dbTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Toast.makeText(this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}

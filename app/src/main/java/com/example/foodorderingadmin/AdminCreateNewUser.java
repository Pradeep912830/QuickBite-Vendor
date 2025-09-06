package com.example.foodorderingadmin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderingadmin.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCreateNewUser extends AppCompatActivity {

    ImageView backButton;
    EditText nameOfVendor, restNameOfVendor, emailOfVendor, passwordOfVendor, locationOfVendor;
    AppCompatButton createNewUserButton;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    DatabaseReference databaseReference;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_create_new_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("AdminUsers");

        nameOfVendor = findViewById(R.id.nameOfVendor);
        emailOfVendor = findViewById(R.id.emailOfVendor);
        locationOfVendor = findViewById(R.id.locationOfVendor);
        passwordOfVendor = findViewById(R.id.passwordOfVendor);
        restNameOfVendor = findViewById(R.id.restaurantNameUpdate);
        createNewUserButton = findViewById(R.id.createNewUserButton);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating new user...");
        progressDialog.setCancelable(false);

        createNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public void registerUser() {
        String name = nameOfVendor.getText().toString().trim();
        String email = emailOfVendor.getText().toString().trim();
        String password = passwordOfVendor.getText().toString().trim();
        String restaurantName = restNameOfVendor.getText().toString().trim();
        String location = locationOfVendor.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            nameOfVendor.setError("Enter name");
            return;
        } else if (TextUtils.isEmpty(email)) {
            emailOfVendor.setError("Enter email");
            return;
        } else if (TextUtils.isEmpty(restaurantName)) {
            restNameOfVendor.setError("Enter restaurant name");
            return;
        } else if (TextUtils.isEmpty(location)) {
            locationOfVendor.setError("Enter location");
            return;
        } else if (TextUtils.isEmpty(password) || password.length() < 6) {
            passwordOfVendor.setError("Password must be at least 6 characters");
            return;
        }

        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        if (firebaseUser != null) {
                            String uid = firebaseUser.getUid();
                            UserModel userModel = new UserModel(name, email, location, restaurantName);

                            databaseReference.child(uid).setValue(userModel)
                                    .addOnCompleteListener(taskDb -> {
                                        progressDialog.dismiss();
                                        if(taskDb.isSuccessful()){
                                            Toast.makeText(AdminCreateNewUser.this, "User created successfully", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }else{
                                            Toast.makeText(AdminCreateNewUser.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                                        }
                                        reLoginAdmin();
                                    });
                        }
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(AdminCreateNewUser.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void reLoginAdmin(){
        String adminEmail = "mohan@gmail.com";
        String adminPass = "123456";

        auth.signInWithEmailAndPassword(adminEmail, adminPass)
                .addOnCompleteListener(task ->{
            if(task.isSuccessful()){
                Toast.makeText(this, "SuccessFully login completed!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Failed to login!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
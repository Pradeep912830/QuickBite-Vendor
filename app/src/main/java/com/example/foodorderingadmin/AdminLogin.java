package com.example.foodorderingadmin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity {

    private EditText emailOrPhoneEditText, passwordEditText;
    private Button loginButton;
    TextView do_notHaveAccount;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login); // Match your XML file name

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        emailOrPhoneEditText = findViewById(R.id.loginWithEmailOrPhone);
        passwordEditText = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.btnLogin);

        loginButton.setOnClickListener(v -> loginAdmin());


        do_notHaveAccount = findViewById(R.id.do_notHaveAccount);
        do_notHaveAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLogin.this, AdminSignup.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void loginAdmin() {
        String emailOrPhone = emailOrPhoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(emailOrPhone)) {
            emailOrPhoneEditText.setError("Enter email or phone");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Enter password");
            return;
        }

        // Email/Password authentication
        mAuth.signInWithEmailAndPassword(emailOrPhone, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AdminLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        // Navigate to dashboard or next screen
                        startActivity(new Intent(AdminLogin.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(AdminLogin.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}

package com.example.foodorderingadmin;

import static android.provider.Settings.System.getString;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AdminLogin extends AppCompatActivity {

    private EditText emailOrPhoneEditText, passwordEditText;
    private Button loginButton;
    TextView do_notHaveAccount;
    AppCompatButton loginWithGoogle;
    private FirebaseAuth mAuth;
    FrameLayout loadingOverLay;
    private static final int GOOGLE_SIGN_IN_CODE = 100;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_admin_login);

        mAuth = FirebaseAuth.getInstance();
        emailOrPhoneEditText = findViewById(R.id.loginWithEmailOrPhone);
        passwordEditText = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.btnLogin);
        loginWithGoogle = findViewById(R.id.loginWithGoogle);
        do_notHaveAccount = findViewById(R.id.do_notHaveAccount);
        loadingOverLay = findViewById(R.id.lodingOverLay);

        loginButton.setOnClickListener(v -> loginAdmin());

        do_notHaveAccount.setOnClickListener(v -> {
            startActivity(new Intent(AdminLogin.this, AdminSignup.class));
            finish();
        });

        loginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingOverLay.setVisibility(View.VISIBLE);
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(AdminLogin.this, gso);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN_CODE);

            }

        });
    }

    // ⛔️ FIXED: onActivityResult is now outside of onCreate but inside the class
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_CODE) {
            Task<GoogleSignInAccount> task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account == null) {
                    Toast.makeText(getApplicationContext(), "Google Sign-in failed", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = account.getEmail();

                mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(methodCheckTask -> {
                    if (methodCheckTask.isSuccessful()) {
                        if (methodCheckTask.getResult().getSignInMethods() != null &&
                                !methodCheckTask.getResult().getSignInMethods().isEmpty()) {

                            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                            mAuth.signInWithCredential(credential)
                                    .addOnCompleteListener(AdminLogin.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task1) {
                                            if (task1.isSuccessful()) {
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                Toast.makeText(getApplicationContext(), "Signed in as: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(AdminLogin.this, MainActivity.class));
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Firebase Sign-in failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        } else {
                            Toast.makeText(AdminLogin.this, "Account does not exist. Please register first.", Toast.LENGTH_LONG).show();
                            loadingOverLay.setVisibility(View.GONE);

                            com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(AdminLogin.this,
                                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()).signOut();
                        }
                    } else {
                        Toast.makeText(AdminLogin.this, "Error checking account: " + methodCheckTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Google Sign-in failed", Toast.LENGTH_SHORT).show();
            }
        }
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

        loadingOverLay.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(emailOrPhone, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(AdminLogin.this, MainActivity.class));
                        loadingOverLay.setVisibility(View.GONE);
                        finish();
                    } else {

                       loadingOverLay.setVisibility(View.GONE);
                       Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show();

                    }

                });
    }
}

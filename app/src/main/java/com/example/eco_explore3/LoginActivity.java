package com.example.eco_explore3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.FirebaseApp;


public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    public Button loginButton;
    public Button signupButton;
    public Button skipButton;
    private ProgressBar loadingProgressBar;
    private FirebaseAuth mAuth;
    private ImageView backgroundImage;
    private int[] backgroundImages = {R.drawable.image1, R.drawable.image2, R.drawable.image3,
            R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8};
    private int currentImageIndex = 0;
    private Handler handler;
    public Runnable imageChangeRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);
        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        signupButton = findViewById(R.id.signup);
        skipButton = findViewById(R.id.btnSkip);
        loadingProgressBar = findViewById(R.id.loading);
        backgroundImage = findViewById(R.id.background_image);

        // Set click listener for login button
        loginButton.setOnClickListener(v -> {
            String email = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                login(email, password);
            } else {
                Toast.makeText(LoginActivity.this, "Please enter name and password", Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listener for signup button
        signupButton.setOnClickListener(v -> navigateToSignUpActivity());

        // Set click listener for skip button
        skipButton.setOnClickListener(v -> navigateToMainActivity());

        // Set up background image change handler
        handler = new Handler();
        imageChangeRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentImageIndex == backgroundImages.length - 1) {
                    currentImageIndex = 0;
                } else {
                    currentImageIndex++;
                }
                backgroundImage.setImageResource(backgroundImages[currentImageIndex]);
                handler.postDelayed(this, 4000); // Change image every 5 seconds
            }
        };
        handler.post(imageChangeRunnable); // Initial delay
    }

    // Method to authenticate user with Firebase
    private void login(String email, String password) {
        loadingProgressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    loadingProgressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Authentication success.", Toast.LENGTH_SHORT).show();
                        navigateToMainActivity();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Authentication failed. " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Method to navigate to main activity
    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish the LoginActivity
    }

    // Method to navigate to signup activity

        private void navigateToSignUpActivity() {

            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

        }
    }


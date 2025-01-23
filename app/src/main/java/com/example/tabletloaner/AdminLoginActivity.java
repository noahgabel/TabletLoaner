package com.example.tabletloaner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    private static final String ADMIN_PASSWORD = "123"; // Hardcoded admin password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        // Initialize UI elements
        EditText editTextPassword = findViewById(R.id.edittext_password);
        Button buttonLogin = findViewById(R.id.button_login);

        // Set up login button click listener
        buttonLogin.setOnClickListener(v -> {
            String enteredPassword = editTextPassword.getText().toString();
            // Check if entered password matches the admin password
            if (ADMIN_PASSWORD.equals(enteredPassword)) {
                // Navigate to AdminActivity if password is correct
                Intent intent = new Intent(AdminLoginActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Show error message if password is incorrect
                Toast.makeText(AdminLoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
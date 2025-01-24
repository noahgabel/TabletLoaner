package com.example.tabletloaner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the GUI elements
        initGui();
    }

    // Method to initialize the GUI elements and set up click listeners
    private void initGui() {
        Button brugerButton = findViewById(R.id.bruger_button);
        Button adminButton = findViewById(R.id.admin_button);

        // Set up click listener for the user button to navigate to BrugerActivity
        brugerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BrugerActivity.class);
            startActivity(intent);
        });

        // Set up click listener for the admin button to navigate to AdminLoginActivity
        adminButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
            startActivity(intent);
        });
    }
}
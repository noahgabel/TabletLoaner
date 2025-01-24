package com.example.tabletloaner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tabletloaner.forms.LoanForm;

public class BrugerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bruger);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        Button buttonBack = findViewById(R.id.button_back);
        Spinner spinnerTabletBrand = findViewById(R.id.spinner_tablet_brand);
        Spinner spinnerCableType = findViewById(R.id.spinner_cable_type);
        EditText editTextBorrowerName = findViewById(R.id.edittext_borrower_name);
        EditText editTextContactInfo = findViewById(R.id.edittext_contact_info);
        Button buttonSave = findViewById(R.id.button_save);

        // Initialize LoanForm with the UI elements
        LoanForm loanForm = new LoanForm(spinnerTabletBrand, spinnerCableType, editTextBorrowerName, editTextContactInfo, buttonSave);
        loanForm.initializeForm(this);

        // Set up back button to navigate to MainActivity
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(BrugerActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
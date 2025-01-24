package com.example.tabletloaner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabletloaner.models.LoanData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    private LoanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        // Initialize UI elements
        Button buttonBack = findViewById(R.id.button_back);
        EditText editTextTabletBrand = findViewById(R.id.edittext_tablet_brand);
        EditText editTextCableType = findViewById(R.id.edittext_cable_type);
        EditText editTextDateInterval = findViewById(R.id.edittext_date_interval);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoanData", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        List<LoanData> loanList = new ArrayList<>();
        List<String> loanKeys = new ArrayList<>();

        // Parse JSON strings into LoanData objects
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String jsonString = (String) entry.getValue();
            LoanData loanData = LoanData.fromJson(jsonString);
            if (loanData != null) {
                loanList.add(loanData);
                loanKeys.add(entry.getKey());
            }
        }

        // Set up back button to navigate to MainActivity
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Initialize adapter and set it to RecyclerView
        adapter = new LoanAdapter(this, loanList, loanKeys);
        recyclerView.setAdapter(adapter);

        // Add text change listeners to filter the list based on input
        TextWatcher filterTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tabletBrand = editTextTabletBrand.getText().toString();
                String cableType = editTextCableType.getText().toString();
                String dateInterval = editTextDateInterval.getText().toString();
                adapter.filter(tabletBrand, cableType, dateInterval);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        // Attach text change listeners to EditText fields
        editTextTabletBrand.addTextChangedListener(filterTextWatcher);
        editTextCableType.addTextChangedListener(filterTextWatcher);
        editTextDateInterval.addTextChangedListener(filterTextWatcher);
    }
}
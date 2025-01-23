package com.example.tabletloaner.forms;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.example.tabletloaner.models.LoanData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class LoanForm {
    private final Spinner spinnerTabletBrand;
    private final Spinner spinnerCableType;
    private final EditText editTextBorrowerName;
    private final EditText editTextContactInfo;
    private final Button buttonSave;

    // Constructor to initialize the form fields and save button
    public LoanForm(Spinner spinnerTabletBrand, Spinner spinnerCableType, EditText editTextBorrowerName, EditText editTextContactInfo, Button buttonSave) {
        this.spinnerTabletBrand = spinnerTabletBrand;
        this.spinnerCableType = spinnerCableType;
        this.editTextBorrowerName = editTextBorrowerName;
        this.editTextContactInfo = editTextContactInfo;
        this.buttonSave = buttonSave;
    }

    // Constructor to initialize the form fields and save button
    public void initializeForm(Context context) {
        buttonSave.setOnClickListener(v -> {
            if (validateForm(context)) {
                saveLoanData(context);
                showLoanSummary(context);
            }
        });
    }

    // Method to validate the form fields
    private boolean validateForm(Context context) {
        String borrowerName = editTextBorrowerName.getText().toString();
        String contactInfo = editTextContactInfo.getText().toString();

        // Check if borrower name and contact info are not empty
        if (borrowerName.isEmpty() || contactInfo.isEmpty()) {
            Toast.makeText(context, "Låner navn og kontakt info skal udfyldes", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Method to save the loan data to SharedPreferences
    private void saveLoanData(Context context) {
        String tabletBrand = spinnerTabletBrand.getSelectedItem().toString();
        String cableType = spinnerCableType.getSelectedItem().toString();
        String borrowerName = editTextBorrowerName.getText().toString();
        String contactInfo = editTextContactInfo.getText().toString();
        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // Create a LoanData object with the form data
        LoanData loanData = new LoanData(tabletBrand, cableType, borrowerName, contactInfo, dateTime);

        // Save the LoanData object to SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoanData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String uniqueKey = UUID.randomUUID().toString();
        editor.putString(uniqueKey, loanData.toString());
        editor.apply();
    }

    // Save the LoanData object to SharedPreferences
    private void showLoanSummary(Context context) {
        String tabletBrand = spinnerTabletBrand.getSelectedItem().toString();
        String cableType = spinnerCableType.getSelectedItem().toString();
        String borrowerName = editTextBorrowerName.getText().toString();
        String contactInfo = editTextContactInfo.getText().toString();
        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // Create a summary string with the loan data
        String summary = "Tablet Mærke: " + tabletBrand + "\n" +
                         "Kabel Type: " + cableType + "\n" +
                         "Låner Navn: " + borrowerName + "\n" +
                         "Kontakt Info: " + contactInfo + "\n" +
                         "Dato og Tidspunkt: " + dateTime;

        // Show the summary in an AlertDialog
        new AlertDialog.Builder(context)
                .setTitle("Låne Kvittering")
                .setMessage(summary)
                .setPositiveButton("OK", null)
                .show();
    }
}
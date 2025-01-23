package com.example.tabletloaner;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabletloaner.models.LoanData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.LoanViewHolder> {
    private final List<LoanData> loanList; // List of all loan data
    private final List<String> loanKeys; // List of keys for each loan data
    private final SharedPreferences sharedPreferences; // SharedPreferences to store loan data
    private List<LoanData> filteredLoanList; // List of filtered loan data

    // Constructor to initialize the adapter with context, loan data list, and loan keys
    public LoanAdapter(Context context, List<LoanData> loanList, List<String> loanKeys) {
        this.loanList = loanList;
        this.loanKeys = loanKeys;
        this.sharedPreferences = context.getSharedPreferences("LoanData", Context.MODE_PRIVATE);
        this.filteredLoanList = new ArrayList<>(loanList);
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public LoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_item, parent, false);
        return new LoanViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull LoanViewHolder holder, int position) {
        LoanData loanData = filteredLoanList.get(position);
        if (loanData != null) {
            // Bind loan data to the view holder
            holder.tabletBrand.setText(loanData.getTabletBrand());
            holder.cableType.setText(loanData.getCableType());
            holder.borrowerName.setText(loanData.getBorrowerName());
            holder.contactInfo.setText(loanData.getContactInfo());
            holder.dateTime.setText(loanData.getDateTime());

            // Set up delete button to remove loan data
            holder.deleteButton.setOnClickListener(v -> {
                String key = loanKeys.get(position);
                sharedPreferences.edit().remove(key).apply();
                loanList.remove(position);
                loanKeys.remove(position);
                filteredLoanList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, filteredLoanList.size());
            });
        }
    }

    // Return the size of the filtered loan list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return filteredLoanList.size();
    }

    // Filter the loan list based on tablet brand, cable type, and date interval
    public void filter(String tabletBrand, String cableType, String dateInterval) {
        filteredLoanList.clear();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date startDate = null, endDate = null;

        // Parse date interval if provided
        if (!dateInterval.isEmpty()) {
            String[] dates = dateInterval.split(" to ");
            try {
                startDate = sdf.parse(dates[0]);
                endDate = sdf.parse(dates[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Filter loan data based on criteria
        for (LoanData loanData : loanList) {
            boolean matchesTabletBrand = loanData.getTabletBrand().toLowerCase().contains(tabletBrand.toLowerCase());
            boolean matchesCableType = loanData.getCableType().toLowerCase().contains(cableType.toLowerCase());
            boolean matchesDateInterval = true;

            // Check if loan date is within the specified date interval
            if (startDate != null && endDate != null) {
                try {
                    Date loanDate = sdf.parse(loanData.getDateTime().split(" ")[0]);
                    matchesDateInterval = loanDate != null && !loanDate.before(startDate) && !loanDate.after(endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            // Add loan data to filtered list if it matches all criteria
            if (matchesTabletBrand && matchesCableType && matchesDateInterval) {
                filteredLoanList.add(loanData);
            }
        }
        notifyDataSetChanged();
    }

    // ViewHolder class to hold references to the views for each loan item
    static class LoanViewHolder extends RecyclerView.ViewHolder {
        TextView tabletBrand, cableType, borrowerName, contactInfo, dateTime;
        Button deleteButton;

        // Constructor to initialize the view holder with item views
        public LoanViewHolder(@NonNull View itemView) {
            super(itemView);
            tabletBrand = itemView.findViewById(R.id.textview_tablet_brand);
            cableType = itemView.findViewById(R.id.textview_cable_type);
            borrowerName = itemView.findViewById(R.id.textview_borrower_name);
            contactInfo = itemView.findViewById(R.id.textview_contact_info);
            dateTime = itemView.findViewById(R.id.textview_date_time);
            deleteButton = itemView.findViewById(R.id.button_delete);
        }
    }
}
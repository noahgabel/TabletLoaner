package com.example.tabletloaner.models;

import org.json.JSONException;
import org.json.JSONObject;

public class LoanData {
    // Fields to store loan data
    private String tabletBrand;
    private String cableType;
    private String borrowerName;
    private String contactInfo;
    private String dateTime;

    // Constructor to initialize loan data
    public LoanData(String tabletBrand, String cableType, String borrowerName, String contactInfo, String dateTime) {
        this.tabletBrand = tabletBrand;
        this.cableType = cableType;
        this.borrowerName = borrowerName;
        this.contactInfo = contactInfo;
        this.dateTime = dateTime;
    }

    // Getter and setter methods for tablet brand
    public String getTabletBrand() {
        return tabletBrand;
    }

    public void setTabletBrand(String tabletBrand) {
        this.tabletBrand = tabletBrand;
    }

    // Getter and setter methods for cable type
    public String getCableType() {
        return cableType;
    }

    public void setCableType(String cableType) {
        this.cableType = cableType;
    }

    // Getter and setter methods for borrower name
    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    // Getter and setter methods for contact info
    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    // Getter and setter methods for date and time
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    // Convert LoanData object to JSON string
    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tabletBrand", tabletBrand);
            jsonObject.put("cableType", cableType);
            jsonObject.put("borrowerName", borrowerName);
            jsonObject.put("contactInfo", contactInfo);
            jsonObject.put("dateTime", dateTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    // Create LoanData object from JSON string
    public static LoanData fromJson(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String tabletBrand = jsonObject.getString("tabletBrand");
            String cableType = jsonObject.getString("cableType");
            String borrowerName = jsonObject.getString("borrowerName");
            String contactInfo = jsonObject.getString("contactInfo");
            String dateTime = jsonObject.getString("dateTime");
            return new LoanData(tabletBrand, cableType, borrowerName, contactInfo, dateTime);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
package com.example.cse412financialmanagementsystem;
import java.util.Date;

public class Receipt {
    private int purId;
    private boolean incoming;
    private double amount;
    private Date date;
    private boolean recurring;
    private int accountId;
    private int vendorId;

    public Receipt(int purId, boolean incoming, double amount, Date date, boolean recurring, int vendorId) {
        this.purId = purId;
        this.incoming = incoming;
        this.amount = amount;
        this.date = date;
        this.recurring = recurring;
        this.accountId = accountId;
        this.vendorId = vendorId;
    }

    // Getters and setters

    public int getPurId() {
        return purId;
    }

    public void setPurId(int purId) {
        this.purId = purId;
    }

    public boolean isIncoming() {
        return incoming;
    }

    public void setIncoming(boolean incoming) {
        this.incoming = incoming;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }
}


package com.banking.model;

public class Account {
    private double balance;
    private String status; // "Unverified", "Verified", "Suspended", "Closed"

    public Account(double initialBalance, String initialStatus) {
        this.balance = initialBalance;
        this.status = initialStatus;
    }

    public boolean deposit(double amount) {
        if (status.equals("Closed") || status.equals("Suspended") || amount <= 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    public boolean withdraw(double amount) {
        if (status.equals("Closed") || status.equals("Suspended") || amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
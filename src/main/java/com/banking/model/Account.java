package com.banking.model;

import java.util.Random;

public class Account {
    private String name;
    private String number;
    private double balance;
    private String status; // "Unverified", "Verified", "Suspended", "Closed"

    // Default constructor
    public Account() {
        this.name = "John Doe";
        this.number = "123456789";
        this.balance = 1000.00;
        this.status = "Verified";
    }

    public Account(double initialBalance, String initialStatus) {
        this.name = "John Doe";
        this.number = "123456789";
        this.balance = initialBalance;
        this.status = initialStatus;
    }

    public Account(String name, String number, double initialBalance, String initialStatus) {
        this.name = name;
        this.number = number;
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

    //new feature
    public int getCreditScore(String accountNumber) {
        // simulate a credit score check
        return new Random().nextInt(551) + 300; // Return a random score between 300 and 850
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
package com.banking.service;

import java.util.Random;

public class CreditScoreService {
    public int getCreditScore(String accountNumber) {
        // Simulate a credit score check
        return new Random().nextInt(551) + 300; // Return a random score between 300 and 850
    }
}
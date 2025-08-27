package com.banking.service;

import com.banking.model.Account;
import org.springframework.stereotype.Service;

@Service
public class TransactionProcessor {
    public boolean deposit(Account account, double amount) {
        return account.deposit(amount);
    }

    public boolean withdraw(Account account, double amount) {
        return account.withdraw(amount);
    }

    public boolean transfer(Account from, Account to, double amount) {
        if (from.withdraw(amount)) {
            if (to.deposit(amount)) {
                return true;
            }
            from.deposit(amount); // Rollback
        }
        return false;
    }
}
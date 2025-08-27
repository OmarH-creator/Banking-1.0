package com.banking.service;

import com.banking.model.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public Account getCurrentAccount() {
        // In a real application, this would get the account from user session/authentication
        // For now, returning a sample account for demonstration
        Account account = new Account();
        account.setName("John Doe");
        account.setNumber("123456789");
        account.setBalance(1000.00);
        account.setStatus("Verified");
        return account;
    }

    public void verifyAccount(Account account) {
        if (account.getStatus().equals("Unverified")) {
            account.setStatus("Verified");
        }
    }

    public void suspendAccount(Account account) {
        if (account.getStatus().equals("Verified")) {
            account.setStatus("Suspended");
        }
    }

    public void closeAccount(Account account) {
        if (!account.getStatus().equals("Closed") && !account.getStatus().equals("Unverified")) {
            account.setStatus("Closed");
        }
    }

    public void appealSuspension(Account account) {
        if (account.getStatus().equals("Suspended")) {
            account.setStatus("Verified");
        }
    }
}
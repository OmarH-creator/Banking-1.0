package com.banking.service;

import com.banking.model.Account;

public class AccountService {
    public void verifyAccount(Account account) {
        account.setStatus("Verified");
    }

    public void suspendAccount(Account account) {
        account.setStatus("Suspended");
    }

    public void closeAccount(Account account) {
        account.setStatus("Closed");
    }

    public void appealSuspension(Account account) {
        account.setStatus("Verified");
    }
}
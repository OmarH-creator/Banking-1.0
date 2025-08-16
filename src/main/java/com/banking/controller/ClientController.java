package com.banking.controller;

import com.banking.model.Account;
import com.banking.service.AccountService;
import com.banking.service.TransactionProcessor;

public class ClientController {
    private Account account;
    private AccountService accountService;
    private TransactionProcessor transactionProcessor;

    public ClientController(Account account, AccountService accountService, TransactionProcessor transactionProcessor) {
        this.account = account;
        this.accountService = accountService;
        this.transactionProcessor = transactionProcessor;
    }

    public String getAccountStatus() {
        return account.getStatus();
    }

    public double getBalance() {
        return account.getBalance();
    }

    public boolean deposit(double amount) {
        return transactionProcessor.deposit(account, amount);
    }

    public boolean withdraw(double amount) {
        return transactionProcessor.withdraw(account, amount);
    }

    public void verify() {
        accountService.verifyAccount(account);
    }

    public void suspend() {
        accountService.suspendAccount(account);
    }

    public void close() {
        accountService.closeAccount(account);
    }

    public void appeal() {
        accountService.appealSuspension(account);
    }
}
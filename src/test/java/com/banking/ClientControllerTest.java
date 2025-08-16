package com.banking;

import com.banking.controller.ClientController;
import com.banking.model.Account;
import com.banking.service.AccountService;
import com.banking.service.TransactionProcessor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientControllerTest {

    private ClientController controller;
    private Account account;
    private AccountService accountService;
    private TransactionProcessor transactionProcessor;

    @Before
    public void setUp() {
        account = new Account(1000, "Verified");
        accountService = new AccountService();
        transactionProcessor = new TransactionProcessor();
        controller = new ClientController(account, accountService, transactionProcessor);
    }

    @Test
    public void testGetAccountStatus() {
        assertEquals("Verified", controller.getAccountStatus());
    }

    @Test
    public void testGetBalance() {
        assertEquals(1000, controller.getBalance(), 0.001);
    }

    @Test
    public void testDeposit() {
        assertTrue(controller.deposit(100));
        assertEquals(1100, controller.getBalance(), 0.001);
    }

    @Test
    public void testWithdraw() {
        assertTrue(controller.withdraw(100));
        assertEquals(900, controller.getBalance(), 0.001);
    }

    @Test
    public void testVerify() {
        account.setStatus("Unverified");
        controller.verify();
        assertEquals("Verified", controller.getAccountStatus());
    }

    @Test
    public void testSuspend() {
        controller.suspend();
        assertEquals("Suspended", controller.getAccountStatus());
    }

    @Test
    public void testClose() {
        controller.close();
        assertEquals("Closed", controller.getAccountStatus());
    }

    @Test
    public void testAppeal() {
        account.setStatus("Suspended");
        controller.appeal();
        assertEquals("Verified", controller.getAccountStatus());
    }
}
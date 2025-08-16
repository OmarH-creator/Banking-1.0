package com.banking;

import com.banking.model.Account;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {

    // Test cases for deposit method
    @Test
    public void testDeposit_PositiveAmount() {
        Account account = new Account(1000, "Verified");
        assertTrue(account.deposit(100));
        assertEquals(1100, account.getBalance(), 0.001);
    }

    @Test
    public void testDeposit_NegativeAmount() {
        Account account = new Account(1000, "Verified");
        assertFalse(account.deposit(-100));
        assertEquals(1000, account.getBalance(), 0.001);
    }

    @Test
    public void testDeposit_ZeroAmount() {
        Account account = new Account(1000, "Verified");
        assertFalse(account.deposit(0));
        assertEquals(1000, account.getBalance(), 0.001);
    }

    @Test
    public void testDeposit_InClosedAccount() {
        Account account = new Account(1000, "Closed");
        assertFalse(account.deposit(100));
        assertEquals(1000, account.getBalance(), 0.001);
    }

    // Test cases for withdraw method
    @Test
    public void testWithdraw_SufficientBalance() {
        Account account = new Account(1000, "Verified");
        assertTrue(account.withdraw(100));
        assertEquals(900, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdraw_InsufficientBalance() {
        Account account = new Account(1000, "Verified");
        assertFalse(account.withdraw(2000));
        assertEquals(1000, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdraw_NegativeAmount() {
        Account account = new Account(1000, "Verified");
        assertFalse(account.withdraw(-100));
        assertEquals(1000, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdraw_ZeroAmount() {
        Account account = new Account(1000, "Verified");
        assertFalse(account.withdraw(0));
        assertEquals(1000, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdraw_FromSuspendedAccount() {
        Account account = new Account(1000, "Suspended");
        assertFalse(account.withdraw(100));
        assertEquals(1000, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdraw_FromClosedAccount() {
        Account account = new Account(1000, "Closed");
        assertFalse(account.withdraw(100));
        assertEquals(1000, account.getBalance(), 0.001);
    }
}
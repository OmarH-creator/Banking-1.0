package com.banking;

import com.banking.model.Account;
import com.banking.service.AccountService;
import org.junit.Test;
import static org.junit.Assert.*;

public class StateBasedTest {

    @Test
    public void testStateTransitions() {
        Account account = new Account(1000, "Unverified");
        AccountService service = new AccountService();

        assertEquals("Unverified", account.getStatus());

        service.verifyAccount(account);
        assertEquals("Verified", account.getStatus());

        service.suspendAccount(account);
        assertEquals("Suspended", account.getStatus());

        service.appealSuspension(account);
        assertEquals("Verified", account.getStatus());

        service.closeAccount(account);
        assertEquals("Closed", account.getStatus());
    }

    @Test
    public void testActionsInSuspendedState() {
        Account account = new Account(1000, "Suspended");
        assertFalse(account.withdraw(100));
        assertFalse(account.deposit(100)); // Assuming deposit is also not allowed in suspended state
    }

    @Test
    public void testActionsInClosedState() {
        Account account = new Account(1000, "Closed");
        assertFalse(account.withdraw(100));
        assertFalse(account.deposit(100));
    }
    @Test
    public void testInvalidTransitions() {
        AccountService service = new AccountService();

        Account account = new Account(500, "Unverified");
        service.suspendAccount(account);
        assertEquals("Unverified", account.getStatus()); // should not change

        service.closeAccount(account);
        assertEquals("Unverified", account.getStatus()); // still no change

        service.appealSuspension(account);
        assertEquals("Unverified", account.getStatus()); // still no change

        // Go to verified, then close
        service.verifyAccount(account);
        assertEquals("Verified", account.getStatus());
        service.closeAccount(account);
        assertEquals("Closed", account.getStatus());

        // Try invalid transitions from closed
        service.verifyAccount(account);
        assertEquals("Closed", account.getStatus());

        service.appealSuspension(account);
        assertEquals("Closed", account.getStatus());
    }
}
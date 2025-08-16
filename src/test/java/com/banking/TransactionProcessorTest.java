package com.banking;

import com.banking.model.Account;
import com.banking.service.TransactionProcessor;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionProcessorTest {

    @Test
    public void testTransfer_Successful() {
        Account from = new Account(1000, "Verified");
        Account to = new Account(500, "Verified");
        TransactionProcessor processor = new TransactionProcessor();
        assertTrue(processor.transfer(from, to, 100));
        assertEquals(900, from.getBalance(), 0.001);
        assertEquals(600, to.getBalance(), 0.001);
    }

    @Test
    public void testTransfer_InsufficientBalance() {
        Account from = new Account(100, "Verified");
        Account to = new Account(500, "Verified");
        TransactionProcessor processor = new TransactionProcessor();
        assertFalse(processor.transfer(from, to, 200));
        assertEquals(100, from.getBalance(), 0.001);
        assertEquals(500, to.getBalance(), 0.001);
    }

    @Test
    public void testTransfer_ToClosedAccount() {
        Account from = new Account(1000, "Verified");
        Account to = new Account(500, "Closed");
        TransactionProcessor processor = new TransactionProcessor();
        assertFalse(processor.transfer(from, to, 100));
        assertEquals(1000, from.getBalance(), 0.001);
        assertEquals(500, to.getBalance(), 0.001);
    }
}
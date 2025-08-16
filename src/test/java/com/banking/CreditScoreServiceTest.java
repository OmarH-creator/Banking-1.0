package com.banking;

import com.banking.service.CreditScoreService;
import org.junit.Test;
import static org.junit.Assert.*;

public class CreditScoreServiceTest {

    @Test
    public void testGetCreditScore() {
        CreditScoreService service = new CreditScoreService();
        int score = service.getCreditScore("123456789");
        assertTrue(score >= 300 && score <= 850);
    }
}
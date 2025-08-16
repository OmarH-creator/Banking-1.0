package com.banking;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class UITest {
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\Chrome Driver\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void testDepositButton() {
        driver.get("file:///c:/Users/Omar%20Hassan/Desktop/Tae/src/main/resources/templates/dashboard.html");
        WebElement amountField = driver.findElement(By.id("amount"));
        WebElement depositButton = driver.findElement(By.id("deposit-btn"));
        WebElement balance = driver.findElement(By.id("balance"));
        WebElement notification = driver.findElement(By.id("notification"));

        amountField.sendKeys("100");
        depositButton.click();

        assertEquals("1100.00", balance.getText());
        assertEquals("Deposit successful.", notification.getText());
    }

    @Test
    public void testTransferButton() {
        driver.get("file:///c:/Users/Omar%20Hassan/Desktop/Tae/src/main/resources/templates/dashboard.html");
        WebElement amountField = driver.findElement(By.id("amount"));
        WebElement transferButton = driver.findElement(By.id("transfer-btn"));
        // Set up initial state
        amountField.clear();
        amountField.sendKeys("100");
        transferButton.click();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
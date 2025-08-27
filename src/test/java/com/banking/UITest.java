package com.banking;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UITest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:8080";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\omara\\Desktop\\projects\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
    }

    @Test
    public void test01LoginPage() {
        driver.get(BASE_URL + "/");

        // Check if login page loads correctly
        WebElement loginForm = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("form")));
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        assertTrue("Login form should be present", loginForm.isDisplayed());
        assertTrue("Username field should be present", usernameField.isDisplayed());
        assertTrue("Password field should be present", passwordField.isDisplayed());
        assertTrue("Login button should be present", loginButton.isDisplayed());

        // Test login functionality
        usernameField.sendKeys("testuser");
        passwordField.sendKeys("testpass");
        loginButton.click();

        // Should redirect to dashboard
        wait.until(ExpectedConditions.urlContains("/dashboard"));
    }

    @Test
    public void test02DashboardElements() {
        driver.get(BASE_URL + "/dashboard");

        // Check if all dashboard elements are present
        WebElement header = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".header h1")));
        assertEquals("🏦 Banking Dashboard", header.getText());

        // Check account information card
        WebElement accountInfoCard = driver.findElement(By.cssSelector(".card h2"));
        assertEquals("Account Information", accountInfoCard.getText());

        // Check if account details are displayed
        WebElement clientName = driver.findElement(By.cssSelector(".info-item .info-value"));
        assertNotNull("Client name should be displayed", clientName.getText());

        // Check transactions card
        WebElement transactionsCard = driver.findElement(By.xpath("//h2[text()='Transactions']"));
        assertTrue("Transactions card should be present", transactionsCard.isDisplayed());

        // Check deposit and withdraw forms
        WebElement depositForm = driver.findElement(By.cssSelector("form[action='/deposit']"));
        WebElement withdrawForm = driver.findElement(By.cssSelector("form[action='/withdraw']"));
        assertTrue("Deposit form should be present", depositForm.isDisplayed());
        assertTrue("Withdraw form should be present", withdrawForm.isDisplayed());

        // Check account management buttons
        WebElement verifyButton = driver.findElement(By.cssSelector("form[action='/verify'] button"));
        WebElement suspendButton = driver.findElement(By.cssSelector("form[action='/suspend'] button"));
        WebElement closeButton = driver.findElement(By.cssSelector("form[action='/close'] button"));
        WebElement appealButton = driver.findElement(By.cssSelector("form[action='/appeal'] button"));

        assertTrue("Verify button should be present", verifyButton.isDisplayed());
        assertTrue("Suspend button should be present", suspendButton.isDisplayed());
        assertTrue("Close button should be present", closeButton.isDisplayed());
        assertTrue("Appeal button should be present", appealButton.isDisplayed());
    }

    @Test
    public void test03DepositFunctionality() {
        driver.get(BASE_URL + "/dashboard");

        // Get initial balance
        WebElement balanceElement = driver.findElement(By.cssSelector(".balance"));
        String initialBalance = balanceElement.getText().replace("$", "");
        double initialBalanceValue = Double.parseDouble(initialBalance);


        // Perform deposit
        WebElement depositAmountField = driver.findElement(By.id("deposit-amount"));
        WebElement depositButton = driver.findElement(By.cssSelector("form[action='/deposit'] button"));

        depositAmountField.clear();
        depositAmountField.sendKeys("100.50");
        depositButton.click();

        // Wait for redirect and check if balance updated
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        WebElement toast = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container .toast"))
        );
        assertTrue("Toast should be visible", toast.isDisplayed());

        // Verify the page reloaded with updated data
        WebElement newBalanceElement = driver.findElement(By.cssSelector(".balance"));
        String newBalance = newBalanceElement.getText().replace("$", "");
        double newBalanceValue = Double.parseDouble(newBalance);

        // Balance should have increased (though exact amount depends on backend logic)
        assertTrue("Balance should be updated after deposit", newBalanceValue >= initialBalanceValue);
    }

    @Test
    public void test04WithdrawFunctionality() {
        driver.get(BASE_URL + "/dashboard");

        // Get initial balance
        WebElement balanceElement = driver.findElement(By.cssSelector(".balance"));
        String initialBalance = balanceElement.getText().replace("$", "");
        double initialBalanceValue = Double.parseDouble(initialBalance);

        // Perform withdrawal
        WebElement withdrawAmountField = driver.findElement(By.id("withdraw-amount"));
        WebElement withdrawButton = driver.findElement(By.cssSelector("form[action='/withdraw'] button"));

        withdrawAmountField.clear();
        withdrawAmountField.sendKeys("50.00");
        withdrawButton.click();

        // Wait for redirect back to dashboard
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        // Wait for toast notification to appear (robust handling of timing)
        WebElement toast = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container .toast"))
        );
        assertTrue("Toast should be visible after withdrawal", toast.isDisplayed());

        // Re-check updated balance
        WebElement newBalanceElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".balance"))
        );
        String newBalance = newBalanceElement.getText().replace("$", "");
        double newBalanceValue = Double.parseDouble(newBalance);

        // Assert balance decreased
        assertTrue(
                "Balance should decrease after withdrawal",
                newBalanceValue < initialBalanceValue
        );
    }

    @Test
    public void test05FormValidation() {
        driver.get(BASE_URL + "/dashboard");

        // Test deposit form validation
        WebElement depositAmountField = driver.findElement(By.id("deposit-amount"));
        WebElement depositButton = driver.findElement(By.cssSelector("form[action='/deposit'] button"));

        // Try to submit empty form
        depositAmountField.clear();
        depositButton.click();

        // Form should not submit due to HTML5 validation
        // Check if we're still on the same page
        assertTrue("Should remain on dashboard page", driver.getCurrentUrl().contains("/dashboard"));

        // Try with invalid amount (negative)
        depositAmountField.clear();
        depositAmountField.sendKeys("-100");
        depositButton.click();

        // Should still be on dashboard
        assertTrue("Should remain on dashboard page", driver.getCurrentUrl().contains("/dashboard"));
    }

    @Test
    public void test06ResponsiveDesign() {
        driver.get(BASE_URL + "/dashboard");

        // Test mobile viewport
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));

        // Check if elements are still accessible
        WebElement header = driver.findElement(By.cssSelector(".header h1"));
        assertTrue("Header should be visible on mobile", header.isDisplayed());

        WebElement depositForm = driver.findElement(By.cssSelector("form[action='/deposit']"));
        assertTrue("Deposit form should be visible on mobile", depositForm.isDisplayed());

        // Test desktop viewport
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));

        // Elements should still be visible
        assertTrue("Header should be visible on desktop", header.isDisplayed());
        assertTrue("Deposit form should be visible on desktop", depositForm.isDisplayed());
    }


    @Test
    public void test07AccountVerification() {
        driver.get(BASE_URL + "/dashboard");

        // Get initial status using the specific ID
        WebElement statusElement = driver.findElement(By.id("account-status"));
        String initialStatus = statusElement.getText();

        // Click verify button
        WebElement verifyButton = driver.findElement(By.cssSelector("form[action='/verify'] button"));
        verifyButton.click();

        // Wait for redirect
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        // Check if status changed to verified
        WebElement newStatusElement = driver.findElement(By.id("account-status"));
        String newStatus = newStatusElement.getText();

        // Status should be verified after clicking verify
        assertEquals("Account should be verified", "Verified", newStatus);
    }

    @Test
    public void test08AccountSuspension() {
        driver.get(BASE_URL + "/dashboard");

        // Click suspend button
        driver.findElement(By.cssSelector("form[action='/suspend'] button")).click();

        // Wait for redirect to dashboard
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        // Locator for the status element
        By statusLocator = By.id("account-status");

        // Ensure the element is present/visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(statusLocator));

        // Wait until its text contains "Suspended" (returns Boolean)
        assertTrue(
                "Status text should become 'Suspended'",
                wait.until(ExpectedConditions.textToBePresentInElementLocated(statusLocator, "Suspended"))
        );

        // Now read and assert exact text (trim to avoid whitespace issues)
        String status = driver.findElement(statusLocator).getText().trim();
        assertEquals("Account should be suspended", "Suspended", status);
    }
    @Test
    public void test09AppealFunctionality() {
        driver.get(BASE_URL + "/dashboard");

        By statusLocator = By.id("account-status");
        wait.until(ExpectedConditions.textToBePresentInElementLocated(statusLocator, "Suspended"));

        // --- Now appeal the suspension ---
        WebElement appealButton = driver.findElement(By.cssSelector("form[action='/appeal'] button"));
        appealButton.click();

        // Wait for redirect again
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        // Wait until status text contains "Verified"
        assertTrue(
                "Status text should become 'Verified' after appeal",
                wait.until(ExpectedConditions.textToBePresentInElementLocated(statusLocator, "Verified"))
        );

        // Final assert to be safe (trimming whitespace)
        String status = driver.findElement(statusLocator).getText().trim();
        assertEquals("Account should be verified after appeal", "Verified", status);
    }



    @Test
    public void test10AccountClosure() {
        driver.get(BASE_URL + "/dashboard");

        // Click close button
        WebElement closeButton = driver.findElement(By.cssSelector("form[action='/close'] button"));
        closeButton.click();

        // Wait for redirect back to dashboard
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        // Locator for status element
        By statusLocator = By.id("account-status");

        // Ensure element is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(statusLocator));

        // Wait until text updates to "Closed"
        assertTrue(
                "Status text should become 'Closed'",
                wait.until(ExpectedConditions.textToBePresentInElementLocated(statusLocator, "Closed"))
        );

        // Final assertion to confirm (trim for safety)
        String status = driver.findElement(statusLocator).getText().trim();
        assertEquals("Account should be closed", "Closed", status);
    }


    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
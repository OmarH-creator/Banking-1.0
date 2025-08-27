package com.banking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.banking.model.Account;
import com.banking.service.AccountService;
import com.banking.service.TransactionProcessor;

@Controller
public class ViewController {

    private final AccountService accountService;
    private final TransactionProcessor transactionProcessor;

    // Store current account state for demonstration
    private Account currentAccount;

    public ViewController(AccountService accountService, TransactionProcessor transactionProcessor) {
        this.accountService = accountService;
        this.transactionProcessor = transactionProcessor;
        // Initialize with a sample account
        this.currentAccount = new Account(1000.0, "Verified");
        this.currentAccount.setName("John Doe");
        this.currentAccount.setNumber("123456789");
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/login")
    public String login() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("account", currentAccount);
        return "dashboard";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam(required = false) Double amount, Model model, RedirectAttributes redirectAttributes) {
        if (amount != null && amount > 0) {
            System.out.println("📋 Before deposit - Balance: $" + currentAccount.getBalance() + ", Status: " + currentAccount.getStatus());

            // Call the actual backend service
            boolean success = transactionProcessor.deposit(currentAccount, amount);

            if (success) {
                System.out.println("✅ Deposit successful: $" + amount + " - New balance: $" + currentAccount.getBalance());
                redirectAttributes.addFlashAttribute("message", "Successfully deposited $" + String.format("%.2f", amount));
                redirectAttributes.addFlashAttribute("messageType", "success");
            } else {
                System.out.println("❌ Deposit failed: $" + amount + " - Account status: " + currentAccount.getStatus());
                redirectAttributes.addFlashAttribute("message", "Deposit failed. Please check account status.");
                redirectAttributes.addFlashAttribute("messageType", "error");
            }
        } else {
            System.out.println("❌ Invalid deposit amount: " + amount);
            redirectAttributes.addFlashAttribute("message", "Invalid deposit amount.");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/dashboard";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam(required = false) Double amount, Model model, RedirectAttributes redirectAttributes) {
        if (amount != null && amount > 0) {
            System.out.println("📋 Before withdrawal - Balance: $" + currentAccount.getBalance() + ", Status: " + currentAccount.getStatus());

            // Call the actual backend service
            boolean success = transactionProcessor.withdraw(currentAccount, amount);

            if (success) {
                System.out.println("✅ Withdrawal successful: $" + amount + " - New balance: $" + currentAccount.getBalance());
                redirectAttributes.addFlashAttribute("message", "Successfully withdrew $" + String.format("%.2f", amount));
                redirectAttributes.addFlashAttribute("messageType", "success");
            } else {
                System.out.println("❌ Withdrawal failed: $" + amount + " - Account status: " + currentAccount.getStatus() + ", Balance: $" + currentAccount.getBalance());
                redirectAttributes.addFlashAttribute("message", "Withdrawal failed. Please check account status and balance.");
                redirectAttributes.addFlashAttribute("messageType", "error");
            }
        } else {
            System.out.println("❌ Invalid withdrawal amount: " + amount);
            redirectAttributes.addFlashAttribute("message", "Invalid withdrawal amount.");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/dashboard";
    }

    @PostMapping("/verify")
    public String verify(Model model, RedirectAttributes redirectAttributes) {
        System.out.println("📋 Before verification - Status: " + currentAccount.getStatus());

        // Call the actual backend service
        accountService.verifyAccount(currentAccount);

        System.out.println("✅ After verification - Status: " + currentAccount.getStatus());
        redirectAttributes.addFlashAttribute("message", "Account verified successfully!");
        redirectAttributes.addFlashAttribute("messageType", "success");

        return "redirect:/dashboard";
    }

    @PostMapping("/suspend")
    public String suspend(Model model, RedirectAttributes redirectAttributes) {
        System.out.println("📋 Before suspension - Status: " + currentAccount.getStatus());

        // Call the actual backend service
        accountService.suspendAccount(currentAccount);

        System.out.println("⏸️ After suspension - Status: " + currentAccount.getStatus());
        redirectAttributes.addFlashAttribute("message", "Account suspended.");
        redirectAttributes.addFlashAttribute("messageType", "warning");

        return "redirect:/dashboard";
    }

    @PostMapping("/close")
    public String close(Model model, RedirectAttributes redirectAttributes) {
        System.out.println("📋 Before closure - Status: " + currentAccount.getStatus());

        // Call the actual backend service
        accountService.closeAccount(currentAccount);

        System.out.println("❌ After closure - Status: " + currentAccount.getStatus());
        redirectAttributes.addFlashAttribute("message", "Account closed.");
        redirectAttributes.addFlashAttribute("messageType", "error");

        return "redirect:/dashboard";
    }

    @PostMapping("/appeal")
    public String appeal(Model model, RedirectAttributes redirectAttributes) {
        System.out.println("📋 Before appeal - Status: " + currentAccount.getStatus());

        // Call the actual backend service
        accountService.appealSuspension(currentAccount);

        System.out.println("📝 After appeal - Status: " + currentAccount.getStatus());

        if (currentAccount.getStatus().equals("Verified")) {
            redirectAttributes.addFlashAttribute("message", "Appeal successful! Account verified.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "No appeal needed for current account status.");
            redirectAttributes.addFlashAttribute("messageType", "info");
        }

        return "redirect:/dashboard";
    }
}
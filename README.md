# Banking System Project

## Overview

This project implements a simplified banking system with the following features:

*   Client onboarding and profile management
*   Deposit, withdrawal, transfer functionalities
*   Admin approval workflows
*   State-based transitions for account statuses

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── banking/
│   │           ├── controller/
│   │           │   └── ClientController.java
│   │           ├── model/
│   │           │   └── Account.java
│   │           └── service/
│   │               ├── AccountService.java
│   │               ├── TransactionProcessor.java
│   │               └── CreditScoreService.java
│   └── resources/
│       └── templates/
│           ├── index.html
│           └── dashboard.html
└── test/
    └── java/
        └── com/
            └── banking/
                ├── AccountTest.java
                ├── TransactionProcessorTest.java
                ├── StateBasedTest.java
                ├── UITest.java
                └── CreditScoreServiceTest.java
```

## Setup Instructions

1.  Install Java 8 or higher
2.  Install Maven
3.  For UI tests:
    *   Install Chrome browser
    *   Download ChromeDriver
    *   Set the path to ChromeDriver in `UITest.java`

**IMPORTANT:** The UI tests will fail if you do not set the correct path to your `chromedriver.exe` in `UITest.java`.

## Running Tests

```bash
mvn test
```

## Test Coverage

The project includes:

*   Black-box testing
*   White-box testing with Control Flow Graphs
*   UI testing using Selenium
*   State-based testing
*   TDD implementation of Credit Score Check feature

## State Diagram

Account states:
*   Unverified -> Verified (via Verify)
*   Verified -> Suspended (via Violation)
*   Suspended -> Verified (via Appeal)
*   Verified/Suspended -> Closed (via AdminAction)

## Implementation Details

### Account Class
*   Manages balance and status
*   Implements deposit and withdraw methods
*   Status validation for transactions

### TransactionProcessor
*   Handles financial transactions
*   Implements transfer between accounts
*   Rollback support for failed transfers

### AccountService
*   Manages account state transitions
*   Implements verification and suspension logic

### CreditScoreService
*   Implements credit score checking
*   Added using TDD methodology

## UI Implementation

The UI includes:
*   Login page
*   Account dashboard
*   Transaction forms
*   Status display
*   Admin action buttons

## Testing Methodology

1.  Black-Box Testing:
    *   Equivalence partitioning
    *   Boundary value analysis
    *   Input validation

2.  White-Box Testing:
    *   Branch coverage
    *   Control flow analysis
    *   Path testing

3.  UI Testing:
    *   Component rendering
    *   State-based button enabling/disabling
    *   Input validation

4.  State-Based Testing:
    *   State transition validation
    *   Invalid state transition prevention
    *   State-dependent functionality

5.  TDD Implementation:
    *   Credit score feature
    *   Test-first development
    *   Iterative implementation
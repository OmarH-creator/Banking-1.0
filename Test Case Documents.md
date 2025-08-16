# Test Case Documents

## Black-Box Testing

| Test Case ID | Input | Expected Output | Notes |
|---|---|---|---|
| BB01 | deposit(-100) | false | Invalid amount |
| BB02 | withdraw(50) | true if balance >= 50 | Blocked if suspended |
| BB03 | withdraw(500) | false | Overdraft prevention |

### Equivalence Partitioning

*   Negative deposit -> invalid
*   Valid deposit -> success
*   Deposit in "Closed" -> fail

### Boundary Value Analysis

*   Deposit: 0, 0.01, max_double
*   Withdraw: 0, 0.01, balance, balance + 0.01

## White-Box Testing

*   Control Flow Graph for `deposit()` and `withdraw()` methods.
*   Test cases to achieve 100% branch coverage.

## UI Testing

*   Functional checks for proper rendering of status label.
*   Buttons disabled based on state.
*   Input validation errors displayed.

## State-Based Testing

| State | Allowed Actions | Illegal Actions |
|---|---|---|
| Verified | Deposit, Withdraw | - |
| Suspended | View only | Withdraw, Transfer |
| Closed | View only | Deposit, Withdraw |

## All Tests Passed

All 26 tests passed successfully, including:
*   AccountTest
*   ClientControllerTest
*   CreditScoreServiceTest
*   StateBasedTest
*   TransactionProcessorTest
*   UITest

## Admin Approval Workflow (Stub)
| Test Case ID | Input | Expected Output | Notes |
|--------------|-------|----------------|-------|
| ADMIN01 | Admin closes account | Status = Closed | Admin action triggers state change |
| ADMIN02 | Admin verifies account | Status = Verified | Admin action triggers state change |

*Note: Full admin approval logic can be implemented as needed. Currently, admin actions are available as buttons in the UI and methods in AccountService.*

## Coverage Note
* BankingApplication.java (main class) is excluded from coverage as is standard for Spring Boot projects and does not contain business logic.
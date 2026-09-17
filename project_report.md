# Project Report: Bank Management System

---

## 1. Abstract

This project implements a console-based Bank Management System in Java, demonstrating core object-oriented programming principles, JDBC-based persistence to a MySQL database, and safe concurrent transaction handling using Java's built-in threading and synchronization primitives. The system supports account creation, deposits, withdrawals, interest calculation, and loan eligibility checks, while a dedicated concurrency demo shows multiple threads safely operating on a single account without corrupting its balance.

## 2. System Architecture

The system follows a layered architecture separating presentation, business logic, persistence, and domain/concurrency concerns:

1. **Presentation Layer (`Main.java`):** A menu-driven console interface that accepts user input and routes it to the appropriate business operation.
2. **Business Logic Layer (`Bank.java`, `ValidationUtils.java`):** Encapsulates account operations — opening accounts, deposits, withdrawals, interest calculation, and loan eligibility — along with input and business-rule validation. Balance-mutating methods are `synchronized` to guard against race conditions.
3. **Persistence Layer (`DBConnection.java`, `DBOperations.java`):** Manages the JDBC connection lifecycle and executes all SQL through `PreparedStatement`, keeping SQL fully isolated from business logic.
4. **Domain / Threading Layer (`Account.java`, `TransactionThread.java`):** `Account` is the encapsulated data model; `TransactionThread` implements `Runnable` and wraps a single deposit or withdrawal so it can be executed concurrently on its own thread.

## 3. Technology Stack

- **Programming Language:** Java (JDK 17+)
- **Database:** MySQL Server 8.0+
- **Database Connectivity:** JDBC via MySQL Connector/J
- **Concurrency:** `Thread`, `Runnable`, `synchronized`
- **Build/Run:** Manual compilation via `javac` / `java` (no build tool dependency)

## 4. Implementation Challenges & Solutions

| Technical Challenge | Root Cause | Engineering Solution |
|---|---|---|
| **Race condition on concurrent balance updates** | Multiple threads reading and writing an account's balance at the same time could cause a lost-update, where one thread's change silently overwrites another's. | Marked balance-mutating methods in `Bank` as `synchronized` on a shared lock, ensuring only one thread can modify a given account's balance at a time. |
| **`out/` build artifacts committed to Git** | The compiled `.class` output folder was pushed to GitHub before `.gitignore` was created, so Git continued tracking it even after the ignore rule was added. | Removed the folder from Git's index with `git rm -r --cached out/` while keeping it locally, then re-committed so future builds are excluded automatically. |
| **`.gitignore` rule silently not matching** | The `.gitignore` file had been saved in UTF-16 encoding (via the editor) instead of UTF-8, which caused inconsistent pattern matching against tracked files on Windows. | Re-saved `.gitignore` as UTF-8 and reapplied `git rm -r --cached out/`, after which Git correctly recognized and ignored the folder. |
| **Stale compiled output during testing** | Java ran outdated bytecode because the source file had unsaved changes in the editor (indicated by an unsaved-changes marker on the file tab) before compilation. | Verified the file was saved (no unsaved-changes indicator) before every `javac` compilation to guarantee the latest code was actually being tested. |
| **Classpath separator differences across OS** | The classpath separator for `java`/`javac` differs between Windows (`;`) and Linux/macOS (`:`), causing run commands to fail if copied across platforms. | Documented both variants explicitly in the README's Build and Run section. |

## 5. Key Features

- **Full account lifecycle:** open accounts, view details, deposit, and withdraw, each validated against invalid amounts and insufficient funds.
- **Interest calculation:** rate applied according to account type (savings vs. current).
- **Loan eligibility check:** determined from account balance and type.
- **Concurrent transaction demo:** launches multiple threads against the same account simultaneously to demonstrate safe, synchronized balance updates.
- **Persistent storage:** all account data is stored in and retrieved from a MySQL database via JDBC, rather than kept only in memory.
- **Defensive exception handling:** invalid account IDs, malformed amounts, and insufficient balances are caught and reported to the user instead of crashing the program.

## 6. Results and Discussion

The concurrency demo was tested by running multiple deposit and withdrawal threads against the same account simultaneously. Across repeated runs, the final balance consistently matched the expected mathematical result regardless of thread execution order, confirming that the `synchronized` guard correctly serializes access to the balance field and prevents lost updates. Standard operations — account creation, deposits, withdrawals, interest calculation, and loan eligibility — were also verified against manually calculated expected values (e.g., interest correctly computed as principal × rate) and matched in every test.

## 7. Future Developments

- **Show the race condition explicitly:** add an unsynchronized code path alongside the synchronized one to visually demonstrate the bug the `synchronized` keyword prevents.
- **Connection pooling:** replace the single JDBC connection with a pool (e.g., HikariCP) for better performance under higher concurrent load.
- **Transaction history:** log each deposit/withdrawal to a separate table for auditability, rather than only reflecting the current balance.
- **Build tool migration:** move from manual `javac`/`java` compilation to Maven or Gradle to simplify dependency management (e.g., the MySQL connector) and cross-platform builds.

## 8. Conclusion

This project demonstrates the practical application of object-oriented design, JDBC-based persistence, and safe multithreading in a realistic banking domain. By separating concerns across presentation, business logic, persistence, and domain/threading layers, and by using `synchronized` methods to guard shared mutable state, the system reliably handles both single-threaded and concurrent operations without data corruption.

## 9. Author

- **Parth Gujar**

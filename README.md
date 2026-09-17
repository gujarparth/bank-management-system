# Bank Management System

A console-based Bank Management System written in Java. It demonstrates core
object-oriented programming, JDBC database connectivity, multithreading, and
thread synchronization through a realistic banking domain — accounts, deposits,
withdrawals, interest calculation, and loan eligibility checks.

---

## Features

- **Open an account** — savings or current, with an initial deposit
- **View account details** — ID, holder name, type, current balance
- **Deposit / Withdraw** — with validation for invalid amounts and insufficient funds
- **Interest calculation** — rate applied according to account type
- **Loan eligibility check** — based on account balance and type
- **Concurrent transaction demo** — multiple threads operating on the same account
  at the same time, kept consistent using `synchronized`
- **Exception handling** — invalid account IDs, bad amounts, and insufficient
  balance are caught and reported instead of crashing the program

---

## Project Structure

```
bank-management-system/
├── src/
│   ├── Account.java             # Account entity — encapsulated fields, getters/setters
│   ├── Bank.java                # Core business logic (interest, loan eligibility, sync)
│   ├── DBConnection.java        # Manages the JDBC connection to MySQL
│   ├── DBOperations.java        # SQL CRUD operations (insert, select, update)
│   ├── TransactionThread.java   # Runnable used for the concurrency demo
│   ├── ValidationUtils.java     # Input and business-rule validation helpers
│   └── Main.java                # Menu-driven console interface
├── lib/
│   └── mysql-connector-j-26.7.0.jar
├── sql/
│   └── schema.sql               # Database and table creation script
├── out/                         # Compiled .class files (generated, not committed)
├── README.md
└── statement.md
```

---

## Requirements

- JDK 17 or later
- MySQL Server 8.0 or later
- MySQL Connector/J (included in `lib/`) — you can also download it directly from
  [dev.mysql.com/downloads/connector/j](https://dev.mysql.com/downloads/connector/j/)
  (select "Platform Independent" to get the `.zip`/`.tar.gz` containing the `.jar`)

---

## Database Setup

Run `sql/schema.sql` against your MySQL server before the first run:

```sql
CREATE DATABASE bankdb;
USE bankdb;

CREATE TABLE accounts (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100)   NOT NULL,
    type     VARCHAR(20)    NOT NULL,
    balance  DOUBLE         NOT NULL DEFAULT 0
);
```

Update the connection credentials in `DBConnection.java` to match your local MySQL
username and password.

---

## Build and Run

**Windows (PowerShell / CMD)**

```bash
javac -d out -cp "lib/mysql-connector-j-26.7.0.jar" src/*.java
java -cp "out;lib/mysql-connector-j-26.7.0.jar" Main
```

**Linux / macOS**

```bash
javac -d out -cp "lib/mysql-connector-j-26.7.0.jar" src/*.java
java -cp "out:lib/mysql-connector-j-26.7.0.jar" Main
```

Note: the classpath separator is `;` on Windows and `:` on Linux and macOS.

---

## Sample Run

```
=== Bank Management System ===

1. Open Account
2. View Account
3. Deposit
4. Withdraw
5. Calculate Interest
6. Check Loan Eligibility
7. Run Concurrent Transactions (Demo)
8. Exit
Enter choice: 1
Name: Parth Gujar
Account type (Savings/Current): savings
Initial deposit: 20000
Account opened! ID: 5

Enter choice: 2
Account ID: 5
Account #5 | Parth Gujar | savings | Balance: 20000.0

Enter choice: 8
Goodbye!
```

---

## Concurrency

Option 7 launches three threads against a single account simultaneously — two
deposits and one withdrawal. Each thread calls into `Bank` through
`TransactionThread`. Because the balance-mutating methods are `synchronized`,
only one thread can modify an account's balance at a time, so the final balance
is always correct regardless of thread interleaving.

Without synchronization this would be a classic lost-update race: two threads
read the same balance, both compute a new value from that stale read, and one
update silently overwrites the other.

---

## Architecture

| Layer | Classes | Responsibility |
|---|---|---|
| Presentation | `Main` | Menu loop and user I/O |
| Business Logic | `Bank`, `ValidationUtils` | Operations, rules, synchronization |
| Persistence | `DBOperations`, `DBConnection` | All SQL; connection lifecycle |
| Domain / Threading | `Account`, `TransactionThread` | Data model; concurrent task wrapper |

---

## Concepts Demonstrated

| Concept | Where it appears |
|---|---|
| Encapsulation | Private fields in `Account` with public accessors |
| Abstraction | `Bank` exposes operations; SQL hidden in `DBOperations` |
| Separation of concerns | 7 classes, each with a single responsibility |
| Exception handling | `ValidationUtils`, `Bank`, `Main` each handle their layer |
| JDBC | `DBConnection` + `DBOperations` using `PreparedStatement` |
| Multithreading | `Thread`, `Runnable` via `TransactionThread` |
| Synchronization | `synchronized` methods in `Bank` guarding balance updates |

---

## Author

Parth Gujar

# Project Report: Bank Management System
**Author:** Parth Gujar

## 1. Project Overview
For this project, I built a console-based Bank Management System using Java and MySQL. My main goal was to move beyond basic Java programming and actually integrate a database using JDBC, while also experimenting with multithreading. The application runs in the command line and allows users to open accounts, deposit and withdraw funds, calculate interest, and check loan eligibility. 

## 2. Technology Stack & Setup
I kept the toolset fairly standard to focus on the core concepts:
*   **Language:** Java (JDK 17+)
*   **Database:** MySQL Server 8.0+
*   **Driver:** MySQL Connector/J (JDBC)
*   **Build Process:** I chose not to use a build tool like Maven or Gradle. Instead, I compiled everything manually using `javac` to better understand how classpaths and external `.jar` libraries work.

## 3. Architecture and Code Structure
To keep the codebase organized and prevent `Main.java` from becoming a massive file, I separated the code into different layers:

*   **User Interface (`Main.java`):** Handles the console menu, taking user inputs (like deposit amounts or account types), and printing results. 
*   **Business Logic (`Bank.java` & `ValidationUtils.java`):** This is where the actual banking rules live. For example, `withdraw()` checks if there are sufficient funds, and `calculateInterest()` applies different math depending on whether the account is "savings" or "current".
*   **Database Layer (`DBConnection.java` & `DBOperations.java`):** I isolated all SQL queries here. I used `PreparedStatement` for things like `INSERT` and `UPDATE` to prevent SQL injection and handle the data safely.
*   **Data Model (`Account.java`):** A simple object holding the account ID, name, type, and balance.

## 4. The Concurrency Demonstration
One of the requirements was to handle concurrent transactions (multiple threads trying to change a balance at the same time). 

To test this, I created `TransactionThread.java`, which implements `Runnable`. Menu Option 7 launches three threads simultaneously against a single account (two deposits and one withdrawal). To prevent a "lost update" race condition—where two threads read the same balance and overwrite each other—I used a `synchronized` block. 

**How I built the lock:** Inside `TransactionThread.java`, I created a `private static final Object lock = new Object();` and synchronized the run method on it. This successfully forces the threads to wait in line, ensuring the final math is always 100% accurate. 

## 5. Challenges Faced During Development
*   **Git Tracking Issues:** Early on, I accidentally committed my compiled `.class` files in the `out/` folder to GitHub. Even after I added `out/` to my `.gitignore` file, Git kept tracking them. I eventually figured out I had to use `git rm -r --cached out/` to clear Git's memory of the folder without deleting my local files.
*   **Cross-Platform Classpaths:** Getting the app to compile with the external JDBC `.jar` file was tricky. I learned the hard way that Windows uses a semicolon (`;`) to separate classpaths, while macOS/Linux uses a colon (`:`). I documented both commands in the README so it runs smoothly on any machine.

## 6. Known Limitations & Future Improvements
While the program works reliably, there are a few things I would improve in a version 2.0:

1.  **Refining the Thread Lock:** Currently, my `synchronized` lock in `TransactionThread.java` is a static global lock. This means it pauses *all* transactions in the entire bank while one thread finishes. In a real-world scenario, I would need to lock only the specific Account ID being modified (perhaps using a `ConcurrentHashMap` of locks) so other users aren't delayed.
2.  **Securing Credentials:** Right now, my database password (`Parth@123`) is hardcoded directly inside `DBConnection.java`. I did this for local testing, but I realize this is a bad security practice for GitHub. In the future, I will pull this from an environment variable.
3.  **Detailed Transaction Logs:** While I implemented a basic transaction log, the main system relies on just updating the `balance` column in the `accounts` table. Building out a full ledger interface would be the next logical step.

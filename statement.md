# Problem Statement

## Project Title

**Bank Management System — A Java Application Demonstrating OOP, JDBC, and Thread Synchronization**

---

## Background

Banks handle a high volume of transactions against the same accounts at the same
time. A single savings account may receive a salary credit, an ATM withdrawal,
and a standing-instruction debit within the same second. If the software behind
those operations reads and writes balances carelessly, two concurrent updates can
overwrite each other and money can appear or disappear from the ledger.

Traditional manual record-keeping is slow, error-prone, and impossible to audit
at scale. A software system solves the record-keeping problem, but introduces a
new one: correctness under concurrency.

---

## Problem Definition

Design and implement a console-based Bank Management System in Java that:

1. Stores account data persistently in a relational database rather than in
   memory, so that records survive program restarts.
2. Supports the core banking operations — opening an account, viewing details,
   depositing, withdrawing, calculating interest, and checking loan eligibility.
3. Enforces business rules: a withdrawal cannot exceed the available balance,
   amounts cannot be negative, and operations on a non-existent account must be
   rejected cleanly.
4. **Remains correct when multiple transactions execute concurrently on the same
   account**, by guarding shared mutable state with proper synchronization.
5. Fails gracefully — errors are caught and reported to the user instead of
   terminating the program with a stack trace.

---

## Objectives

- Apply the four pillars of object-oriented programming — encapsulation,
  abstraction, inheritance, and polymorphism — to model a real banking domain.
- Connect a Java application to a MySQL database using JDBC and perform CRUD
  operations through `PreparedStatement`.
- Demonstrate multithreading using the `Runnable` interface and the `Thread`
  class.
- Demonstrate the lost-update race condition and show how the `synchronized`
  keyword eliminates it.
- Implement structured exception handling across the input, business-logic, and
  database layers.

---

## Scope

**In scope**

- Console (CLI) interface driven by a numbered menu
- Savings and current account types
- Single-account operations: deposit, withdraw, interest, loan eligibility
- A concurrency demonstration that runs several transaction threads against one
  account
- MySQL persistence via JDBC

**Out of scope**

- Graphical or web user interface
- Authentication, user roles, and session management
- Inter-account fund transfers and transaction history reporting
- Encryption of stored data and regulatory compliance features
- Network or multi-client deployment

---

## Expected Outcome

A working Java application in which a user can open and manage bank accounts
through a menu, with all data persisted to MySQL, and in which running several
concurrent deposit and withdrawal threads against a single account always yields
the arithmetically correct final balance — proving that the synchronization
strategy is sound.

---

## Tools and Technologies

| Component | Choice |
|---|---|
| Language | Java (JDK 17+) |
| Database | MySQL 8.0 |
| Connectivity | MySQL Connector/J 26.7.0 (JDBC) |
| Interface | Console / Command line |
| Version control | Git and GitHub |

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.println("=== Bank Management System ===");

        do {
            System.out.println("\n1. Open Account");
            System.out.println("2. View Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Calculate Interest");
            System.out.println("6. Check Loan Eligibility");
            System.out.println("7. Run Concurrent Transactions (Demo)");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        sc.nextLine();
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Account type (Savings/Current): ");
                        String type = sc.nextLine();
                        System.out.print("Initial deposit: ");
                        double init = sc.nextDouble();
                        int id = bank.openAccount(name, type, init);
                        System.out.println("Account opened! ID: " + id);
                        break;

                    case 2:
                        System.out.print("Account ID: ");
                        Account a = bank.getAccount(sc.nextInt());
                        System.out.println(a);
                        break;

                    case 3:
                        System.out.print("Account ID: ");
                        int depId = sc.nextInt();
                        System.out.print("Amount: ");
                        bank.deposit(depId, sc.nextDouble());
                        System.out.println("Deposited. New balance: " + bank.getAccount(depId).getBalance());
                        break;

                    case 4:
                        System.out.print("Account ID: ");
                        int witId = sc.nextInt();
                        System.out.print("Amount: ");
                        bank.withdraw(witId, sc.nextDouble());
                        System.out.println("Withdrawn. New balance: " + bank.getAccount(witId).getBalance());
                        break;

                    case 5:
                        System.out.print("Account ID: ");
                        Account ai = bank.getAccount(sc.nextInt());
                        System.out.println("Interest: " + bank.calculateInterest(ai));
                        break;

                    case 6:
                        System.out.print("Account ID: ");
                        Account al = bank.getAccount(sc.nextInt());
                        System.out.println("Loan eligible: " + bank.isLoanEligible(al));
                        break;

                    case 7:
                        System.out.print("Account ID to run concurrent transactions on: ");
                        int tid = sc.nextInt();
                        Thread t1 = new Thread(new TransactionThread(bank, tid, "deposit", 200));
                        Thread t2 = new Thread(new TransactionThread(bank, tid, "withdraw", 100));
                        Thread t3 = new Thread(new TransactionThread(bank, tid, "deposit", 300));
                        t1.start(); t2.start(); t3.start();
                        t1.join(); t2.join(); t3.join();
                        System.out.println("Done. Balance: " + bank.getAccount(tid).getBalance());
                        break;

                    case 8:
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 8);

        sc.close();
    }
}
public class Account {
    private int accountId;
    private String accountHolderName;
    private String accountType;
    private double balance;

    // Constructor for creating a NEW account (no ID yet, database assigns it)
    public Account(String accountHolderName, String accountType, double balance) {
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = balance;
    }

    // Constructor for loading an EXISTING account (already has an ID from the database)
    public Account(int accountId, String accountHolderName, String accountType, double balance) {
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = balance;
    }

    // Getters
    public int getAccountId() { return accountId; }
    public String getAccountHolderName() { return accountHolderName; }
    public String getAccountType() { return accountType; }
    public double getBalance() { return balance; }

    // Setters
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public void setBalance(double balance) { this.balance = balance; }

    @Override
    public String toString() {
        return "Account #" + accountId + " | " + accountHolderName +
               " | " + accountType + " | Balance: " + balance;
    }
}
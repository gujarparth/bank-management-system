public class Account {
    private int accountId;
    private String accountHolderName;
    private String accountType;
    private double balance;
    public Account(String accountHolderName, String accountType, double balance) {
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = balance;
    }
    public Account(int accountId, String accountHolderName, String accountType, double balance) {
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = balance;
    }
    public int getAccountId() { return accountId; }
    public String getAccountHolderName() { return accountHolderName; }
    public String getAccountType() { return accountType; }
    public double getBalance() { return balance; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public void setBalance(double balance) { this.balance = balance; }
    @Override
    public String toString() {
        return "Account #" + accountId + " | " + accountHolderName +
               " | " + accountType + " | Balance: " + balance;
    }
}
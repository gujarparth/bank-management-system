import java.sql.SQLException;
import java.util.List;

public class Bank {
    public int openAccount(String name, String type, double initialDeposit) throws SQLException {
        if (!ValidationUtils.isValidName(name)) {
            throw new IllegalArgumentException("Invalid account holder name.");
        }
        if (!ValidationUtils.isValidAccountType(type)) {
            throw new IllegalArgumentException("Account type must be 'Savings' or 'Current'.");
        }
        if (!ValidationUtils.isValidAmount(initialDeposit)) {
            throw new IllegalArgumentException("Initial deposit must be greater than zero.");
        }
        Account account = new Account(name, type, initialDeposit);
        return DBOperations.createAccount(account);
    }
    public void deposit(int accountId, double amount) throws SQLException {
        if (!ValidationUtils.isValidAmount(amount)) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        Account acc = DBOperations.getAccountById(accountId);
        if (acc == null) {
            throw new IllegalArgumentException("No account found with ID " + accountId);
        }
        double newBalance = acc.getBalance() + amount;
        DBOperations.updateBalance(accountId, newBalance);
        DBOperations.logTransaction(accountId, "deposit", amount);
    }
    public void withdraw(int accountId, double amount) throws SQLException {
        if (!ValidationUtils.isValidAmount(amount)) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        Account acc = DBOperations.getAccountById(accountId);
        if (acc == null) {
            throw new IllegalArgumentException("No account found with ID " + accountId);
        }
        if (!ValidationUtils.hasSufficientBalance(acc.getBalance(), amount)) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        double newBalance = acc.getBalance() - amount;
        DBOperations.updateBalance(accountId, newBalance);
        DBOperations.logTransaction(accountId, "withdrawal", amount);
    }
    public double calculateInterest(Account account) {
        double rate;
        switch (account.getAccountType().toLowerCase()) {
            case "savings":
                rate = 0.04;
                break;
            case "current":
                rate = 0.01;
                break;
            default:
                rate = 0.0;
        }
        return account.getBalance() * rate;
    }
    public boolean isLoanEligible(Account account) {
        if (account.getAccountType().equalsIgnoreCase("savings") && account.getBalance() >= 10000) {
            return true;
        } else if (account.getAccountType().equalsIgnoreCase("current") && account.getBalance() >= 25000) {
            return true;
        }
        return false;
    }
    public List<Account> listAllAccounts() throws SQLException {
        return DBOperations.getAllAccounts();
    }
    public Account getAccount(int accountId) throws SQLException {
        return DBOperations.getAccountById(accountId);
    }
}
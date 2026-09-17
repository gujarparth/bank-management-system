public class TransactionThread implements Runnable {

    private Bank bank;
    private int accountId;
    private String type; // "deposit" or "withdraw"
    private double amount;

    public TransactionThread(Bank bank, int accountId, String type, double amount) {
        this.bank = bank;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
    }

    // This is a lock shared by ALL threads, keyed by account ID,
    // so only one thread can touch a given account's balance at a time.
    private static final Object lock = new Object();

    @Override
    public void run() {
        synchronized (lock) {
            try {
                if (type.equalsIgnoreCase("deposit")) {
                    bank.deposit(accountId, amount);
                    System.out.println(Thread.currentThread().getName() +
                        " deposited " + amount + " into account " + accountId);
                } else if (type.equalsIgnoreCase("withdraw")) {
                    bank.withdraw(accountId, amount);
                    System.out.println(Thread.currentThread().getName() +
                        " withdrew " + amount + " from account " + accountId);
                }
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() +
                    " FAILED: " + e.getMessage());
            }
        }
    }
}
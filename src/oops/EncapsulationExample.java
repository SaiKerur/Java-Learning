package oops;

/**
 * Encapsulation means:
 * 1) Hide data (fields) from direct outside access.
 * 2) Expose safe methods that control how data changes.
 *
 * Real-world idea:
 * A bank does not let anyone directly edit account balance.
 * You must use deposit/withdraw rules.
 */
public class EncapsulationExample {

    private static class BankAccount {
        private final String accountHolderName;
        private double balance;

        public BankAccount(String accountHolderName, double initialBalance) {
            if (accountHolderName == null || accountHolderName.isBlank()) {
                throw new IllegalArgumentException("Account holder name cannot be empty.");
            }
            if (initialBalance < 0) {
                throw new IllegalArgumentException("Initial balance cannot be negative.");
            }
            this.accountHolderName = accountHolderName;
            this.balance = initialBalance;
        }

        public String getAccountHolderName() {
            return accountHolderName;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            if (amount <= 0) {
                System.out.println("Deposit failed: amount must be greater than 0.");
                return;
            }
            balance += amount;
            System.out.println("Deposited: " + amount + ", new balance: " + balance);
        }

        public void withdraw(double amount) {
            if (amount <= 0) {
                System.out.println("Withdraw failed: amount must be greater than 0.");
                return;
            }
            if (amount > balance) {
                System.out.println("Withdraw failed: insufficient balance.");
                return;
            }
            balance -= amount;
            System.out.println("Withdrew: " + amount + ", new balance: " + balance);
        }
    }

    public static void demo() {
        System.out.println("\n--- ENCAPSULATION DEMO ---");
        BankAccount account = new BankAccount("Aman", 1000);

        System.out.println("Account holder: " + account.getAccountHolderName());
        System.out.println("Starting balance: " + account.getBalance());

        account.deposit(500);
        account.withdraw(1200);
        account.withdraw(2000); // invalid: more than balance
        account.deposit(-10);   // invalid: negative amount
    }

    public static void main(String[] args) {
        demo();
    }
}

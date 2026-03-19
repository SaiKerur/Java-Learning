package oops;

/**
 * Abstraction means:
 * Focus on "what to do" and hide "how it is done".
 *
 * We use an abstract class with a common payment flow,
 * while each payment type implements its own internal processing.
 */
public class AbstractionExample {

    private abstract static class Payment {
        protected final double amount;

        public Payment(double amount) {
            this.amount = amount;
        }

        // Concrete method: common steps for all payments.
        public final void pay() {
            validateAmount();
            processPayment();
            printReceipt();
        }

        private void validateAmount() {
            if (amount <= 0) {
                throw new IllegalArgumentException("Payment amount must be positive.");
            }
        }

        // Abstract method: subclasses define their own logic.
        protected abstract void processPayment();

        private void printReceipt() {
            System.out.println("Receipt generated for amount: " + amount);
        }
    }

    private static class CreditCardPayment extends Payment {
        private final String cardLast4Digits;

        public CreditCardPayment(double amount, String cardLast4Digits) {
            super(amount);
            this.cardLast4Digits = cardLast4Digits;
        }

        @Override
        protected void processPayment() {
            System.out.println("Processing credit card payment using card ending " + cardLast4Digits);
        }
    }

    private static class UpiPayment extends Payment {
        private final String upiId;

        public UpiPayment(double amount, String upiId) {
            super(amount);
            this.upiId = upiId;
        }

        @Override
        protected void processPayment() {
            System.out.println("Processing UPI payment from id: " + upiId);
        }
    }

    public static void demo() {
        System.out.println("\n--- ABSTRACTION DEMO ---");

        Payment cardPayment = new CreditCardPayment(1499, "2048");
        Payment upiPayment = new UpiPayment(299, "learner@upi");

        cardPayment.pay();
        upiPayment.pay();
    }

    public static void main(String[] args) {
        demo();
    }
}

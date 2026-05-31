package java_8_features.interface_enhancements;

/**
 * DEFAULT & STATIC METHODS IN INTERFACES (Java 8)
 * ===============================================
 *
 * Problem before Java 8:
 * - Adding a new method to an interface broke all existing implementing classes.
 * - Teams had to either avoid evolving interfaces or provide abstract adapter classes.
 *
 * Java 8 solution:
 * 1) default method  -> instance method with body in interface (inheritance-like)
 * 2) static method     -> utility method on interface itself
 *
 * Why this matters:
 * - Evolve libraries (e.g., Collection.forEach default method) without breaking code.
 * - Share common behavior in interface itself.
 *
 * Diamond problem note:
 * - If two interfaces provide same default method signature, class must override it.
 */
public class DefaultAndStaticMethodsDemo {

    public static void demo() {
        System.out.println("\n--- DEFAULT & STATIC METHODS IN INTERFACES DEMO ---");

        paymentGatewayScenario();
        notificationScenario();
        diamondProblemScenario();
        staticUtilityScenario();
    }

    /**
     * Scenario: Payment gateway with common default logging + custom processing.
     */
    private static void paymentGatewayScenario() {
        System.out.println("\n[Scenario A] default method reused by implementers:");

        PaymentProcessor upi = new UpiProcessor();
        PaymentProcessor card = new CardProcessor();

        upi.process(1500);
        card.process(3200);
    }

    /**
     * Scenario: Notification channels with optional override.
     */
    private static void notificationScenario() {
        System.out.println("\n[Scenario B] override default when needed:");

        NotifierChannel email = new EmailNotifier();
        NotifierChannel sms = new SmsNotifier();

        email.send("Your OTP is 123456");
        sms.send("Your OTP is 123456");
    }

    /**
     * Scenario: Two interfaces with same default method -> class must resolve.
     */
    private static void diamondProblemScenario() {
        System.out.println("\n[Scenario C] diamond problem resolution:");

        SmartSpeaker device = new SmartSpeaker();
        device.connect(); // explicit override required
    }

    private static void staticUtilityScenario() {
        System.out.println("\n[Scenario D] static method on interface:");

        // Called directly on interface, not on instance
        String formatted = PaymentProcessor.formatAmount(1999.5);
        System.out.println("Formatted amount: " + formatted);
    }

    /* ========================= Interfaces ========================= */

    interface PaymentProcessor {
        void process(double amount);

        // default method: optional to override
        default void log(String message) {
            System.out.println("[PAYMENT-LOG] " + message);
        }

        static String formatAmount(double amount) {
            return String.format("INR %.2f", amount);
        }
    }

    static class UpiProcessor implements PaymentProcessor {
        @Override
        public void process(double amount) {
            log("UPI processing " + PaymentProcessor.formatAmount(amount));
            System.out.println("UPI transaction completed.");
        }
    }

    static class CardProcessor implements PaymentProcessor {
        @Override
        public void process(double amount) {
            log("Card processing " + PaymentProcessor.formatAmount(amount));
            System.out.println("Card transaction completed.");
        }
    }

    interface NotifierChannel {
        void send(String message);

        default void beforeSend() {
            System.out.println("[NOTIFIER] preparing message...");
        }
    }

    static class EmailNotifier implements NotifierChannel {
        @Override
        public void send(String message) {
            beforeSend();
            System.out.println("[EMAIL] " + message);
        }
    }

    static class SmsNotifier implements NotifierChannel {
        @Override
        public void send(String message) {
            beforeSend();
            System.out.println("[SMS] " + message);
        }

        // Override default hook to add SMS-specific behavior
        @Override
        public void beforeSend() {
            System.out.println("[SMS] validating mobile number...");
        }
    }

    interface WifiConnectable {
        default void connect() {
            System.out.println("WifiConnectable default connect");
        }
    }

    interface BluetoothConnectable {
        default void connect() {
            System.out.println("BluetoothConnectable default connect");
        }
    }

    static class SmartSpeaker implements WifiConnectable, BluetoothConnectable {
        @Override
        public void connect() {
            // Must override when two interfaces provide same default signature
            System.out.println("SmartSpeaker resolved connect: trying Wi-Fi first, then Bluetooth");
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

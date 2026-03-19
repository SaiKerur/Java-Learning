package oops;

/**
 * Interface means:
 * A contract of behavior without storing object state.
 *
 * Any class implementing the interface must define the declared methods.
 * This gives flexibility: you can swap implementations easily.
 */
public class InterfaceExample {

    private interface NotificationService {
        void send(String message);
    }

    private static class EmailNotification implements NotificationService {
        private final String email;

        public EmailNotification(String email) {
            this.email = email;
        }

        @Override
        public void send(String message) {
            System.out.println("Email sent to " + email + ": " + message);
        }
    }

    private static class SmsNotification implements NotificationService {
        private final String mobileNumber;

        public SmsNotification(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        @Override
        public void send(String message) {
            System.out.println("SMS sent to " + mobileNumber + ": " + message);
        }
    }

    public static void demo() {
        System.out.println("\n--- INTERFACE DEMO ---");

        NotificationService emailService = new EmailNotification("student@example.com");
        NotificationService smsService = new SmsNotification("+91-9999999999");

        emailService.send("Your Java class starts at 5 PM.");
        smsService.send("Assignment deadline is tomorrow.");
    }

    public static void main(String[] args) {
        demo();
    }
}

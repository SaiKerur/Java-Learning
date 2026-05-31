package java_8_features.functional_interfaces;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * FUNCTIONAL INTERFACES (Java 8)
 * ==============================
 *
 * Definition:
 * - An interface with exactly ONE abstract method (Single Abstract Method = SAM).
 * - Can have default/static methods (Java 8+), but only one abstract method.
 *
 * @FunctionalInterface annotation:
 * - Optional, but compiler validates SAM rule.
 *
 * Built-in functional interfaces (java.util.function):
 * - Predicate<T>     : T -> boolean      (test/filter)
 * - Function<T,R>    : T -> R            (transform/map)
 * - Consumer<T>      : T -> void         (side-effect/forEach)
 * - Supplier<T>      : () -> T           (lazy creation/factory)
 * - BiFunction<T,U,R>: (T,U) -> R        (combine two inputs)
 *
 * Primitive specializations (avoid boxing overhead):
 * - IntPredicate, LongPredicate, ToIntFunction, IntConsumer, etc.
 *
 * Why this matters:
 * - Lambdas and method references are allowed where a functional interface is expected.
 * - Stream API methods accept these interfaces (filter, map, forEach, etc.).
 */
public class FunctionalInterfacesDemo {

    public static void demo() {
        System.out.println("\n--- FUNCTIONAL INTERFACES DEMO ---");

        predicateScenario();
        functionScenario();
        consumerScenario();
        supplierScenario();
        biFunctionScenario();
        composingPredicatesScenario();
        customSamScenario();
    }

    /**
     * Scenario: Filter active users in admin dashboard.
     */
    private static void predicateScenario() {
        System.out.println("\n[Scenario A] Predicate<T> — boolean test:");

        List<User> users = List.of(
                new User("Aman", true, 24),
                new User("Priya", false, 30),
                new User("Ravi", true, 17)
        );

        // Predicate<User>: boolean test(User user)
        Predicate<User> isActive = user -> user.active;
        Predicate<User> isAdult = user -> user.age >= 18;

        List<User> activeAdults = users.stream()
                .filter(isActive.and(isAdult)) // composition
                .collect(Collectors.toList());

        System.out.println("Active adults: " + activeAdults);
    }

    /**
     * Scenario: Convert internal employee code to display name.
     */
    private static void functionScenario() {
        System.out.println("\n[Scenario B] Function<T,R> — transform input to output:");

        List<String> employeeCodes = Arrays.asList("E101", "E102", "E999");

        Function<String, String> codeToDisplayName = code -> {
            if ("E101".equals(code)) return "Aman Sharma";
            if ("E102".equals(code)) return "Priya Patel";
            return "Unknown Employee";
        };

        List<String> names = employeeCodes.stream()
                .map(codeToDisplayName) // map uses Function
                .collect(Collectors.toList());

        System.out.println("Display names: " + names);

        // Function composition: first trim, then uppercase
        Function<String, String> normalize = Function.<String>identity()
                .andThen(String::trim)
                .andThen(String::toUpperCase);
        System.out.println("Normalized: " + normalize.apply("  java 8  "));
    }

    /**
     * Scenario: Audit logging when records are processed.
     */
    private static void consumerScenario() {
        System.out.println("\n[Scenario C] Consumer<T> — perform action, no return:");

        List<String> payments = List.of("UPI-1", "CARD-2", "UPI-3");

        Consumer<String> auditLog = paymentId ->
                System.out.println("[AUDIT] Payment processed: " + paymentId);

        payments.forEach(auditLog);

        // andThen chains two consumers
        Consumer<String> auditWithTimestamp = auditLog.andThen(p ->
                System.out.println("[AUDIT] Timestamp logged for: " + p));
        System.out.println("Chained consumer:");
        auditWithTimestamp.accept("NETBANK-4");
    }

    /**
     * Scenario: Lazy object creation (only when needed).
     */
    private static void supplierScenario() {
        System.out.println("\n[Scenario D] Supplier<T> — produce value without input:");

        Supplier<String> connectionIdSupplier = () -> "CONN-" + System.nanoTime();

        // Value is generated only when get() is called.
        System.out.println("Lazy generated ID #1: " + connectionIdSupplier.get());
        System.out.println("Lazy generated ID #2: " + connectionIdSupplier.get());

        // Optional.orElseGet(Supplier) uses Supplier for lazy fallback (see optional package)
    }

    /**
     * Scenario: Calculate tax from price and tax rate.
     */
    private static void biFunctionScenario() {
        System.out.println("\n[Scenario E] BiFunction<T,U,R> — two inputs, one output:");

        BiFunction<Double, Double, Double> taxCalculator = (price, ratePercent) ->
                price + (price * ratePercent / 100.0);

        double finalPrice = taxCalculator.apply(1000.0, 18.0);
        System.out.println("Price with 18% tax: " + finalPrice);
    }

    /**
     * Scenario: Fraud detection combining multiple rules.
     */
    private static void composingPredicatesScenario() {
        System.out.println("\n[Scenario F] Predicate composition (and/or/negate):");

        Transaction t1 = new Transaction("UPI", 5000, false);
        Transaction t2 = new Transaction("CARD", 250000, true);
        Transaction t3 = new Transaction("UPI", 900, false);

        Predicate<Transaction> highAmount = tx -> tx.amount > 10000;
        Predicate<Transaction> flagged = tx -> tx.flagged;
        Predicate<Transaction> upi = tx -> "UPI".equals(tx.channel);

        Predicate<Transaction> needsReview = highAmount.or(flagged).and(upi.negate());

        System.out.println("t1 needs review? " + needsReview.test(t1));
        System.out.println("t2 needs review? " + needsReview.test(t2));
        System.out.println("t3 needs review? " + needsReview.test(t3));
    }

    private static void customSamScenario() {
        System.out.println("\n[Scenario G] Custom SAM interface:");

        EmailValidator basicValidator = email -> email != null && email.contains("@");
        System.out.println("Valid email? " + basicValidator.isValid("aman@example.com"));
        System.out.println("Valid email? " + basicValidator.isValid("invalid-email"));
    }

    @FunctionalInterface
    interface EmailValidator {
        boolean isValid(String email);
    }

    static class User {
        final String name;
        final boolean active;
        final int age;

        User(String name, boolean active, int age) {
            this.name = name;
            this.active = active;
            this.age = age;
        }

        @Override
        public String toString() {
            return name + "{active=" + active + ", age=" + age + "}";
        }
    }

    static class Transaction {
        final String channel;
        final double amount;
        final boolean flagged;

        Transaction(String channel, double amount, boolean flagged) {
            this.channel = channel;
            this.amount = amount;
            this.flagged = flagged;
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

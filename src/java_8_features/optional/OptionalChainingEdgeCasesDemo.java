package java_8_features.optional;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * OPTIONAL CHAINING EDGE CASES
 * ============================
 *
 * Covers:
 * - empty Optional.flatMap chain
 * - filter removing present value
 * - ifPresent vs ifPresentOrElse (Java 9+ for latter - use if/else for Java 8)
 * - Optional.of(null) trap
 * - Double Optional (Optional<Optional<T>>) smell
 */
public class OptionalChainingEdgeCasesDemo {

    public static void demo() {
        System.out.println("\n--- OPTIONAL CHAINING EDGE CASES DEMO ---");

        ofVsOfNullableTrap();
        filterRemovingValueScenario();
        flatMapNestedOptionalScenario();
        getWithoutCheckTrap();
        optionalInEqualsScenario();
    }

    private static void ofVsOfNullableTrap() {
        System.out.println("\n[Edge A] Optional.of(null) vs ofNullable(null):");

        Optional<String> empty = Optional.ofNullable(null);
        System.out.println("ofNullable(null) => " + empty);

        try {
            Optional<String> boom = Optional.of(null);
            System.out.println(boom);
        } catch (NullPointerException ex) {
            System.out.println("Optional.of(null) throws NPE immediately.");
        }
    }

    /**
     * Scenario: filter out blank emails even if present.
     */
    private static void filterRemovingValueScenario() {
        System.out.println("\n[Scenario B] filter can turn present Optional into empty:");

        User user = new User("priya", "   ");

        Optional<String> emailToSend = Optional.ofNullable(user.email)
                .map(String::trim)
                .filter(e -> !e.isEmpty());

        System.out.println("Can send email? " + emailToSend.isPresent());
    }

    /**
     * flatMap avoids Optional<Optional<T>> nesting.
     */
    private static void flatMapNestedOptionalScenario() {
        System.out.println("\n[Scenario C] map vs flatMap with Optional-returning methods:");

        Optional<User> user = Optional.of(new User("aman", "aman@example.com"));

        // map would give Optional<Optional<String>> (bad)
        // flatMap flattens one level
        Optional<String> domain = user
                .flatMap(OptionalChainingEdgeCasesDemo::extractDomain);

        System.out.println("Domain: " + domain.orElse("unknown"));
    }

    private static Optional<String> extractDomain(User user) {
        if (user.email == null || !user.email.contains("@")) {
            return Optional.empty();
        }
        return Optional.of(user.email.substring(user.email.indexOf('@') + 1));
    }

    private static void getWithoutCheckTrap() {
        System.out.println("\n[Edge D] get() without presence check:");

        Optional<String> missing = Optional.empty();
        try {
            System.out.println(missing.get());
        } catch (NoSuchElementException ex) {
            System.out.println("get() on empty throws NoSuchElementException.");
        }
    }

    private static void optionalInEqualsScenario() {
        System.out.println("\n[Edge E] Optional in business equals/hashCode:");
        System.out.println("Avoid using Optional fields inside entity equals(); compare underlying values.");
    }

    static class User {
        final String name;
        final String email;

        User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

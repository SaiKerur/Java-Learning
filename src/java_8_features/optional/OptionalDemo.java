package java_8_features.optional;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * OPTIONAL<T> (Java 8)
 * ====================
 *
 * Purpose:
 * - Explicitly model "value may be absent" instead of returning null.
 * - Encourage caller to handle absence safely.
 *
 * Important clarifications (common misconceptions):
 * - Optional is mainly for RETURN TYPES, not fields/parameters (overuse adds noise).
 * - Optional does NOT remove null from Java; it is a wrapper to communicate intent.
 * - Never call get() without checking isPresent(); prefer orElse/orElseGet/map.
 *
 * Main APIs:
 * - Optional.of(value)        -> value must not be null
 * - Optional.ofNullable(value)-> wraps null as empty Optional
 * - Optional.empty()          -> explicit empty
 * - isPresent(), ifPresent(Consumer)
 * - orElse(default), orElseGet(Supplier), orElseThrow()
 * - map, flatMap, filter
 */
public class OptionalDemo {

    private static final Map<String, UserProfile> USER_DB = new HashMap<>();

    static {
        USER_DB.put("aman", new UserProfile("aman", "aman@example.com", "9876543210"));
        USER_DB.put("priya", new UserProfile("priya", "priya@example.com", null));
    }

    public static void demo() {
        System.out.println("\n--- OPTIONAL DEMO ---");

        creationScenario();
        safeAccessScenario();
        chainingScenario();
        antiPatternsScenario();
        serviceLayerScenario();
    }

    private static void creationScenario() {
        System.out.println("\n[Scenario A] Creating Optional:");

        Optional<String> present = Optional.of("Java 8");
        Optional<String> maybeNull = Optional.ofNullable(null);
        Optional<String> empty = Optional.empty();

        System.out.println("of(value): " + present);
        System.out.println("ofNullable(null): " + maybeNull);
        System.out.println("empty(): " + empty);

        /*
         * Optional.of(null) throws NullPointerException immediately.
         * Use ofNullable when source may be null.
         */
    }

    private static void safeAccessScenario() {
        System.out.println("\n[Scenario B] Safe access patterns:");

        Optional<String> email = findEmailByUsername("aman");
        Optional<String> missing = findEmailByUsername("unknown");

        // Bad style: if (email.isPresent()) return email.get();
        System.out.println("Email (aman): " + email.orElse("No email available"));
        System.out.println("Email (unknown): " + missing.orElse("No email available"));

        // Lazy fallback using Supplier
        String phone = findPhoneByUsername("priya")
                .orElseGet(() -> lookupFromBackupService("priya"));
        System.out.println("Phone (priya): " + phone);

        // Fail fast with custom exception
        try {
            String mustExist = findEmailByUsername("ghost")
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            System.out.println(mustExist);
        } catch (NoSuchElementException ex) {
            System.out.println("orElseThrow triggered: " + ex.getMessage());
        }
    }

    private static void chainingScenario() {
        System.out.println("\n[Scenario C] map / flatMap / filter chain:");

        Optional<UserProfile> profile = findProfile("priya");

        String normalizedEmail = profile
                .filter(p -> p.email != null && !p.email.trim().isEmpty())
                .map(p -> p.email)
                .map(String::toLowerCase)
                .orElse("email-not-available");

        System.out.println("Normalized email: " + normalizedEmail);

        // flatMap when mapper itself returns Optional
        Optional<String> countryCode = profile
                .flatMap(OptionalDemo::extractCountryCodeFromPhone);

        if (!countryCode.isPresent()) {
            countryCode = Optional.of("IN");
        }

        System.out.println("Country code: " + countryCode.orElse("NA"));
    }

    private static void antiPatternsScenario() {
        System.out.println("\n[Scenario D] Anti-patterns to avoid:");
        System.out.println("- optional.get() without check");
        System.out.println("- using Optional for every method parameter");
        System.out.println("- storing Optional fields in entity classes");
        System.out.println("- serializing Optional in APIs without need");
    }

    /**
     * Scenario: Service returns Optional to force caller to handle missing user.
     */
    private static void serviceLayerScenario() {
        System.out.println("\n[Scenario E] Service layer return type:");

        findProfile("aman").ifPresent(p ->
                System.out.println("Welcome back, " + p.username));

        String display = findProfile("ghost")
                .map(p -> "Hello " + p.username)
                .orElse("Guest user");
        System.out.println(display);
    }

    private static Optional<String> findEmailByUsername(String username) {
        return findProfile(username).map(p -> p.email);
    }

    private static Optional<String> findPhoneByUsername(String username) {
        return findProfile(username).map(p -> p.phone);
    }

    private static Optional<UserProfile> findProfile(String username) {
        return Optional.ofNullable(USER_DB.get(username));
    }

    private static Optional<String> extractCountryCodeFromPhone(UserProfile profile) {
        if (profile.phone == null || profile.phone.length() < 2) {
            return Optional.empty();
        }
        // Dummy extraction for demo
        return Optional.of("IN");
    }

    private static String lookupFromBackupService(String username) {
        return "0000000000";
    }

    static class UserProfile {
        final String username;
        final String email;
        final String phone;

        UserProfile(String username, String email, String phone) {
            this.username = username;
            this.email = email;
            this.phone = phone;
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

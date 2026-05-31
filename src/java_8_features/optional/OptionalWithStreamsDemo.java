package java_8_features.optional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * OPTIONAL + STREAMS
 * ==================
 *
 * Common patterns:
 * - map service methods returning Optional
 * - flatMap(Optional::stream) [Java 9+] — Java 8 pattern shown below
 * - filter(Optional::isPresent) + map(Optional::get)  (legacy; careful)
 */
public class OptionalWithStreamsDemo {

    public static void demo() {
        System.out.println("\n--- OPTIONAL WITH STREAMS DEMO ---");

        findFirstPresentValueScenario();
        java8FlatMapOptionalPattern();
        streamOfOptionalsScenario();
    }

    /**
     * Scenario: Collect first available phone from multiple users.
     */
    private static void findFirstPresentValueScenario() {
        System.out.println("\n[Scenario A] first available contact number:");

        List<Optional<String>> phones = Arrays.asList(
                Optional.empty(),
                Optional.of("111"),
                Optional.of("222")
        );

        Optional<String> firstPhone = phones.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        System.out.println("First phone: " + firstPhone.orElse("N/A"));
    }

    /**
     * Java 8 pattern before Optional.stream() (Java 9+).
     */
    private static void java8FlatMapOptionalPattern() {
        System.out.println("\n[Scenario B] Java 8 flatMap pattern:");

        List<String> usernames = Arrays.asList("aman", "ghost", "priya");

        List<String> emails = usernames.stream()
                .map(OptionalWithStreamsDemo::findEmail)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        System.out.println("Resolved emails: " + emails);
    }

    /**
     * Edge: Stream.of(Optional) is not the same as optional.stream().
     */
    private static void streamOfOptionalsScenario() {
        System.out.println("\n[Edge C] Stream.of(optional) contains Optional object:");

        Optional<String> opt = Optional.of("data");
        List<?> collected = Stream.of(opt).collect(Collectors.toList());
        System.out.println("Collected item type is Optional: " + collected);
    }

    private static Optional<String> findEmail(String username) {
        if ("aman".equals(username)) return Optional.of("aman@example.com");
        if ("priya".equals(username)) return Optional.of("priya@example.com");
        return Optional.empty();
    }

    public static void main(String[] args) {
        demo();
    }
}

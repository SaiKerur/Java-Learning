package java_8_features.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * STREAM EDGE CASES & LOOPHOLES
 * =============================
 *
 * 1) null elements in stream -> NPE in many ops unless filtered
 * 2) Stream reuse after terminal operation -> IllegalStateException
 * 3) Modifying backing collection during stream -> ConcurrentModificationException risk
 * 4) sorted() without comparator on mixed/null data
 * 5) collect vs toList mutability expectations
 */
public class StreamEdgeCasesDemo {

    public static void demo() {
        System.out.println("\n--- STREAM EDGE CASES DEMO ---");

        nullElementsScenario();
        streamReuseScenario();
        concurrentModificationScenario();
        sortedWithNullsScenario();
        collectMutabilityNote();
    }

    /**
     * Edge: map on null element throws NPE.
     */
    private static void nullElementsScenario() {
        System.out.println("\n[Edge A] null elements in stream:");

        List<String> cities = new ArrayList<>(Arrays.asList("Pune", null, "Mumbai"));

        List<String> safe = cities.stream()
                .filter(Objects::nonNull)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("Safe pipeline result: " + safe);
    }

    private static void streamReuseScenario() {
        System.out.println("\n[Edge B] stream cannot be reused:");

        Stream<String> stream = Stream.of("A", "B");
        System.out.println("First terminal count: " + stream.count());

        try {
            stream.forEach(System.out::println); // would throw
        } catch (IllegalStateException ex) {
            System.out.println("Caught expected: " + ex.getClass().getSimpleName());
        }
    }

    /**
     * Edge: structural modification of source during stream pipeline.
     */
    private static void concurrentModificationScenario() {
        System.out.println("\n[Edge C] modify source during stream:");

        List<Integer> data = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        try {
            data.stream()
                    .forEach(x -> {
                        if (x == 2) {
                            data.add(99); // risky structural change
                        }
                    });
        } catch (Exception ex) {
            System.out.println("Possible exception: " + ex.getClass().getSimpleName());
        }
        System.out.println("Avoid modifying same collection inside stream/forEach.");
    }

    /**
     * Edge: natural ordering with null throws NPE.
     */
    private static void sortedWithNullsScenario() {
        System.out.println("\n[Edge D] sorted() with null values:");

        List<String> values = Arrays.asList("b", null, "a");
        try {
            values.stream().sorted().collect(Collectors.toList());
        } catch (NullPointerException ex) {
            System.out.println("sorted() failed due to null: " + ex.getClass().getSimpleName());
        }

        List<String> fixed = values.stream()
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Filtered + sorted: " + fixed);
    }

    private static void collectMutabilityNote() {
        System.out.println("\n[Edge E] collect(toList()) mutability:");
        List<String> result = Stream.of("x", "y").collect(Collectors.toList());
        result.add("z"); // allowed for ArrayList-backed toList in modern Java
        System.out.println("Mutable result list: " + result);
        System.out.println("If immutability required, wrap with Collections.unmodifiableList.");
    }

    public static void main(String[] args) {
        demo();
    }
}

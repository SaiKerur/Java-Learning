package java_8_features.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * SHORT-CIRCUIT STREAM OPERATIONS
 * ===============================
 *
 * Terminal ops that may stop early:
 * - anyMatch, allMatch, noneMatch
 * - findFirst, findAny
 * - limit (intermediate but stops pulling after N)
 *
 * Why important:
 * - Performance on large/infinite streams
 * - Correct "exists?" checks without processing entire dataset
 */
public class StreamShortCircuitDemo {

    public static void demo() {
        System.out.println("\n--- STREAM SHORT-CIRCUIT DEMO ---");

        matchOperationsScenario();
        findFirstVsFindAnyScenario();
        limitOnInfiniteStreamScenario();
        emptyStreamEdgeCase();
    }

    /**
     * Scenario: Fraud monitoring - does any transaction exceed threshold?
     */
    private static void matchOperationsScenario() {
        System.out.println("\n[Scenario A] anyMatch / allMatch / noneMatch:");

        List<Integer> txnAmounts = Arrays.asList(500, 1200, 300, 9900);

        boolean anyHighRisk = txnAmounts.stream().anyMatch(amount -> amount > 5000);
        boolean allBelow10k = txnAmounts.stream().allMatch(amount -> amount < 10000);
        boolean noneNegative = txnAmounts.stream().noneMatch(amount -> amount < 0);

        System.out.println("Any high risk? " + anyHighRisk);
        System.out.println("All below 10k? " + allBelow10k);
        System.out.println("None negative? " + noneNegative);
    }

    /**
     * findFirst preserves encounter order; findAny is optimized for parallel.
     */
    private static void findFirstVsFindAnyScenario() {
        System.out.println("\n[Scenario B] findFirst vs findAny:");

        List<String> queue = Arrays.asList("job-1", "job-2", "job-3");

        Optional<String> first = queue.stream().findFirst();
        Optional<String> any = queue.parallelStream().findAny();

        System.out.println("findFirst: " + first.orElse("none"));
        System.out.println("findAny (parallel): " + any.orElse("none"));
    }

    private static void limitOnInfiniteStreamScenario() {
        System.out.println("\n[Scenario C] limit on infinite stream:");

        List<Integer> firstFive = java.util.stream.IntStream.iterate(0, n -> n + 2)
                .limit(5)
                .boxed()
                .collect(java.util.stream.Collectors.toList());

        System.out.println("First five even: " + firstFive);
    }

    /**
     * Edge: empty stream => anyMatch false, allMatch true, noneMatch true (vacuous truth).
     */
    private static void emptyStreamEdgeCase() {
        System.out.println("\n[Edge D] empty stream match behavior:");

        List<Integer> empty = List.of();

        System.out.println("anyMatch: " + empty.stream().anyMatch(x -> x > 0));   // false
        System.out.println("allMatch: " + empty.stream().allMatch(x -> x > 0));   // true
        System.out.println("noneMatch: " + empty.stream().noneMatch(x -> x > 0)); // true

        System.out.println("Loophole: allMatch/noneMatch on empty can surprise if not documented.");
    }

    public static void main(String[] args) {
        demo();
    }
}

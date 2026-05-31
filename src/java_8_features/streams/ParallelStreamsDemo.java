package java_8_features.streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * PARALLEL STREAMS (Java 8)
 * =========================
 *
 * What is parallel stream?
 * - stream.parallel() or collection.parallelStream()
 * - Splits work across multiple threads (ForkJoinPool common pool by default).
 *
 * When parallel helps:
 * - Large datasets
 * - CPU-intensive, independent per-element operations
 * - No/controllable shared mutable state
 *
 * When parallel can hurt:
 * - Small collections (thread coordination overhead > benefit)
 * - Operations with synchronization/IO blocking
 * - Order-sensitive logic done incorrectly
 *
 * Critical rules:
 * 1) Ensure operations are thread-safe and side-effect free.
 * 2) Avoid mutating shared variables from parallel forEach.
 * 3) Use collect with combiner-friendly collectors for parallel pipelines.
 * 4) Benchmark before assuming parallel is faster.
 */
public class ParallelStreamsDemo {

    public static void demo() {
        System.out.println("\n--- PARALLEL STREAMS DEMO ---");

        sequentialVsParallelSum();
        wrongSharedStateExample();
        correctAggregationExample();
        whenToAvoidParallel();
    }

    private static void sequentialVsParallelSum() {
        System.out.println("\n[Scenario A] Sequential vs parallel sum on larger range:");

        int upper = 1_000_000;

        long startSeq = System.nanoTime();
        long seqSum = IntStream.rangeClosed(1, upper).sum();
        long endSeq = System.nanoTime();

        long startPar = System.nanoTime();
        long parSum = IntStream.rangeClosed(1, upper).parallel().sum();
        long endPar = System.nanoTime();

        System.out.println("Sequential sum: " + seqSum + " in " + (endSeq - startSeq) + " ns");
        System.out.println("Parallel sum  : " + parSum + " in " + (endPar - startPar) + " ns");
        System.out.println("Note: timings vary by CPU load and JVM warmup.");
    }

    /**
     * Anti-pattern: mutating shared ArrayList from parallel stream.
     */
    private static void wrongSharedStateExample() {
        System.out.println("\n[Scenario B] WRONG: shared mutable state in parallel stream:");

        List<Integer> source = IntStream.range(0, 1000).boxed().collect(Collectors.toList());

        /*
         * Never do this in production:
         * List<Integer> unsafe = new ArrayList<>();
         * source.parallelStream().forEach(unsafe::add); // race condition, data loss
         */

        System.out.println("parallelStream().forEach(sharedList::add) can lose elements due to races.");
        System.out.println("Use collect(Collectors.toList()) instead.");
    }

    /**
     * Correct pattern: terminal collect handles merging safely.
     */
    private static void correctAggregationExample() {
        System.out.println("\n[Scenario C] CORRECT: collect in parallel pipeline:");

        List<Integer> source = IntStream.range(0, 1000).boxed().collect(Collectors.toList());

        List<Integer> doubled = source.parallelStream()
                .map(n -> n * 2)
                .collect(Collectors.toList());

        System.out.println("Doubled list size: " + doubled.size());
        System.out.println("First 5 values: " + doubled.subList(0, 5));
    }

    private static void whenToAvoidParallel() {
        System.out.println("\n[Scenario D] When to avoid parallel streams:");
        System.out.println("- Tiny lists (e.g., < 1000 elements) for simple map/filter.");
        System.out.println("- Tasks involving blocking I/O (DB/network/file).");
        System.out.println("- Strict encounter-order requirements with costly coordination.");
        System.out.println("- Pipelines depending on non-thread-safe external resources.");
    }

    public static void main(String[] args) {
        demo();
    }
}

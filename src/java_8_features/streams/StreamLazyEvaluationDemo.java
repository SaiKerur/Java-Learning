package java_8_features.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LAZY EVALUATION IN STREAMS
 * ==========================
 *
 * Intermediate operations are lazy:
 * - Nothing runs until terminal operation executes.
 * - Enables fusing operations and short-circuiting.
 *
 * peek() is for debugging; it should not change pipeline behavior in production logic.
 */
public class StreamLazyEvaluationDemo {

    public static void demo() {
        System.out.println("\n--- STREAM LAZY EVALUATION DEMO ---");

        lazyPipelineDemo();
        shortCircuitStopsEarlyDemo();
        peekMisuseWarning();
    }

    private static void lazyPipelineDemo() {
        System.out.println("\n[Scenario A] pipeline executes only at terminal:");

        List<String> names = Arrays.asList("Aman", "Priya", "Ravi", "Neha");

        System.out.println("Building pipeline (no output yet)...");

        List<String> result = names.stream()
                .peek(n -> System.out.println("  stream saw: " + n))
                .filter(n -> n.startsWith("A") || n.startsWith("P"))
                .peek(n -> System.out.println("  after filter: " + n))
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("Terminal collect done. Result: " + result);
    }

    /**
     * Because of short-circuit, not all elements may be processed.
     */
    private static void shortCircuitStopsEarlyDemo() {
        System.out.println("\n[Scenario B] findFirst stops early:");

        List<Integer> ids = Arrays.asList(11, 22, 33, 44, 55);

        ids.stream()
                .peek(id -> System.out.println("Checking id " + id))
                .filter(id -> id > 30)
                .findFirst()
                .ifPresent(id -> System.out.println("First match: " + id));
    }

    private static void peekMisuseWarning() {
        System.out.println("\n[Edge] peek misuse:");
        System.out.println("- Do not use peek() to modify elements or external state in production.");
        System.out.println("- Use map/forEach/collect intentionally for behavior.");
    }

    public static void main(String[] args) {
        demo();
    }
}

package java_8_features.functional_interfaces;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.function.IntToLongFunction;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

/**
 * PRIMITIVE SPECIALIZED FUNCTIONAL INTERFACES
 * ===========================================
 *
 * Problem with generic types:
 * - Stream<Integer> boxes ints into Integer objects (extra memory/GC pressure).
 *
 * Java 8 solution:
 * - IntPredicate, LongUnaryOperator, DoubleConsumer, ToIntFunction, etc.
 * - IntStream, LongStream, DoubleStream for primitive pipelines.
 *
 * Use when:
 * - Large numeric datasets
 * - Performance-sensitive calculations (billing, metrics, scoring)
 */
public class PrimitiveFunctionalInterfacesDemo {

    public static void demo() {
        System.out.println("\n--- PRIMITIVE FUNCTIONAL INTERFACES DEMO ---");

        intStreamScenario();
        toIntFunctionScenario();
        intPredicateScenario();
        objIntConsumerScenario();
        boxingCostNote();
    }

    private static void intStreamScenario() {
        System.out.println("\n[Scenario A] IntStream (no Integer boxing):");

        int[] scores = {45, 78, 92, 33, 88};

        int max = Arrays.stream(scores).max().orElse(0);
        double avg = Arrays.stream(scores).average().orElse(0.0);

        System.out.println("Max score: " + max);
        System.out.println("Average: " + avg);
    }

    /**
     * Scenario: Extract length from words for indexing.
     */
    private static void toIntFunctionScenario() {
        System.out.println("\n[Scenario B] ToIntFunction<T>:");

        ToIntFunction<String> wordLength = String::length;

        int totalChars = Arrays.stream(new String[]{"Java", "Stream", "API"})
                .mapToInt(wordLength)
                .sum();

        System.out.println("Total characters: " + totalChars);
    }

    /**
     * Scenario: Filter only passing exam scores (>= 40).
     */
    private static void intPredicateScenario() {
        System.out.println("\n[Scenario C] IntPredicate:");

        IntPredicate passing = score -> score >= 40;

        int[] marks = {35, 41, 67, 39, 90};
        int passCount = (int) Arrays.stream(marks).filter(passing).count();

        System.out.println("Pass count: " + passCount);
    }

    /**
     * Scenario: Print indexed leaderboard lines.
     */
    private static void objIntConsumerScenario() {
        System.out.println("\n[Scenario D] ObjIntConsumer<T> (object + int index):");

        String[] players = {"Aman", "Priya", "Ravi"};

        ObjIntConsumer<String> printRank = (name, rank) ->
                System.out.println("#" + (rank + 1) + " " + name);

        for (int i = 0; i < players.length; i++) {
            printRank.accept(players[i], i);
        }
    }

    private static void boxingCostNote() {
        System.out.println("\n[Edge] Boxing cost:");
        System.out.println("- List<Integer> + stream() boxes every value.");
        System.out.println("- Prefer int[] or IntStream for heavy numeric processing.");

        IntToLongFunction squareAsLong = x -> (long) x * x;
        long sumSquares = IntStream.rangeClosed(1, 5).mapToLong(squareAsLong).sum();
        System.out.println("Sum of squares 1..5: " + sumSquares);
    }

    public static void main(String[] args) {
        demo();
    }
}

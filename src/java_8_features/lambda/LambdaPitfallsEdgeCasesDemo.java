package java_8_features.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * LAMBDA PITFALLS & EDGE CASES
 * ============================
 *
 * Topics covered:
 * 1) Variable shadowing (parameter name vs outer variable)
 * 2) Checked exceptions in lambdas (must handle inside or wrap)
 * 3) Returning null from Function used in stream (NPE risk downstream)
 * 4) Lambdas are not magic threads (still run on caller thread unless async framework)
 * 5) Serialization caution for lambdas capturing state
 */
public class LambdaPitfallsEdgeCasesDemo {

    public static void demo() {
        System.out.println("\n--- LAMBDA PITFALLS & EDGE CASES DEMO ---");

        effectivelyFinalTrap();
        shadowingTrapExplanation();
        checkedExceptionPattern();
        nullReturnInFunctionTrap();
        lambdaPerformanceNote();
    }

    /**
     * Edge case: variable used in lambda must not be reassigned after capture.
     */
    private static void effectivelyFinalTrap() {
        System.out.println("\n[Edge A] effectively final rule:");

        int multiplier = 2; // effectively final if not reassigned
        List<Integer> values = List.of(1, 2, 3);

        List<Integer> doubled = values.stream()
                .map(v -> v * multiplier)
                .toList();

        System.out.println("Doubled: " + doubled);

        /*
         * multiplier = 3; // compile error if placed before lambda usage in same scope rules
         * Reason: lambda may run later; mutable outer state would be unsafe.
         */
    }

    /**
     * Loophole: reusing the same variable name in lambda parameters as a local variable
     * in the same scope is a compile-time error in Java.
     */
    private static void shadowingTrapExplanation() {
        System.out.println("\n[Edge B] lambda parameter naming vs outer variables:");

        int outerValue = 10;
        // In Java, lambda parameters cannot shadow locals in the same scope (compile error if same name).
        Function<Integer, Integer> increment = n -> n + 1;

        System.out.println("Outer value still " + outerValue + ", lambda increments arg: " + increment.apply(5));
        System.out.println("Avoid confusing names between outer variables and lambda parameters.");
    }

    /**
     * Lambdas cannot throw checked exceptions unless handled inside.
     */
    private static void checkedExceptionPattern() {
        System.out.println("\n[Edge C] checked exceptions:");

        List<String> inputs = List.of("10", "bad", "20");

        List<Integer> parsed = new ArrayList<>();
        for (String input : inputs) {
            try {
                parsed.add(parseStrict(input));
            } catch (NumberFormatException ex) {
                System.out.println("Skipped invalid input: " + input);
            }
        }

        System.out.println("Parsed values: " + parsed);

        /*
         * In streams, common pattern is map with try/catch inside lambda
         * or custom wrapper throwing unchecked exception.
         */
    }

    private static int parseStrict(String value) {
        return Integer.parseInt(value); // may throw NumberFormatException
    }

    /**
     * Returning null from mapper can cause NullPointerException later.
     */
    private static void nullReturnInFunctionTrap() {
        System.out.println("\n[Edge D] null return in Function/map:");

        List<String> codes = List.of("IN", "US", "XX");

        // Dangerous if later code assumes non-null
        List<String> names = codes.stream()
                .map(LambdaPitfallsEdgeCasesDemo::countryNameOrNull)
                .toList();

        System.out.println("Mapped list may contain null: " + names);

        // Safer: filter out nulls explicitly
        List<String> safeNames = codes.stream()
                .map(LambdaPitfallsEdgeCasesDemo::countryNameOrNull)
                .filter(name -> name != null)
                .toList();

        System.out.println("Null-filtered names: " + safeNames);
    }

    private static String countryNameOrNull(String code) {
        if ("IN".equals(code)) return "India";
        if ("US".equals(code)) return "United States";
        return null; // unknown code
    }

    private static void lambdaPerformanceNote() {
        System.out.println("\n[Edge E] performance misconception:");
        System.out.println("- Lambdas are not always faster than loops.");
        System.out.println("- Hot JVM optimizes both; readability often matters more.");
        System.out.println("- For tiny lists, classic loop can be simpler and equally fast.");
    }

    public static void main(String[] args) {
        demo();
    }
}

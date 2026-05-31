package java_8_features.functional_interfaces;

import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * MORE BUILT-IN FUNCTIONAL INTERFACES (Java 8)
 * ============================================
 *
 * - UnaryOperator<T>  : T -> T   (specialized Function where input=output type)
 * - BinaryOperator<T> : (T,T) -> T
 * - Comparator<T>     : (T,T) -> int   (compare; also has default/instance methods)
 *
 * Edge cases:
 * - BinaryOperator requires same type for both operands and result.
 * - Comparator must be consistent with equals for sorted sets/maps (loophole if ignored).
 */
public class BuiltInFunctionalInterfacesDemo {

    public static void demo() {
        System.out.println("\n--- BUILT-IN FUNCTIONAL INTERFACES (EXTRA) DEMO ---");

        unaryOperatorScenario();
        binaryOperatorScenario();
        comparatorConsistencyScenario();
    }

    /**
     * Scenario: Normalize usernames to lowercase in-place logic.
     */
    private static void unaryOperatorScenario() {
        System.out.println("\n[Scenario A] UnaryOperator<T>:");

        UnaryOperator<String> normalizeUser = username -> username.trim().toLowerCase();

        System.out.println(normalizeUser.apply("  Aman  "));
        System.out.println(normalizeUser.apply("PRIYA"));
    }

    /**
     * Scenario: Merge two partial inventory counts.
     */
    private static void binaryOperatorScenario() {
        System.out.println("\n[Scenario B] BinaryOperator<T>:");

        BinaryOperator<Integer> mergeStock = (warehouseA, warehouseB) -> warehouseA + warehouseB;

        int total = mergeStock.apply(120, 80);
        System.out.println("Total stock: " + total);

        // Stream reduce uses BinaryOperator
        int max = java.util.stream.Stream.of(10, 45, 23, 45)
                .reduce(Integer::max)
                .orElse(0);
        System.out.println("Max via BinaryOperator: " + max);
    }

    /**
     * Loophole: Comparator inconsistent with equals breaks Set/Map contracts.
     */
    private static void comparatorConsistencyScenario() {
        System.out.println("\n[Scenario C] Comparator vs equals consistency:");

        Comparator<String> byLength = Comparator.comparingInt(String::length);

        String a = "cat";
        String b = "dog"; // same length, different chars

        System.out.println("compare(cat, dog)=" + byLength.compare(a, b)); // 0 => "equal" for sort order
        System.out.println("equals(cat, dog)=" + a.equals(b));             // false

        System.out.println("If compare returns 0 but equals is false, TreeSet may keep only one.");
        System.out.println("Rule: compare(a,b)==0 should imply Objects.equals(a,b) when using sorted collections.");
    }

    public static void main(String[] args) {
        demo();
    }
}

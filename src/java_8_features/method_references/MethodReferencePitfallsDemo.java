package java_8_features.method_references;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * METHOD REFERENCE PITFALLS
 * =========================
 *
 * 1) Readability: forced method ref can hide intent.
 * 2) Overloading: compiler picks method based on target functional interface signature.
 * 3) Instance vs static confusion.
 * 4) Generics erasure can surprise in some advanced cases.
 */
public class MethodReferencePitfallsDemo {

    public static void demo() {
        System.out.println("\n--- METHOD REFERENCE PITFALLS DEMO ---");

        whenNotToUseScenario();
        overloadResolutionScenario();
        instanceVsStaticConfusionScenario();
        constructorArityScenario();
    }

    private static void whenNotToUseScenario() {
        System.out.println("\n[Edge A] When method reference hurts readability:");

        List<Integer> nums = Arrays.asList(1, 2, 3);

        // Clear lambda for business rule
        List<String> labels = nums.stream()
                .map(n -> "ORDER-" + n)
                .collect(Collectors.toList());

        // Method ref would be awkward here (no single existing method)
        System.out.println(labels);
    }

    /**
     * Compiler chooses method based on functional interface type.
     */
    private static void overloadResolutionScenario() {
        System.out.println("\n[Edge B] overload resolution via target type:");

        Printer printer = new Printer();

        // Consumer<String> -> void print(String)
        Consumer<String> printConsumer = printer::print;

        // Function<String, String> -> String printAndReturn(String)
        Function<String, String> printFunction = printer::printAndReturn;

        printConsumer.accept("hello");
        System.out.println("Returned: " + printFunction.apply("world"));
    }

    private static void instanceVsStaticConfusionScenario() {
        System.out.println("\n[Edge C] static vs instance reference:");

        // Unbound: String::toLowerCase  (first arg becomes receiver)
        Function<String, String> lower = String::toLowerCase;
        System.out.println(lower.apply("JAVA"));

        // Static on wrapper class
        Function<String, Integer> parse = Integer::parseInt;
        System.out.println(parse.apply("42"));

        System.out.println("Do not use instance ref for static methods or vice versa.");
    }

    /**
     * Constructor reference must match SAM parameter list exactly.
     */
    private static void constructorArityScenario() {
        System.out.println("\n[Edge D] constructor arity must match SAM:");

        // Supplier -> no-arg constructor
        java.util.function.Supplier<List<String>> listFactory = java.util.ArrayList::new;
        System.out.println("Empty list: " + listFactory.get());

        // BiConsumer cannot use single-arg constructor reference directly
        BiConsumer<List<String>, String> adder = List::add; // instance method ref, not constructor
        List<String> names = listFactory.get();
        adder.accept(names, "Aman");
        System.out.println("After add: " + names);
    }

    static class Printer {
        void print(String msg) {
            System.out.println("[print] " + msg);
        }

        String printAndReturn(String msg) {
            System.out.println("[printAndReturn] " + msg);
            return msg.toUpperCase();
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

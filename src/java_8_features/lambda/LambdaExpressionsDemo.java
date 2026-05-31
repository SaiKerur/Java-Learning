package java_8_features.lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * LAMBDA EXPRESSIONS (Java 8)
 * ===========================
 *
 * What is a lambda?
 * - A lambda is a short way to write an implementation of a functional interface
 *   (an interface with exactly ONE abstract method).
 * - Syntax: (parameters) -> expression   OR   (parameters) -> { statements; }
 *
 * Why were lambdas added?
 * - Before Java 8, passing behavior required anonymous inner classes:
 *     new Runnable() { public void run() { ... } }
 * - Lambdas remove boilerplate when the logic is small.
 *
 * Core rules (no loopholes):
 * 1) A lambda is NOT a new object type by itself; it is syntax sugar for SAM interfaces.
 * 2) Parameter types can be inferred by compiler in most cases.
 * 3) If body is a single expression, "return" is implicit; braces mean you must use return explicitly.
 * 4) Local variables used inside lambda must be effectively final (not reassigned after capture).
 * 5) "this" inside lambda refers to enclosing class, NOT the lambda (lambda has no own "this").
 *
 * Real use cases:
 * - Sorting with custom Comparator
 * - Event handlers / callbacks
 * - Collection iteration with forEach
 * - Stream operations (filter/map/reduce)
 */
public class LambdaExpressionsDemo {

    public static void demo() {
        System.out.println("\n--- LAMBDA EXPRESSIONS DEMO ---");

        beforeJava8Runnable();
        afterJava8Runnable();

        comparatorSortingScenario();
        forEachScenario();
        customFunctionalInterfaceScenario();
        effectivelyFinalScenario();
        lambdaVsAnonymousClassNotes();
    }

    /**
     * Scenario: Background task (classic Runnable example).
     */
    private static void beforeJava8Runnable() {
        System.out.println("\n[Scenario A] Runnable before Java 8 (anonymous class):");

        Runnable oldStyle = new Runnable() {
            @Override
            public void run() {
                System.out.println("Old style: task executed");
            }
        };
        oldStyle.run();
    }

    private static void afterJava8Runnable() {
        System.out.println("\n[Scenario A] Runnable with lambda:");

        // Zero-parameter lambda because Runnable has one abstract method: void run()
        Runnable newStyle = () -> System.out.println("Lambda style: task executed");
        newStyle.run();

        // Multi-statement lambda body requires braces
        Runnable withBlock = () -> {
            String message = "Lambda with block body";
            System.out.println(message);
        };
        withBlock.run();
    }

    /**
     * Scenario: E-commerce product sorting by price and then by name.
     */
    private static void comparatorSortingScenario() {
        System.out.println("\n[Scenario B] Sorting products using Comparator lambdas:");

        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", 75000));
        products.add(new Product("Mouse", 900));
        products.add(new Product("Keyboard", 2200));
        products.add(new Product("Monitor", 12000));

        // Sort by price ascending
        products.sort((p1, p2) -> Integer.compare(p1.price, p2.price));
        System.out.println("Sorted by price: " + products);

        // Sort by name (case-insensitive) using Comparator.comparing (built on lambdas)
        products.sort(Comparator.comparing(p -> p.name, String.CASE_INSENSITIVE_ORDER));
        System.out.println("Sorted by name: " + products);

        // Chained comparator: price desc, then name asc
        products.sort(Comparator.comparingInt((Product p) -> p.price).reversed()
                .thenComparing(p -> p.name));
        System.out.println("Sorted by price(desc) then name: " + products);
    }

    /**
     * Scenario: Notify each student after exam result publish.
     */
    private static void forEachScenario() {
        System.out.println("\n[Scenario C] forEach with lambda (internal iterator):");

        List<String> students = List.of("Aman", "Priya", "Ravi");

        // Consumer<T> SAM: void accept(T t)
        Consumer<String> printStudent = name -> System.out.println("Result published for: " + name);
        students.forEach(printStudent);

        // Method reference version is covered in method_references package
        System.out.println("Inline forEach:");
        students.forEach(name -> System.out.println("  -> " + name));
    }

    /**
     * Scenario: Discount calculator using custom functional interface.
     */
    private static void customFunctionalInterfaceScenario() {
        System.out.println("\n[Scenario D] Custom functional interface + lambda:");

        /*
         * @FunctionalInterface is optional but recommended.
         * Compiler enforces exactly one abstract method.
         */
        DiscountPolicy flat10Percent = price -> price * 0.90; // 10% off
        DiscountPolicy flat500Off = price -> Math.max(0, price - 500);

        int laptopPrice = 75000;
        System.out.println("Laptop after 10% discount: " + flat10Percent.applyDiscount(laptopPrice));
        System.out.println("Mouse after flat 500 off: " + flat500Off.applyDiscount(900));
    }

    /**
     * Scenario: Show "effectively final" rule used by lambdas.
     */
    private static void effectivelyFinalScenario() {
        System.out.println("\n[Scenario E] Effectively final capture:");

        String prefix = "Invoice#"; // not reassigned later => effectively final
        int startId = 1000;         // effectively final primitive

        List<String> orders = List.of("ORD-1", "ORD-2");
        orders.forEach(order -> {
            // Lambda can read variables from enclosing scope if effectively final.
            System.out.println(prefix + startId + " -> " + order);
        });

        /*
         * If you uncomment below, compilation fails:
         * prefix = "Bill#";
         * Reason: lambda may execute later/async; allowing mutation would be unsafe.
         */
    }

    private static void lambdaVsAnonymousClassNotes() {
        System.out.println("\n[Important differences: Lambda vs Anonymous Class]");
        System.out.println("- Lambda: only for functional interfaces (SAM).");
        System.out.println("- Anonymous class: can implement classes/interfaces with multiple methods.");
        System.out.println("- Lambda: no explicit constructor logic.");
        System.out.println("- Anonymous class: can have fields/state beyond captured variables.");
    }

    @FunctionalInterface
    interface DiscountPolicy {
        double applyDiscount(double originalPrice);
    }

    static class Product {
        final String name;
        final int price;

        Product(String name, int price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return name + "(" + price + ")";
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

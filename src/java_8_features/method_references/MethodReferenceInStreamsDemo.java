package java_8_features.method_references;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * METHOD REFERENCES IN STREAM PIPELINES
 * =====================================
 *
 * Common stream + method reference combinations:
 * - map(Class::method)
 * - filter(Objects::nonNull)   (often with static import)
 * - sorted(Comparator.comparing(Type::getter))
 * - forEach(System.out::println)
 */
public class MethodReferenceInStreamsDemo {

    public static void demo() {
        System.out.println("\n--- METHOD REFERENCES IN STREAMS DEMO ---");

        mapWithInstanceMethodRef();
        sortedWithComparingMethodRef();
        forEachPrintScenario();
        distinctByPropertyScenario();
    }

    /**
     * Scenario: Convert DTO list to display labels.
     */
    private static void mapWithInstanceMethodRef() {
        System.out.println("\n[Scenario A] map(Type::instanceMethod):");

        List<Invoice> invoices = Arrays.asList(
                new Invoice("INV-1", 1200),
                new Invoice("INV-2", 800)
        );

        List<String> labels = invoices.stream()
                .map(Invoice::displayLabel) // equivalent to inv -> inv.displayLabel()
                .collect(Collectors.toList());

        System.out.println(labels);
    }

    /**
     * Scenario: Sort customers by name then by loyalty points descending.
     */
    private static void sortedWithComparingMethodRef() {
        System.out.println("\n[Scenario B] Comparator + method references:");

        List<Customer> customers = Arrays.asList(
                new Customer("Ravi", 100),
                new Customer("Aman", 250),
                new Customer("Priya", 250)
        );

        List<Customer> sorted = customers.stream()
                .sorted(Comparator.comparing(Customer::name)
                        .thenComparingInt(Customer::points).reversed())
                .collect(Collectors.toList());

        System.out.println(sorted);
    }

    private static void forEachPrintScenario() {
        System.out.println("\n[Scenario C] forEach(System.out::println):");
        List<String> topics = Arrays.asList("Lambda", "Streams", "Optional");
        System.out.println("Topics:");
        topics.forEach(System.out::println);
    }

    /**
     * Edge: method references do not replace custom distinct-by logic alone.
     */
    private static void distinctByPropertyScenario() {
        System.out.println("\n[Scenario D] distinct() uses equals/hashCode of object:");

        List<Customer> customers = Arrays.asList(
                new Customer("Aman", 10),
                new Customer("Aman", 20) // same name, different points
        );

        long distinctCount = customers.stream().distinct().count();
        System.out.println("distinct() count (object identity): " + distinctCount);
        System.out.println("For distinct-by-name, use groupingBy or custom state (advanced).");
    }

    static class Invoice {
        final String id;
        final int amount;

        Invoice(String id, int amount) {
            this.id = id;
            this.amount = amount;
        }

        String displayLabel() {
            return id + " = INR " + amount;
        }
    }

    static class Customer {
        final String name;
        final int points;

        Customer(String name, int points) {
            this.name = name;
            this.points = points;
        }

        String name() {
            return name;
        }

        int points() {
            return points;
        }

        @Override
        public String toString() {
            return name + "(" + points + ")";
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

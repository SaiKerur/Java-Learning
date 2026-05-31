package java_8_features.collectors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * COLLECTORS (Java 8 Stream terminal operations)
 * ==============================================
 *
 * What is Collectors?
 * - Utility class with factory methods used by stream.collect(...).
 * - Converts stream elements into Sets, Lists, Maps, Strings, statistics, etc.
 *
 * Why important:
 * - Real applications rarely stop at forEach; they need structured output.
 * - Enables grouping, partitioning, joining, summarizing in declarative style.
 *
 * Common collectors:
 * - toList, toSet, toCollection
 * - joining
 * - groupingBy, partitioningBy
 * - counting, summingInt, averagingInt
 * - summarizingInt
 * - collectingAndThen (post-process immutable view)
 * - toMap (handle duplicate keys carefully)
 */
public class CollectorsDemo {

    public static void demo() {
        System.out.println("\n--- COLLECTORS DEMO ---");

        toListAndToSetScenario();
        joiningScenario();
        groupingByScenario();
        partitioningByScenario();
        statisticsScenario();
        toMapScenario();
        collectingAndThenScenario();
        downstreamCollectorsScenario();
    }

    /**
     * Scenario: Deduplicate product tags and preserve order list.
     */
    private static void toListAndToSetScenario() {
        System.out.println("\n[Scenario A] toList vs toSet:");

        List<String> tags = Arrays.asList("java", "spring", "java", "docker", "spring");

        List<String> listResult = tags.stream().collect(Collectors.toList());
        Set<String> setResult = tags.stream().collect(Collectors.toSet());

        System.out.println("List (allows duplicates): " + listResult);
        System.out.println("Set (unique): " + setResult);
    }

    /**
     * Scenario: Build CSV row from selected columns.
     */
    private static void joiningScenario() {
        System.out.println("\n[Scenario B] joining collector:");

        List<String> columns = Arrays.asList("Aman", "Developer", "Bangalore");

        String csv = columns.stream().collect(Collectors.joining(","));
        String csvWithPrefixSuffix = columns.stream()
                .collect(Collectors.joining(",", "[", "]"));

        System.out.println("CSV: " + csv);
        System.out.println("CSV with brackets: " + csvWithPrefixSuffix);

        // Manual equivalent using StringJoiner (also Java 8)
        StringJoiner joiner = new StringJoiner(" | ");
        columns.forEach(joiner::add);
        System.out.println("StringJoiner: " + joiner);
    }

    /**
     * Scenario: Group orders by status for operations dashboard.
     */
    private static void groupingByScenario() {
        System.out.println("\n[Scenario C] groupingBy:");

        List<Order> orders = Arrays.asList(
                new Order("O1", "SHIPPED", 1200),
                new Order("O2", "PENDING", 800),
                new Order("O3", "SHIPPED", 2200),
                new Order("O4", "CANCELLED", 500),
                new Order("O5", "PENDING", 1500)
        );

        Map<String, List<Order>> byStatus = orders.stream()
                .collect(Collectors.groupingBy(o -> o.status));

        System.out.println("Grouped by status: " + byStatus);

        // grouping + mapping: status -> list of order ids
        Map<String, List<String>> statusToIds = orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.status,
                        Collectors.mapping(o -> o.id, Collectors.toList())
                ));

        System.out.println("Status to order IDs: " + statusToIds);

        // grouping + counting
        Map<String, Long> countByStatus = orders.stream()
                .collect(Collectors.groupingBy(o -> o.status, Collectors.counting()));

        System.out.println("Count by status: " + countByStatus);
    }

    /**
     * Scenario: Split transactions into high-value and regular buckets.
     */
    private static void partitioningByScenario() {
        System.out.println("\n[Scenario D] partitioningBy (boolean key):");

        List<Order> orders = Arrays.asList(
                new Order("O1", "SHIPPED", 1200),
                new Order("O2", "PENDING", 800),
                new Order("O3", "SHIPPED", 2200)
        );

        Map<Boolean, List<Order>> partitioned = orders.stream()
                .collect(Collectors.partitioningBy(o -> o.amount >= 1500));

        System.out.println("High-value(true): " + partitioned.get(true));
        System.out.println("Regular(false): " + partitioned.get(false));
    }

    /**
     * Scenario: Sales analytics summary.
     */
    private static void statisticsScenario() {
        System.out.println("\n[Scenario E] summarizingInt:");

        List<Order> orders = Arrays.asList(
                new Order("O1", "SHIPPED", 1200),
                new Order("O2", "PENDING", 800),
                new Order("O3", "SHIPPED", 2200)
        );

        IntSummaryStatistics stats = orders.stream()
                .collect(Collectors.summarizingInt(o -> o.amount));

        System.out.println("Count: " + stats.getCount());
        System.out.println("Sum: " + stats.getSum());
        System.out.println("Min: " + stats.getMin());
        System.out.println("Max: " + stats.getMax());
        System.out.println("Average: " + stats.getAverage());
    }

    /**
     * Scenario: Build id -> order map for quick lookup.
     */
    private static void toMapScenario() {
        System.out.println("\n[Scenario F] toMap (watch duplicate keys):");

        List<Order> orders = Arrays.asList(
                new Order("O1", "SHIPPED", 1200),
                new Order("O2", "PENDING", 800)
        );

        Map<String, Order> orderMap = orders.stream()
                .collect(Collectors.toMap(o -> o.id, o -> o));

        System.out.println("Lookup O2: " + orderMap.get("O2"));

        /*
         * Duplicate keys cause IllegalStateException by default.
         * Use toMap(keyMapper, valueMapper, mergeFunction) when collisions expected.
         */
    }

    /**
     * Scenario: Return unmodifiable view after collection.
     */
    private static void collectingAndThenScenario() {
        System.out.println("\n[Scenario G] collectingAndThen:");

        List<String> names = Arrays.asList("Aman", "Priya", "Ravi");

        List<String> unmodifiableNames = names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> Collections.unmodifiableList(new ArrayList<>(list))
                ));

        System.out.println("Processed names: " + unmodifiableNames);
    }

    /**
     * Scenario: Department-wise total salary report.
     */
    private static void downstreamCollectorsScenario() {
        System.out.println("\n[Scenario H] groupingBy with downstream summing:");

        List<Employee> employees = Arrays.asList(
                new Employee("Aman", "DEV", 70000),
                new Employee("Priya", "DEV", 80000),
                new Employee("Ravi", "QA", 55000),
                new Employee("Neha", "QA", 60000)
        );

        Map<String, Integer> totalSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        e -> e.department,
                        LinkedHashMap::new,
                        Collectors.summingInt(e -> e.salary)
                ));

        System.out.println("Total salary by department: " + totalSalaryByDept);
    }

    static class Order {
        final String id;
        final String status;
        final int amount;

        Order(String id, String status, int amount) {
            this.id = id;
            this.status = status;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return id + "(" + status + "," + amount + ")";
        }
    }

    static class Employee {
        final String name;
        final String department;
        final int salary;

        Employee(String name, String department, int salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

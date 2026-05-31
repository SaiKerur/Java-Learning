package java_8_features.collectors;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ADVANCED COLLECTORS SCENARIOS
 * =============================
 *
 * - teeing-like patterns via collectingAndThen (Java 8 style)
 * - maxBy / minBy downstream
 * - mapping + filtering downstream
 * - preserving insertion order with LinkedHashMap supplier
 */
public class CollectorsAdvancedScenariosDemo {

    public static void demo() {
        System.out.println("\n--- COLLECTORS ADVANCED SCENARIOS DEMO ---");

        topEmployeePerDepartmentScenario();
        orderPreservingGroupingScenario();
        joiningWithFilteringScenario();
        countingWithFilterScenario();
    }

    /**
     * Scenario: Pick highest-paid employee in each department.
     */
    private static void topEmployeePerDepartmentScenario() {
        System.out.println("\n[Scenario A] maxBy per group:");

        List<Employee> employees = Arrays.asList(
                new Employee("Aman", "DEV", 70000),
                new Employee("Ravi", "DEV", 90000),
                new Employee("Priya", "QA", 60000),
                new Employee("Neha", "QA", 65000)
        );

        Map<String, Employee> topPaidByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        e -> e.department,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(e -> e.salary)),
                                opt -> opt.orElse(null)
                        )
                ));

        System.out.println("Top paid by department: " + topPaidByDept);
    }

    /**
     * Scenario: Dashboard must preserve status order from first appearance.
     */
    private static void orderPreservingGroupingScenario() {
        System.out.println("\n[Scenario B] LinkedHashMap supplier keeps order:");

        List<Order> orders = Arrays.asList(
                new Order("O1", "SHIPPED"),
                new Order("O2", "PENDING"),
                new Order("O3", "SHIPPED"),
                new Order("O4", "CANCELLED")
        );

        Map<String, Long> countByStatus = orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.status,
                        LinkedHashMap::new,
                        Collectors.counting()
                ));

        System.out.println("Ordered status counts: " + countByStatus);
    }

    private static void joiningWithFilteringScenario() {
        System.out.println("\n[Scenario C] joining after filter/map:");

        List<String> words = Arrays.asList("Java", null, "Stream", "", "API");

        String sentence = words.stream()
                .filter(w -> w != null && !w.isEmpty())
                .collect(Collectors.joining(" "));

        System.out.println("Joined sentence: " + sentence);
    }

    private static void countingWithFilterScenario() {
        System.out.println("\n[Scenario D] counting with filter downstream:");

        List<Order> orders = Arrays.asList(
                new Order("O1", "SHIPPED"),
                new Order("O2", "PENDING"),
                new Order("O3", "SHIPPED")
        );

        long shippedCount = orders.stream()
                .filter(o -> "SHIPPED".equals(o.status))
                .count();

        System.out.println("Shipped count: " + shippedCount);
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

        @Override
        public String toString() {
            return name + "(" + salary + ")";
        }
    }

    static class Order {
        final String id;
        final String status;

        Order(String id, String status) {
            this.id = id;
            this.status = status;
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

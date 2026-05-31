package java_8_features.streams;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * STREAM API (Java 8)
 * ===================
 *
 * What is a Stream?
 * - A sequence of elements supporting aggregate operations.
 * - NOT a data structure (does not store elements).
 * - Source can be Collection, array, I/O channel, generate(), etc.
 *
 * Two operation types:
 * 1) Intermediate (lazy): filter, map, flatMap, sorted, distinct, limit, skip ...
 *    - Return another stream; chained together.
 * 2) Terminal (eager): collect, forEach, reduce, count, min, max, findFirst ...
 *    - Trigger pipeline execution and produce result/side-effect.
 *
 * Important properties:
 * - Streams are consumed once (cannot reuse after terminal operation).
 * - Most stream operations are lazy until terminal call.
 * - Stream does NOT modify source collection unless explicitly mutable ops used.
 *
 * When to use streams:
 * - Declarative transformations on collections
 * - Filtering/mapping/reducing large datasets
 * - Readable pipelines compared to nested loops
 *
 * When loops may be better:
 * - Very simple one-pass logic
 * - Need break/continue in complex imperative flow
 * - Performance-critical tiny collections (overhead may matter)
 */
public class StreamApiDemo {

    public static void demo() {
        System.out.println("\n--- STREAM API DEMO ---");

        createStreamSources();
        filterMapCollectScenario();
        flatMapScenario();
        reduceScenario();
        sortingDistinctLimitScenario();
        primitiveStreamScenario();
        groupingScenario();
        streamPitfallsScenario();
    }

    private static void createStreamSources() {
        System.out.println("\n[Scenario A] Creating streams from different sources:");

        // From Collection
        List<String> languages = List.of("Java", "Kotlin", "Scala");
        Stream<String> fromList = languages.stream();

        // From array
        String[] arr = {"A", "B", "C"};
        Stream<String> fromArray = Arrays.stream(arr);

        // From values directly
        Stream<Integer> fromValues = Stream.of(1, 2, 3);

        // Infinite stream (use limit to avoid endless run)
        Stream<Integer> infinite = Stream.iterate(0, n -> n + 2); // 0,2,4,6...

        System.out.println("From list: " + fromList.collect(Collectors.joining(", ")));
        System.out.println("From array: " + fromArray.collect(Collectors.joining(", ")));
        System.out.println("From values: " + fromValues.map(String::valueOf).collect(Collectors.joining(", ")));
        System.out.println("First 5 even numbers: " +
                infinite.limit(5).map(String::valueOf).collect(Collectors.joining(", ")));
    }

    /**
     * Scenario: HR filters employees with salary > threshold and collects names.
     */
    private static void filterMapCollectScenario() {
        System.out.println("\n[Scenario B] filter -> map -> collect pipeline:");

        List<Employee> employees = List.of(
                new Employee("Aman", "DEV", 70000),
                new Employee("Priya", "QA", 55000),
                new Employee("Ravi", "DEV", 90000),
                new Employee("Neha", "HR", 48000)
        );

        List<String> highPaidDevNames = employees.stream()
                .filter(e -> "DEV".equals(e.department))
                .filter(e -> e.salary >= 70000)
                .map(e -> e.name)
                .sorted()
                .collect(Collectors.toList());

        System.out.println("High-paid DEV names: " + highPaidDevNames);
    }

    /**
     * Scenario: Blog platform flattens tags from multiple posts.
     */
    private static void flatMapScenario() {
        System.out.println("\n[Scenario C] flatMap (map + flatten one level):");

        List<Post> posts = List.of(
                new Post("Java 8 Basics", List.of("java", "lambda")),
                new Post("Spring Boot", List.of("spring", "java")),
                new Post("Docker Intro", List.of("docker", "devops"))
        );

        // map would give Stream<List<String>>; flatMap merges inner streams
        List<String> uniqueTags = posts.stream()
                .flatMap(post -> post.tags.stream())
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        System.out.println("Unique tags: " + uniqueTags);
    }

    /**
     * Scenario: Billing total for shopping cart.
     */
    private static void reduceScenario() {
        System.out.println("\n[Scenario D] reduce (combine elements into one result):");

        List<Integer> prices = List.of(1200, 450, 2999, 150);

        // Optional because stream may be empty
        Optional<Integer> totalOptional = prices.stream().reduce(Integer::sum);
        int total = totalOptional.orElse(0);

        // With identity (safe default for empty stream)
        int totalWithIdentity = prices.stream().reduce(0, Integer::sum);

        System.out.println("Total (Optional): " + total);
        System.out.println("Total (identity): " + totalWithIdentity);

        // Custom reducer: max price
        Optional<Integer> max = prices.stream().reduce(Integer::max);
        System.out.println("Max price: " + max.orElse(0));
    }

    private static void sortingDistinctLimitScenario() {
        System.out.println("\n[Scenario E] sorted + distinct + limit + skip:");

        List<Integer> scores = Arrays.asList(80, 95, 80, 70, 95, 60, 88);

        List<Integer> topDistinctScores = scores.stream()
                .sorted((a, b) -> Integer.compare(b, a)) // descending
                .distinct()
                .limit(3)
                .collect(Collectors.toList());

        System.out.println("Top 3 distinct scores: " + topDistinctScores);

        List<Integer> skipFirstTwo = scores.stream().sorted().skip(2).collect(Collectors.toList());
        System.out.println("After skipping first two sorted: " + skipFirstTwo);
    }

    /**
     * Scenario: Performance-friendly numeric operations using IntStream.
     */
    private static void primitiveStreamScenario() {
        System.out.println("\n[Scenario F] Primitive streams (IntStream, no boxing):");

        List<String> numberStrings = List.of("10", "20", "30");

        int sum = numberStrings.stream()
                .mapToInt(Integer::parseInt) // converts to IntStream
                .sum();

        OptionalInt max = numberStrings.stream().mapToInt(Integer::parseInt).max();
        IntSummaryStatistics stats = numberStrings.stream()
                .mapToInt(Integer::parseInt)
                .summaryStatistics();

        System.out.println("Sum: " + sum);
        System.out.println("Max: " + max.orElse(-1));
        System.out.println("Stats: count=" + stats.getCount() + ", avg=" + stats.getAverage());
    }

    /**
     * Scenario: Group orders by status for dashboard.
     */
    private static void groupingScenario() {
        System.out.println("\n[Scenario G] groupingBy (covered deeper in collectors package):");

        List<Order> orders = List.of(
                new Order("O1", "SHIPPED"),
                new Order("O2", "PENDING"),
                new Order("O3", "SHIPPED"),
                new Order("O4", "CANCELLED")
        );

        Map<String, List<Order>> byStatus = orders.stream()
                .collect(Collectors.groupingBy(o -> o.status));

        System.out.println("Grouped orders: " + byStatus);
    }

    private static void streamPitfallsScenario() {
        System.out.println("\n[Scenario H] Common pitfalls:");

        Stream<String> stream = Stream.of("A", "B");

        // First terminal operation consumes stream
        System.out.println("Count: " + stream.count());

        /*
         * Second terminal call throws IllegalStateException: stream already operated upon.
         * stream.forEach(System.out::println);
         */

        // Side-effect caution: avoid mutating external state in parallel streams
        System.out.println("Rule: prefer collect/reduce over shared mutable variables.");
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

    static class Post {
        final String title;
        final List<String> tags;

        Post(String title, List<String> tags) {
            this.title = title;
            this.tags = tags;
        }
    }

    static class Order {
        final String id;
        final String status;

        Order(String id, String status) {
            this.id = id;
            this.status = status;
        }

        @Override
        public String toString() {
            return id;
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

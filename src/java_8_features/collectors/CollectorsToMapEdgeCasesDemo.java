package java_8_features.collectors;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * COLLECTORS.toMap EDGE CASES
 * ===========================
 *
 * Loopholes:
 * - Duplicate keys => IllegalStateException by default
 * - null keys/values may cause NPE depending on map implementation
 * - Merge function required when collisions expected
 */
public class CollectorsToMapEdgeCasesDemo {

    public static void demo() {
        System.out.println("\n--- COLLECTORS.toMap EDGE CASES DEMO ---");

        duplicateKeyTrap();
        mergeFunctionScenario();
        downstreamValueCollisionScenario();
    }

    private static void duplicateKeyTrap() {
        System.out.println("\n[Edge A] duplicate key without merge function:");

        List<Item> items = Arrays.asList(
                new Item("A", 10),
                new Item("A", 20) // duplicate key
        );

        try {
            Map<String, Integer> map = items.stream()
                    .collect(Collectors.toMap(i -> i.key, i -> i.value));
            System.out.println(map);
        } catch (IllegalStateException ex) {
            System.out.println("Expected failure: duplicate key 'A'");
        }
    }

    /**
     * Scenario: Keep maximum value when duplicate keys appear.
     */
    private static void mergeFunctionScenario() {
        System.out.println("\n[Scenario B] toMap with merge function:");

        List<Item> items = Arrays.asList(
                new Item("A", 10),
                new Item("A", 20),
                new Item("B", 5)
        );

        Map<String, Integer> maxByKey = items.stream()
                .collect(Collectors.toMap(
                        i -> i.key,
                        i -> i.value,
                        Integer::max
                ));

        System.out.println("Max value per key: " + maxByKey);
    }

    /**
     * Scenario: Build map of key -> list by grouping instead of toMap when one-to-many.
     */
    private static void downstreamValueCollisionScenario() {
        System.out.println("\n[Scenario C] one-to-many should use groupingBy, not toMap:");

        List<Item> items = Arrays.asList(
                new Item("A", 10),
                new Item("A", 20),
                new Item("B", 5)
        );

        Map<String, List<Integer>> grouped = items.stream()
                .collect(Collectors.groupingBy(
                        i -> i.key,
                        Collectors.mapping(i -> i.value, Collectors.toList())
                ));

        System.out.println("Grouped values: " + grouped);
    }

    static class Item {
        final String key;
        final int value;

        Item(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

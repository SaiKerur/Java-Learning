package java_8_features.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Java 8 added lambda-friendly default methods on core Collection interfaces.
 *
 * Covered APIs:
 * - Iterable.forEach(Consumer)
 * - Collection.removeIf(Predicate)
 * - List.replaceAll(UnaryOperator)   [Java 8 on List]
 * - Map.forEach, replaceAll, computeIfAbsent, computeIfPresent
 *
 * Real scenarios:
 * - Cleanup invalid records
 * - Bulk update prices
 * - Cache "get or create" with computeIfAbsent
 */
public class LambdaCollectionsApiDemo {

    public static void demo() {
        System.out.println("\n--- LAMBDA + COLLECTIONS API DEMO ---");

        removeIfScenario();
        replaceAllScenario();
        mapComputeScenario();
        forEachVsEnhancedForScenario();
    }

    /**
     * Scenario: Remove expired coupons from active list.
     */
    private static void removeIfScenario() {
        System.out.println("\n[Scenario A] removeIf(Predicate):");

        List<String> coupons = new ArrayList<>();
        coupons.add("SAVE10");
        coupons.add("EXPIRED_OLD");
        coupons.add("FREESHIP");
        coupons.add("EXPIRED_NEW");

        // Safe removal while iterating internally (no ConcurrentModificationException)
        coupons.removeIf(code -> code.startsWith("EXPIRED"));
        System.out.println("Active coupons: " + coupons);
    }

    /**
     * Scenario: Apply 10% price increase to all menu items in-place.
     */
    private static void replaceAllScenario() {
        System.out.println("\n[Scenario B] replaceAll(UnaryOperator):");

        List<Integer> prices = new ArrayList<>();
        prices.add(100);
        prices.add(250);
        prices.add(399);

        prices.replaceAll(price -> (int) Math.round(price * 1.10));
        System.out.println("Updated prices: " + prices);
    }

    /**
     * Scenario: User session cache keyed by userId.
     */
    private static void mapComputeScenario() {
        System.out.println("\n[Scenario C] Map compute methods:");

        Map<String, Integer> loginCountByUser = new ConcurrentHashMap<>();

        // Only compute value if key absent (thread-safe for ConcurrentHashMap)
        loginCountByUser.computeIfAbsent("aman", id -> 0);
        loginCountByUser.computeIfPresent("aman", (id, count) -> count + 1);
        loginCountByUser.computeIfPresent("aman", (id, count) -> count + 1);

        System.out.println("Login count for aman: " + loginCountByUser.get("aman"));

        loginCountByUser.forEach((user, count) ->
                System.out.println("User " + user + " logins=" + count));
    }

    /**
     * Edge case note: forEach cannot break early like enhanced-for with break.
     */
    private static void forEachVsEnhancedForScenario() {
        System.out.println("\n[Scenario D] forEach limitation (no break):");

        List<Integer> ids = List.of(101, 102, 103, 104);

        /*
         * forEach always visits all elements.
         * If you need "stop at first match", use stream.findFirst or classic loop.
         */
        ids.forEach(id -> {
            if (id == 103) {
                System.out.println("Found 103 but forEach still continues...");
            }
        });

        System.out.println("Use stream short-circuit or loop when early exit is required.");
    }

    public static void main(String[] args) {
        demo();
    }
}

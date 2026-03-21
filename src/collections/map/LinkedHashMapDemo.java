package collections.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LinkedHashMap deep-dive:
 *
 * 1) What it is:
 *    - HashMap + linked list of entries.
 *    - Keeps predictable order while still giving near O(1) average ops.
 *
 * 2) Ordering modes:
 *    - Insertion-order (default): items stay in order of insertion.
 *    - Access-order (optional): recently accessed item moves to end.
 *      Useful for LRU cache style behavior.
 *
 * 3) Null support:
 *    - Like HashMap: one null key, many null values allowed.
 *
 * 4) Thread safety:
 *    - Not thread-safe.
 */
public class LinkedHashMapDemo {

    public static void demo() {
        System.out.println("\n--- LINKEDHASHMAP DEMO ---");

        // Default constructor -> insertion-order iteration.
        Map<String, Integer> scoreByPlayer = new LinkedHashMap<>();
        scoreByPlayer.put("Aman", 40);
        scoreByPlayer.put("Priya", 60);
        scoreByPlayer.put("Ravi", 50);
        scoreByPlayer.put("Neha", 70);

        System.out.println("Insertion-order map: " + scoreByPlayer);

        // Update value of existing key: key position remains same in insertion mode.
        scoreByPlayer.put("Ravi", 55);
        System.out.println("After updating Ravi score: " + scoreByPlayer);

        // Access-order demo:
        // true means order changes when get/put is called on existing key.
        LinkedHashMap<String, Integer> recentlyAccessedOrder =
                new LinkedHashMap<>(16, 0.75f, true);

        recentlyAccessedOrder.put("Page-A", 1);
        recentlyAccessedOrder.put("Page-B", 2);
        recentlyAccessedOrder.put("Page-C", 3);

        System.out.println("Access-order initial: " + recentlyAccessedOrder);
        recentlyAccessedOrder.get("Page-A"); // moves Page-A to end
        recentlyAccessedOrder.get("Page-B"); // moves Page-B to end
        System.out.println("After accessing A, then B: " + recentlyAccessedOrder);
    }

    public static void main(String[] args) {
        demo();
    }
}

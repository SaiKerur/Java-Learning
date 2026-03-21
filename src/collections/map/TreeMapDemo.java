package collections.map;

import java.util.Map;
import java.util.TreeMap;

/**
 * TreeMap deep-dive:
 *
 * 1) What it is:
 *    - Sorted map implementation.
 *    - Stores keys in natural order (or custom Comparator order).
 *
 * 2) Performance:
 *    - put/get/remove are O(log n), not O(1).
 *    - Usually slower than HashMap for plain lookup, but gives sorted data.
 *
 * 3) Null support:
 *    - Does NOT allow null keys (throws NullPointerException).
 *    - Allows null values.
 *
 * 4) Useful methods:
 *    - firstKey(), lastKey()
 *    - higherKey(), lowerKey()
 *    - subMap(), headMap(), tailMap()
 */
public class TreeMapDemo {

    public static void demo() {
        System.out.println("\n--- TREEMAP DEMO ---");

        TreeMap<Integer, String> rankToName = new TreeMap<>();
        rankToName.put(3, "Ravi");
        rankToName.put(1, "Aman");
        rankToName.put(4, "Neha");
        rankToName.put(2, "Priya");

        // Output is sorted by key automatically: 1,2,3,4
        System.out.println("Sorted map by key: " + rankToName);

        // Navigation-like operations
        System.out.println("firstKey: " + rankToName.firstKey());
        System.out.println("lastKey : " + rankToName.lastKey());
        System.out.println("higherKey(2): " + rankToName.higherKey(2)); // next greater key
        System.out.println("lowerKey(2) : " + rankToName.lowerKey(2));  // next smaller key

        // Range view: keys [2,4) means 2 included, 4 excluded.
        Map<Integer, String> sub = rankToName.subMap(2, 4);
        System.out.println("subMap(2,4): " + sub);

        // TreeMap allows null value.
        rankToName.put(5, null);
        System.out.println("After adding key 5 with null value: " + rankToName);

        // NOTE:
        // rankToName.put(null, "X"); -> would throw NullPointerException.
    }

    public static void main(String[] args) {
        demo();
    }
}

package collections.map;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap deep-dive (simple words):
 *
 * 1) What it is:
 *    - A key-value data structure.
 *    - Very fast for put/get/remove in average case: O(1).
 *
 * 2) Ordering:
 *    - Does NOT guarantee insertion order.
 *    - Iteration order can look random and may change.
 *
 * 3) Null support:
 *    - Allows one null key.
 *    - Allows multiple null values.
 *
 * 4) Thread safety:
 *    - NOT thread-safe.
 *    - For concurrent writes, prefer ConcurrentHashMap.
 *
 * 5) Internal idea:
 *    - Uses hashing (bucket indexing by key hash).
 *    - If many keys collide, operations can degrade.
 */
public class HashMapDemo {

    public static void demo() {
        System.out.println("\n--- HASHMAP DEMO ---");

        // Generic form: Map<KeyType, ValueType>
        Map<Integer, String> employeeById = new HashMap<>();

        // put(key, value): adds new or updates existing key.
        employeeById.put(101, "Aman");
        employeeById.put(102, "Priya");
        employeeById.put(103, "Ravi");

        // Duplicate key updates value, old value is replaced.
        employeeById.put(103, "Ravi Kumar");

        // HashMap supports null key and null values.
        employeeById.put(null, "Unknown Employee");
        employeeById.put(104, null);

        System.out.println("Complete map: " + employeeById);

        // get(key): returns value or null if key absent.
        System.out.println("Employee with id 102: " + employeeById.get(102));
        System.out.println("Employee with id 999: " + employeeById.get(999)); // null

        // containsKey / containsValue checks existence.
        System.out.println("Contains key 101? " + employeeById.containsKey(101));
        System.out.println("Contains value Priya? " + employeeById.containsValue("Priya"));

        // remove(key): deletes entry by key.
        employeeById.remove(104);
        System.out.println("After removing key 104: " + employeeById);

        // Iteration over entries (key-value pairs).
        System.out.println("Iterating entries:");
        for (Map.Entry<Integer, String> entry : employeeById.entrySet()) {
            System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

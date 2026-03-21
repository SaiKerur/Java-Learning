package collections.map;

import java.util.Hashtable;
import java.util.Map;

/**
 * Hashtable deep-dive:
 *
 * 1) What it is:
 *    - Legacy synchronized key-value class (older than HashMap).
 *
 * 2) Thread safety:
 *    - Methods are synchronized, so single operations are thread-safe.
 *    - But this full-object locking can reduce performance under heavy concurrency.
 *
 * 3) Null support:
 *    - Does NOT allow null key.
 *    - Does NOT allow null value.
 *    - Null attempts throw NullPointerException.
 *
 * 4) Modern guidance:
 *    - Usually prefer ConcurrentHashMap for concurrent programs.
 *    - You still see Hashtable in legacy code and interviews.
 */
public class HashtableDemo {

    public static void demo() {
        System.out.println("\n--- HASHTABLE DEMO ---");

        Map<String, String> countryCode = new Hashtable<>();
        countryCode.put("India", "IN");
        countryCode.put("United States", "US");
        countryCode.put("Japan", "JP");

        System.out.println("Hashtable entries: " + countryCode);
        System.out.println("Code for India: " + countryCode.get("India"));

        // Update existing key value.
        countryCode.put("Japan", "JPN");
        System.out.println("After updating Japan code: " + countryCode);

        // Hashtable forbids null key/value.
        try {
            countryCode.put(null, "XX");
        } catch (NullPointerException e) {
            System.out.println("Null key is not allowed in Hashtable.");
        }

        try {
            countryCode.put("Germany", null);
        } catch (NullPointerException e) {
            System.out.println("Null value is not allowed in Hashtable.");
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

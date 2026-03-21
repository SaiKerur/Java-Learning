package collections.map;

/**
 * Run this class to compare all major Map implementations:
 * - HashMap
 * - LinkedHashMap
 * - TreeMap
 * - Hashtable
 * - ConcurrentHashMap
 */
public class MapTypesMasterDemo {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" JAVA MAP TYPES MASTER DEMO");
        System.out.println("========================================");

        printComparisonGuide();

        HashMapDemo.demo();
        LinkedHashMapDemo.demo();
        TreeMapDemo.demo();
        HashtableDemo.demo();
        ConcurrentHashMapDemo.demo();

        System.out.println("\nLearning tip:");
        System.out.println("Pick requirement first, then map type:");
        System.out.println("- fastest average lookup -> HashMap");
        System.out.println("- maintain insertion/access order -> LinkedHashMap");
        System.out.println("- keep keys sorted -> TreeMap");
        System.out.println("- legacy synchronized code -> Hashtable");
        System.out.println("- modern multi-threaded map -> ConcurrentHashMap");
    }

    private static void printComparisonGuide() {
        System.out.println("\nQuick comparison:");
        System.out.println("- HashMap         : unordered, allows null key/value, not thread-safe");
        System.out.println("- LinkedHashMap   : ordered (insertion/access), allows null, not thread-safe");
        System.out.println("- TreeMap         : sorted by key, null key not allowed, not thread-safe");
        System.out.println("- Hashtable       : unordered, null key/value not allowed, synchronized");
        System.out.println("- ConcurrentHashMap: unordered, null key/value not allowed, highly concurrent");
    }
}

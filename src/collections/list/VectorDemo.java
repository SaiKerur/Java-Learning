package collections.list;

import java.util.Vector;

/**
 * Vector:
 * - Legacy dynamic array (older than ArrayList).
 * - Methods are synchronized (thread-safe for single operations).
 * - Usually slower than ArrayList in single-threaded programs.
 * - Use in modern code only when synchronization behavior is needed.
 */
public class VectorDemo {

    public static void demo() {
        System.out.println("\n--- VECTOR DEMO ---");

        Vector<Integer> marks = new Vector<>();

        marks.add(82);
        marks.add(90);
        marks.add(76);
        marks.add(90); // duplicate allowed

        System.out.println("Initial vector: " + marks);

        // Insert at index.
        marks.add(1, 88);
        System.out.println("After add at index 1: " + marks);

        // Update element by index.
        marks.set(2, 95);
        System.out.println("After set index 2 -> 95: " + marks);

        // Capacity is internal array size; size is elements count.
        System.out.println("Size: " + marks.size());
        System.out.println("Capacity (internal): " + marks.capacity());

        // Remove by value and index.
        marks.remove(Integer.valueOf(90));
        marks.remove(0);
        System.out.println("After removals: " + marks);
    }

    public static void main(String[] args) {
        demo();
    }
}

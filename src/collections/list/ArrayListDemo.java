package collections.list;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayList:
 * - Backed by a dynamic array.
 * - Fast random access by index (get/set).
 * - Slower insert/remove in middle (shifting required).
 * - Allows duplicates and null values.
 */
public class ArrayListDemo {

    public static void demo() {
        System.out.println("\n--- ARRAYLIST DEMO ---");

        // Prefer interface type on left side for flexible coding style.
        List<String> students = new ArrayList<>();

        // Add elements at end (amortized O(1)).
        students.add("Aman");
        students.add("Priya");
        students.add("Ravi");
        students.add("Priya"); // duplicate allowed
        students.add(null);    // null is allowed in ArrayList

        System.out.println("Initial list: " + students);

        // Access by index is fast in ArrayList.
        System.out.println("Element at index 1: " + students.get(1));

        // Insert in middle: elements on right shift one step.
        students.add(2, "Neha");
        System.out.println("After adding Neha at index 2: " + students);

        // Remove by value (removes first match).
        students.remove("Priya");
        System.out.println("After removing first Priya: " + students);

        // Remove by index.
        students.remove(0);
        System.out.println("After removing index 0: " + students);

        System.out.println("Final size: " + students.size());
    }

    public static void main(String[] args) {
        demo();
    }
}

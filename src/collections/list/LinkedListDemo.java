package collections.list;

import java.util.LinkedList;

/**
 * LinkedList:
 * - Doubly-linked list implementation.
 * - Fast add/remove at first/last positions.
 * - Slower random index access (traversal needed).
 * - Also works like Queue/Deque because of first/last operations.
 */
public class LinkedListDemo {

    public static void demo() {
        System.out.println("\n--- LINKEDLIST DEMO ---");

        LinkedList<String> tasks = new LinkedList<>();

        // Add at end.
        tasks.add("Read topic");
        tasks.add("Write notes");
        tasks.add("Solve examples");
        System.out.println("Initial tasks: " + tasks);

        // Add at beginning and end efficiently.
        tasks.addFirst("Wake up");
        tasks.addLast("Sleep");
        System.out.println("After addFirst/addLast: " + tasks);

        // Queue-like behavior.
        System.out.println("peekFirst: " + tasks.peekFirst());
        System.out.println("peekLast : " + tasks.peekLast());

        // Remove from beginning and end efficiently.
        String firstDone = tasks.removeFirst();
        String lastDone = tasks.removeLast();
        System.out.println("Completed first: " + firstDone + ", last: " + lastDone);
        System.out.println("After removeFirst/removeLast: " + tasks);

        // Random index access works, but is not as fast as ArrayList.
        System.out.println("Element at index 1: " + tasks.get(1));
    }

    public static void main(String[] args) {
        demo();
    }
}

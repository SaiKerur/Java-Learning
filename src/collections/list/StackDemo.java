package collections.list;

import java.util.Stack;

/**
 * Stack:
 * - LIFO (Last In, First Out) structure.
 * - Extends Vector.
 * - Main operations: push, pop, peek.
 *
 * Example use cases:
 * - Undo/redo
 * - Browser back history
 * - Expression evaluation
 */
public class StackDemo {

    public static void demo() {
        System.out.println("\n--- STACK DEMO ---");

        Stack<String> history = new Stack<>();

        // push -> place element on top.
        history.push("google.com");
        history.push("youtube.com");
        history.push("github.com");
        System.out.println("After pushes: " + history);

        // peek -> view top element, do not remove.
        System.out.println("Current page (peek): " + history.peek());

        // pop -> remove and return top element.
        String backTo = history.pop();
        System.out.println("Popped page: " + backTo);
        System.out.println("After pop: " + history);

        // Search returns 1-based position from top, or -1 if absent.
        System.out.println("Position of google.com from top: " + history.search("google.com"));
    }

    public static void main(String[] args) {
        demo();
    }
}

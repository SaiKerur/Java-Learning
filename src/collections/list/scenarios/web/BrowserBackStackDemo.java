package collections.list.scenarios.web;

import java.util.Stack;

/**
 * Scenario: Browser back navigation.
 *
 * Why Stack here?
 * - Back history follows LIFO:
 *   last visited page is the first one to go back to.
 */
public class BrowserBackStackDemo {

    public static void demo() {
        System.out.println("\n--- SCENARIO: BROWSER BACK HISTORY (STACK) ---");

        Stack<String> backHistory = new Stack<>();

        // User visits pages.
        backHistory.push("home");
        backHistory.push("products");
        backHistory.push("product/42");
        backHistory.push("checkout");

        System.out.println("Current page: " + backHistory.peek());
        System.out.println("History stack: " + backHistory);

        // User clicks Back twice.
        String previous = backHistory.pop();
        System.out.println("Back from: " + previous + ", now on: " + backHistory.peek());

        previous = backHistory.pop();
        System.out.println("Back from: " + previous + ", now on: " + backHistory.peek());
    }

    public static void main(String[] args) {
        demo();
    }
}

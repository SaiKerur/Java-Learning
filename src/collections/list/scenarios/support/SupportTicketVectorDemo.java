package collections.list.scenarios.support;

import java.util.Vector;

/**
 * Scenario: Shared support ticket list in older thread-based systems.
 *
 * Why Vector here?
 * - Legacy synchronized list.
 * - Safer for simple concurrent modifications per operation.
 * - Useful when maintaining older codebases that already use Vector.
 */
public class SupportTicketVectorDemo {

    public static void demo() {
        System.out.println("\n--- SCENARIO: SUPPORT TICKETS (VECTOR) ---");

        Vector<String> tickets = new Vector<>();

        tickets.add("T-1001: Login issue");
        tickets.add("T-1002: Payment failed");
        tickets.add("T-1003: Profile update bug");

        System.out.println("Open tickets: " + tickets);
        System.out.println("Size: " + tickets.size() + ", Capacity: " + tickets.capacity());

        // Insert high-priority issue near top.
        tickets.add(1, "T-1004: Server down (High Priority)");
        System.out.println("After priority insert: " + tickets);

        // Resolve one ticket by value.
        tickets.remove("T-1002: Payment failed");
        System.out.println("After resolving payment issue: " + tickets);
    }

    public static void main(String[] args) {
        demo();
    }
}

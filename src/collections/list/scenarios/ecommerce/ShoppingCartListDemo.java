package collections.list.scenarios.ecommerce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Scenario: E-commerce cart and order flow.
 *
 * Demonstrates:
 * - ArrayList for cart items (mostly reads/append).
 * - LinkedList for shipping checkpoints (front removal).
 * - Read-only list for safe exposure to UI/services.
 */
public class ShoppingCartListDemo {

    public static void demo() {
        System.out.println("\n--- SCENARIO: SHOPPING CART + DELIVERY FLOW ---");

        List<String> cartItems = new ArrayList<>();
        cartItems.add("Laptop");
        cartItems.add("Mouse");
        cartItems.add("Keyboard");
        System.out.println("Cart items: " + cartItems);

        // Expose read-only view to prevent accidental modification.
        List<String> readOnlyCart = Collections.unmodifiableList(cartItems);
        System.out.println("Read-only cart view: " + readOnlyCart);

        // If you need to modify safely, create a new copy.
        List<String> editableCopy = new ArrayList<>(readOnlyCart);
        editableCopy.add("USB Hub");
        System.out.println("Editable copy after adding item: " + editableCopy);
        System.out.println("Original cart still unchanged: " + cartItems);

        // LinkedList for step-by-step shipment state.
        LinkedList<String> deliverySteps = new LinkedList<>();
        deliverySteps.add("Packed");
        deliverySteps.add("Shipped");
        deliverySteps.add("Out for delivery");
        deliverySteps.add("Delivered");

        System.out.println("Delivery flow: " + deliverySteps);
        while (!deliverySteps.isEmpty()) {
            System.out.println("Processing step: " + deliverySteps.removeFirst());
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

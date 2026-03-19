package oops;

/**
 * Run this class to see all major OOP principles one-by-one.
 *
 * Why this file exists:
 * - Gives one place to run all demos.
 * - Prints plain-English explanations before each section.
 * - Helps compare concepts clearly.
 */
public class OOPSMasterDemo {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" OOPS MASTER DEMO (Simple + In-Depth)");
        System.out.println("========================================");

        printQuickDifferenceTable();

        System.out.println("\n1) ENCAPSULATION");
        System.out.println("Data is kept private; safe methods control updates.");
        EncapsulationExample.demo();

        System.out.println("\n2) INHERITANCE");
        System.out.println("Child classes reuse parent features and add specific behavior.");
        InheritanceExample.demo();

        System.out.println("\n3) ABSTRACTION");
        System.out.println("Expose required behavior, hide internal implementation details.");
        AbstractionExample.demo();

        System.out.println("\n4) POLYMORPHISM");
        System.out.println("Same method name behaves differently by context/object type.");
        PolymorphismExample.demo();

        System.out.println("\n5) INTERFACE");
        System.out.println("A contract that different classes can implement in different ways.");
        InterfaceExample.demo();

        System.out.println("\n6) COMPOSITION");
        System.out.println("One object is built using another object (has-a relationship).");
        CompositionExample.demo();

        System.out.println("\nLearning tip:");
        System.out.println("Try changing values and adding new subclasses to observe behavior.");
        System.out.println("That is the fastest way to build strong OOP intuition.");
    }

    private static void printQuickDifferenceTable() {
        System.out.println("\nQuick comparison:");
        System.out.println("- Encapsulation: Protect data + controlled access");
        System.out.println("- Inheritance : Reuse code using is-a relationship");
        System.out.println("- Abstraction : Hide complexity, show essential operations");
        System.out.println("- Polymorphism: One interface/method, many behaviors");
        System.out.println("- Interface   : Pure contract of behavior");
        System.out.println("- Composition : Build objects using has-a relationship");
    }
}

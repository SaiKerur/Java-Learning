package java_8_features.method_references;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * METHOD REFERENCES (Java 8)
 * ==========================
 *
 * What are they?
 * - Shorthand when a lambda does nothing except call an existing method.
 * - Syntax uses :: operator.
 *
 * Four common forms:
 * 1) Static method reference:       ClassName::staticMethod
 *    Example:  s -> Integer.parseInt(s)  =>  Integer::parseInt
 *
 * 2) Instance method on particular object:
 *    Example:  s -> printer.print(s)     =>  printer::print
 *
 * 3) Instance method on arbitrary object (first param becomes receiver):
 *    Example:  s -> s.toLowerCase()      =>  String::toLowerCase
 *
 * 4) Constructor reference:           ClassName::new
 *    Example:  () -> new ArrayList<>()  =>  ArrayList::new
 *
 * When NOT to use method references:
 * - If extra logic is needed beyond one method call, keep lambda for readability.
 *
 * Relationship with functional interfaces:
 * - Method reference must match SAM signature of target functional interface.
 */
public class MethodReferencesDemo {

    public static void demo() {
        System.out.println("\n--- METHOD REFERENCES DEMO ---");

        staticMethodReferenceScenario();
        instanceOnSpecificObjectScenario();
        instanceOnArbitraryObjectScenario();
        constructorReferenceScenario();
        constructorWithArgsScenario();
        comparisonWithLambdaScenario();
    }

    /**
     * Scenario: Parse user-entered numeric strings in form validation.
     */
    private static void staticMethodReferenceScenario() {
        System.out.println("\n[Scenario A] Static method reference (ClassName::staticMethod):");

        List<String> inputs = Arrays.asList("10", "20", "abc");

        // Lambda form:
        // inputs.stream().map(s -> Integer.parseInt(s)) ...

        List<Integer> parsed = inputs.stream()
                .filter(MethodReferencesDemo::isNumeric) // static method in this class
                .map(Integer::parseInt)                  // Integer.parseInt(String)
                .collect(Collectors.toList());

        System.out.println("Parsed integers: " + parsed);
    }

    private static boolean isNumeric(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Scenario: Delegate printing to a specific logger object.
     */
    private static void instanceOnSpecificObjectScenario() {
        System.out.println("\n[Scenario B] Bound instance reference (object::instanceMethod):");

        Logger logger = new Logger("APP");
        List<String> events = List.of("LOGIN", "LOGOUT", "PASSWORD_RESET");

        // Lambda: events.forEach(msg -> logger.info(msg));
        events.forEach(logger::info);
    }

    /**
     * Scenario: Normalize product names before saving to DB.
     */
    private static void instanceOnArbitraryObjectScenario() {
        System.out.println("\n[Scenario C] Unbound instance reference (Type::instanceMethod):");

        List<String> productNames = Arrays.asList("  Laptop ", "KEYBOARD", " mouse ");

        // String::trim equivalent to s -> s.trim()
        // String::toLowerCase equivalent to s -> s.toLowerCase()
        List<String> cleaned = productNames.stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        System.out.println("Cleaned names: " + cleaned);

        // String::compareToIgnoreCase as Comparator method reference
        List<String> sorted = new ArrayList<>(cleaned);
        sorted.sort(String::compareToIgnoreCase);
        System.out.println("Sorted names: " + sorted);
    }

    /**
     * Scenario: Factory pattern creating empty collections lazily.
     */
    private static void constructorReferenceScenario() {
        System.out.println("\n[Scenario D] Constructor reference with zero args (Supplier):");

        // Supplier<List<String>> factory = () -> new ArrayList<>();
        Supplier<List<String>> factory = ArrayList::new;

        List<String> cart = factory.get();
        cart.add("Book");
        cart.add("Pen");
        System.out.println("Cart from constructor ref: " + cart);
    }

    /**
     * Scenario: Build Employee objects from name and salary pairs.
     */
    private static void constructorWithArgsScenario() {
        System.out.println("\n[Scenario E] Constructor reference with args (BiFunction):");

        // BiFunction<String, Integer, Employee> creator = (n, s) -> new Employee(n, s);
        BiFunction<String, Integer, Employee> creator = Employee::new;

        Employee e1 = creator.apply("Aman", 60000);
        Employee e2 = creator.apply("Priya", 75000);
        System.out.println(e1);
        System.out.println(e2);
    }

    private static void comparisonWithLambdaScenario() {
        System.out.println("\n[Scenario F] Lambda vs method reference readability:");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);

        // Good method reference: direct existing method mapping
        List<String> asString1 = numbers.stream().map(String::valueOf).collect(Collectors.toList());

        // Better lambda when extra logic exists
        List<String> asString2 = numbers.stream()
                .map(n -> "NUM-" + n)
                .collect(Collectors.toList());

        System.out.println("valueOf ref: " + asString1);
        System.out.println("custom lambda: " + asString2);
    }

    static class Logger {
        private final String appName;

        Logger(String appName) {
            this.appName = appName;
        }

        void info(String message) {
            System.out.println("[" + appName + "][INFO] " + message);
        }
    }

    static class Employee {
        final String name;
        final int salary;

        Employee(String name, int salary) {
            this.name = name;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Employee{name='" + name + "', salary=" + salary + "}";
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

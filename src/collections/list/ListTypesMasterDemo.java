package collections.list;

/**
 * Run this class to understand major Java List types quickly.
 *
 * Included:
 * - ArrayList
 * - LinkedList
 * - Vector
 * - Stack
 */
public class ListTypesMasterDemo {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" JAVA LIST TYPES MASTER DEMO");
        System.out.println("========================================");

        printQuickGuide();

        ArrayListDemo.demo();
        LinkedListDemo.demo();
        VectorDemo.demo();
        StackDemo.demo();

        System.out.println("\nPractice idea:");
        System.out.println("Change same task with each list type and observe differences in behavior/performance.");
    }

    private static void printQuickGuide() {
        System.out.println("\nWhen to use what?");
        System.out.println("- ArrayList : frequent reads by index, mostly append operations");
        System.out.println("- LinkedList: frequent insert/remove at start/end");
        System.out.println("- Vector    : legacy synchronized list");
        System.out.println("- Stack     : LIFO operations (push/pop/peek)");
    }
}

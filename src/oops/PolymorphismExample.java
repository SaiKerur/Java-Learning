package oops;

/**
 * Polymorphism means "many forms".
 *
 * Two common types:
 * 1) Compile-time polymorphism -> Method overloading (same method name, different parameters).
 * 2) Runtime polymorphism -> Method overriding (parent reference, child object).
 */
public class PolymorphismExample {

    // Compile-time polymorphism (overloading).
    private static class MathHelper {
        public int add(int a, int b) {
            return a + b;
        }

        public int add(int a, int b, int c) {
            return a + b + c;
        }
    }

    // Runtime polymorphism (overriding).
    private static class Animal {
        public void speak() {
            System.out.println("Animal makes a sound.");
        }
    }

    private static class Dog extends Animal {
        @Override
        public void speak() {
            System.out.println("Dog says: Woof!");
        }
    }

    private static class Cat extends Animal {
        @Override
        public void speak() {
            System.out.println("Cat says: Meow!");
        }
    }

    public static void demo() {
        System.out.println("\n--- POLYMORPHISM DEMO ---");

        MathHelper helper = new MathHelper();
        System.out.println("Overloading -> add(2, 3) = " + helper.add(2, 3));
        System.out.println("Overloading -> add(2, 3, 4) = " + helper.add(2, 3, 4));

        Animal animal1 = new Dog();
        Animal animal2 = new Cat();
        Animal animal3 = new Animal();

        System.out.println("Overriding at runtime:");
        animal1.speak();
        animal2.speak();
        animal3.speak();
    }

    public static void main(String[] args) {
        demo();
    }
}

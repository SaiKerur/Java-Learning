package oops;

/**
 * Inheritance means:
 * A child class reuses fields/methods of a parent class,
 * then adds its own specific behavior.
 *
 * Real-world idea:
 * Car and Bike are both Vehicles, so they share common properties.
 */
public class InheritanceExample {

    private static class Vehicle {
        protected final String brand;
        protected final int maxSpeed;

        public Vehicle(String brand, int maxSpeed) {
            this.brand = brand;
            this.maxSpeed = maxSpeed;
        }

        public void start() {
            System.out.println(brand + " vehicle is starting...");
        }

        public void stop() {
            System.out.println(brand + " vehicle is stopping...");
        }
    }

    private static class Car extends Vehicle {
        private final int numberOfDoors;

        public Car(String brand, int maxSpeed, int numberOfDoors) {
            super(brand, maxSpeed);
            this.numberOfDoors = numberOfDoors;
        }

        public void openTrunk() {
            System.out.println(brand + " car trunk opened.");
        }

        public void showDetails() {
            System.out.println("Car -> brand: " + brand + ", maxSpeed: " + maxSpeed + ", doors: " + numberOfDoors);
        }
    }

    private static class Bike extends Vehicle {
        private final boolean hasCarrier;

        public Bike(String brand, int maxSpeed, boolean hasCarrier) {
            super(brand, maxSpeed);
            this.hasCarrier = hasCarrier;
        }

        public void ringBell() {
            System.out.println(brand + " bike bell: tring tring!");
        }

        public void showDetails() {
            System.out.println("Bike -> brand: " + brand + ", maxSpeed: " + maxSpeed + ", carrier: " + hasCarrier);
        }
    }

    public static void demo() {
        System.out.println("\n--- INHERITANCE DEMO ---");

        Car car = new Car("Toyota", 220, 4);
        Bike bike = new Bike("Hero", 80, true);

        car.start();
        car.showDetails();
        car.openTrunk();
        car.stop();

        bike.start();
        bike.showDetails();
        bike.ringBell();
        bike.stop();
    }

    public static void main(String[] args) {
        demo();
    }
}

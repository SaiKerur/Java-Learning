package oops;

/**
 * Composition means:
 * One class contains another class as a part.
 *
 * Real-world idea:
 * A Car "has an" Engine.
 * Car behavior depends on Engine, but they are separate classes.
 */
public class CompositionExample {

    private static class Engine {
        private final String engineType;
        private boolean running;

        public Engine(String engineType) {
            this.engineType = engineType;
            this.running = false;
        }

        public void start() {
            running = true;
            System.out.println(engineType + " engine started.");
        }

        public void stop() {
            running = false;
            System.out.println(engineType + " engine stopped.");
        }

        public boolean isRunning() {
            return running;
        }
    }

    private static class Car {
        private final String model;
        private final Engine engine;

        public Car(String model, Engine engine) {
            this.model = model;
            this.engine = engine;
        }

        public void startCar() {
            System.out.println(model + " is trying to start...");
            engine.start();
        }

        public void stopCar() {
            System.out.println(model + " is stopping...");
            engine.stop();
        }

        public void carStatus() {
            System.out.println(model + " running status: " + engine.isRunning());
        }
    }

    public static void demo() {
        System.out.println("\n--- COMPOSITION DEMO ---");

        Engine engine = new Engine("Petrol");
        Car car = new Car("Honda City", engine);

        car.carStatus();
        car.startCar();
        car.carStatus();
        car.stopCar();
        car.carStatus();
    }

    public static void main(String[] args) {
        demo();
    }
}

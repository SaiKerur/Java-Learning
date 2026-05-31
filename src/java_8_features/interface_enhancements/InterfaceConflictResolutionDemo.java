package java_8_features.interface_enhancements;

/**
 * INTERFACE CONFLICT RESOLUTION (Java 8)
 * ====================================
 *
 * Rules when multiple inherited defaults clash:
 * 1) Class override wins over interface default.
 * 2) If two interfaces provide same default, implementing class MUST override.
 * 3) Inside override, syntax: InterfaceName.super.methodName()
 *
 * Also covers:
 * - Class method vs interface default (class wins)
 * - Re-declaring abstract method in subinterface can force implementer to implement again
 */
public class InterfaceConflictResolutionDemo {

    public static void demo() {
        System.out.println("\n--- INTERFACE CONFLICT RESOLUTION DEMO ---");

        diamondOverrideScenario();
        explicitSuperCallScenario();
        classMethodOverridesDefaultScenario();
    }

    private static void diamondOverrideScenario() {
        System.out.println("\n[Scenario A] class resolves conflicting defaults:");

        PdfReportExporter exporter = new PdfReportExporter();
        exporter.export();
    }

    private static void explicitSuperCallScenario() {
        System.out.println("\n[Scenario B] InterfaceName.super.method():");

        PdfReportExporter exporter = new PdfReportExporter();
        exporter.exportWithBothDefaults();
    }

    private static void classMethodOverridesDefaultScenario() {
        System.out.println("\n[Scenario C] class method beats interface default:");

        LegacyPrinter printer = new LegacyPrinter();
        printer.print();
    }

    interface Exportable {
        default void export() {
            System.out.println("Exportable default export");
        }
    }

    interface Compressible {
        default void export() {
            System.out.println("Compressible default export");
        }
    }

    static class PdfReportExporter implements Exportable, Compressible {
        @Override
        public void export() {
            System.out.println("PdfReportExporter resolved export()");
        }

        void exportWithBothDefaults() {
            Exportable.super.export();
            Compressible.super.export();
        }
    }

    interface Printable {
        default void print() {
            System.out.println("Printable default");
        }
    }

    static class LegacyPrinter implements Printable {
        public void print() {
            System.out.println("LegacyPrinter class print()");
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

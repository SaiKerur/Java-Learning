package java_8_features;

import java_8_features.collectors.CollectorsAdvancedScenariosDemo;
import java_8_features.collectors.CollectorsDemo;
import java_8_features.collectors.CollectorsToMapEdgeCasesDemo;
import java_8_features.date_time.DateTimeBusinessRulesDemo;
import java_8_features.date_time.DateTimeParsingPitfallsDemo;
import java_8_features.date_time.JavaTimeApiDemo;
import java_8_features.functional_interfaces.BuiltInFunctionalInterfacesDemo;
import java_8_features.functional_interfaces.FunctionalInterfacesDemo;
import java_8_features.functional_interfaces.PrimitiveFunctionalInterfacesDemo;
import java_8_features.interface_enhancements.DefaultAndStaticMethodsDemo;
import java_8_features.interface_enhancements.InterfaceConflictResolutionDemo;
import java_8_features.interface_enhancements.InterfaceInheritanceRulesDemo;
import java_8_features.lambda.LambdaCollectionsApiDemo;
import java_8_features.lambda.LambdaExpressionsDemo;
import java_8_features.lambda.LambdaPitfallsEdgeCasesDemo;
import java_8_features.method_references.MethodReferenceInStreamsDemo;
import java_8_features.method_references.MethodReferencePitfallsDemo;
import java_8_features.method_references.MethodReferencesDemo;
import java_8_features.optional.OptionalChainingEdgeCasesDemo;
import java_8_features.optional.OptionalDemo;
import java_8_features.optional.OptionalWithStreamsDemo;
import java_8_features.streams.ParallelStreamsDemo;
import java_8_features.streams.StreamApiDemo;
import java_8_features.streams.StreamEdgeCasesDemo;
import java_8_features.streams.StreamLazyEvaluationDemo;
import java_8_features.streams.StreamShortCircuitDemo;

/**
 * Run all Java 8 learning demos (core + extended scenarios + edge cases).
 * See Java_8_Features.md in project root for full theory guide.
 */
public class Java8FeaturesMasterDemo {

    public static void main(String[] args) {
        System.out.println("====================================================");
        System.out.println(" JAVA 8 FEATURES — MASTER DEMO (Core + Extended)");
        System.out.println("====================================================");
        System.out.println("Theory guide: Java_8_Features.md (project root)");

        runSection("1. LAMBDA EXPRESSIONS", () -> {
            LambdaExpressionsDemo.demo();
            LambdaCollectionsApiDemo.demo();
            LambdaPitfallsEdgeCasesDemo.demo();
        });

        runSection("2. FUNCTIONAL INTERFACES", () -> {
            FunctionalInterfacesDemo.demo();
            PrimitiveFunctionalInterfacesDemo.demo();
            BuiltInFunctionalInterfacesDemo.demo();
        });

        runSection("3. METHOD REFERENCES", () -> {
            MethodReferencesDemo.demo();
            MethodReferenceInStreamsDemo.demo();
            MethodReferencePitfallsDemo.demo();
        });

        runSection("4. STREAM API", () -> {
            StreamApiDemo.demo();
            StreamShortCircuitDemo.demo();
            StreamLazyEvaluationDemo.demo();
            StreamEdgeCasesDemo.demo();
            ParallelStreamsDemo.demo();
        });

        runSection("5. OPTIONAL", () -> {
            OptionalDemo.demo();
            OptionalChainingEdgeCasesDemo.demo();
            OptionalWithStreamsDemo.demo();
        });

        runSection("6. INTERFACE ENHANCEMENTS", () -> {
            DefaultAndStaticMethodsDemo.demo();
            InterfaceConflictResolutionDemo.demo();
            InterfaceInheritanceRulesDemo.demo();
        });

        runSection("7. java.time API", () -> {
            JavaTimeApiDemo.demo();
            DateTimeParsingPitfallsDemo.demo();
            DateTimeBusinessRulesDemo.demo();
        });

        runSection("8. COLLECTORS", () -> {
            CollectorsDemo.demo();
            CollectorsToMapEdgeCasesDemo.demo();
            CollectorsAdvancedScenariosDemo.demo();
        });

        System.out.println("\n--- Done ---");
        System.out.println("Tip: run individual class main() methods for focused practice.");
    }

    private static void runSection(String title, Runnable runnable) {
        System.out.println("\n====================================================");
        System.out.println(title);
        System.out.println("====================================================");
        runnable.run();
    }
}

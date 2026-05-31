package java_8_features.interface_enhancements;

/**
 * INTERFACE INHERITANCE RULES (Java 8+)
 * =====================================
 *
 * Key loopholes closed by rules:
 * - You cannot have two defaults with same signature without class resolution.
 * - Static methods in interfaces are not inherited by implementers.
 * - Default methods are inherited; implementer may override.
 * - If parent interface re-abstracts a default method, child class must implement it.
 */
public class InterfaceInheritanceRulesDemo {

    public static void demo() {
        System.out.println("\n--- INTERFACE INHERITANCE RULES DEMO ---");

        staticNotInheritedScenario();
        defaultInheritedScenario();
        reabstractedMethodScenario();
    }

    private static void staticNotInheritedScenario() {
        System.out.println("\n[Rule A] static interface methods are not inherited:");

        // Valid: called on interface
        String version = AppConfig.version();
        System.out.println("Config version: " + version);

        AppConfigImpl impl = new AppConfigImpl();
        impl.loadDefaults();
        // impl.version(); // compile error - static method not inherited
    }

    private static void defaultInheritedScenario() {
        System.out.println("\n[Rule B] default methods inherited by implementer:");

        AppConfigImpl impl = new AppConfigImpl();
        impl.loadDefaults();
        impl.printProfile();
    }

    private static void reabstractedMethodScenario() {
        System.out.println("\n[Rule C] child interface can redeclare abstract method:");

        SecureUserServiceImpl service = new SecureUserServiceImpl();
        service.authenticate("aman", "secret");
    }

    interface AppConfig {
        static String version() {
            return "1.0.0";
        }

        default void loadDefaults() {
            System.out.println("Loading default configuration...");
        }

        default void printProfile() {
            System.out.println("Profile: default-app");
        }
    }

    static class AppConfigImpl implements AppConfig {
        // inherits defaults; may override if needed
    }

    interface AuthService {
        default boolean authenticate(String user, String pass) {
            System.out.println("Default auth for " + user);
            return false;
        }
    }

    interface SecureAuthService extends AuthService {
        @Override
        boolean authenticate(String user, String pass); // re-abstracted => must implement
    }

    interface SecureUserService extends SecureAuthService {
    }

    static class SecureUserServiceImpl implements SecureUserService {
        @Override
        public boolean authenticate(String user, String pass) {
            System.out.println("Secure auth for " + user);
            return "secret".equals(pass);
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

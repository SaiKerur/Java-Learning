package java_8_features.date_time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

/**
 * java.time PARSING PITFALLS
 * ==========================
 *
 * Edge cases:
 * - Wrong pattern letters (MM vs mm, yyyy vs YY)
 * - Lenient parsing accepting invalid dates (Feb 31)
 * - Locale affecting month/day names
 * - User input not trimmed
 */
public class DateTimeParsingPitfallsDemo {

    public static void demo() {
        System.out.println("\n--- java.time PARSING PITFALLS DEMO ---");

        patternLetterTrapScenario();
        strictVsLenientScenario();
        localeMonthParsingScenario();
        safeParseUtilityScenario();
    }

    /**
     * Loophole: mm means minutes, MM means month.
     */
    private static void patternLetterTrapScenario() {
        System.out.println("\n[Edge A] pattern letter traps:");

        DateTimeFormatter dateOnly = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate valid = LocalDate.parse("31-05-2026", dateOnly);
        System.out.println("Parsed date: " + valid);

        System.out.println("Remember: yyyy=year, MM=month, dd=day, HH=hour(24), mm=minute, ss=second");
    }

    /**
     * Strict resolver rejects impossible dates.
     */
    private static void strictVsLenientScenario() {
        System.out.println("\n[Edge B] strict resolver rejects invalid calendar dates:");

        DateTimeFormatter lenient = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter strict = new DateTimeFormatterBuilder()
                .appendPattern("dd-MM-yyyy")
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT);

        String invalid = "31-02-2026"; // Feb 31 does not exist

        try {
            LocalDate.parse(invalid, lenient);
            System.out.println("Lenient may resolve invalid date unexpectedly.");
        } catch (DateTimeParseException ex) {
            System.out.println("Lenient failed: " + ex.getMessage());
        }

        try {
            LocalDate.parse(invalid, strict);
        } catch (DateTimeParseException ex) {
            System.out.println("Strict correctly failed for Feb 31.");
        }
    }

    private static void localeMonthParsingScenario() {
        System.out.println("\n[Scenario C] locale-specific month names:");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
        LocalDate parsed = LocalDate.parse("15-May-2026", formatter);
        System.out.println("Parsed with English locale: " + parsed);
    }

    /**
     * Scenario: Defensive parse for UI form submission.
     */
    private static void safeParseUtilityScenario() {
        System.out.println("\n[Scenario D] safe parse helper:");

        System.out.println("Input ' 31-05-2026 ' => " + safeParseDate(" 31-05-2026 "));
        System.out.println("Input '31/05/2026'   => " + safeParseDate("31/05/2026"));
        System.out.println("Input 'bad'          => " + safeParseDate("bad"));
    }

    private static String safeParseDate(String input) {
        if (input == null) {
            return "invalid";
        }
        String trimmed = input.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            return LocalDate.parse(trimmed, formatter).toString();
        } catch (DateTimeParseException ex) {
            return "invalid";
        }
    }

    public static void main(String[] args) {
        demo();
    }
}

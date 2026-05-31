package java_8_features.date_time;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * java.time BUSINESS RULES & EDGE CASES
 * =====================================
 *
 * Scenarios:
 * - Next working day
 * - EMI due reminder window
 * - Leap year age check
 * - Injecting Clock for testability (loophole: LocalDate.now() hard to test)
 */
public class DateTimeBusinessRulesDemo {

    public static void demo() {
        System.out.println("\n--- java.time BUSINESS RULES DEMO ---");

        nextWorkingDayScenario();
        emiReminderWindowScenario();
        leapYearScenario();
        clockInjectionScenario();
        betweenUnitsScenario();
    }

    private static void nextWorkingDayScenario() {
        System.out.println("\n[Scenario A] next working day:");

        LocalDate friday = LocalDate.of(2026, 5, 29); // Friday
        LocalDate nextWorkingDay = friday.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        System.out.println("Friday -> next working day: " + nextWorkingDay);
    }

    private static void emiReminderWindowScenario() {
        System.out.println("\n[Scenario B] EMI reminder 3 days before due:");

        LocalDate dueDate = LocalDate.of(2026, 6, 10);
        LocalDate today = LocalDate.of(2026, 6, 7);

        long daysUntilDue = ChronoUnit.DAYS.between(today, dueDate);
        boolean sendReminder = daysUntilDue == 3;

        System.out.println("Days until due: " + daysUntilDue);
        System.out.println("Send reminder today? " + sendReminder);
    }

    private static void leapYearScenario() {
        System.out.println("\n[Scenario C] leap year edge (Feb 29):");

        LocalDate dob = LocalDate.of(2000, 2, 29); // leap day
        LocalDate today = LocalDate.of(2026, 5, 31);

        long years = ChronoUnit.YEARS.between(dob, today);
        System.out.println("Years between (approx rule): " + years);
        System.out.println("For exact age, use Period.between(dob, today).");
    }

    /**
     * Loophole fix: inject Clock instead of calling now() directly everywhere.
     */
    private static void clockInjectionScenario() {
        System.out.println("\n[Scenario D] Clock injection for testability:");

        Clock fixedClock = Clock.fixed(
                LocalDateTime.of(2026, 1, 1, 10, 0).atZone(ZoneId.of("Asia/Kolkata")).toInstant(),
                ZoneId.of("Asia/Kolkata")
        );

        LocalDate today = LocalDate.now(fixedClock);
        System.out.println("Deterministic 'today' for tests: " + today);
    }

    private static void betweenUnitsScenario() {
        System.out.println("\n[Edge E] choose correct unit in between():");
        LocalDate start = LocalDate.of(2026, 1, 1);
        LocalDate end = LocalDate.of(2026, 1, 31);

        System.out.println("Days between: " + ChronoUnit.DAYS.between(start, end));
        System.out.println("Months between: " + ChronoUnit.MONTHS.between(start, end));
        System.out.println("Use Period for human calendar difference, Duration for clock time.");
    }

    public static void main(String[] args) {
        demo();
    }
}

package java_8_features.date_time;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * java.time API (Java 8)
 * ======================
 *
 * Why replace old java.util.Date / Calendar?
 * - Date was mutable and confusing (month indexing, etc.).
 * - Calendar API verbose and not intuitive.
 * - Poor timezone handling and thread-safety concerns.
 *
 * Core design principles of java.time:
 * - Immutable objects (thread-safe by design)
 * - Clear separation of concerns:
 *     LocalDate      -> date only (2026-05-31)
 *     LocalTime      -> time only (14:30:00)
 *     LocalDateTime  -> date + time without zone
 *     ZonedDateTime  -> date + time + timezone
 *     Instant        -> point on UTC timeline (machine timestamps)
 *
 * Common use cases:
 * - Birth date, invoice date -> LocalDate
 * - Meeting schedule in one city -> ZonedDateTime
 * - API logs / event ordering -> Instant
 * - Human-readable formatting/parsing -> DateTimeFormatter
 */
public class JavaTimeApiDemo {

    private static final DateTimeFormatter INDIAN_DATE =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter INDIAN_DATE_TIME =
            DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");

    public static void demo() {
        System.out.println("\n--- java.time API DEMO ---");

        localDateScenario();
        localTimeScenario();
        localDateTimeScenario();
        periodAndDurationScenario();
        zonedDateTimeScenario();
        instantScenario();
        parsingAndFormattingScenario();
        businessRulesScenario();
    }

    /**
     * Scenario: Student date of birth and age calculation.
     */
    private static void localDateScenario() {
        System.out.println("\n[Scenario A] LocalDate:");

        LocalDate dob = LocalDate.of(2000, 5, 15);
        LocalDate today = LocalDate.now();

        Period age = Period.between(dob, today);
        System.out.println("DOB: " + dob);
        System.out.println("Age: " + age.getYears() + " years " + age.getMonths() + " months");

        LocalDate nextWeek = today.plusDays(7);
        System.out.println("Today + 7 days: " + nextWeek);
        System.out.println("Is weekend today? " + isWeekend(today));
    }

    private static boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    /**
     * Scenario: Office working hours check.
     */
    private static void localTimeScenario() {
        System.out.println("\n[Scenario B] LocalTime:");

        LocalTime shiftStart = LocalTime.of(9, 30);
        LocalTime shiftEnd = LocalTime.of(18, 0);
        LocalTime now = LocalTime.now();

        boolean inShift = !now.isBefore(shiftStart) && now.isBefore(shiftEnd);
        System.out.println("Shift: " + shiftStart + " to " + shiftEnd);
        System.out.println("Currently in shift? " + inShift);
    }

    /**
     * Scenario: Appointment booking timestamp (no timezone yet).
     */
    private static void localDateTimeScenario() {
        System.out.println("\n[Scenario C] LocalDateTime:");

        LocalDateTime appointment = LocalDateTime.of(2026, 6, 10, 15, 45);
        LocalDateTime reminder = appointment.minusHours(2);

        System.out.println("Appointment: " + appointment);
        System.out.println("Reminder at: " + reminder);
        System.out.println("Month value: " + appointment.getMonthValue()); // 1-12 (not zero-based)
    }

    /**
     * Scenario: Subscription period vs stopwatch duration.
     */
    private static void periodAndDurationScenario() {
        System.out.println("\n[Scenario D] Period vs Duration:");

        // Period: date-based (years/months/days)
        LocalDate planStart = LocalDate.of(2026, 1, 1);
        LocalDate planEnd = planStart.plusMonths(3);
        Period planPeriod = Period.between(planStart, planEnd);
        System.out.println("Plan length: " + planPeriod.getMonths() + " months");

        // Duration: time-based (hours/minutes/seconds/nanos)
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(90);
        Duration meetingDuration = Duration.between(start, end);
        System.out.println("Meeting duration minutes: " + meetingDuration.toMinutes());

        // ChronoUnit for direct arithmetic
        long daysBetween = ChronoUnit.DAYS.between(planStart, planEnd);
        System.out.println("Days between plan start/end: " + daysBetween);
    }

    /**
     * Scenario: Global meeting across India and US time zones.
     */
    private static void zonedDateTimeScenario() {
        System.out.println("\n[Scenario E] ZonedDateTime:");

        ZoneId india = ZoneId.of("Asia/Kolkata");
        ZoneId usEast = ZoneId.of("America/New_York");

        ZonedDateTime indiaMeeting = ZonedDateTime.of(
                LocalDateTime.of(2026, 7, 1, 10, 0), india);

        ZonedDateTime usView = indiaMeeting.withZoneSameInstant(usEast);

        System.out.println("India time: " + indiaMeeting);
        System.out.println("Same instant in US East: " + usView);
    }

    /**
     * Scenario: Event log timestamp in UTC instant.
     */
    private static void instantScenario() {
        System.out.println("\n[Scenario F] Instant (UTC timeline):");

        Instant eventTime = Instant.now();
        System.out.println("Event instant: " + eventTime);

        Instant later = eventTime.plusSeconds(30);
        System.out.println("30 seconds later: " + later);
    }

    /**
     * Scenario: UI date parsing and display formatting.
     */
    private static void parsingAndFormattingScenario() {
        System.out.println("\n[Scenario G] Parsing and formatting:");

        String userInput = "31-05-2026";
        LocalDate parsed = LocalDate.parse(userInput, INDIAN_DATE);
        System.out.println("Parsed date: " + parsed);

        LocalDateTime now = LocalDateTime.now();
        System.out.println("Formatted for UI: " + now.format(INDIAN_DATE_TIME));
    }

    /**
     * Scenario: EMI due date rule and overdue check.
     */
    private static void businessRulesScenario() {
        System.out.println("\n[Scenario H] Business rule with java.time:");

        LocalDate emiDueDate = LocalDate.of(2026, 5, 25);
        LocalDate paymentDate = LocalDate.of(2026, 5, 28);

        boolean overdue = paymentDate.isAfter(emiDueDate);
        long lateDays = ChronoUnit.DAYS.between(emiDueDate, paymentDate);

        System.out.println("EMI due: " + emiDueDate);
        System.out.println("Paid on: " + paymentDate);
        System.out.println("Overdue? " + overdue + ", late by days: " + lateDays);
    }

    public static void main(String[] args) {
        demo();
    }
}

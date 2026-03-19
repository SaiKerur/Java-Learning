package collections.list.scenarios.education;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Scenario: Manage student records in a coaching class.
 *
 * Why ArrayList here?
 * - Frequent iteration and index-based access.
 * - Mostly append operations.
 * - Simple and memory-efficient for sequential data.
 */
public class StudentManagementArrayListDemo {

    private static class Student {
        private final int rollNumber;
        private final String name;
        private final int marks;

        public Student(int rollNumber, String name, int marks) {
            this.rollNumber = rollNumber;
            this.name = name;
            this.marks = marks;
        }

        public int getMarks() {
            return marks;
        }

        @Override
        public String toString() {
            return "Student{roll=" + rollNumber + ", name='" + name + "', marks=" + marks + "}";
        }
    }

    public static void demo() {
        System.out.println("\n--- SCENARIO: STUDENT MANAGEMENT (ARRAYLIST) ---");

        List<Student> students = new ArrayList<>();

        // Add students at the end (common use-case).
        students.add(new Student(101, "Aman", 83));
        students.add(new Student(102, "Priya", 91));
        students.add(new Student(103, "Ravi", 77));
        students.add(new Student(104, "Neha", 95));

        System.out.println("All students: " + students);

        // Fast random access by index.
        System.out.println("Student at index 2: " + students.get(2));

        // Sort by marks descending for rank display.
        students.sort(Comparator.comparingInt(Student::getMarks).reversed());
        System.out.println("After sorting by marks (desc): " + students);

        // Remove failed student record by condition.
        students.removeIf(student -> student.getMarks() < 80);
        System.out.println("After removing students with marks < 80: " + students);
    }

    public static void main(String[] args) {
        demo();
    }
}

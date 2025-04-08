package org.maven.jdbc;

import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String DB_URL = "jdbc:mysql://localhost:3306/studentdb";
    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- Student Management System ---");
                System.out.println("1. Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Search Student by ID");
                System.out.println("4. Delete Student by ID");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");

                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter course: ");
                        String course = sc.nextLine();
                        addStudent(conn, name, course);
                    }
                    case 2 -> viewAllStudents(conn);
                    case 3 -> {
                        System.out.print("Enter student ID: ");
                        int id = sc.nextInt();
                        searchStudentById(conn, id);
                    }
                    case 4 -> {
                        System.out.print("Enter student ID to delete: ");
                        int id = sc.nextInt();
                        deleteStudentById(conn, id);
                    }
                    case 5 -> System.exit(0);
                    default -> System.out.println("Invalid choice!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addStudent(Connection conn, String name, String course) throws SQLException {
        String sql = "INSERT INTO students (name, course) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, course);
            stmt.executeUpdate();
            System.out.println("Student added successfully.");
        }
    }

    private static void viewAllStudents(Connection conn) throws SQLException {
        String sql = "SELECT * FROM students";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nID | Name | Course");
            while (rs.next()) {
                System.out.printf("%d | %s | %s\n", rs.getInt("id"), rs.getString("name"), rs.getString("course"));
            }
        }
    }

    private static void searchStudentById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Student Found: " + rs.getString("name") + " - " + rs.getString("course"));
                } else {
                    System.out.println("No student found with ID: " + id);
                }
            }
        }
    }

    private static void deleteStudentById(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Student deleted successfully.");
            else System.out.println("No student found with ID: " + id);
        }
    }
}


package org.file;

import org.file.service.TaskService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskService service = new TaskService();
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("=== Todo App ===");

        do {
            System.out.println("\n1. Add Task\n2. View Tasks\n3. Mark as Complete\n4. Delete Task\n5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter task description: ");
                    service.addTask(scanner.nextLine());
                    break;
                case 2:
                    service.viewTasks();
                    break;
                case 3:
                    System.out.print("Enter task number to mark complete: ");
                    service.completeTask(scanner.nextInt() - 1);
                    break;
                case 4:
                    System.out.print("Enter task number to delete: ");
                    service.deleteTask(scanner.nextInt() - 1);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        scanner.close();
    }
}

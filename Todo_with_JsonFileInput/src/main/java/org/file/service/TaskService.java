package org.file.service;
import org.file.model.Task;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private final String FILE_NAME = "tasks.json";
    private final ObjectMapper mapper = new ObjectMapper();

    public TaskService() {
        loadTasks();
    }

    public void addTask(String description) {
        tasks.add(new Task(description));
        saveTasks();
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void completeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).setCompleted(true);
            saveTasks();
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            saveTasks();
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private void saveTasks() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_NAME), tasks);
        } catch (Exception e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private void loadTasks() {
        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                List<Task> loadedTasks = mapper.readValue(file, new TypeReference<List<Task>>() {});
                tasks.addAll(loadedTasks);
            }
        } catch (Exception e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }
}




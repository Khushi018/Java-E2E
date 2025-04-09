package org.file.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Task {
    private String description;
    private boolean completed;

    @Override
    public String toString() {
        return (completed ? "[✓] " : "[ ] ") + description;
    }
    public Task(String description) {
        this.description = description;
        this.completed = false;
    }
}


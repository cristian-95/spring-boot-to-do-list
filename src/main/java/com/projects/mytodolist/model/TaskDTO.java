package com.projects.mytodolist.model;

import java.time.LocalDateTime;

public record TaskDTO(
        String title,
        String description,
        LocalDateTime createdDate,
        Boolean completed
) {
    public TaskDTO {
        if (title == null) {
            throw new IllegalArgumentException("Toda tarefa deve ter um titulo.");
        }
        if (completed == null) {
            completed = false;
        }
        if (createdDate == null) {
            createdDate = LocalDateTime.now();
        }
    }
}

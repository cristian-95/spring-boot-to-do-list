package com.projects.mytodolist.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskDTO(
        @NotNull
        @Size(min = 4, max = 30)
        String title,
        String description
) {
}

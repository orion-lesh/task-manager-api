package com.orion_lesh.taskmanager.dto.request;

import com.orion_lesh.taskmanager.entity.enums.TaskPriority;
import com.orion_lesh.taskmanager.entity.enums.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateTaskRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "title must not exceed 200 characters")
        String title,

        @Size(max = 5000, message = "Description must not exceed 5000 characters")
        String description,

        @NotNull(message = "Status is required")
        TaskStatus status,

        @NotNull(message = "Priority is required")
        TaskPriority priority,

        @FutureOrPresent(message = "Due date must be today or in the future")
        LocalDate dueDate,

        Long categoryId
) {}
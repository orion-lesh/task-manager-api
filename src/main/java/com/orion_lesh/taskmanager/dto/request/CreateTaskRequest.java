package com.orion_lesh.taskmanager.dto.request;

import com.orion_lesh.taskmanager.entity.enums.TaskPriority;
import com.orion_lesh.taskmanager.entity.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateTaskRequest(

        @Schema(description = "Task title", example = "Learn Spring Boot")
        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must not exceed 200 characters")
        String title,

        @Schema(description = "Detailed description", example = "Read documentation and build a project")
        @Size(max = 5000, message = "Description must not exceed 5000 characters")
        String description,

        @Schema(description = "Current status", example = "TODO")
        @NotNull(message = "Status is required")
        TaskStatus status,

        @Schema(description = "Task priority", example = "HIGH")
        @NotNull(message = "Priority is required")
        TaskPriority priority,

        @Schema(description = "Due date (today or future)", example = "2026-12-31")
        @FutureOrPresent(message = "Due date must be today or in the future")
        LocalDate dueDate,

        @Schema(description = "Category id (optional)", example = "1")
        Long categoryId
) {}
package com.orion_lesh.taskmanager.dto.request;

import com.orion_lesh.taskmanager.entity.enums.TaskPriority;
import com.orion_lesh.taskmanager.entity.enums.TaskStatus;

import java.time.LocalDate;

public record TaskFilter(
        TaskStatus status,
        TaskPriority priority,
        Long categoryId,
        LocalDate dueDateFrom,
        LocalDate dueDateTo,
        String search
) {}
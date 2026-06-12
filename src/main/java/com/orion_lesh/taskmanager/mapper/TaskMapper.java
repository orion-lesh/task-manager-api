package com.orion_lesh.taskmanager.mapper;

import com.orion_lesh.taskmanager.dto.request.CreateTaskRequest;
import com.orion_lesh.taskmanager.dto.response.TaskResponse;
import com.orion_lesh.taskmanager.entity.Category;
import com.orion_lesh.taskmanager.entity.Task;

public final class TaskMapper {

    private TaskMapper() {}

    public static Task toEntity(CreateTaskRequest request, Category category) {
        return Task.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status())
                .priority(request.priority())
                .dueDate(request.dueDate())
                .category(category)
                .build();
    }

    public static TaskResponse toResponse(Task task) {
        if (task == null) {
            return null;
        }
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                CategoryMapper.toResponse(task.getCategory())
        );
    }
}

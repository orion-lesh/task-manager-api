package com.orion_lesh.taskmanager.service;

import com.orion_lesh.taskmanager.dto.request.CreateTaskRequest;
import com.orion_lesh.taskmanager.dto.request.UpdateTaskRequest;
import com.orion_lesh.taskmanager.dto.response.TaskResponse;
import com.orion_lesh.taskmanager.entity.Category;
import com.orion_lesh.taskmanager.entity.Task;
import com.orion_lesh.taskmanager.exception.ResourceNotFoundException;
import com.orion_lesh.taskmanager.mapper.TaskMapper;
import com.orion_lesh.taskmanager.repository.CategoryRepository;
import com.orion_lesh.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public TaskResponse create(CreateTaskRequest request) {
        Category category = resolveCategory(request.categoryId());
        Task task = TaskMapper.toEntity(request, category);
        Task saved = taskRepository.save(task);
        return TaskMapper.toResponse(saved);
    }

    public TaskResponse findById(Long id) {
        return TaskMapper.toResponse(getTaskOrThrow(id));
    }

    public Page<TaskResponse> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(TaskMapper::toResponse);
    }

    @Transactional
    public TaskResponse update(Long id, UpdateTaskRequest request) {
        Task task = getTaskOrThrow(id);

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());
        task.setPriority(request.priority());
        task.setDueDate(request.dueDate());
        task.setCategory(resolveCategory(request.categoryId()));


        return TaskMapper.toResponse(task);
    }

    @Transactional
    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task with id " + id + " not found");
        }
        taskRepository.deleteById(id);
    }

    private Task getTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task with id " + id + " not found"));
    }

    private Category resolveCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category with id " + categoryId + " not found"));
    }
}

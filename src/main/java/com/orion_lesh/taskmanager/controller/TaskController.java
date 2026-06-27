package com.orion_lesh.taskmanager.controller;

import com.orion_lesh.taskmanager.dto.request.CreateTaskRequest;
import com.orion_lesh.taskmanager.dto.request.TaskFilter;          // ← новый импорт
import com.orion_lesh.taskmanager.dto.request.UpdateTaskRequest;
import com.orion_lesh.taskmanager.dto.response.TaskResponse;
import com.orion_lesh.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> create(
            @Valid @RequestBody CreateTaskRequest request) {
        TaskResponse created = taskService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ОБНОВЛЁННЫЙ метод
    @GetMapping
    public Page<TaskResponse> findAll(
            @ModelAttribute TaskFilter filter,
            Pageable pageable) {
        return taskService.findAll(filter, pageable);
    }

    @GetMapping("/{id}")
    public TaskResponse findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PutMapping("/{id}")
    public TaskResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request) {
        return taskService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}

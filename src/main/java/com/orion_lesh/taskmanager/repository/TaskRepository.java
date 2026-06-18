package com.orion_lesh.taskmanager.repository;

import com.orion_lesh.taskmanager.entity.Task;
import com.orion_lesh.taskmanager.entity.enums.TaskPriority;
import com.orion_lesh.taskmanager.entity.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TaskRepository
        extends JpaRepository<Task, Long>,
        JpaSpecificationExecutor<Task> {

    @EntityGraph(attributePaths = {"category"})
    @Override
    Page<Task> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"category"})
    @Override
    Optional<Task> findById(Long id);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(TaskPriority priority);

    Page<Task> findByCategoryId(Long categoryId, Pageable pageable);

    long countByCategoryId(Long categoryId);
}
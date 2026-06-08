package com.orion_lesh.taskmanager.repository;

import com.orion_lesh.taskmanager.entity.Task;
import com.orion_lesh.taskmanager.entity.enums.TaskPriority;
import com.orion_lesh.taskmanager.entity.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskRepository
        extends JpaRepository<Task, Long>,
        JpaSpecificationExecutor<Task> {


    List<Task> findByStatus(TaskStatus status);


    List<Task> findByPriority(TaskPriority priority);


    Page<Task> findByCategoryId(Long categoryId, Pageable pageable);


    long countByCategoryId(Long categoryId);
}
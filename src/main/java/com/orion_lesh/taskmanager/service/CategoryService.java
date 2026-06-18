package com.orion_lesh.taskmanager.service;

import com.orion_lesh.taskmanager.dto.request.CreateCategoryRequest;
import com.orion_lesh.taskmanager.dto.request.UpdateCategoryRequest;
import com.orion_lesh.taskmanager.dto.response.CategoryResponse;
import com.orion_lesh.taskmanager.entity.Category;
import com.orion_lesh.taskmanager.exception.BusinessException;
import com.orion_lesh.taskmanager.exception.ResourceNotFoundException;
import com.orion_lesh.taskmanager.mapper.CategoryMapper;
import com.orion_lesh.taskmanager.repository.CategoryRepository;
import com.orion_lesh.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public CategoryResponse create(CreateCategoryRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new BusinessException(
                    "Category with name '" + request.name() + "' already exists");
        }
        Category saved = categoryRepository.save(CategoryMapper.toEntity(request));
        return CategoryMapper.toResponse(saved);
    }

    public CategoryResponse findById(Long id) {
        return CategoryMapper.toResponse(getCategoryOrThrow(id));
    }

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    @Transactional
    public CategoryResponse update(Long id, UpdateCategoryRequest request) {
        Category category = getCategoryOrThrow(id);


        if (!category.getName().equals(request.name())
                && categoryRepository.existsByName(request.name())) {
            throw new BusinessException(
                    "Category with name '" + request.name() + "' already exists");
        }

        category.setName(request.name());
        return CategoryMapper.toResponse(category);
    }

    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category with id " + id + " not found");
        }
        if (taskRepository.countByCategoryId(id) > 0) {
            throw new BusinessException(
                    "Cannot delete category — it has tasks attached");
        }
        categoryRepository.deleteById(id);
    }

    private Category getCategoryOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category with id " + id + " not found"));
    }
}

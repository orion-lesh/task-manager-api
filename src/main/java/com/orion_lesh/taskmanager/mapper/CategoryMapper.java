package com.orion_lesh.taskmanager.mapper;

import com.orion_lesh.taskmanager.dto.request.CreateCategoryRequest;
import com.orion_lesh.taskmanager.dto.response.CategoryResponse;
import com.orion_lesh.taskmanager.entity.Category;

public final class CategoryMapper {

    private CategoryMapper() {

    }

    public static Category toEntity(CreateCategoryRequest request) {
        return Category.builder()
                .name(request.name())
                .build();
    }

    public static CategoryResponse toResponse(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getCreatedAt()
        );
    }
}
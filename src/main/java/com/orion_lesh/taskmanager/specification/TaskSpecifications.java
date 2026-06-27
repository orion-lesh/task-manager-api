package com.orion_lesh.taskmanager.specification;

import com.orion_lesh.taskmanager.dto.request.TaskFilter;
import com.orion_lesh.taskmanager.entity.Task;
import org.springframework.data.jpa.domain.Specification;

public final class TaskSpecifications {

    private TaskSpecifications() {}

    public static Specification<Task> build(TaskFilter filter) {
        Specification<Task> spec = Specification.unrestricted();

        if (filter.status() != null) {
            spec = spec.and(hasStatus(filter));
        }
        if (filter.priority() != null) {
            spec = spec.and(hasPriority(filter));
        }
        if (filter.categoryId() != null) {
            spec = spec.and(hasCategoryId(filter));
        }
        if (filter.dueDateFrom() != null) {
            spec = spec.and(dueDateFrom(filter));
        }
        if (filter.dueDateTo() != null) {
            spec = spec.and(dueDateTo(filter));
        }
        if (filter.search() != null && !filter.search().isBlank()) {
            spec = spec.and(titleOrDescriptionContains(filter));
        }

        return spec;
    }

    private static Specification<Task> hasStatus(TaskFilter filter) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), filter.status());
    }

    private static Specification<Task> hasPriority(TaskFilter filter) {
        return (root, query, cb) ->
                cb.equal(root.get("priority"), filter.priority());
    }

    private static Specification<Task> hasCategoryId(TaskFilter filter) {
        return (root, query, cb) ->
                cb.equal(root.get("category").get("id"), filter.categoryId());
    }

    private static Specification<Task> dueDateFrom(TaskFilter filter) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("dueDate"), filter.dueDateFrom());
    }

    private static Specification<Task> dueDateTo(TaskFilter filter) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("dueDate"), filter.dueDateTo());
    }

    private static Specification<Task> titleOrDescriptionContains(TaskFilter filter) {
        return (root, query, cb) -> {
            String pattern = "%" + filter.search().toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("title")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
            );
        };
    }
}

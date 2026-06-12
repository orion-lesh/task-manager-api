package com.orion_lesh.taskmanager.dto.response;

import java.time.Instant;

public record CategoryResponse(
        Long id,
        String name,
        Instant createdAt
) {

}

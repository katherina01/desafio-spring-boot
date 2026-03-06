package com.katherina.nuevospa.taskmanagerapi.dto;

import com.katherina.nuevospa.taskmanagerapi.entity.User;

public record UserResponse(
        Long id,
        String name,
        String email
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}

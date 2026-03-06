package com.katherina.nuevospa.taskmanagerapi.dto;

import com.katherina.nuevospa.taskmanagerapi.entity.TaskStatus;

public record TaskStatusResponse(
        Long id,
        String name
) {
    public static TaskStatusResponse fromEntity(TaskStatus taskStatus) {
        return new TaskStatusResponse(taskStatus.getId(), taskStatus.getName());
    }
}

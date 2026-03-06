package com.katherina.nuevospa.taskmanagerapi.dto;

import com.katherina.nuevospa.taskmanagerapi.entity.Task;
import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        LocalDateTime createDate,
        LocalDateTime updateDate,
        TaskStatusResponse taskStatus,
        UserResponse user
) {
    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
        		task.getId(),
        		task.getTitle(),
        		task.getDescription(),
        		task.getCreateDate(),
        		task.getUpdateDate(),
                TaskStatusResponse.fromEntity(task.getStatus()),
                UserResponse.fromEntity(task.getUser())
        );
    }
}

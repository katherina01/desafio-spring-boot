package com.katherina.nuevospa.taskmanagerapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskRequest(
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 255, message = "El título no puede exceder 255 caracteres")
        String title,

        @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
        String description,

        @NotNull(message = "El estado de tarea es obligatorio")
        Long taskStatusId
) {
}

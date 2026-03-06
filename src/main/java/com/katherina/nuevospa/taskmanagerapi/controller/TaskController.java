package com.katherina.nuevospa.taskmanagerapi.controller;

import com.katherina.nuevospa.taskmanagerapi.dto.TaskRequest;
import com.katherina.nuevospa.taskmanagerapi.dto.TaskResponse;
import com.katherina.nuevospa.taskmanagerapi.entity.Task;
import com.katherina.nuevospa.taskmanagerapi.repository.TaskRepository;
import com.katherina.nuevospa.taskmanagerapi.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Tasks", description = "Operaciones CRUD de tareas")
public class TaskController {
	
	private final TaskService taskService;
	
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    // Obtener todas las tareas
    @GetMapping
    @Operation(summary = "Listar tareas", description = "Obtiene todas las tareas del usuario autenticado")
    public ResponseEntity<List<TaskResponse>> listTasks(Principal principal) {
        List<TaskResponse> tasks = taskService.listTasksByUser(principal.getName());
        return ResponseEntity.ok(tasks);
    }
    
    // Obtener tarea por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener tarea", description = "Obtiene una tarea por su ID")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id,
                                                      Principal principal) {
        TaskResponse task = taskService.getTask(id, principal.getName());
        return ResponseEntity.ok(task);
    }
    
    // Crear nueva tarea
    @PostMapping
    @Operation(summary = "Crear tarea", description = "Crea una nueva tarea")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request,
                                                    Principal principal) {
        TaskResponse task = taskService.createTask(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }
    
    // Actualizar tarea
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tarea", description = "Actualiza una tarea existente")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,
                                                         @Valid @RequestBody TaskRequest request,
                                                         Principal principal) {
        TaskResponse task = taskService.updateTask(id, request, principal.getName());
        return ResponseEntity.ok(task);
    }
    
    // Eliminar tarea
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tarea", description = "Elimina una tarea existente")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, Principal principal) {
        taskService.deleteTask(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
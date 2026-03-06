package com.katherina.nuevospa.taskmanagerapi.service;

import com.katherina.nuevospa.taskmanagerapi.dto.TaskRequest;
import com.katherina.nuevospa.taskmanagerapi.dto.TaskResponse;
import com.katherina.nuevospa.taskmanagerapi.entity.TaskStatus;
import com.katherina.nuevospa.taskmanagerapi.entity.Task;
import com.katherina.nuevospa.taskmanagerapi.entity.User;
import com.katherina.nuevospa.taskmanagerapi.exception.ResourceNotFoundException;
import com.katherina.nuevospa.taskmanagerapi.repository.TaskStatusRepository;
import com.katherina.nuevospa.taskmanagerapi.repository.TaskRepository;
import com.katherina.nuevospa.taskmanagerapi.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;

    public TaskService(TaskRepository taskRepository,
                        UserRepository userRepository,
                        TaskStatusRepository taskStatusRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskStatusRepository = taskStatusRepository;
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> listTasksByUser(String email) {
        User user = findUserByEmail(email);
        return taskRepository.findByUserId(user.getId()).stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse getTask(Long id, String email) {
        User user = findUserByEmail(email);
        Task task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tarea no encontrada con id: " + id));
        return TaskResponse.fromEntity(task);
    }
    
    public TaskResponse createTask(TaskRequest request, String email) {
    	User user = findUserByEmail(email);
        TaskStatus taskStatus = findTaskStatus(request.taskStatusId());

        Task tarea = new Task();
        tarea.setTitle(request.title());
        tarea.setDescription(request.description());
        tarea.setTaskStatus(taskStatus);
        tarea.setUser(user);

        Task saved = taskRepository.save(tarea);
        return TaskResponse.fromEntity(saved);
    }
    
    public TaskResponse updateTask(Long id, TaskRequest request, String email) {
    	User user = findUserByEmail(email);
    	Task task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tarea no encontrada con id: " + id));

    	TaskStatus estadoTarea = findTaskStatus(request.taskStatusId());

    	task.setTitle(request.title());
    	task.setDescription(request.description());
    	task.setTaskStatus(estadoTarea);

        Task updated = taskRepository.save(task);
        return TaskResponse.fromEntity(updated);
    }

    public void deleteTask(Long id, String email) {
    	User usuario = findUserByEmail(email);
    	Task tarea = taskRepository.findByIdAndUserId(id, usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tarea no encontrada con id: " + id));
    	taskRepository.delete(tarea);
    }
    
    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con email: " + email));
    }

    private TaskStatus findTaskStatus(Long id) {
        return taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Estado de tarea no encontrado con id: " + id));
    }
}

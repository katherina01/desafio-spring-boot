package com.katherina.nuevospa.taskmanagerapi.repository;

import com.katherina.nuevospa.taskmanagerapi.entity.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserId(Long userId);
    
    Optional<Task> findByIdAndUserId(Long id, Long usuarioId);

}
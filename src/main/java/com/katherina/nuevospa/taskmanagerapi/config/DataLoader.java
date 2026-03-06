package com.katherina.nuevospa.taskmanagerapi.config;

import com.katherina.nuevospa.taskmanagerapi.entity.TaskStatus;
import com.katherina.nuevospa.taskmanagerapi.entity.User;
import com.katherina.nuevospa.taskmanagerapi.repository.TaskStatusRepository;
import com.katherina.nuevospa.taskmanagerapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository usuarioRepository;
    private final TaskStatusRepository estadoTareaRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository usuarioRepository,
    				  TaskStatusRepository estadoTareaRepository,
                      PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.estadoTareaRepository = estadoTareaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        loadEstadosTarea();
        loadUsuarios();
    }

    private void loadEstadosTarea() {
        if (estadoTareaRepository.count() == 0) {
            estadoTareaRepository.save(new TaskStatus("PENDIENTE"));
            estadoTareaRepository.save(new TaskStatus("EN_PROGRESO"));
            estadoTareaRepository.save(new TaskStatus("COMPLETADA"));
        }
    }

    private void loadUsuarios() {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new User(
                    "Admin",
                    "admin@nuevospa.com",
                    passwordEncoder.encode("admin123")
            ));
            usuarioRepository.save(new User(
                    "Usuario Test",
                    "user@nuevospa.com",
                    passwordEncoder.encode("user123")
            ));
        }
    }
}

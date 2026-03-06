package com.katherina.nuevospa.taskmanagerapi.service;

import com.katherina.nuevospa.taskmanagerapi.dto.LoginRequest;
import com.katherina.nuevospa.taskmanagerapi.dto.LoginResponse;
import com.katherina.nuevospa.taskmanagerapi.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse authenticate(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        String token = jwtTokenProvider.generateToken(request.email());
        return new LoginResponse(token, request.email());
    }
}

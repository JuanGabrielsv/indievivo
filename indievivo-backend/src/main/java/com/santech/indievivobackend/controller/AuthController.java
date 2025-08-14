package com.santech.indievivobackend.controller;

import com.santech.indievivobackend.dto.JwtResponseDTO;
import com.santech.indievivobackend.dto.LoginRequestDTO;
import com.santech.indievivobackend.dto.MessageResponseDTO;
import com.santech.indievivobackend.dto.RegisterRequestDTO;
import com.santech.indievivobackend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // 1. Inyeccion de dependencias por constructor (práctica recomendada).
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 2. Endpoint para el login (Autenticacion).
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        // Delegamos toda la lógica al servicio
        JwtResponseDTO jwtResponse = authService.loginUser(loginRequestDTO);
        // Devuelve la respuesta del servicio con un estado 200 OK
        return ResponseEntity.ok(jwtResponse);
    }

    // 3. Endpoint para el Registro.
    @PostMapping("/register")
    public ResponseEntity<MessageResponseDTO> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        // Delegamos al servicio la lógica
        authService.registerUser(registerRequestDTO);
        // Si el servicio no lanza una excepción, devuelve un mensaje de éxito con un estado 200 OK
        return ResponseEntity.ok(new MessageResponseDTO("Usuario registrado exitosamente"));
    }
}
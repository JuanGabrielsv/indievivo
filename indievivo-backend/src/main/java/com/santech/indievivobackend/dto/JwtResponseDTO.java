package com.santech.indievivobackend.dto;

import java.util.List;

public record JwtResponseDTO(
        String token,
        Long id,
        String username,
        String email,
        List<String> roles) {}
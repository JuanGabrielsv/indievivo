package com.santech.indievivobackend.dto;

public record ErrorResponseDTO(
        int statusCode,
        String message,
        long timestamp) {
}

package com.santech.indievivobackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank
        @Size(min = 3, max = 50)
        String username,

        @NotBlank
        @Size(min = 8, max = 120)
        String password,

        @NotBlank
        @Size(max = 100)
        @Email
        String email,

        @NotBlank
        String nombre,

        @NotBlank
        String primerApellido,

        @NotBlank
        String segundoApellido,

        @NotBlank
        String ciudad
) {
}

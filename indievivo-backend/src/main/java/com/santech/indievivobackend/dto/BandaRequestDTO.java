package com.santech.indievivobackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record BandaRequestDTO(

        @NotBlank @Size(max = 100)
        String nombre,

        @NotBlank @Size(max = 2000)
        String descripcion,

        @NotBlank
        String ciudadOrigen,

        @Size(max = 255)
        String sitioWeb,

        @NotEmpty(message = "Debes seleccionar al menos un género musical")
        Set<String> generos
) {

}

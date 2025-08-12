package com.santech.indievivobackend.controller;

import com.santech.indievivobackend.dto.BandaRequestDTO;
import com.santech.indievivobackend.model.Banda;
import com.santech.indievivobackend.security.UserDetailsImpl;
import com.santech.indievivobackend.service.BandaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bandas")
public class BandaController {

    private final BandaService bandaService;

    public BandaController(BandaService bandaService) {
        this.bandaService = bandaService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Banda> createBanda(@Valid @RequestBody BandaRequestDTO bandaRequestDTO) {

        // 1. Obtenemos los datos del usuario autenticado.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();

        // 2. Llamamos al servicio para que haga la logica.
        Banda nuevaBanda = bandaService.createBanda(bandaRequestDTO, currentUser);

        //3. Devolvemos la entidad creada y un código de estado HTTP 201 Created.
        return new ResponseEntity<>(nuevaBanda, HttpStatus.CREATED);
    }
}

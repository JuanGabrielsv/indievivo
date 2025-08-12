package com.santech.indievivobackend.service;

import com.santech.indievivobackend.dto.BandaRequestDTO;
import com.santech.indievivobackend.exception.UserAlreadyExistsException;
import com.santech.indievivobackend.model.Banda;
import com.santech.indievivobackend.model.GeneroMusical;
import com.santech.indievivobackend.model.User;
import com.santech.indievivobackend.repository.BandaRepository;
import com.santech.indievivobackend.repository.GeneroMusicalRepository;
import com.santech.indievivobackend.repository.UserRepository;

import com.santech.indievivobackend.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BandaService {

    private final BandaRepository bandaRepository;
    private final UserRepository userRepository;
    private final GeneroMusicalRepository generoMusicalRepository;

    public BandaService(BandaRepository bandaRepository,
                        UserRepository userRepository,
                        GeneroMusicalRepository generoMusicalRepository) {
        this.bandaRepository = bandaRepository;
        this.userRepository = userRepository;
        this.generoMusicalRepository = generoMusicalRepository;
    }

    @Transactional(readOnly = true)
    public List<Banda> findAllBandas() {
        return bandaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Banda> findBandaById(Long id) {
        return bandaRepository.findById(id);
    }

    @Transactional
    public Banda createBanda(BandaRequestDTO bandaRequestDTO, UserDetailsImpl currentUser) {
        // 1.Comprobamos primero que no exista nunguna banda con el mismo nombre.
        if (bandaRepository.existsByNombre(bandaRequestDTO.nombre())) {
            throw new UserAlreadyExistsException("Error: Ya existe una banda con el nombre: '" +
                    bandaRequestDTO.nombre() +
                    "'.");
        }

        // 2. Obtener la entidad User completa del propietario.
        User propietario = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Error fatal: Usuario autenticado no encontrado en la BBDD. ID: " +
                                currentUser.getId()));

        // 3. Procesamos los generos musicales por su IDs.
        Set<GeneroMusical> generosMusicales = new HashSet<>();
        if (bandaRequestDTO.generos() != null && !bandaRequestDTO.generos().isEmpty()) {

            for (String nombreGenero : bandaRequestDTO.generos()) {
                GeneroMusical genero = generoMusicalRepository.findByNombre(nombreGenero)
                        .orElseThrow(() -> new RuntimeException("Error: El género '" +
                                nombreGenero +
                                "' no es válido o no existe"));
                generosMusicales.add(genero);
            }

        }

        // 4. Crear la nueva entidad Banda y asignar datos (sin cambios).
        Banda nuevaBanda = new Banda();
        nuevaBanda.setNombre(bandaRequestDTO.nombre());
        nuevaBanda.setDescripcion(bandaRequestDTO.descripcion());
        nuevaBanda.setCiudadOrigen(bandaRequestDTO.ciudadOrigen());
        nuevaBanda.setSitioWeb(bandaRequestDTO.sitioWeb());
        nuevaBanda.setPropietario(propietario);
        nuevaBanda.setGeneros(generosMusicales);

        //5. Guardar la banda.
        return bandaRepository.save(nuevaBanda);
    }
}

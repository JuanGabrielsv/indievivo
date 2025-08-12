package com.santech.indievivobackend.repository;

import com.santech.indievivobackend.model.GeneroMusical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneroMusicalRepository extends JpaRepository<GeneroMusical, Long> {

    Optional<GeneroMusical> findByNombre(String nombre);
}

package com.santech.indievivobackend.repository;

import com.santech.indievivobackend.model.Banda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BandaRepository extends JpaRepository<Banda, Long> {

    Boolean existsByNombre(String nombre);

}

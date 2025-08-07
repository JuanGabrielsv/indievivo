package com.santech.indievivobackend.repository;

import com.santech.indievivobackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Metodos para el LOGIN.
    Optional<User> findByEmail(String email);

    // Metodos para el REGISTRO (comprobar duplicados)
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}

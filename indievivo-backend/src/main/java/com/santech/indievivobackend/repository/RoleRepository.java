package com.santech.indievivobackend.repository;

import com.santech.indievivobackend.enums.ERole;
import com.santech.indievivobackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}

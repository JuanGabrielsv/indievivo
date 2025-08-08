package com.santech.indievivobackend;

import com.santech.indievivobackend.enums.ERole;
import com.santech.indievivobackend.model.Role;
import com.santech.indievivobackend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IndievivoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndievivoBackendApplication.class, args);
	}

    @Bean
    CommandLineRunner run(RoleRepository roleRepository) {
        return args -> {
            // Solo insertamos los roles si la tabla está vacía, para evitar duplicados en reinicios.
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role(ERole.ROLE_USER));
                roleRepository.save(new Role(ERole.ROLE_MODERATOR));
                roleRepository.save(new Role(ERole.ROLE_ADMIN));
                System.out.println(">>> Roles inicializados en la base de datos.");
            }
        };
    }

}

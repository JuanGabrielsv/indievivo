package com.santech.indievivobackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity // Habilita la seguridad web de Spring en nuestro proyecto. Es crucial.
@EnableMethodSecurity // Permite user anotaciones de seguridad en los metodos.
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Dehabilita la protección CSRF. Es seguro para APIs stateless con JWT.
                .csrf(AbstractHttpConfigurer::disable)

                // Establecemos la politica de sesion a STATELESS. No usaremos sesiones HTTP.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Definimos las reglas de autorización de las peticiones.
                .authorizeHttpRequests(auth -> auth
                        // Permitimos el acceso público (sin autentificaión) a cualquier ruta que empiece con /auth/
                        .requestMatchers("/auth/**").permitAll()
                        // Para cualquier otra petición, se requiere autenticación.
                        .anyRequest().authenticated());

        // Construimos y devolvemos la cadena de filtros configurada.
        return http.build();
    }
}


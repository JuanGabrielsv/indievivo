package com.santech.indievivobackend.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.santech.indievivobackend.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String username;
    private final String email;

    // Evitamos que la contraseña se seialize a JSON
    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    // Constructor privado para ser usado por el metodo build
    private UserDetailsImpl(Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // Metodo estatico "factory" para crear una instancia de UserDetailsImpl a partir de nuestra entidad User.

    public static UserDetailsImpl build(User user) {
        // 1. Convertimos el Set<Role> de nuestro User a una List<GrantedAuthority>
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

        // 2. Creamos y devolvemos la nueva instancia de UserDetailsImpl
        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
    }

    // --- Métodos de la interfaz UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Podríamos añadir lógica para que las cuentas expiren
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Podríamos añadir lógica para bloquear cuentas
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Podríamos añadir lógica para que las credenciales expiren
    }

    @Override
    public boolean isEnabled() {
        return true; // Podríamos añadir lógica para deshabilitar cuentas
    }

    // --- Getters adicionales y metodo equals/hashCode ---

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
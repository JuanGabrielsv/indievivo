package com.santech.indievivobackend.service;

import com.santech.indievivobackend.dto.JwtResponseDTO;
import com.santech.indievivobackend.dto.LoginRequestDTO;
import com.santech.indievivobackend.dto.RegisterRequestDTO;
import com.santech.indievivobackend.enums.ERole;
import com.santech.indievivobackend.exception.UserAlreadyExistsException;
import com.santech.indievivobackend.model.Role;
import com.santech.indievivobackend.model.User;
import com.santech.indievivobackend.repository.RoleRepository;
import com.santech.indievivobackend.repository.UserRepository;
import com.santech.indievivobackend.security.JwtUtils;
import com.santech.indievivobackend.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public JwtResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponseDTO(jwt,
                userDetails.getId(),
                userDetails.getUsernameReal(),
                userDetails.getEmail(),
                roles);
    }

    public void registerUser(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.existsByUsername(registerRequestDTO.username())) {
            throw new UserAlreadyExistsException("Error: El username ya está registrado.");
        }

        if (userRepository.existsByEmail(registerRequestDTO.email())) {
            throw new UserAlreadyExistsException("Error: El email ya está registrado.");
        }

        User user = new User(
                registerRequestDTO.username(),
                registerRequestDTO.email(),
                passwordEncoder.encode(registerRequestDTO.password()),
                registerRequestDTO.nombre(),
                registerRequestDTO.primerApellido(),
                registerRequestDTO.segundoApellido(),
                registerRequestDTO.ciudad()
        );

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Rol 'USER' no encontrado."));
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
    }
}
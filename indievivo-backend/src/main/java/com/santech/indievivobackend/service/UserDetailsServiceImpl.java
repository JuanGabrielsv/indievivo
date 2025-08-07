package com.santech.indievivobackend.service;

import com.santech.indievivobackend.model.User;
import com.santech.indievivobackend.repository.UserRepository;

import com.santech.indievivobackend.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Buscamos el usuario en la base de datos usando el repositorio
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No hay ningún usuario vinculado con el email: " + email));

        // Si se encuentra lo convertimos a un objeto UserDetails
        return UserDetailsImpl.build(user);
    }
}

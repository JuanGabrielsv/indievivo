package com.santech.indievivobackend.security;

import com.santech.indievivobackend.service.UserDetailsServiceImpl;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthTokenFilter extends OncePerRequestFilter {


    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    // Constructor para inyectar las dependencias
    public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("AuthTokenFilter: Petición recibida para la URL: {} {}", request.getMethod(), request.getRequestURI());

        try {
            // 1.Extraer el token JWT de la cabecera de la peticion
            String jwt = parseJwt(request);

            // 2.Si hay un token y es válido
            if (jwt != null && jwtUtils.isValidJwtToken(jwt)) {
                // 3.Extraer el email del token
                String email = jwtUtils.getEmailFromJwtToken(jwt);

                // 4.Cargar los detalles del usuario a partir del email
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // 5.Crear un objeto de autenticacion
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // 6.Establecer los detalles de la pericion actual en la autenticacion
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 7. Autenticar el usuario en el contexto de seguridad Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("No se pudo establecer la autenticación del usuario: {}", e.getMessage());
        }

        // 8. Continuar con el resto de la cadena de filtros
        filterChain.doFilter(request, response);
    }

    // Extrae el token JWT de la cabecera "Authorization"
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        // El token debe empezar con "Bearer"
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}

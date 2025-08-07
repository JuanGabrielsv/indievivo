package com.santech.indievivobackend.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // Cogemos los valores desde application.properties.
    @Value("${indievivo.app.jwtSecret}")
    private String jwtSecret;

    @Value("${indievivo.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    // Generamos un token JWT a partir de la informacion de autenticacion del usuario.
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().subject(userPrincipal.getUsername()) // En este caso el email
                .issuedAt(new Date()).expiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(key()).compact();
    }

    // Construye la clave secreta para firmar y validar el token.
    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Extrae el nombre del usuario (email) desde el token JWT.
    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    // Validar un token JWT, comprueba si está bien formado, si la firma es correcta y si no ha expirado.
    public boolean isValidJwtToken(String authToken) {

        try {
            Jwts.parser().verifyWith(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token JWT inválido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token JWT ha expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT no soportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("La cadena de claims del JWT está vacía: {}", e.getMessage());
        }
        return false;
    }
}
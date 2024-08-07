package com.uast.ms_auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.uast.ms_auth.entities.User;
import com.uast.ms_auth.exceptions.InvalidJWTException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JWTService {

    private final String SECRET = "my-secret";
    private final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    private final String ISSUER = "ms-auth";

    public String generateToken(User user) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getUsername())
                    .withExpiresAt(generateExpirationDate())
                    .sign(ALGORITHM);
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Error while generating JWT token!");
        }
    }

    public String validateTokenAndGetUsername(String token) {
        try {
            return JWT.require(ALGORITHM)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new InvalidJWTException();
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.ofHours(-3));
    }
}

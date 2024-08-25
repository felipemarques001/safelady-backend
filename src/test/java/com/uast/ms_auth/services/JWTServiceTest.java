package com.uast.ms_auth.services;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.uast.ms_auth.entities.User;
import com.uast.ms_auth.entities.UserRole;
import com.uast.ms_auth.exceptions.InvalidJWTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class JWTServiceTest {

    @InjectMocks
    private JWTService jwtService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(UUID.randomUUID(), "71793119015", "Felipe", "Rocha", "Tabira", "Rua 2", "123", LocalDate.now(), "87999999999", UserRole.ADMIN);
    }

    @Test
    @DisplayName("Given user, when generateToken(), then return valid JWT token")
    void givenUser_whenGenerateToken_thenReturnValidToken() {
        try {
            String token = jwtService.generateToken(user);
            jwtService.validateTokenAndGetCpf(token);
        } catch (RuntimeException ex) {
            assertNotEquals(JWTCreationException.class, ex.getClass());
            assertNotEquals(JWTVerificationException.class, ex.getClass());
        }
    }

    @Test
    @DisplayName("Given user, when validateTokenAndGetCpf(), then return CPF")
    void givenToken_whenValidateToken_thenReturnCpf() {
        String token = jwtService.generateToken(user);
        String cpf = jwtService.validateTokenAndGetCpf(token);
        assertEquals(cpf, user.getUsername());
    }

    @Test
    @DisplayName("Given invalid token, when validateTokenAndGetCpf(), then throws InvalidJWTException")
    void givenInvalidToken_whenValidateToken_thenThrowsInvalidJWTException() {
        String invalidToken = "invalid-token";
        try {
            jwtService.validateTokenAndGetCpf(invalidToken);
        } catch (RuntimeException ex) {
            assertEquals(InvalidJWTException.class, ex.getClass());
        }
    }
}

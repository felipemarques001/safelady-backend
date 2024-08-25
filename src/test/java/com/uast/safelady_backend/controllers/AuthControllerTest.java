package com.uast.safelady_backend.controllers;

import com.uast.safelady_backend.dtos.login.LoginReqDTO;
import com.uast.safelady_backend.dtos.login.LoginResDTO;
import com.uast.safelady_backend.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Given valid login data, when login(), then return JWT token")
    @Test
    void givenValidLogin_whenLogin_thenReturnJwtToken() {
        final LoginReqDTO loginReqDTO = new LoginReqDTO("71793119015", "123");
        final String jwtToken = "Bearer jwt-token";

        when(authService.loginUser(any(LoginReqDTO.class))).thenReturn(jwtToken);

        ResponseEntity<LoginResDTO> response = authController.login(loginReqDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(LoginResDTO.class, response.getBody().getClass());
        assertEquals(jwtToken, response.getBody().jwtToken());
    }
}

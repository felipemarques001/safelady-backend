package com.uast.ms_auth.controllers;

import com.uast.ms_auth.dtos.login.LoginReqDTO;
import com.uast.ms_auth.dtos.login.LoginResDTO;
import com.uast.ms_auth.services.AuthService;
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
import static org.mockito.Mockito.verify;
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

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getClass(), LoginResDTO.class);
        assertEquals(response.getBody().jwtToken(), jwtToken);
    }
}

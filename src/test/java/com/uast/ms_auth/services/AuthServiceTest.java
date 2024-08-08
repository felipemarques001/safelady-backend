package com.uast.ms_auth.services;

import com.uast.ms_auth.dtos.login.LoginReqDTO;
import com.uast.ms_auth.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    private LoginReqDTO loginReqDTO;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginReqDTO = new LoginReqDTO("71793119015", "123");
        user = new User();
    }

    @Test
    @DisplayName("Given loginReqDTO, when login(), then authenticate user and returns JWT token")
    void givenLoginReqDTO_whenLogin_thenAuthenticateUserAndReturnJWT() {
        final String jwtToken = "valid-JWT-token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(user, null, null));

        when(jwtService.generateToken(any(User.class))).thenReturn(jwtToken);

        String serviceRes = authService.loginUser(loginReqDTO);
        assertEquals(jwtToken, serviceRes);
    }
}

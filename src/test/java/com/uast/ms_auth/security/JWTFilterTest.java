package com.uast.ms_auth.security;

import com.uast.ms_auth.entities.User;
import com.uast.ms_auth.entities.UserRole;
import com.uast.ms_auth.repository.UserRepository;
import com.uast.ms_auth.services.JWTService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class JWTFilterTest {

    @InjectMocks
    private JWTFilter jwtFilter;

    @Mock
    private JWTService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private SecurityContext securityContext;

    private final User user = new User(UUID.randomUUID(), "71793119015", "Felipe", "Tabira", "Rua 2", "123", LocalDate.now(), "87999999999", UserRole.ADMIN);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @DisplayName("Given user, when doFilterInternal(), then authenticate user")
    @Test
    void givenUser_whenDoFilterInternal_thenAuthenticateUser() throws ServletException, IOException {
        final String jwtToken = "Bearer jwt-token";
        final String cpf = "71793119015";

        when(request.getHeader(anyString())).thenReturn(jwtToken);
        when(jwtService.validateTokenAndGetCpf(anyString())).thenReturn(cpf);
        when(userRepository.findByCpf(cpf)).thenReturn(user);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(securityContext).setAuthentication(any(Authentication.class));
        verify(filterChain).doFilter(request, response);
    }

    @DisplayName("Given not user, when doFilterInternal(), then don't call methods and call next filter")
    @Test
    void givenNotUser_whenDoFilterInternal_thenCallNextFilter() throws ServletException, IOException {
        when(request.getHeader(anyString())).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, never()).validateTokenAndGetCpf(anyString());
        verify(userRepository, never()).findByCpf(anyString());
        verify(securityContext, never()).setAuthentication(any(Authentication.class));
        verify(filterChain).doFilter(request, response);
    }
}

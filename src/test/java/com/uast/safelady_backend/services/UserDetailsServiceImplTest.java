package com.uast.safelady_backend.services;

import com.uast.safelady_backend.entities.User;
import com.uast.safelady_backend.entities.UserRole;
import com.uast.safelady_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    private final String CPF = "71793119015";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Given CPF, when loadUserByUsername(), then return UserDetails")
    @Test
    void givenCpf_whenLoadUserByUsername_thenReturnUserDetails() {
        UserDetails user = new User(UUID.randomUUID(), "71793119015", "Felipe", "Rocha", "Tabira", "Rua 2", "123", LocalDate.now(), "87999999999", UserRole.ADMIN);
        when(userRepository.findByCpf(anyString())).thenReturn(user);

        UserDetails userFounded = userDetailsService.loadUserByUsername(CPF);
        assertEquals(CPF, userFounded.getUsername());
    }

    @DisplayName("Given CPF, when loadUserByUsername(), then return null")
    @Test
    void givenCpf_whenLoadUserByUsername_thenReturnNull() {
        when(userRepository.findByCpf(anyString())).thenReturn(null);
        UserDetails userFounded = userDetailsService.loadUserByUsername(CPF);
        assertNull(userFounded);
    }
}

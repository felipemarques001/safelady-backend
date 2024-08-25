package com.uast.ms_auth.services;

import com.uast.ms_auth.dtos.user.CreateUserReqDTO;
import com.uast.ms_auth.entities.User;
import com.uast.ms_auth.exceptions.FieldAlreadyInUseException;
import com.uast.ms_auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private CreateUserReqDTO createUserReqDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createUserReqDTO = new CreateUserReqDTO("71793119015", "Felipe", "Rocha", "Tabira", "Rua 2", "123", "87999999999", "2002-09-09");
    }

    @DisplayName("Given valid CPF, when saveUser(), then calls userRepository.save() and password.encode()")
    @Test
    void givenValidCpf_whenSaveUser_tenCallsRepositoryAndPasswordEncode() {
        when(userRepository.findByCpf(anyString())).thenReturn(null);

        userService.saveUser(createUserReqDTO);

        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(any(User.class));
    }

    @DisplayName("Given invalid CPF, when saveUser(), then throws FieldAlreadyInUseException")
    @Test
    void givenInvalidCpf_whenSaveUser_thenThrowsException() {
        final String dtoCpf = createUserReqDTO.cpf();

        when(userRepository.findByCpf(anyString())).thenReturn(new User());

        try {
            userService.saveUser(createUserReqDTO);
        } catch (RuntimeException ex) {
            assertEquals(FieldAlreadyInUseException.class, ex.getClass());
            assertEquals("Error in the field 'CPF', the value '" + dtoCpf + "' is already in use!", ex.getMessage());
        }
    }
}

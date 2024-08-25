package com.uast.ms_auth.controllers;

import com.uast.ms_auth.dtos.user.CreateUserReqDTO;
import com.uast.ms_auth.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private CreateUserReqDTO createUserReqDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createUserReqDTO = new CreateUserReqDTO("71793119015", "Felipe", "Rocha", "Tabira", "Rua 2", "123", "87999999999", "2002-09-09");
    }

    @DisplayName("Given CreateUserReqDTO, when createUser(), then return a 201 response")
    @Test
    void givenDTO_whenCreateUser_thenReturn201Res() {
        ResponseEntity<Void> response = userController.createUser(createUserReqDTO);

        verify(userService).saveUser(createUserReqDTO);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }
}

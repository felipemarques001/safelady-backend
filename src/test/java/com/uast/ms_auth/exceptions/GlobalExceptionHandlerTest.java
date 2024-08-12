package com.uast.ms_auth.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler handler;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Give FieldAlreadyInUseException, when handleFieldAlreadyInUseException(), then return 400 response with error description")
    @Test
    void giveException_whenHandleFieldAlreadyInUseException_thenReturn400Response() {
        final String fieldName = "CPF";
        final String fieldValue = "71793119015";

        final FieldAlreadyInUseException ex = new FieldAlreadyInUseException(fieldName, fieldValue);

        ResponseEntity<Map<String, String>> response = handler.handleFieldAlreadyInUseException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().containsKey(fieldName.toLowerCase()));
        assertEquals(ex.getMessage(), response.getBody().get(fieldName.toLowerCase()));
    }

    @DisplayName("Given ObjectError list, when handleArgumentNotValidException(), then return 400 response with errors description")
    @Test
    void givenErrorList_whenHandleArgumentNotValidException_thenReturn400Response() {
        final List<ObjectError> errors = Arrays.asList(
                new FieldError("email", "email", "Invalid email format"),
                new FieldError("password", "password", "Password is too short")
        );

        Mockito.when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        Mockito.when(methodArgumentNotValidException.getBindingResult().getAllErrors()).thenReturn(errors);

        ResponseEntity<Map<String, String>> response = handler
                .handleArgumentNotValidException(methodArgumentNotValidException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Invalid email format", response.getBody().get("email"));
        assertEquals("Password is too short", response.getBody().get("password"));
    }
}

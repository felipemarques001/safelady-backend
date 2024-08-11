package com.uast.ms_auth.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @DisplayName("Give FieldAlreadyInUseException, when handleFieldAlreadyInUseException(), then return 400 response")
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
}

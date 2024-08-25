package com.uast.safelady_backend.exceptions;

import com.uast.safelady_backend.utils.StringUtils;
import lombok.Getter;

@Getter
public class FieldAlreadyInUseException extends RuntimeException {

    private final String fieldError;

    public FieldAlreadyInUseException(String field, String value) {
        super(String.format("Error in the field '%s', the value '%s' is already in use!", field, value));
        this.fieldError = StringUtils.toCamelCase(field);
    }
}
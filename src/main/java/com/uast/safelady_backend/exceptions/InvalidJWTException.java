package com.uast.safelady_backend.exceptions;

public class InvalidJWTException extends RuntimeException {
    public InvalidJWTException() {
        super("The JWT token sent is invalid!");
    }
}
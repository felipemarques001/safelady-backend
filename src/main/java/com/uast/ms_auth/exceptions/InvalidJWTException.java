package com.uast.ms_auth.exceptions;

public class InvalidJWTException extends RuntimeException {
    public InvalidJWTException() {
        super("The JWT token sent is invalid!");
    }
}
package com.uast.safelady_backend.exceptions;

public class CpfFormatException extends RuntimeException {
    public CpfFormatException() {
        super("The CPF sent has an invalid format!");
    }
}

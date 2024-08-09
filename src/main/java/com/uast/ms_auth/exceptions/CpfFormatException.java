package com.uast.ms_auth.exceptions;

public class CpfFormatException extends RuntimeException {
    public CpfFormatException() {
        super("The CPF sent has an invalid format!");
    }
}

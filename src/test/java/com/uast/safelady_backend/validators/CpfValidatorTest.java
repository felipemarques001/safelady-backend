package com.uast.safelady_backend.validators;

import com.uast.safelady_backend.validators.cpf.CpfValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CpfValidatorTest {

    private final CpfValidator validator = new CpfValidator();

    @DisplayName("Given valid CPF, when isValid(), then return true")
    @Test
    void givenValidCpf_whenIsValid_thenReturnTrue() {
        final String cpf = "71793119015";

        boolean isCpfValid = validator.isValid(cpf, null);

        assertTrue(isCpfValid);
    }

    @DisplayName("Given invalids CPFs, when isValid(), then return false")
    @Test
    void givenInvalidsCpf_whenIsValid_thenReturnFalse() {
        final String cpf1 = "11122233344";
        final String cpf2 = "717931190";
        final String cpf3 = "717931190151";
        final String cpf4 = "11111111111";

        boolean isCpf1Valid = validator.isValid(cpf1, null);
        boolean isCpf2Valid = validator.isValid(cpf2, null);
        boolean isCpf3Valid = validator.isValid(cpf3, null);
        boolean isCpf4Valid = validator.isValid(cpf4, null);

        assertFalse(isCpf1Valid);
        assertFalse(isCpf2Valid);
        assertFalse(isCpf3Valid);
        assertFalse(isCpf4Valid);
    }

    @DisplayName("Given CPF equals null, when isValid(), then return false")
    @Test
    void givenNullCpf_whenIsValid_thenReturnFalse() {
        final String cpf = null;

        boolean isCpfValid = validator.isValid(cpf, null);

        assertFalse(isCpfValid);
    }
}

package com.uast.ms_auth.validators;

import com.uast.ms_auth.validators.localDate.LocalDateValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocalDateValidatorTest {

    private final LocalDateValidator validator = new LocalDateValidator();

    @DisplayName("Given valid date format, when isValid(), then return true")
    @Test
    void givenValidDateFormat_whenIsValid_thenReturnTrue() {
        final String dateFormat = "2002-09-09";
        boolean isDateFormatValid = validator.isValid(dateFormat, null);
        assertTrue(isDateFormatValid);
    }

    @DisplayName("Given invalid dates formats, when isValid(), then return false")
    @Test
    void givenInvalidDatesFormats_whenIsValid_thenReturnFalse() {
        final String dateFormat1 = "09/09/2002";
        final String dateFormat2 = "09-09-2002";
        final String dateFormat3 = "2002/09/09";
        final String dateFormat4 = "10/20/2002";

        boolean isDateFormat1Valid = validator.isValid(dateFormat1, null);
        boolean isDateFormat2Valid = validator.isValid(dateFormat2, null);
        boolean isDateFormat3Valid = validator.isValid(dateFormat3, null);
        boolean isDateFormat4Valid = validator.isValid(dateFormat4, null);

        assertFalse(isDateFormat1Valid);
        assertFalse(isDateFormat2Valid);
        assertFalse(isDateFormat3Valid);
        assertFalse(isDateFormat4Valid);
    }

    @DisplayName("Given date format equals null, when isValid, then return false")
    @Test
    void givenNullDateFormat_whenIsValid_thenReturnFalse() {
        final String dateFormat = null;

        boolean isDateFormatValid = validator.isValid(dateFormat, null);

        assertFalse(isDateFormatValid);
    }
}

package com.uast.ms_auth.validators;

import com.uast.ms_auth.validators.phoneNumber.PhoneNumberValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhoneNumberValidatorTest {

    private final PhoneNumberValidator validator = new PhoneNumberValidator();

    @DisplayName("Given valid number format, when isValid(), then return true")
    @Test
    void givenValidNumberFormat_whenIsValid_thenReturnTrue() {
        final String phoneFormat = "00999999999";

        boolean isPhoneFormatValid = validator.isValid(phoneFormat, null);

        assertTrue(isPhoneFormatValid);
    }

    @DisplayName("Given invalid phone numbers formats, when isValid(), then return false")
    @Test
    void givenInvalidPhoneNumberFormats_whenIsValid_thenReturnFalse() {
        final String phoneFormat1 = "(87)999999999";
        final String phoneFormat2 = "(87) 999999999";
        final String phoneFormat3 = "(87)99999-9999";
        final String phoneFormat4 = "(87) 99999-9999";

        boolean isPhoneFormat1Valid = validator.isValid(phoneFormat1, null);
        boolean isPhoneFormat2Valid = validator.isValid(phoneFormat2, null);
        boolean isPhoneFormat3Valid = validator.isValid(phoneFormat3, null);
        boolean isPhoneFormat4Valid = validator.isValid(phoneFormat4, null);

        assertFalse(isPhoneFormat1Valid);
        assertFalse(isPhoneFormat2Valid);
        assertFalse(isPhoneFormat3Valid);
        assertFalse(isPhoneFormat4Valid);
    }

    @DisplayName("Given phone number format equals null, when isValid, then return false")
    @Test
    void givenNullPhoneNumberFormat_whenIsValid_thenReturnFalse() {
        final String phoneFormat = null;

        boolean isPhoneFormatValid = validator.isValid(phoneFormat, null);

        assertFalse(isPhoneFormatValid);
    }
}

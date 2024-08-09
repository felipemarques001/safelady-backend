package com.uast.ms_auth.validators.cpf;

import com.uast.ms_auth.exceptions.CpfFormatException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class CpfValidator implements ConstraintValidator<CpfConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null) return false;

        String cpfRegex = "([0-9]{11})";
        Pattern pattern = Pattern.compile(cpfRegex);

        if (!pattern.matcher(value).matches()) return false;

        if (value.equals("00000000000") ||
            value.equals("11111111111") ||
            value.equals("22222222222") ||
            value.equals("33333333333") ||
            value.equals("44444444444") ||
            value.equals("55555555555") ||
            value.equals("66666666666") ||
            value.equals("77777777777") ||
            value.equals("88888888888") ||
            value.equals("99999999999") ||
            (value.length() != 11)) {
            return false;
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" protects the code of possible errors in int conversion
        try {
            // First verification digit calculation
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // Converts the i-th character of the CPF into a number:
                // Ex.: Transform the character '0' to int 0, because 48 it's the position of '0' at ASCII table
                num = value.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48); // Converts to the corresponding numeric character

            // Second verification digit calculation
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = value.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);

            // Verifies if the computed digits correspond to the given digits
            return (dig10 == value.charAt(9)) && (dig11 == value.charAt(10));
        } catch (RuntimeException ex) {
            throw new CpfFormatException();
        }
    }
}

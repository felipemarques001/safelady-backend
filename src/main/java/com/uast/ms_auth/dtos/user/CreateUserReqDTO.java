package com.uast.ms_auth.dtos.user;

import com.uast.ms_auth.validators.cpf.CpfConstraint;
import com.uast.ms_auth.validators.localDate.LocalDateConstraint;
import com.uast.ms_auth.validators.phoneNumber.PhoneNumberConstraint;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateUserReqDTO (

        @CpfConstraint
        String cpf,

        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotBlank(message = "City cannot be empty")
        String city,

        @NotBlank(message = "Street cannot be empty")
        String street,

        @NotBlank(message = "Password cannot be empty")
        String password,

        @PhoneNumberConstraint
        String phoneNumber,

        @LocalDateConstraint
        String birthDate
){ }

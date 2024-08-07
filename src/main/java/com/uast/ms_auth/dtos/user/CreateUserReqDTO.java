package com.uast.ms_auth.dtos.user;

import java.time.LocalDate;

public record CreateUserReqDTO (
        String cpf,
        String name,
        String city,
        String street,
        String password,
        LocalDate birthDate
){ }

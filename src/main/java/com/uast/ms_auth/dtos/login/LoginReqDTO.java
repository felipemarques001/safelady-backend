package com.uast.ms_auth.dtos.login;

import com.uast.ms_auth.validators.cpf.CpfConstraint;
import jakarta.validation.constraints.NotBlank;

public record LoginReqDTO(
        @CpfConstraint
        String cpf,
        @NotBlank(message = "Password cannot be empty")
        String password
) {
}

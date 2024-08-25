package com.uast.safelady_backend.dtos.login;

import com.uast.safelady_backend.validators.cpf.CpfConstraint;
import jakarta.validation.constraints.NotBlank;

public record LoginReqDTO(
        @CpfConstraint
        String cpf,
        @NotBlank(message = "Password cannot be empty")
        String password
) {
}

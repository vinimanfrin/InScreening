package com.vinimanfrin.inscreening.dtos.funcionario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record FuncionarioUpdateDTO(
        @NotBlank @Email String email
) {
}

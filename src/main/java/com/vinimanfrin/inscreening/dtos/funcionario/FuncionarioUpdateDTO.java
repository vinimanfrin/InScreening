package com.vinimanfrin.inscreening.dtos.funcionario;

import jakarta.validation.constraints.Email;

public record FuncionarioUpdateDTO(
        @Email String email
) {
}

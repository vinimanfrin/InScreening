package com.vinimanfrin.inscreening.dtos.funcionario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record FuncionarioCreateDTO(
        @CPF String cpf,
        @NotBlank @Email String email,
        @NotBlank String nome,
        @NotBlank String idHospital
) {
}

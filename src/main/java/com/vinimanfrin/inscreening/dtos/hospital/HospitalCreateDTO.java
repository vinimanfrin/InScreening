package com.vinimanfrin.inscreening.dtos.hospital;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

public record HospitalCreateDTO(
        @NotBlank @CNPJ String cnpj,
        @NotBlank String razaoSocial,
        @NotBlank String senha
) {
}

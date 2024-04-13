package com.vinimanfrin.inscreening.dtos.paciente;

import com.vinimanfrin.inscreening.dtos.endereco.EnderecoDTO;
import com.vinimanfrin.inscreening.models.SexoPaciente;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record PacienteCreateDTO(
        @CPF String cpf,
        @Email String email,
        @NotBlank String rg,
        @NotNull SexoPaciente sexo,
        @Valid EnderecoDTO endereco,
        @NotBlank String nome,
        @NotBlank String filiacaoPaterna,
        @NotBlank String filiacaoMaterna
) {
}

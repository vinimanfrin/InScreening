package com.vinimanfrin.inscreening.dtos.paciente;

import com.vinimanfrin.inscreening.dtos.endereco.EnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PacienteUpdateDTO(
        @NotBlank @Email String email,
        @Valid EnderecoDTO endereco
) {
}

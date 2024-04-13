package com.vinimanfrin.inscreening.dtos.paciente;

import com.vinimanfrin.inscreening.dtos.endereco.EnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

public record PacienteUpdateDTO(
        @Email String email,
        @Valid EnderecoDTO endereco
) {
}

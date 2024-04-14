package com.vinimanfrin.inscreening.dtos.triagem;

import com.vinimanfrin.inscreening.models.Sintomas;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record TriagemCreateDTO(
        @NotBlank String idPaciente,
        List<Sintomas> sintomas
) {
}

package com.vinimanfrin.inscreening.dtos.endereco;

import com.vinimanfrin.inscreening.models.Endereco;
import jakarta.validation.constraints.NotBlank;

public record EnderecoDTO(
        @NotBlank String estado,
        @NotBlank String municipio,
        @NotBlank String logradouro,
        @NotBlank String numero,
        String complemento,
        @NotBlank String cep
) {
    public EnderecoDTO(Endereco endereco) {
        this(endereco.getEstado(), endereco.getMunicipio(), endereco.getLogradouro(), endereco.getNumero(), endereco.getComplemento(), endereco.getCep());
    }
}

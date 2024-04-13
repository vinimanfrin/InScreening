package com.vinimanfrin.inscreening.dtos.hospital;

import jakarta.validation.constraints.NotBlank;

public record HospitalUpdateDTO(
        @NotBlank String razaoSocial
) {
}

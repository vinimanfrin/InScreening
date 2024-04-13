package com.vinimanfrin.inscreening.dtos.funcionario;

import com.vinimanfrin.inscreening.models.Funcionario;
import com.vinimanfrin.inscreening.models.Hospital;
import com.vinimanfrin.inscreening.models.Status;


public record FuncionarioResponseDTO(
        String cpf,
        String email,
        String nome,
        Status status,
        Hospital hospital
) {
    public FuncionarioResponseDTO(Funcionario funcionario) {
        this(funcionario.getCpf(), funcionario.getEmail(), funcionario.getNome(), funcionario.getStatus(), funcionario.getHospital());
    }
}



package com.vinimanfrin.inscreening.dtos.paciente;

import com.vinimanfrin.inscreening.dtos.endereco.EnderecoDTO;
import com.vinimanfrin.inscreening.models.Paciente;
import com.vinimanfrin.inscreening.models.SexoPaciente;

public record PacienteResponseDTO(
        String cpf,
        String email,
        String rg,
        SexoPaciente sexo,
        EnderecoDTO endereco,
        String nome,
        String filiacaoPaterna,
        String filiacaoMaterna
) {
    public PacienteResponseDTO(Paciente paciente) {
        this(paciente.getCpf(), paciente.getEmail(), paciente.getRg(), paciente.getSexo(),new EnderecoDTO(paciente.getEndereco()), paciente.getNome(), paciente.getFiliacaoPaterna(), paciente.getFiliacaoMaterna());
    }
}

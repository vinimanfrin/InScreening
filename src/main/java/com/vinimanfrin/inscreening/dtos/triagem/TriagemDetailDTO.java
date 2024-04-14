package com.vinimanfrin.inscreening.dtos.triagem;

import com.vinimanfrin.inscreening.dtos.paciente.PacienteResponseDTO;
import com.vinimanfrin.inscreening.models.Prioridade;
import com.vinimanfrin.inscreening.models.SituacaoTriagem;
import com.vinimanfrin.inscreening.models.Triagem;

import java.time.LocalDateTime;
import java.util.List;

public record TriagemDetailDTO(
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        String sintomas,
        SituacaoTriagem situacao,
        Prioridade prioridade,
        PacienteResponseDTO paciente
        ) {
    public TriagemDetailDTO(Triagem triagem) {
        this(triagem.getDataInicio(), triagem.getDataFim(), triagem.getSintomas(), triagem.getSituacao(), triagem.getPrioridade(), new PacienteResponseDTO(triagem.getPaciente()));
    }
}

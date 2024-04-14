package com.vinimanfrin.inscreening.models;

import com.vinimanfrin.inscreening.dtos.triagem.TriagemCreateDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Entity
@Table(name = "t_triagens")
@Data
@NoArgsConstructor
public class Triagem {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String sintomas;

    @Enumerated(EnumType.STRING)
    private SituacaoTriagem situacao;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    public Triagem(TriagemCreateDTO triagemCreateDTO, Paciente paciente) {
        this.dataInicio = LocalDateTime.now();
        this.sintomas = triagemCreateDTO.sintomas().stream().map(Enum::name).collect(Collectors.joining(","));
        this.situacao = SituacaoTriagem.CONCLUIDA;
        this.prioridade = Prioridade.NORMAL;
        this.paciente = paciente;
    }
}

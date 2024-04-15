package com.vinimanfrin.inscreening.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_exames")
@Data
@NoArgsConstructor
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String resultado;
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;
}

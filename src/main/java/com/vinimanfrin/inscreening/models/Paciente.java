package com.vinimanfrin.inscreening.models;

import com.vinimanfrin.inscreening.dtos.paciente.PacienteCreateDTO;
import com.vinimanfrin.inscreening.dtos.paciente.PacienteUpdateDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_pacientes")
@Data
@NoArgsConstructor
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String rg;

    @Enumerated(EnumType.STRING)
    private SexoPaciente sexo;

    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    private String nome;

    private String filiacaoPaterna;

    private String filiacaoMaterna;

    public Paciente(PacienteCreateDTO pacienteCreateDTO, Endereco endereco) {
        this.cpf = pacienteCreateDTO.cpf();
        this.email = pacienteCreateDTO.email();
        this.rg = pacienteCreateDTO.rg();
        this.sexo = pacienteCreateDTO.sexo();
        this.endereco = endereco;
        this.nome = pacienteCreateDTO.nome();
        this.filiacaoPaterna = pacienteCreateDTO.filiacaoPaterna();
        this.filiacaoMaterna = pacienteCreateDTO.filiacaoMaterna();
    }

    public void update(PacienteUpdateDTO pacienteUpdateDTO) {
        this.email = pacienteUpdateDTO.email();
    }
}

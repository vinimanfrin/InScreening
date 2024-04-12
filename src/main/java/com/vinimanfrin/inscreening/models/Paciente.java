package com.vinimanfrin.inscreening.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pacientes")
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
    private Endereco endereco;

    private String nome;

    private String filiacaoPaterna;

    private String filiacaoMaterna;

}

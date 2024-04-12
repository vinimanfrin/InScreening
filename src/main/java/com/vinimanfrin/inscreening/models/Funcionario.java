package com.vinimanfrin.inscreening.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "funcionarios")
@Data
@NoArgsConstructor
public class Funcionario {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;

    @ManyToOne
    private Hospital hospital;

    private String nome;

}

package com.vinimanfrin.inscreening.models;

import com.vinimanfrin.inscreening.dtos.funcionario.FuncionarioCreateDTO;
import com.vinimanfrin.inscreening.dtos.funcionario.FuncionarioUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_funcionarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    public Funcionario(FuncionarioCreateDTO funcionarioCreateDTO) {
        this.cpf = funcionarioCreateDTO.cpf();
        this.email = funcionarioCreateDTO.email();
        this.nome = funcionarioCreateDTO.nome();
    }

    public void update(FuncionarioUpdateDTO funcionarioUpdateDTO) {
        this.email = funcionarioUpdateDTO.email();
    }

    public void delete() {
        this.status = status;
    }
}

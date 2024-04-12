package com.vinimanfrin.inscreening.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enderecos")
@Data
@NoArgsConstructor
public class Endereco {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String estado;
    private String municipio;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cep;
}

package com.vinimanfrin.inscreening.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hospitais")
@Data
@NoArgsConstructor
public class Hospital {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String cnpj;

    private String razaoSocial;
    private String senha;
}

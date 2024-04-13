package com.vinimanfrin.inscreening.models;

import com.vinimanfrin.inscreening.dtos.endereco.EnderecoDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_enderecos")
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

    public Endereco(EnderecoDTO endereco) {
        this.estado = endereco.estado();
        this.municipio = endereco.municipio();
        this.logradouro = endereco.logradouro();
        this.numero = endereco.numero();
        this.complemento = endereco.complemento();
        this.cep = endereco.cep();
    }

    public void update(EnderecoDTO enderecoDto) {
        this.estado = enderecoDto.estado();
        this.municipio = enderecoDto.municipio();
        this.logradouro = enderecoDto.logradouro();
        this.numero = enderecoDto.numero();
        this.complemento = enderecoDto.complemento();
        this.cep = enderecoDto.cep();
    }
}

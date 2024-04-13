package com.vinimanfrin.inscreening.models;

import com.vinimanfrin.inscreening.dtos.hospital.HospitalCreateDTO;
import com.vinimanfrin.inscreening.dtos.hospital.HospitalUpdateDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_hospitais")
@Data
@NoArgsConstructor
public class Hospital {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String cnpj;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    private String razaoSocial;
    private String senha;

    public Hospital(HospitalCreateDTO hospitalDto) {
        this.status = Status.ATIVO;
        this.cnpj = hospitalDto.cnpj();
        this.razaoSocial = hospitalDto.razaoSocial();
        this.senha = hospitalDto.senha();
    }

    public void update(HospitalUpdateDTO hospitalUpdateDTO) {
        this.razaoSocial = hospitalUpdateDTO.razaoSocial();
    }

    public void delete() {
        this.status = Status.INATIVO;
    }
}

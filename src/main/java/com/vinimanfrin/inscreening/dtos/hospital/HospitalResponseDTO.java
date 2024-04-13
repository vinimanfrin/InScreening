package com.vinimanfrin.inscreening.dtos.hospital;

import com.vinimanfrin.inscreening.models.Hospital;
import com.vinimanfrin.inscreening.models.Status;

public record HospitalResponseDTO(String cnpj, String razaoSocial, Status status) {

    public HospitalResponseDTO(Hospital hospital){
        this(hospital.getCnpj(), hospital.getRazaoSocial(), hospital.getStatus());
    }
}

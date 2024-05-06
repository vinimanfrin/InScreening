package com.vinimanfrin.inscreening.service;

import com.vinimanfrin.inscreening.dtos.hospital.HospitalCreateDTO;
import com.vinimanfrin.inscreening.dtos.hospital.HospitalUpdateDTO;
import com.vinimanfrin.inscreening.infra.exceptions.CadastroDuplicadoException;
import com.vinimanfrin.inscreening.models.Hospital;
import com.vinimanfrin.inscreening.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository repository;

    public Page<Hospital> index(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Hospital get(String id){
        return repository.getReferenceById(id);
    }

    public Hospital create(HospitalCreateDTO hospitalDto){
        boolean hospitalExistente = repository.existsByCnpj(hospitalDto.cnpj());
        if (hospitalExistente == true) throw new CadastroDuplicadoException("hospital já cadastrado com o cnpj:"+hospitalDto.cnpj());

        return repository.save(new Hospital(hospitalDto));
    }

    public Hospital update(String id, HospitalUpdateDTO hospitalUpdateDTO){
        Hospital hospitalAtualizado = repository.getReferenceById(id);
        hospitalAtualizado.update(hospitalUpdateDTO);

        return hospitalAtualizado;
    }

    public void delete(String id){
        Hospital hospital = repository.getReferenceById(id);

        hospital.delete();
    }
}

package com.vinimanfrin.inscreening.service;

import com.vinimanfrin.inscreening.dtos.hospital.HospitalCreateDTO;
import com.vinimanfrin.inscreening.dtos.hospital.HospitalUpdateDTO;
import com.vinimanfrin.inscreening.models.Hospital;
import com.vinimanfrin.inscreening.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository repository;

    public List<Hospital> index(){
        return repository.findAll();
    }

    public Hospital get(String id){
        return repository.getReferenceById(id);
    }

    public Hospital create(HospitalCreateDTO hospitalDto){
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

package com.vinimanfrin.inscreening.service;

import com.vinimanfrin.inscreening.dtos.triagem.TriagemCreateDTO;
import com.vinimanfrin.inscreening.models.Paciente;
import com.vinimanfrin.inscreening.models.Triagem;
import com.vinimanfrin.inscreening.repository.PacienteRepository;
import com.vinimanfrin.inscreening.repository.TriagemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TriagemService {

    @Autowired
    private TriagemRepository repository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public Page<Triagem> index(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Triagem get(String id){
        return repository.getReferenceById(id);
    }

    public Triagem create(TriagemCreateDTO triagemCreateDTO){
        Paciente paciente = pacienteRepository.findById(triagemCreateDTO.idPaciente()).orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado com o id:"+triagemCreateDTO.idPaciente()));
        Triagem triagem = new Triagem(triagemCreateDTO, paciente);

        return repository.save(triagem);
    }
}

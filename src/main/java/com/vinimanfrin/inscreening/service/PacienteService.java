package com.vinimanfrin.inscreening.service;

import com.vinimanfrin.inscreening.dtos.paciente.PacienteCreateDTO;
import com.vinimanfrin.inscreening.dtos.paciente.PacienteUpdateDTO;
import com.vinimanfrin.inscreening.infra.exceptions.CadastroDuplicadoException;
import com.vinimanfrin.inscreening.models.Endereco;
import com.vinimanfrin.inscreening.models.Paciente;
import com.vinimanfrin.inscreening.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    @Autowired
    private EnderecoService enderecoService;

    public Page<Paciente> index(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Paciente get(String id){
        return repository.getReferenceById(id);
    }

    public Paciente create(PacienteCreateDTO pacienteCreateDTO){
        boolean pacienteExistente = repository.existsByCpfOrRg(pacienteCreateDTO.cpf(),pacienteCreateDTO.rg());
        if (pacienteExistente == true) throw new CadastroDuplicadoException("paciente já cadastrado com os documentos: cpf:"+pacienteCreateDTO.cpf()+" rg:"+pacienteCreateDTO.rg());

        Endereco endereco = enderecoService.create(new Endereco(pacienteCreateDTO.endereco()));
        Paciente paciente = new Paciente(pacienteCreateDTO, endereco);

        return repository.save(paciente);
    }

    public Paciente update(String id, PacienteUpdateDTO pacienteUpdateDTO){
        Paciente pacienteAtualizado = repository.getReferenceById(id);
        pacienteAtualizado.update(pacienteUpdateDTO);

        enderecoService.update(pacienteAtualizado.getEndereco().getId(),pacienteUpdateDTO.endereco());

        return pacienteAtualizado;
    }

}

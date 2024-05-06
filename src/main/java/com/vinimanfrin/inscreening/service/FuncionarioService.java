package com.vinimanfrin.inscreening.service;

import com.vinimanfrin.inscreening.dtos.funcionario.FuncionarioCreateDTO;
import com.vinimanfrin.inscreening.dtos.funcionario.FuncionarioUpdateDTO;
import com.vinimanfrin.inscreening.infra.exceptions.CadastroDuplicadoException;
import com.vinimanfrin.inscreening.models.Funcionario;
import com.vinimanfrin.inscreening.models.Hospital;
import com.vinimanfrin.inscreening.models.Status;
import com.vinimanfrin.inscreening.repository.FuncionarioRepository;
import com.vinimanfrin.inscreening.repository.HospitalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private HospitalRepository hospitalRepository;

    public Page<Funcionario> index(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Funcionario get(String id){
        return repository.getReferenceById(id);
    }

    public Funcionario create(FuncionarioCreateDTO funcionarioCreateDTO){
        boolean funcionarioExistente = repository.existsByCpf(funcionarioCreateDTO.cpf());
        if (funcionarioExistente == true) throw new CadastroDuplicadoException("funcionário já cadastrado com o cpf:"+funcionarioCreateDTO.cpf());


        Hospital hospital = hospitalRepository.findById(funcionarioCreateDTO.idHospital()).orElseThrow(() -> new EntityNotFoundException("Hospital não encontrado com o id:"+funcionarioCreateDTO.idHospital()));
        Funcionario funcionario = new Funcionario(null, funcionarioCreateDTO.cpf(), funcionarioCreateDTO.email(), funcionarioCreateDTO.nome(), Status.ATIVO, hospital);

        return repository.save(funcionario);
    }

    public Funcionario update(String id, FuncionarioUpdateDTO funcionarioUpdateDTO){
        Funcionario funcionarioAtualizado = repository.getReferenceById(id);
        funcionarioAtualizado.update(funcionarioUpdateDTO);

        return funcionarioAtualizado;
    }

    public void delete(String id){
        Funcionario funcionario = repository.getReferenceById(id);

        funcionario.delete();
    }
}

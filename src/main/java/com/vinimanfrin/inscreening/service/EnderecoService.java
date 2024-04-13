package com.vinimanfrin.inscreening.service;

import com.vinimanfrin.inscreening.dtos.endereco.EnderecoDTO;
import com.vinimanfrin.inscreening.models.Endereco;
import com.vinimanfrin.inscreening.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    public List<Endereco> index(){
        return repository.findAll();
    }

    public Endereco get(String id){
        return repository.getReferenceById(id);
    }

    public Endereco create(Endereco endereco){
        return repository.save(endereco);
    }

    public Endereco update(String id, EnderecoDTO enderecoDto){
        Endereco enderecoAtualizado = repository.getReferenceById(id);
        enderecoAtualizado.update(enderecoDto);

        return enderecoAtualizado;
    }

}

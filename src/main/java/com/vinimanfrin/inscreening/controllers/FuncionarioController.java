package com.vinimanfrin.inscreening.controllers;

import com.vinimanfrin.inscreening.dtos.funcionario.FuncionarioCreateDTO;
import com.vinimanfrin.inscreening.dtos.funcionario.FuncionarioResponseDTO;
import com.vinimanfrin.inscreening.dtos.funcionario.FuncionarioUpdateDTO;
import com.vinimanfrin.inscreening.models.Funcionario;
import com.vinimanfrin.inscreening.service.FuncionarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/funcionario")
@CacheConfig(cacheNames = "funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @GetMapping
    @Cacheable
    public ResponseEntity<List<FuncionarioResponseDTO>> index(){
        List<FuncionarioResponseDTO> funcionarios = service.index().stream().map(funcionario -> new FuncionarioResponseDTO(funcionario)).toList();

        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> get(@PathVariable String id){
        Funcionario funcionario = service.get(id);

        return ResponseEntity.ok(new FuncionarioResponseDTO(funcionario));
    }

    @PostMapping
    @Transactional
    @CacheEvict(allEntries = true)
    public ResponseEntity create(@RequestBody @Valid FuncionarioCreateDTO funcionarioCreateDTO){
        Funcionario funcionarioCreate = service.create(funcionarioCreateDTO);

        return ResponseEntity.created(createUri(funcionarioCreate)).build();
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(allEntries = true)
    public ResponseEntity<FuncionarioResponseDTO> update(@PathVariable String id, @RequestBody @Valid FuncionarioUpdateDTO funcionarioUpdateDTO){
        Funcionario funcionarioAtualizado = service.update(id,funcionarioUpdateDTO);

        return ResponseEntity.ok(new FuncionarioResponseDTO(funcionarioAtualizado));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(allEntries = true)
    public ResponseEntity delete(@PathVariable String id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    private URI createUri(Funcionario funcionario){
        var path = "/"+funcionario.getId();
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .build()
                .toUri();
    }
}

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/funcionario")
@CacheConfig(cacheNames = "funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @GetMapping
    @Cacheable
    public ResponseEntity<Page<FuncionarioResponseDTO>> index(@PageableDefault(size = 10,sort = {"nome"})Pageable pageable){
        Page<FuncionarioResponseDTO> funcionarios = service.index(pageable).map(funcionario -> new FuncionarioResponseDTO(funcionario));

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

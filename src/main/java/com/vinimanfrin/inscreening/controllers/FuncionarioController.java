package com.vinimanfrin.inscreening.controllers;

import com.vinimanfrin.inscreening.dtos.funcionario.FuncionarioCreateDTO;
import com.vinimanfrin.inscreening.dtos.funcionario.FuncionarioResponseDTO;
import com.vinimanfrin.inscreening.dtos.funcionario.FuncionarioUpdateDTO;
import com.vinimanfrin.inscreening.models.Funcionario;
import com.vinimanfrin.inscreening.service.FuncionarioService;
import com.vinimanfrin.inscreening.utils.UriUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
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
@Tag(name = "funcionarios", description = "Endpoint relacionado aos dados de funcionários")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @GetMapping
    @Cacheable
    @Operation(summary = "Lista todos os funcionários", description = "Endpoint retorna de forma paginada todos os funcionários cadastrados, por padrão cada pagina contém 10 funcionários ordenados por nome, mas esses dados de paginação podem ser personalizados.")
    public ResponseEntity<Page<FuncionarioResponseDTO>> index(@ParameterObject @PageableDefault(size = 10,sort = {"nome"})Pageable pageable){
        Page<FuncionarioResponseDTO> funcionarios = service.index(pageable).map(funcionario -> new FuncionarioResponseDTO(funcionario));

        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Exibe os detalhes do funcionário de id equivalente", description = "Endpoint retorna um objeto contendo os dados não sensíveis de um funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado"),
            @ApiResponse(responseCode = "200", description = "Funcionário detalhado com sucesso!")
    })
    public ResponseEntity<FuncionarioResponseDTO> get(@PathVariable String id){
        Funcionario funcionario = service.get(id);

        return ResponseEntity.ok(new FuncionarioResponseDTO(funcionario));
    }

    @PostMapping
    @Transactional
    @CacheEvict(allEntries = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do funcionário"),
            @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso!")
    })
    @Operation(summary = "Cadastra um novo funcionário no sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados necessários para realizar o cadastro de um novo funcionário")
    public ResponseEntity create(@ParameterObject @RequestBody @Valid FuncionarioCreateDTO funcionarioCreateDTO){
        Funcionario funcionarioCreate = service.create(funcionarioCreateDTO);

        return ResponseEntity.created(UriUtils.createUri(funcionarioCreate.getId())).build();
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(allEntries = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do funcionário"),
            @ApiResponse(responseCode = "200", description = "Dados do funcionário atualizado com sucesso!")
    })
    @Operation(summary = "Atualiza os dados de um funcionário no sistema", description = "Endpoint recebe no path o id do funcionário a ser atualizado , e no corpo da requisição um objeto contendo os dados necessários para atualizar um funcionário")
    public ResponseEntity<FuncionarioResponseDTO> update(@PathVariable String id,@ParameterObject @RequestBody @Valid FuncionarioUpdateDTO funcionarioUpdateDTO){
        Funcionario funcionarioAtualizado = service.update(id,funcionarioUpdateDTO);

        return ResponseEntity.ok(new FuncionarioResponseDTO(funcionarioAtualizado));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(allEntries = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado"),
            @ApiResponse(responseCode = "204", description = "Funcionário removido com sucesso!")
    })
    @Operation(summary = "Deleta um funcionário do sistema", description = "Endpoint recebe no path o id do funcionário a ser deletado")
    public ResponseEntity delete(@PathVariable String id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}

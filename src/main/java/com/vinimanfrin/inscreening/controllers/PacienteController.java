package com.vinimanfrin.inscreening.controllers;

import com.vinimanfrin.inscreening.dtos.paciente.PacienteCreateDTO;
import com.vinimanfrin.inscreening.dtos.paciente.PacienteResponseDTO;
import com.vinimanfrin.inscreening.dtos.paciente.PacienteUpdateDTO;
import com.vinimanfrin.inscreening.models.Paciente;
import com.vinimanfrin.inscreening.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/paciente")
@Tag(name = "pacientes", description = "Endpoint relacionado aos dados de pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @GetMapping
    @Operation(summary = "Lista todos os pacientes", description = "Endpoint retorna de forma paginada todos os pacientes cadastrados, por padrão cada pagina contém 10 pacientes ordenados por nome, mas esses dados de paginação podem ser personalizados.")
    public ResponseEntity<Page<PacienteResponseDTO>> index(@ParameterObject @PageableDefault(size = 10,sort = {"nome"})Pageable pageable){
        Page<PacienteResponseDTO> pacientes = service.index(pageable).map(paciente -> new PacienteResponseDTO(paciente));

        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Exibe os detalhes do paciente de id equivalente", description = "Endpoint retorna um objeto contendo os dados não sensíveis de um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "200", description = "Paciente detalhado com sucesso!")
    })
    public ResponseEntity<PacienteResponseDTO> get(@PathVariable String id){
        Paciente paciente = service.get(id);

        return ResponseEntity.ok(new PacienteResponseDTO(paciente));
    }

    @PostMapping
    @Transactional
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do paciente"),
            @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso!")
    })
    @Operation(summary = "Cadastra um novo paciente no sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados necessários para realizar o cadastro de um novo paciente")
    public ResponseEntity create(@ParameterObject @RequestBody @Valid PacienteCreateDTO pacienteCreateDTO){
        Paciente pacienteCreated = service.create(pacienteCreateDTO);

        return ResponseEntity.created(createUri(pacienteCreated)).build();
    }

    @PutMapping("/{id}")
    @Transactional
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do paciente"),
            @ApiResponse(responseCode = "200", description = "Dados do paciente atualizado com sucesso!")
    })
    @Operation(summary = "Atualiza os dados de um paciente no sistema", description = "Endpoint recebe no path o id do paciente a ser atualizado , e no corpo da requisição um objeto contendo os dados necessários para atualizar um paciente")
    public ResponseEntity<PacienteResponseDTO> update(@PathVariable String id, @RequestBody @Valid PacienteUpdateDTO pacienteUpdateDTO){
        Paciente pacienteAtualizado = service.update(id,pacienteUpdateDTO);

        return ResponseEntity.ok(new PacienteResponseDTO(pacienteAtualizado));
    }

    private URI createUri(Paciente hospital){
        var path = "/"+hospital.getId();
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .build()
                .toUri();
    }
}

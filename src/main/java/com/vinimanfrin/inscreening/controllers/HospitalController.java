package com.vinimanfrin.inscreening.controllers;

import com.vinimanfrin.inscreening.dtos.hospital.HospitalCreateDTO;
import com.vinimanfrin.inscreening.dtos.hospital.HospitalResponseDTO;
import com.vinimanfrin.inscreening.dtos.hospital.HospitalUpdateDTO;
import com.vinimanfrin.inscreening.models.Hospital;
import com.vinimanfrin.inscreening.service.HospitalService;
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
@RequestMapping("/hospital")
@CacheConfig(cacheNames = "hospitais")
@Tag(name = "hospitais", description = "Endpoint relacionado aos dados de hospitais")
public class HospitalController {

    @Autowired
    private HospitalService service;

    @GetMapping
    @Cacheable
    @Operation(summary = "Lista todos os hospitais", description = "Endpoint retorna de forma paginada todos os hospitais cadastrados, por padrão cada pagina contém 10 hospitais ordenados por razão sozial, mas esses dados de paginação podem ser personalizados.")
    public ResponseEntity<Page<HospitalResponseDTO>> index(@ParameterObject @PageableDefault(size = 10,sort = {"razaoSocial"})Pageable pageable){
        Page<HospitalResponseDTO> hospitais = service.index(pageable).map(hospital -> new HospitalResponseDTO(hospital));

        return ResponseEntity.ok(hospitais);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Exibe os detalhes do hospital de id equivalente", description = "Endpoint retorna um objeto contendo os dados não sensíveis de um hospital")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Hospital não encontrado"),
            @ApiResponse(responseCode = "200", description = "Hospital detalhado com sucesso!")
    })
    public ResponseEntity<HospitalResponseDTO> get(@PathVariable String id){
        Hospital hospital = service.get(id);

        return ResponseEntity.ok(new HospitalResponseDTO(hospital));
    }

    @PostMapping
    @Transactional
    @CacheEvict(allEntries = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do hospital"),
            @ApiResponse(responseCode = "201", description = "Hospital cadastrado com sucesso!")
    })
    @Operation(summary = "Cadastra um novo hospital no sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados necessários para realizar o cadastro de um novo hospital")
    public ResponseEntity create(@ParameterObject @RequestBody @Valid HospitalCreateDTO hospitalDto){
        Hospital hospitalCreated = service.create(hospitalDto);

        return ResponseEntity.created(UriUtils.createUri(hospitalCreated.getId())).build();
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(allEntries = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do Hospital"),
            @ApiResponse(responseCode = "200", description = "Dados do hospital atualizado com sucesso!")
    })
    @Operation(summary = "Atualiza os dados de um hospital no sistema", description = "Endpoint recebe no path o id do hosptital a ser atualizado , e no corpo da requisição um objeto contendo os dados necessários para atualizar um hospital")
    public ResponseEntity<HospitalResponseDTO> update(@PathVariable String id, @ParameterObject @RequestBody @Valid HospitalUpdateDTO hospitalUpdateDTO){
        Hospital hospitalAtualizado = service.update(id,hospitalUpdateDTO);

        return ResponseEntity.ok(new HospitalResponseDTO(hospitalAtualizado));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(allEntries = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Hospital não encontrado"),
            @ApiResponse(responseCode = "204", description = "Hospital removido com sucesso!")
    })
    @Operation(summary = "Deleta um hospital do sistema", description = "Endpoint recebe no path o id do hospital   a ser deletado")
    public ResponseEntity delete(@PathVariable String id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}

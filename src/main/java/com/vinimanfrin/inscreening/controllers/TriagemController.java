package com.vinimanfrin.inscreening.controllers;

import com.vinimanfrin.inscreening.dtos.triagem.TriagemCreateDTO;
import com.vinimanfrin.inscreening.dtos.triagem.TriagemDetailDTO;
import com.vinimanfrin.inscreening.models.Triagem;
import com.vinimanfrin.inscreening.service.TriagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/triagem")
@Tag(name = "triagens", description = "Endpoint relacionado aos dados de triagens")
public class TriagemController {

    @Autowired
    private TriagemService service;

    @GetMapping
    @Operation(summary = "Lista todas os triagens", description = "Endpoint retorna de forma paginada todas as triagens cadastradas, por padrão cada pagina contém 10 triagens ordenadas por nome, mas esses dados de paginação podem ser personalizados.")
    public ResponseEntity<Page<TriagemDetailDTO>> index(@ParameterObject @PageableDefault(size = 10, sort = {"dataInicio"})Pageable pageable){
        Page<TriagemDetailDTO> triagens = service.index(pageable).map(TriagemDetailDTO::new);
        return ResponseEntity.ok(triagens);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Exibe os detalhes da triagem de id equivalente", description = "Endpoint retorna um objeto contendo os dados não sensíveis de uma triagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Triagem não encontrada"),
            @ApiResponse(responseCode = "200", description = "Triagem detalhada com sucesso!")
    })
    public ResponseEntity<TriagemDetailDTO> get(@PathVariable String id){
        Triagem triagem = service.get(id);

        return ResponseEntity.ok(new TriagemDetailDTO(triagem));
    }


    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados da triagem"),
            @ApiResponse(responseCode = "201", description = "Triagem cadastrada com sucesso!")
    })
    @Operation(summary = "Cadastra uma nova triagem no sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados necessários para realizar uma nova triagem")
    public ResponseEntity create(@ParameterObject @RequestBody TriagemCreateDTO dadosTriagem){
        Triagem triagem = service.create(dadosTriagem);

        return ResponseEntity.created(createUri(triagem)).build();
    }

    public URI createUri(Triagem triagem){
        var path = "/"+triagem.getId();

        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .build()
                .toUri();
    }
}

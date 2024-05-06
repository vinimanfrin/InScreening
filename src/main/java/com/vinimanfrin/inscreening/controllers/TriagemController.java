package com.vinimanfrin.inscreening.controllers;

import com.vinimanfrin.inscreening.dtos.triagem.TriagemCreateDTO;
import com.vinimanfrin.inscreening.dtos.triagem.TriagemDetailDTO;
import com.vinimanfrin.inscreening.models.Triagem;
import com.vinimanfrin.inscreening.service.TriagemService;
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
public class TriagemController {

    @Autowired
    private TriagemService service;

    @GetMapping
    public ResponseEntity<Page<TriagemDetailDTO>> index(@PageableDefault(size = 10, sort = {"dataInicio"})Pageable pageable){
        Page<TriagemDetailDTO> triagens = service.index(pageable).map(TriagemDetailDTO::new);
        return ResponseEntity.ok(triagens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TriagemDetailDTO> get(@PathVariable String id){
        Triagem triagem = service.get(id);

        return ResponseEntity.ok(new TriagemDetailDTO(triagem));
    }


    @PostMapping
    public ResponseEntity create(@RequestBody TriagemCreateDTO dadosTriagem){
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

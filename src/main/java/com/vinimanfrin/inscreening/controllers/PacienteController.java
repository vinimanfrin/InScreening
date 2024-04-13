package com.vinimanfrin.inscreening.controllers;

import com.vinimanfrin.inscreening.dtos.paciente.PacienteCreateDTO;
import com.vinimanfrin.inscreening.dtos.paciente.PacienteResponseDTO;
import com.vinimanfrin.inscreening.dtos.paciente.PacienteUpdateDTO;
import com.vinimanfrin.inscreening.models.Paciente;
import com.vinimanfrin.inscreening.service.PacienteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> index(){
        List<PacienteResponseDTO> pacientes = service.index().stream().map(paciente -> new PacienteResponseDTO(paciente)).toList();

        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> get(@PathVariable String id){
        Paciente paciente = service.get(id);

        return ResponseEntity.ok(new PacienteResponseDTO(paciente));
    }

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid PacienteCreateDTO pacienteCreateDTO){
        Paciente pacienteCreated = service.create(pacienteCreateDTO);

        return ResponseEntity.created(createUri(pacienteCreated)).build();
    }

    @PutMapping("/{id}")
    @Transactional
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

package com.vinimanfrin.inscreening.controllers;

import com.vinimanfrin.inscreening.dtos.hospital.HospitalCreateDTO;
import com.vinimanfrin.inscreening.dtos.hospital.HospitalResponseDTO;
import com.vinimanfrin.inscreening.dtos.hospital.HospitalUpdateDTO;
import com.vinimanfrin.inscreening.models.Hospital;
import com.vinimanfrin.inscreening.service.HospitalService;
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
@RequestMapping("/hospital")
@CacheConfig(cacheNames = "hospitais")
public class HospitalController {

    @Autowired
    private HospitalService service;

    @GetMapping
    @Cacheable
    public ResponseEntity<Page<HospitalResponseDTO>> index(@PageableDefault(size = 10,sort = {"razaoSocial"})Pageable pageable){
        Page<HospitalResponseDTO> hospitais = service.index(pageable).map(hospital -> new HospitalResponseDTO(hospital));

        return ResponseEntity.ok(hospitais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponseDTO> get(@PathVariable String id){
        Hospital hospital = service.get(id);

        return ResponseEntity.ok(new HospitalResponseDTO(hospital));
    }

    @PostMapping
    @Transactional
    @CacheEvict(allEntries = true)
    public ResponseEntity create(@RequestBody @Valid HospitalCreateDTO hospitalDto){
        Hospital hospitalCreated = service.create(hospitalDto);

        return ResponseEntity.created(createUri(hospitalCreated)).build();
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(allEntries = true)
    public ResponseEntity<HospitalResponseDTO> update(@PathVariable String id, @RequestBody @Valid HospitalUpdateDTO hospitalUpdateDTO){
        Hospital hospitalAtualizado = service.update(id,hospitalUpdateDTO);

        return ResponseEntity.ok(new HospitalResponseDTO(hospitalAtualizado));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(allEntries = true)
    public ResponseEntity delete(@PathVariable String id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }


    private URI createUri(Hospital hospital){
        var path = "/"+hospital.getId();
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .build()
                .toUri();
    }
}

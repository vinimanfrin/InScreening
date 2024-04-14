package com.vinimanfrin.inscreening.infra.exceptions.dto;

import org.springframework.validation.FieldError;

public record DataErrorsFormDTO(String field, String message) {

    public DataErrorsFormDTO(FieldError error){
        this(error.getField(), error.getDefaultMessage());
    }
}

package com.vinimanfrin.inscreening.infra.exceptions;

public class CadastroDuplicadoException extends RuntimeException{

    public CadastroDuplicadoException(String message) {
        super(message);
    }
}

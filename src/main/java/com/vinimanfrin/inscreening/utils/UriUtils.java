package com.vinimanfrin.inscreening.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class UriUtils {

    public static URI createUri(String id){
        var path = "/" + id;
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .build()
                .toUri();
    }
}

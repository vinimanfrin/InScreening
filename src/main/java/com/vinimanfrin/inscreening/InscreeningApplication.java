package com.vinimanfrin.inscreening;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class InscreeningApplication {

	public static void main(String[] args) {
		SpringApplication.run(InscreeningApplication.class, args);
	}

}

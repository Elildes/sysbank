package com.sysbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada da aplicação Spring Boot. Sobe o servidor embarcado (Tomcat)
 * e expõe a API REST.
 */
@SpringBootApplication
public class SysBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SysBankApplication.class, args);
	}
}
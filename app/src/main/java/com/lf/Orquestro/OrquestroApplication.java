package com.lf.Orquestro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = "com.lf.Orquestro")
public class OrquestroApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrquestroApplication.class, args);
	}

}

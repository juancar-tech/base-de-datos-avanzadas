package com.estaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {BatchAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "com.estaciones.infrastructure.persistence.jpa.repository")
@EntityScan(basePackages = "com.estaciones.infrastructure.persistence.jpa.entity")
public class EstacionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstacionesApplication.class, args);
    }
}



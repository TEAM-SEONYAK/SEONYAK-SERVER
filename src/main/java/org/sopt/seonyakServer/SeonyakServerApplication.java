package org.sopt.seonyakServer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(servers = {@Server(url = "https://api.seonyak.com", description = "product seonyak server")})
@SpringBootApplication
@EnableScheduling
public class SeonyakServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeonyakServerApplication.class, args);
    }
}

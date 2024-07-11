package org.sopt.seonyakServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SeonyakServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeonyakServerApplication.class, args);
    }
}

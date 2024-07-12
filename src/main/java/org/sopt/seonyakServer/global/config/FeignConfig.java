package org.sopt.seonyakServer.global.config;

import feign.codec.ErrorDecoder;
import org.sopt.seonyakServer.SeonyakServerApplication;
import org.sopt.seonyakServer.global.common.external.univcert.feign.FeignErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = SeonyakServerApplication.class)
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
}

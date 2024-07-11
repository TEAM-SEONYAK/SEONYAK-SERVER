package org.sopt.seonyakServer.global.config;

import feign.codec.ErrorDecoder;
import org.sopt.seonyakServer.global.common.external.univcert.feign.FeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
}

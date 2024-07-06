package org.sopt.seonyakServer.global.config;

import org.sopt.seonyakServer.SeonyakServerApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = SeonyakServerApplication.class)
@ImportAutoConfiguration(FeignAutoConfiguration.class)
public class FeignClientConfig {
}

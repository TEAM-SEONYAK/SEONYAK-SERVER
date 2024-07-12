package org.sopt.seonyakServer.global.common.external.univcert;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class UnivCertConfig {

    @Value("${univ-cert.api-key}")
    private String univKey;

}

package org.sopt.seonyakServer.global.common.external.naver;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class OcrConfig {

    @Value("${naver.ocr.api.url}")
    private String apiUrl;

    @Value("${naver.ocr.api.key}")
    private String apiKey;
    
}

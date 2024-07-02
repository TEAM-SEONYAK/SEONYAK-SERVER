package org.sopt.seonyakServer.global.common.external.naver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OcrConfig {
    
    @Value("${naver.ocr.api.url}")
    private String apiUrl;

    @Value("${naver.ocr.api.key}")
    private String apiKey;

    public String getApiUrl() {
        return apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }
}

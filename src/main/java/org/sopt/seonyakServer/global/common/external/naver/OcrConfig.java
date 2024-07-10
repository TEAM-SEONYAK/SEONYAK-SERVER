package org.sopt.seonyakServer.global.common.external.naver;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class OcrConfig {

    @Value("${naver.ocr.api.univ-url}")
    private String univUrl;

    @Value("${naver.ocr.api.univ-key}")
    private String univUrlKey;

    @Value("${naver.ocr.api.business-url}")
    private String businessUrl;

    @Value("${naver.ocr.api.business-key}")
    private String businessKey;

}

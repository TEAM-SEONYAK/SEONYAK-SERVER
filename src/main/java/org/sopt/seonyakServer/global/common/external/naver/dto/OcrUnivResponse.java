package org.sopt.seonyakServer.global.common.external.naver.dto;

import java.util.List;

public record OcrUnivResponse(
        List<String> univName
) {
    public static OcrUnivResponse of(List<String> univName) {
        return new OcrUnivResponse(univName);
    }
}

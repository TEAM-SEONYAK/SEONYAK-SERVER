package org.sopt.seonyakServer.global.common.dto;

public record OcrUnivResponse(
        String univName
) {
    public static OcrUnivResponse of(String univName) {
        return new OcrUnivResponse(univName);
    }
}

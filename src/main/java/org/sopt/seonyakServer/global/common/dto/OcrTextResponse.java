package org.sopt.seonyakServer.global.common.dto;

public record OcrTextResponse(
        String univ
) {
    public static OcrTextResponse of(String univ) {
        return new OcrTextResponse(univ);
    }
}

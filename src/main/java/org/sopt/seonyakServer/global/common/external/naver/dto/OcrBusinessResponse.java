package org.sopt.seonyakServer.global.common.external.naver.dto;

public record OcrBusinessResponse(
        String company,
        String phoneNumber
) {
    public static OcrBusinessResponse of(String company, String phoneNumber) {
        return new OcrBusinessResponse(company, phoneNumber);
    }
}

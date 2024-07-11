package org.sopt.seonyakServer.global.common.external.univcert.dto;

public record UnivCertResponse(
        String success,
        Integer status,
        String message
) {
    public static UnivCertResponse of(
            String success,
            Integer status,
            String message
    ) {
        return new UnivCertResponse(success, status, message);
    }
}

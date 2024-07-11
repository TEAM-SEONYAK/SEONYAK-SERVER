package org.sopt.seonyakServer.global.common.external.univcert.dto;

public record UnivCertVerifyClientRequest(
        String key,
        String email,
        String univName,
        int code
) {
    public static UnivCertVerifyClientRequest of(
            String key,
            String email,
            String univName,
            int code
    ) {
        return new UnivCertVerifyClientRequest(key, email, univName, code);
    }
}

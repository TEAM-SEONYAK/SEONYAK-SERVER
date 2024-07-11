package org.sopt.seonyakServer.global.common.external.univcert.dto;

public record UnivCertVerifyRequest(
        String email,
        String univName,
        int code
) {
    public static UnivCertVerifyRequest of(
            String email,
            String univName,
            int code
    ) {
        return new UnivCertVerifyRequest(email, univName, code);
    }
}

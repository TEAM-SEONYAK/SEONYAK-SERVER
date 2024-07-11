package org.sopt.seonyakServer.global.common.external.univcert.dto;

public record UnivCertRequest(
        String email,
        String univName
) {
    public static UnivCertRequest of(
            String email,
            String univName
    ) {
        return new UnivCertRequest(email, univName);
    }
}

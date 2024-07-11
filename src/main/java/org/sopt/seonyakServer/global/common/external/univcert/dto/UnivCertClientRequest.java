package org.sopt.seonyakServer.global.common.external.univcert.dto;

public record UnivCertClientRequest(
        String key,
        String email,
        String univName,
        boolean univ_check
) {
    public static UnivCertClientRequest of(
            String key,
            String email,
            String univName,
            boolean univ_check
    ) {
        return new UnivCertClientRequest(key, email, univName, univ_check);
    }
}

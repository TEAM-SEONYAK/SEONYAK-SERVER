package org.sopt.seonyakServer.global.common.external.univcert.dto;

public record UnivCertVerifyRequest(
        String email,
        String univName,
        int code
) {
}

package org.sopt.seonyakServer.domain.member.dto;

public record CertifyCodeRequest(
        String phoneNumber,
        String certificationCode
) {
}

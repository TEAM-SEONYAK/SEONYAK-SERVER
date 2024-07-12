package org.sopt.seonyakServer.domain.member.dto;

public record VerifyCodeRequest(
        String phoneNumber,
        String verificationCode
) {
}

package org.sopt.seonyakServer.domain.member.dto;

import jakarta.validation.constraints.NotBlank;

public record VerifyCodeRequest(
        @NotBlank(message = "전화번호는 공백일 수 없습니다.")
        String phoneNumber,

        @NotBlank(message = "인증번호는 공백일 수 없습니다.")
        String verificationCode
) {
}

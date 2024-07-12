package org.sopt.seonyakServer.domain.member.dto;

import jakarta.validation.constraints.NotBlank;

public record NicknameRequest(
        @NotBlank(message = "닉네임은 공백일 수 없습니다.")
        String nickname
) {
}

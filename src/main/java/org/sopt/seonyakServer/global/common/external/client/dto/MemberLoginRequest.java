package org.sopt.seonyakServer.global.common.external.client.dto;

import jakarta.validation.constraints.NotNull;
import org.sopt.seonyakServer.global.common.external.client.SocialType;

public record MemberLoginRequest(
        String redirectUri,
        @NotNull(message = "소셜 로그인 종류가 입력되지 않았습니다.")
        SocialType socialType
) {
}

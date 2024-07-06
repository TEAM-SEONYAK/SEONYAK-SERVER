package org.sopt.seonyakServer.domain.member.dto;

public record LoginSuccessResponse(
        String accessToken
) {
    public static LoginSuccessResponse of(final String accessToken) {
        return new LoginSuccessResponse(accessToken);
    }
}

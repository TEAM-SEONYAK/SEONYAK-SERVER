package org.sopt.seonyakServer.domain.member.dto;

public record NicknameRequest(
        String nickname
) {
    public static NicknameRequest of(final String nickname) {
        return new NicknameRequest(nickname);
    }
}

package org.sopt.seonyakServer.global.common.external.client.dto;


import org.sopt.seonyakServer.global.common.external.client.SocialType;

public record MemberInfoResponse(
        SocialType socialType,
        String socialId,
        String email
) {
    public static MemberInfoResponse of(
            final SocialType socialType,
            final String socialId,
            final String email
    ) {
        return new MemberInfoResponse(socialType, socialId, email);
    }
}

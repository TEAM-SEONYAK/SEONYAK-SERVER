package org.sopt.seonyakServer.global.common.external.client.service;

import org.sopt.seonyakServer.global.common.external.client.SocialType;
import org.sopt.seonyakServer.global.common.external.client.dto.MemberInfoResponse;
import org.sopt.seonyakServer.global.common.external.client.dto.MemberLoginRequest;

public interface SocialService {

    MemberInfoResponse login(
            final String authorizationToken,
            final MemberLoginRequest loginRequest
    );

    default MemberInfoResponse getLoginDto(
            final SocialType socialType,
            final String clientId,
            final String email
    ) {
        return MemberInfoResponse.of(socialType, clientId, email);
    }
}

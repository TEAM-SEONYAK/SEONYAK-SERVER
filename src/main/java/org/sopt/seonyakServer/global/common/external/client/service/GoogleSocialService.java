package org.sopt.seonyakServer.global.common.external.client.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.seonyakServer.domain.member.model.SocialType;
import org.sopt.seonyakServer.global.common.external.client.dto.GoogleUserInfoResponse;
import org.sopt.seonyakServer.global.common.external.client.dto.MemberInfoResponse;
import org.sopt.seonyakServer.global.common.external.client.dto.MemberLoginRequest;
import org.sopt.seonyakServer.global.common.external.client.google.GoogleAccessTokenClient;
import org.sopt.seonyakServer.global.common.external.client.google.GoogleUserClient;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleSocialService implements SocialService {

    @Getter
    private final SocialType socialType = SocialType.GOOGLE;

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${google.clientId}")
    private String clientId;

    @Value("${google.clientSecret}")
    private String clientSecret;

    private final GoogleAccessTokenClient googleAccessTokenClient;
    private final GoogleUserClient googleUserClient;

    @Transactional
    @Override
    public MemberInfoResponse login(
            final String authorizationCode,
            final MemberLoginRequest loginRequest
    ) {
        String accessToken;

//        try {
        // 인가 코드로 Access Token 받아오기
        accessToken = getOAuth2Authentication(authorizationCode, loginRequest.redirectUri());
//        } catch (FeignException e) {
//            throw new CustomException(ErrorType.EXPIRED_AUTHENTICATION_CODE);
//        }

        GoogleUserInfoResponse response = getGoogleUserInfo(accessToken);

        // Access Token으로 유저 정보 불러오기
        return getLoginDto(loginRequest.socialType(), response.id(), response.email());
    }

    private String getOAuth2Authentication(
            final String authorizationCode,
            final String redirectUri
    ) {
        return googleAccessTokenClient.getAccessToken(
                authorizationCode,
                clientId,
                clientSecret,
                redirectUri,
                GRANT_TYPE
        ).accessToken();
    }

    private GoogleUserInfoResponse getGoogleUserInfo(final String accessToken) {
        return googleUserClient.getGoogleUserInfo(accessToken);
    }
}

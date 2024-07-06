package org.sopt.seonyakServer.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.domain.member.dto.LoginSuccessResponse;
import org.sopt.seonyakServer.domain.member.model.Member;
import org.sopt.seonyakServer.domain.member.repository.MemberRepository;
import org.sopt.seonyakServer.global.auth.MemberAuthentication;
import org.sopt.seonyakServer.global.auth.jwt.JwtTokenProvider;
import org.sopt.seonyakServer.global.common.external.client.SocialType;
import org.sopt.seonyakServer.global.common.external.client.dto.MemberInfoResponse;
import org.sopt.seonyakServer.global.common.external.client.dto.MemberLoginRequest;
import org.sopt.seonyakServer.global.common.external.client.google.GoogleSocialService;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final GoogleSocialService googleSocialService;

    public LoginSuccessResponse create(
            final String authorizationCode,
            final MemberLoginRequest loginRequest
    ) {
        return getTokenDto(
                getMemberInfoResponse(authorizationCode, loginRequest)
        );
    }

    private LoginSuccessResponse getTokenDto(final MemberInfoResponse memberInfoResponse) {
        try {
            if (isExistingMember(memberInfoResponse.socialType(), memberInfoResponse.socialId())) {
                return getTokenByMemberId(
                        getMemberIdBySocialId(memberInfoResponse.socialType(), memberInfoResponse.socialId())
                );
            } else {
                Long id = createMember(memberInfoResponse);

                return getTokenByMemberId(id);
            }
        } catch (DataIntegrityViolationException e) { // DB 무결성 제약 조건 위반 예외
            return getTokenByMemberId(
                    getMemberIdBySocialId(memberInfoResponse.socialType(), memberInfoResponse.socialId())
            );
        }
    }

    public MemberInfoResponse getMemberInfoResponse(
            final String authorizationCode,
            final MemberLoginRequest loginRequest
    ) {
        if (loginRequest.socialType() == SocialType.GOOGLE) {
            return googleSocialService.login(authorizationCode, loginRequest);
        }
        throw new CustomException(ErrorType.INVALID_SOCIAL_TYPE_ERROR);
    }

    public boolean isExistingMember(
            final SocialType socialType,
            final String socialId
    ) {
        return memberRepository.findBySocialTypeAndSocialId(socialType, socialId).isPresent();
    }

    public Long getMemberIdBySocialId(
            final SocialType socialType,
            final String socialId
    ) {
        Member member = memberRepository.findBySocialTypeAndSocialId(socialType, socialId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_MEMBER_ERROR)
        );

        return member.getId();
    }

    @Transactional
    public Long createMember(final MemberInfoResponse memberInfoResponse) {
        Member member = Member.of(
                memberInfoResponse.socialType(),
                memberInfoResponse.socialId(),
                memberInfoResponse.email()
        );

        return memberRepository.save(member).getId();
    }

    public LoginSuccessResponse getTokenByMemberId(final Long id) {
        MemberAuthentication memberAuthentication = new MemberAuthentication(id, null, null);

        return LoginSuccessResponse.of(jwtTokenProvider.issueAccessToken(memberAuthentication));
    }
}

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
            if (isExistingMember(memberInfoResponse.socialId(), memberInfoResponse.socialType())) {
                return getTokenByMemberId(
                        getBySocialId(memberInfoResponse.socialId(), memberInfoResponse.socialType()).getId()
                );
            } else {
                Long id = createMember(memberInfoResponse);

                return getTokenByMemberId(id);
            }
        } catch (DataIntegrityViolationException e) { // DB 무결성 제약 조건 위반 예외
            return getTokenByMemberId(
                    getBySocialId(memberInfoResponse.socialId(), memberInfoResponse.socialType()).getId()
            );
        }
    }

    public MemberInfoResponse getMemberInfoResponse(
            final String authorizationCode,
            final MemberLoginRequest loginRequest
    ) {
        switch (loginRequest.socialType()) {
            case GOOGLE:
                return googleSocialService.login(authorizationCode, loginRequest);
            default:
                throw new CustomException(ErrorType.INVALID_SOCIAL_TYPE_ERROR);
        }
    }

    public boolean isExistingMember(
            final String socialId,
            final SocialType socialType
    ) {
        return memberRepository.findBySocialTypeAndSocialId(socialId, socialType).isPresent();
    }

    public Member getBySocialId(
            final String socialId,
            final SocialType socialType
    ) {
        Member member = memberRepository.findBySocialTypeAndSocialId(socialId, socialType).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_MEMBER_ERROR)
        );

        return member;
    }

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

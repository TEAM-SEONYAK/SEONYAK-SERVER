package org.sopt.seonyakServer.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.domain.member.dto.LoginSuccessResponse;
import org.sopt.seonyakServer.domain.member.dto.MemberJoinRequest;
import org.sopt.seonyakServer.domain.member.dto.MemberJoinResponse;
import org.sopt.seonyakServer.domain.member.dto.NicknameRequest;
import org.sopt.seonyakServer.domain.member.model.Member;
import org.sopt.seonyakServer.domain.member.model.SocialType;
import org.sopt.seonyakServer.domain.member.repository.MemberRepository;
import org.sopt.seonyakServer.domain.senior.service.SeniorService;
import org.sopt.seonyakServer.global.auth.MemberAuthentication;
import org.sopt.seonyakServer.global.auth.PrincipalHandler;
import org.sopt.seonyakServer.global.auth.jwt.JwtTokenProvider;
import org.sopt.seonyakServer.global.common.external.client.dto.MemberInfoResponse;
import org.sopt.seonyakServer.global.common.external.client.dto.MemberLoginRequest;
import org.sopt.seonyakServer.global.common.external.client.service.GoogleSocialService;
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
    private final PrincipalHandler principalHandler;
    private final GoogleSocialService googleSocialService;
    private final MemberManagementService memberManagementService;
    private final SeniorService seniorService;

    private static final String NICKNAME_PATTERN = "^[a-zA-Z0-9가-힣]{2,8}$";

    // JWT Access Token 생성
    public LoginSuccessResponse create(
            final String authorizationCode,
            final MemberLoginRequest loginRequest
    ) {
        return getTokenDto(
                getMemberInfoResponse(authorizationCode, loginRequest)
        );
    }

    // 소셜 플랫폼으로부터 해당 유저 정보를 받아옴
    public MemberInfoResponse getMemberInfoResponse(
            final String authorizationCode,
            final MemberLoginRequest loginRequest
    ) {
        if (loginRequest.socialType() == SocialType.GOOGLE) {
            return googleSocialService.login(authorizationCode, loginRequest);
        }
        throw new CustomException(ErrorType.INVALID_SOCIAL_TYPE_ERROR);
    }

    // Access Token을 생성할 때, 해당 유저의 회원가입 여부를 판단
    private LoginSuccessResponse getTokenDto(final MemberInfoResponse memberInfoResponse) {
        try {
            if (isExistingMember(memberInfoResponse.socialType(), memberInfoResponse.socialId())) {
                return getTokenByMemberId(
                        getMemberIdBySocialId(memberInfoResponse.socialType(), memberInfoResponse.socialId())
                );
            } else {
                Long id = memberManagementService.createMember(memberInfoResponse);

                return getTokenByMemberId(id);
            }
        } catch (DataIntegrityViolationException e) { // DB 무결성 제약 조건 위반 예외
            return getTokenByMemberId(
                    getMemberIdBySocialId(memberInfoResponse.socialType(), memberInfoResponse.socialId())
            );
        }
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

    public LoginSuccessResponse getTokenByMemberId(final Long id) {
        MemberAuthentication memberAuthentication = new MemberAuthentication(id, null, null);

        return LoginSuccessResponse.of(jwtTokenProvider.issueAccessToken(memberAuthentication));
    }

    // 닉네임 유효성 검증
    public void validNickname(final NicknameRequest nicknameRequest) {
        if (!nicknameRequest.nickname().matches(NICKNAME_PATTERN)) { // 형식 체크
            throw new CustomException(ErrorType.INVALID_NICKNAME_ERROR);
        }

        if (memberRepository.existsByNickname(nicknameRequest.nickname())) { // 중복 체크
            throw new CustomException(ErrorType.NICKNAME_DUP_ERROR);
        }
    }

    @Transactional
    public MemberJoinResponse patchMemberJoin(MemberJoinRequest memberJoinRequest) {
        Member member = memberRepository.findMemberByIdOrThrow(principalHandler.getUserIdFromPrincipal());

        System.out.println("Department List: " + memberJoinRequest.departmentList()); // 로그 추가

        member.updateMember(
                memberJoinRequest.isSubscribed(),
                memberJoinRequest.nickname(),
                memberJoinRequest.image(),
                memberJoinRequest.phoneNumber(),
                memberJoinRequest.univName(),
                memberJoinRequest.field(),
                memberJoinRequest.departmentList()
        );

        memberRepository.save(member);

        if (memberJoinRequest.role().equals("SENIOR")) {
            return MemberJoinResponse.of(seniorService.createSenior(memberJoinRequest, member));
        }

        return MemberJoinResponse.of(memberJoinRequest.role());
    }
}

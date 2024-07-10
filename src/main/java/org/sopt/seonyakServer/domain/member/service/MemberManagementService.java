package org.sopt.seonyakServer.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.domain.member.model.Member;
import org.sopt.seonyakServer.domain.member.repository.MemberRepository;
import org.sopt.seonyakServer.global.common.external.client.dto.MemberInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberManagementService {
    // 트랜잭션 작업을 관리할 서비스 클래스 (자기 호출 방지 및 단일 책임 원칙 준수)

    private final MemberRepository memberRepository;

    @Transactional
    public Long createMember(final MemberInfoResponse memberInfoResponse) {
        Member member = Member.createMember(
                memberInfoResponse.socialType(),
                memberInfoResponse.socialId(),
                memberInfoResponse.email()
        );

        return memberRepository.save(member).getId();
    }
}

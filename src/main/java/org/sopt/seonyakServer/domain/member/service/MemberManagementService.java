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

    private final MemberRepository memberRepository;

    @Transactional
    public Long createMember(final MemberInfoResponse memberInfoResponse) {
        Member member = Member.of(
                memberInfoResponse.socialType(),
                memberInfoResponse.socialId(),
                memberInfoResponse.email()
        );

        return memberRepository.save(member).getId();
    }
}

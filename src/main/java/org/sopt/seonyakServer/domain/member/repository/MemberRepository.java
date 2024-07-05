package org.sopt.seonyakServer.domain.member.repository;

import java.util.Optional;
import org.sopt.seonyakServer.domain.member.model.Member;
import org.sopt.seonyakServer.global.common.external.client.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}

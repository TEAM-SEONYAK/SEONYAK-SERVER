package org.sopt.seonyakServer.domain.member.repository;

import java.util.Optional;
import org.sopt.seonyakServer.domain.member.model.Member;
import org.sopt.seonyakServer.global.common.external.client.SocialType;

public interface MemberRepositoryCustom {

    Optional<Member> findBySocialTypeAndSocialId(
            final String socialId,
            final SocialType socialType
    );
}

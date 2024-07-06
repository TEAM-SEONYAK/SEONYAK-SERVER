package org.sopt.seonyakServer.domain.member.repository;

import static org.sopt.seonyakServer.domain.member.model.QMember.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.domain.member.model.Member;
import org.sopt.seonyakServer.global.common.external.client.SocialType;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Member> findBySocialTypeAndSocialId(
            final String socialId,
            final SocialType socialType
    ) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(member)
                        .where(
                                member.socialId.eq(socialId),
                                member.socialType.eq(socialType)
                        )
                        .fetchOne()
        );
    }
}

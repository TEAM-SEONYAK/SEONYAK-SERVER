package org.sopt.seonyakServer.domain.member.repository;

import java.util.Optional;
import org.sopt.seonyakServer.domain.member.model.Member;
import org.sopt.seonyakServer.domain.member.model.SocialType;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Optional<Member> findMemberById(Long id);

    boolean existsByNickname(String nickname);

    boolean existsByPhoneNumber(String phoneNumber);

    default Member findMemberByIdOrThrow(Long id) {
        return findMemberById(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_MEMBER_ERROR));
    }
}

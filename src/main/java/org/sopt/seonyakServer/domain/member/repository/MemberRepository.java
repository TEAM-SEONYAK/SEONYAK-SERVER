package org.sopt.seonyakServer.domain.member.repository;

import java.time.LocalDateTime;
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

    // phoneNumber가 null이고 updatedAt 시간이 time만큼 보다 더 이전인 모든 Member 엔티티를 삭제
    void deleteByPhoneNumberIsNullAndUpdatedAtBefore(LocalDateTime time);
}

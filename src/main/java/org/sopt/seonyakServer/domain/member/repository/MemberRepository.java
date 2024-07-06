package org.sopt.seonyakServer.domain.member.repository;

import org.sopt.seonyakServer.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
}

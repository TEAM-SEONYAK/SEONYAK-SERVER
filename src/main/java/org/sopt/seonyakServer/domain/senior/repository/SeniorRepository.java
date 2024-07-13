package org.sopt.seonyakServer.domain.senior.repository;

import java.util.Optional;
import org.sopt.seonyakServer.domain.senior.model.Senior;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeniorRepository extends JpaRepository<Senior, Long> {

    Optional<Senior> findSeniorById(Long id);

    Optional<Senior> findSeniorByMemberId(Long id);

    default Senior findSeniorByIdOrThrow(Long id) {
        return findSeniorById(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_SENIOR_ERROR));
    }
}

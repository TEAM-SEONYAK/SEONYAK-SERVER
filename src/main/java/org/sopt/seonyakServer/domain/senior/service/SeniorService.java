package org.sopt.seonyakServer.domain.senior.service;

import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.domain.senior.dto.SeniorProfileRequest;
import org.sopt.seonyakServer.domain.senior.model.Senior;
import org.sopt.seonyakServer.domain.senior.repository.SeniorRepository;
import org.sopt.seonyakServer.global.auth.PrincipalHandler;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SeniorService {

    private final SeniorRepository seniorRepository;
    private final PrincipalHandler principalHandler;

    public void patchSeniorProfile(SeniorProfileRequest seniorProfileRequest) {
        Senior senior = seniorRepository.findSeniorByMemberId(principalHandler.getUserIdFromPrincipal())
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_MEMBER_ERROR));

        senior.updateSenior(
                seniorProfileRequest.catchphrase(),
                seniorProfileRequest.career(),
                seniorProfileRequest.award(),
                seniorProfileRequest.story(),
                seniorProfileRequest.preferredTimeList()
        );
        seniorRepository.save(senior);
    }
}

package org.sopt.seonyakServer.global.auth.redis.service;

import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.global.auth.redis.domain.Code;
import org.sopt.seonyakServer.global.auth.redis.repository.CodeRepository;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CodeService {

    private final CodeRepository codeRepository;

    @Transactional
    public void saveCertificationCode(
            final String phoneNumber,
            final String certificationCode
    ) {
        codeRepository.save(
                Code.createCode(
                        phoneNumber,
                        certificationCode
                )
        );
    }

    public String findCodeByPhoneNumber(final String phoneNumber) {
        Code code = codeRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new CustomException(ErrorType.NO_VERIFICATION_REQUEST_HISTORY)
        );

        return code.getCertificationCode();
    }

    @Transactional
    public void deleteCertificationCode(final String phoneNumber) {
        Code code = codeRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new CustomException(ErrorType.NO_VERIFICATION_REQUEST_HISTORY)
        );

        codeRepository.delete(code);
    }
}

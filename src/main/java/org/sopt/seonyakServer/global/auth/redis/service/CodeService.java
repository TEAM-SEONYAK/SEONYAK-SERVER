package org.sopt.seonyakServer.global.auth.redis.service;

import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.global.auth.redis.domain.Code;
import org.sopt.seonyakServer.global.auth.redis.repository.CodeRepository;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeService {

    private final CodeRepository codeRepository;

    public void saveVerificationCode(
            final String phoneNumber,
            final String verificationCode
    ) {
        codeRepository.save(
                Code.builder()
                        .phoneNumber(phoneNumber)
                        .verificationCode(verificationCode)
                        .build()
        );
    }

    public String findCodeByPhoneNumber(final String phoneNumber) {
        Code code = codeRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new CustomException(ErrorType.NO_VERIFICATION_REQUEST_HISTORY)
        );

        return code.getVerificationCode();
    }
    
    public void deleteVerificationCode(final String phoneNumber) {
        Code code = codeRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new CustomException(ErrorType.NO_VERIFICATION_REQUEST_HISTORY)
        );

        codeRepository.delete(code);
    }
}

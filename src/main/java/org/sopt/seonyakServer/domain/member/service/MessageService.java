package org.sopt.seonyakServer.domain.member.service;

import jakarta.annotation.PostConstruct;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.sopt.seonyakServer.domain.member.dto.SendCodeRequest;
import org.sopt.seonyakServer.domain.member.dto.VerifyCodeRequest;
import org.sopt.seonyakServer.domain.member.repository.MemberRepository;
import org.sopt.seonyakServer.global.auth.redis.service.CodeService;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecret;

    @Value("${coolsms.fromNumber}")
    private String fromNumber;

    private final MemberRepository memberRepository;
    private DefaultMessageService defaultMessageService;
    private final CodeService codeService;

    private static final String PHONE_NUMBER_PATTERN = "^010\\d{8}$";

    @PostConstruct
    public void init() {
        this.defaultMessageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    public void sendMessage(SendCodeRequest sendCodeRequest) {
        Message message = new Message();

        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 함.
        String toNumber = sendCodeRequest.phoneNumber().replaceAll("-", "");

        if (!toNumber.matches(PHONE_NUMBER_PATTERN)) {
            throw new CustomException(ErrorType.INVALID_PHONE_NUMBER_ERROR);
        }

        message.setFrom(fromNumber);
        message.setTo(toNumber);

        String verificationCode = generateRandomNumber(4);
        message.setText("[선약] 인증번호는 [" + verificationCode + "] 입니다.");

        this.defaultMessageService.sendOne(new SingleMessageSendingRequest(message));
        codeService.saveCertificationCode(toNumber, verificationCode);
    }

    // 인증번호를 위한 랜덤 숫자 생성
    private String generateRandomNumber(int digitCount) {
        Random random = new Random();
        int min = (int) Math.pow(10, digitCount - 1);
        int max = (int) Math.pow(10, digitCount) - 1;

        return String.valueOf(random.nextInt((max - min) + 1) + min);
    }

    // 인증번호 일치 여부 확인
    public void verifyCode(VerifyCodeRequest verifyCodeRequest) {
        if (verifyCodeRequest.verificationCode().equals(
                codeService.findCodeByPhoneNumber(verifyCodeRequest.phoneNumber()))) {
            codeService.deleteCertificationCode(verifyCodeRequest.phoneNumber());
        } else {
            throw new CustomException(ErrorType.INVALID_VERIFICATION_CODE_ERROR);
        }
    }

    public void validPhoneNumberDuplication(VerifyCodeRequest verifyCodeRequest) {
        if (memberRepository.existsByPhoneNumber(verifyCodeRequest.phoneNumber())) {
            throw new CustomException(ErrorType.PHONE_NUMBER_DUP_ERROR);
        }
    }
}

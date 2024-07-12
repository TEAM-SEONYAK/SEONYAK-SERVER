package org.sopt.seonyakServer.domain.member.service;

import jakarta.annotation.PostConstruct;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.sopt.seonyakServer.domain.member.dto.CertifyCodeRequest;
import org.sopt.seonyakServer.domain.member.dto.SendMessageRequest;
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

    private DefaultMessageService defaultMessageService;
    private final CodeService codeService;

    @PostConstruct
    public void init() {
        this.defaultMessageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    public void sendMessage(SendMessageRequest sendMessageRequest) {
        Message message = new Message();

        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 함.
        String toNumber = sendMessageRequest.phoneNumber().replaceAll("-", "");

        message.setFrom(fromNumber);
        message.setTo(toNumber);

        String certificationCode = generateRandomNumber(4);
        message.setText("[선약]의 문자 테스트 간다 간다 뿅간다 슝 ~ 인증번호: " + certificationCode);

        this.defaultMessageService.sendOne(new SingleMessageSendingRequest(message));
        codeService.saveCertificationCode(toNumber, certificationCode);
    }

    // 인증번호를 위한 랜덤 숫자 생성
    private String generateRandomNumber(int digitCount) {
        Random random = new Random();
        int min = (int) Math.pow(10, digitCount - 1);
        int max = (int) Math.pow(10, digitCount) - 1;

        return String.valueOf(random.nextInt((max - min) + 1) + min);
    }

    // 인증번호 일치 여부 확인
    public void certifyCode(CertifyCodeRequest certifyCodeRequest) {
        if (certifyCodeRequest.certificationCode().equals(
                codeService.findCodeByPhoneNumber(certifyCodeRequest.phoneNumber()))) {
            codeService.deleteCertificationCode(certifyCodeRequest.phoneNumber());
        } else {
            throw new CustomException(ErrorType.INVALID_CERTIFICATION_CODE_ERROR);
        }
    }
}

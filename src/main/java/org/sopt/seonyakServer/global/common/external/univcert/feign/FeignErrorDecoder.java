package org.sopt.seonyakServer.global.common.external.univcert.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        // 400에러가 내려오는 경우
        if (response.status() == 400) {
            try (InputStream bodyStream = response.body().asInputStream()) {
                String body = new BufferedReader(new InputStreamReader(bodyStream, StandardCharsets.UTF_8))
                        .lines().collect(Collectors.joining("\n"));

                // 응답결과 JSON 파싱
                JSONObject jsonObject = new JSONObject(body);
                String message = jsonObject.getString("message");
                System.out.println(body);
                // 응답 메시지를 기준으로 분기처리
                if (message.equals("대학과 일치하지 않는 메일 도메인입니다.")) {
                    throw new CustomException(ErrorType.INVALID_EMAIL_DOMAIN_ERROR);
                }
                if (message.equals("이미 완료된 요청입니다.")) {
                    throw new CustomException(ErrorType.UNIV_CERT_REQUEST_ERROR);
                }
                if (message.equals("인증 요청 이력이 존재하지 않습니다.")) {
                    throw new CustomException(ErrorType.UNIV_CERT_VERIFY_ERROR);
                }
                throw new CustomException(ErrorType.INTERNAL_SERVER_ERROR);
            } catch (IOException e) {
                throw new CustomException(ErrorType.INTERNAL_SERVER_ERROR);
            }
        } else {
            // 다른 상태 코드에 대한 처리를 추가합니다.
            return new CustomException(ErrorType.INTERNAL_FEIGN_ERROR);
        }
    }
}

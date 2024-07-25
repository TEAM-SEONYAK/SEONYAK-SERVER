package org.sopt.seonyakServer.global.common.external.univcert.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import feign.codec.StringDecoder;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    private StringDecoder stringDecoder = new StringDecoder();

    @Override
    public Exception decode(String methodKey, Response response) {

        String message;
        String error;

        if (response.body() != null) {
            try {
                String body = (String) stringDecoder.decode(response, String.class);

                log.debug(body);

                // 응답결과 JSON 파싱
                JSONObject jsonObject = new JSONObject(body);
                message = jsonObject.optString("message", "message 필드가 존재하지 않습니다.");
                error = jsonObject.optString("error", "error 필드가 존재하지 않습니다.");
            } catch (IOException | JSONException e) {
                log.error(methodKey + "Feign 요청이 실패한 후 받은 Response Body를 객체로 변환하는 과정에서 오류가 발생했습니다.", e);
                throw new CustomException(ErrorType.INTERNAL_FEIGN_ERROR);
            }
        } else {
            throw new CustomException(ErrorType.NO_RESPONSE_BODY_ERROR);
        }

        // 응답 메시지를 기준으로 분기처리
        if (message.equals("대학과 일치하지 않는 메일 도메인입니다.")) {
            throw new CustomException(ErrorType.INVALID_EMAIL_DOMAIN_ERROR);
        }
        if (message.equals("이미 완료된 요청입니다.")) {
            throw new CustomException(ErrorType.UNIV_CERT_REQUEST_ERROR);
        }
        if (message.equals("인증 요청 이력이 존재하지 않습니다.")) {
            throw new CustomException(ErrorType.NO_VERIFICATION_REQUEST_HISTORY);
        }

        if (error.equals("redirect_uri_mismatch")) {
            throw new CustomException(ErrorType.REDIRECT_URI_MISMATCH_ERROR);
        }
        if (error.equals("invalid_grant")) {
            throw new CustomException(ErrorType.EXPIRED_AUTHENTICATION_CODE);
        }

        log.error(String.valueOf(response.status()));
        log.error(message);
        log.error(error);
        log.error(String.valueOf(response.headers()));

        throw new CustomException(ErrorType.INTERNAL_SERVER_ERROR);
    }
}

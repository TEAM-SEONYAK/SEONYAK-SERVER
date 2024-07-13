package org.sopt.seonyakServer.global.auth.redis.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "VerificationCode", timeToLive = 5 * 60L) // TTL 5분
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Code {

    @Id
    private String phoneNumber;

    private String verificationCode;

    @Builder(access = AccessLevel.PRIVATE)
    private Code(
            final String phoneNumber,
            final String verificationCode
    ) {
        this.phoneNumber = phoneNumber;
        this.verificationCode = verificationCode;
    }

    public static Code createCode(
            final String phoneNumber,
            final String verificationCode
    ) {
        return Code.builder()
                .phoneNumber(phoneNumber)
                .verificationCode(verificationCode)
                .build();
    }
}

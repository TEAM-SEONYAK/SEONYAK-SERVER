package org.sopt.seonyakServer.global.auth.redis.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "Code", timeToLive = 5 * 60L) // TTL 5분
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Code {

    @Id
    private String phoneNumber;

    private String certificationCode;

    @Builder(access = AccessLevel.PRIVATE)
    private Code(
            final String phoneNumber,
            final String certificationCode
    ) {
        this.phoneNumber = phoneNumber;
        this.certificationCode = certificationCode;
    }

    public static Code createCode(
            final String phoneNumber,
            final String certificationCode
    ) {
        return Code.builder()
                .phoneNumber(phoneNumber)
                .certificationCode(certificationCode)
                .build();
    }
}

package org.sopt.seonyakServer.global.common.external.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SocialType {

    GOOGLE("GOOGLE"),
    ;

    private final String socialType;
}

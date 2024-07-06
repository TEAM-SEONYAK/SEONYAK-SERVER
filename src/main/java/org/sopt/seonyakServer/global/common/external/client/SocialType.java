package org.sopt.seonyakServer.global.common.external.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialType {

    GOOGLE("GOOGLE"),
    ;

    private final String socialType;
}

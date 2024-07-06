package org.sopt.seonyakServer.domain.member.model;

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

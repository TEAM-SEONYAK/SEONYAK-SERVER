package org.sopt.seonyakServer.global.auth;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class MemberAuthentication extends UsernamePasswordAuthenticationToken {

    // 사용자 인증 객체 생성
    public MemberAuthentication(Object principal,
                                Object credentials,
                                Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}

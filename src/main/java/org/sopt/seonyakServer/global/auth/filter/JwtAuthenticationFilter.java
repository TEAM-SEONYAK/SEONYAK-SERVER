package org.sopt.seonyakServer.global.auth.filter;

import static org.sopt.seonyakServer.global.auth.jwt.JwtValidationType.VALID_JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.global.auth.MemberAuthentication;
import org.sopt.seonyakServer.global.auth.jwt.JwtTokenProvider;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final String token = getJwtFromRequest(request);

            if (jwtTokenProvider.validateToken(token) == VALID_JWT) {
                Long memberId = jwtTokenProvider.getMemberFromJwt(token);

                // authentication 객체 생성 -> principal에 유저정보를 담는다.
                MemberAuthentication authentication = new MemberAuthentication(memberId.toString(), null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception exception) {
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        } else if (StringUtils.hasText(bearerToken) && !bearerToken.startsWith("Bearer ")) {
            throw new CustomException(ErrorType.INVALID_JWT_SIGNATURE);
        }

        return null;
    }
}

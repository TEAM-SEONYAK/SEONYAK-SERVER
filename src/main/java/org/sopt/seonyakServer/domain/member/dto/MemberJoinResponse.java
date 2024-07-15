package org.sopt.seonyakServer.domain.member.dto;

public record MemberJoinResponse(
        Long seniorId,
        String role
) {
    public static MemberJoinResponse of(
            final Long seniorId,
            final String role
    ) {
        return new MemberJoinResponse(
                seniorId,
                role
        );
    }
}

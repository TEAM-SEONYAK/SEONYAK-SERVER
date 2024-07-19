package org.sopt.seonyakServer.domain.senior.dto;

public record SeniorCardProfileResponse(
        String nickname,
        String company,
        String field,
        String position,
        String detailPosition,
        String level,
        String image
) {
    public static SeniorCardProfileResponse of(
            final String nickname,
            final String company,
            final String field,
            final String position,
            final String detailPosition,
            final String level,
            final String image
    ) {
        return new SeniorCardProfileResponse(
                nickname,
                company,
                field,
                position,
                detailPosition,
                level,
                image
        );
    }
}

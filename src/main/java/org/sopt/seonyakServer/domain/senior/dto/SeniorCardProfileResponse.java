package org.sopt.seonyakServer.domain.senior.dto;

public record SeniorCardProfileResponse(
        String nickname,
        String company,
        String filed,
        String position,
        String detailPosition,
        String level
) {
    public static SeniorCardProfileResponse of(
            final String nickname,
            final String company,
            final String filed,
            final String position,
            final String detailPosition,
            final String level
    ) {
        return new SeniorCardProfileResponse(
                nickname,
                company,
                filed,
                position,
                detailPosition,
                level
        );
    }
}

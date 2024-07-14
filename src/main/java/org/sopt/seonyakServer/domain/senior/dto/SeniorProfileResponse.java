package org.sopt.seonyakServer.domain.senior.dto;

public record SeniorProfileResponse(
        String level,
        String career,
        String award,
        String catchphrase,
        String story
) {
    public static SeniorProfileResponse of(
            final String level,
            final String career,
            final String award,
            final String catchphrase,
            final String story
    ) {
        return new SeniorProfileResponse(
                level,
                career,
                award,
                catchphrase,
                story
        );
    }
}

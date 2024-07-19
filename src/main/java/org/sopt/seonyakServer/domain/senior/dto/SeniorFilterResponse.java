package org.sopt.seonyakServer.domain.senior.dto;

import java.util.List;

public record SeniorFilterResponse(
        String myNickname,
        List<SeniorListResponse> seniorList
) {
    public static SeniorFilterResponse of(
            final String myNickname,
            final List<SeniorListResponse> seniorList
    ) {
        return new SeniorFilterResponse(
                myNickname,
                seniorList
        );
    }
}

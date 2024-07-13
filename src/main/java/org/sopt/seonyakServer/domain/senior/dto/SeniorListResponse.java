package org.sopt.seonyakServer.domain.senior.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
// QueryDsl이 Record 클래스 인식을 못해서 사용하지 않음
public class SeniorListResponse {
    private Long seniorId;
    private String nickname;
    private String company;
    private String position;
    private String detailPosition;
    private String field;
    private String level;

    public static SeniorListResponse of(
            final Long seniorId,
            final String nickname,
            final String company,
            final String position,
            final String detailPosition,
            final String field,
            final String level) {
        return new SeniorListResponse(
                seniorId,
                nickname,
                company,
                position,
                detailPosition,
                field,
                level);
    }
}

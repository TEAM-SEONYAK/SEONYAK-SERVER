package org.sopt.seonyakServer.domain.appointment.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeniorInfo {

    private String nickname;
    private String image;
    private String company;
    private String field;
    private String position;
    private String detailPosition;
    private String level;

    @Builder(access = AccessLevel.PRIVATE)
    private SeniorInfo(
            String nickname,
            String image,
            String company,
            String field,
            String position,
            String detailPosition,
            String level
    ) {
        this.nickname = nickname;
        this.image = image;
        this.company = company;
        this.field = field;
        this.position = position;
        this.detailPosition = detailPosition;
        this.level = level;
    }

    public static SeniorInfo create(
            String nickname,
            String image,
            String company,
            String field,
            String position,
            String detailPosition,
            String level
    ) {
        return SeniorInfo.builder()
                .nickname(nickname)
                .image(image)
                .company(company)
                .field(field)
                .position(position)
                .detailPosition(detailPosition)
                .level(level)
                .build();
    }
}

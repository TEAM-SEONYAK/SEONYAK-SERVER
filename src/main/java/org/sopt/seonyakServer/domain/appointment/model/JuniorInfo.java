package org.sopt.seonyakServer.domain.appointment.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JuniorInfo {

    private String nickname;
    private String univName;
    private String field;
    private String department;

    @Builder(access = AccessLevel.PRIVATE)
    private JuniorInfo(
            String nickname,
            String univName,
            String field,
            String department
    ) {
        this.nickname = nickname;
        this.univName = univName;
        this.field = field;
        this.department = department;
    }

    public static JuniorInfo create(
            String nickname,
            String univName,
            String field,
            String department
    ) {
        return JuniorInfo.builder()
                .nickname(nickname)
                .univName(univName)
                .field(field)
                .department(department)
                .build();
    }
}

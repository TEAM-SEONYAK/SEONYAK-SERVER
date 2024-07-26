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

    @Builder
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
}

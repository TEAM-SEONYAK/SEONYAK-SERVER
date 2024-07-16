package org.sopt.seonyakServer.domain.senior.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeRange {
    private String startTime;
    private String endTime;
}

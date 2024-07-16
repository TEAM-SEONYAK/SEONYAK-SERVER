package org.sopt.seonyakServer.domain.senior.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TimeRange {
    private String startTime;
    private String endTime;
}

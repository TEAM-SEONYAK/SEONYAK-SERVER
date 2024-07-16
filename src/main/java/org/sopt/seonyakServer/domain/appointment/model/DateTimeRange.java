package org.sopt.seonyakServer.domain.appointment.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class DateTimeRange {
    private String date;
    private String startTime;
    private String endTime;
}

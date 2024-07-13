package org.sopt.seonyakServer.domain.appointment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataTimeRange {
    private String date;
    private String startTime;
    private String endTime;
}

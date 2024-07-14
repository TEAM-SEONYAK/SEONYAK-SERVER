package org.sopt.seonyakServer.domain.appointment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import org.sopt.seonyakServer.domain.appointment.model.DataTimeRange;
import org.sopt.seonyakServer.domain.appointment.model.JuniorInfo;
import org.sopt.seonyakServer.domain.appointment.model.SeniorInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AppointmentDetailResponse(
        JuniorInfo juniorInfo,
        SeniorInfo seniorInfo,
        List<String> topic,
        String personalTopic,
        List<DataTimeRange> timeList
) {
    public static AppointmentDetailResponse of(
            JuniorInfo juniorInfo,
            SeniorInfo seniorInfo,
            List<String> topic,
            String personalTopic,
            List<DataTimeRange> timeList
    ) {
        return new AppointmentDetailResponse(
                juniorInfo,
                seniorInfo,
                topic,
                personalTopic,
                timeList
        );
    }
}

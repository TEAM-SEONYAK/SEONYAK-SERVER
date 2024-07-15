package org.sopt.seonyakServer.domain.appointment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import org.sopt.seonyakServer.domain.appointment.model.AppointmentStatus;
import org.sopt.seonyakServer.domain.appointment.model.DataTimeRange;
import org.sopt.seonyakServer.domain.appointment.model.JuniorInfo;
import org.sopt.seonyakServer.domain.appointment.model.SeniorInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AppointmentDetailResponse(
        AppointmentStatus appointmentStatus,
        JuniorInfo juniorInfo,
        SeniorInfo seniorInfo,
        List<String> topic,
        String personalTopic,
        List<DataTimeRange> timeList
) {
    public static AppointmentDetailResponse of(
            AppointmentStatus appointmentStatus,
            JuniorInfo juniorInfo,
            SeniorInfo seniorInfo,
            List<String> topic,
            String personalTopic,
            List<DataTimeRange> timeList
    ) {
        return new AppointmentDetailResponse(
                appointmentStatus,
                juniorInfo,
                seniorInfo,
                topic,
                personalTopic,
                timeList
        );
    }
}

package org.sopt.seonyakServer.domain.appointment.dto;

import java.util.List;
import org.sopt.seonyakServer.domain.appointment.model.DateTimeRange;

public record AppointmentRequest(
        Long seniorId,
        List<String> topic,
        String personalTopic,
        List<DateTimeRange> timeList
) {
}

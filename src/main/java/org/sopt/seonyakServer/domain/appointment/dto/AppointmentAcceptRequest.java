package org.sopt.seonyakServer.domain.appointment.dto;

import java.util.List;
import org.sopt.seonyakServer.domain.appointment.model.DataTimeRange;

public record AppointmentAcceptRequest(
        Long appointmentId,
        String googleMeetLink,
        List<DataTimeRange> timeList
) {
}
